package mojichimera.augments.rare;

import CardAugments.cardmods.AbstractAugment;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.Iterator;

public class AccursedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(AccursedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int COST = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public boolean atBattleStartPreDraw(AbstractCard card) {
        if (AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return false;
        }

        Iterator cardIterator = AbstractDungeon.player.masterDeck.group.iterator();
        int curses = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE) {
                curses++;
            }
        }

        if (curses > 0) {
            card.cost -= COST * curses;
            if (card.cost < 0) {
                card.cost = 0;
            }
            card.costForTurn = card.cost;
            card.isCostModified = true;
        }

        return curses > 0;
    }

    @Override
    public void onCreatedMidCombat(AbstractCard card) {
        atBattleStartPreDraw(card);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost > 0 && hasACurse() && cardCheck(card, c -> doesntUpgradeCost());
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0]));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new AccursedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
