package mojichimera.augments.rare;

import CardAugments.cardmods.AbstractAugment;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class SozuMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(SozuMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int ENERGY = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost -= 2;
        if (card.cost < 0)
            card.cost = 0;
        card.costForTurn = card.cost;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.cost > 1 && cardCheck(card, c -> doesntUpgradeCost()));
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        int potions = 0;
        for (AbstractPotion po : AbstractDungeon.player.potions) {
            if (!(po instanceof com.megacrit.cardcrawl.potions.PotionSlot)) {
                potions++;
            }
        }
        if (potions > 0) {
            addToBot(new LoseEnergyAction(ENERGY * potions));
        }
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new SozuMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
