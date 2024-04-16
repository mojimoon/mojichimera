package mojichimera.augments;

import CardAugments.cardmods.AbstractAugment;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class SkimMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(SkimMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int DRAW = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.cost != -2);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot((AbstractGameAction)new DrawCardAction(DRAW));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], DRAW));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new SkimMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
