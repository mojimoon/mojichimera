package MojiMod.augments;

import CardAugments.cardmods.AbstractAugment;
import MojiMod.MojiMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RentedMod extends AbstractAugment {
    public static final String ID = MojiMod.makeID(RentedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int LOSS = 2;
    public void onInitialApplication(AbstractCard card) {
        card.cost--;
        if (card.cost < 0)
            card.cost = 0;
        card.costForTurn = card.cost;
    }

    public boolean validCard(AbstractCard card) {
        return (card.cost > 0 && cardCheck(card, c -> doesntUpgradeCost()));
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
//        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RunicPower((AbstractCreature)AbstractDungeon.player, 2), 2));
        AbstractDungeon.player.loseGold(LOSS);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], LOSS));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() {
        return AbstractAugment.AugmentRarity.RARE;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return (AbstractCardModifier)new RentedMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
