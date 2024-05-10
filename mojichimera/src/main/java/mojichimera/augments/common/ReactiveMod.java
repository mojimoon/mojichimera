package mojichimera.augments.common;

import CardAugments.cardmods.AbstractAugment;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.actions.ModifyMagicAction;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ReactiveMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ReactiveMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
//        card.selfRetain = true;
        CardModifierManager.addModifier(card, new RetainMod());
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasMagic(card)
                && AugmentHelper.isRetainValid(card)
                && AugmentHelper.isNormal(card);
    }

    @Override
    public void onRetained(AbstractCard card) {
        addToBot((AbstractGameAction) new ModifyMagicAction(card.uuid, EFFECT));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
//        if (rawDescription.contains(CARD_TEXT[0])) {
            return insertAfterText(rawDescription, String.format(CARD_TEXT[1], EFFECT));
//        }
//        return insertAfterText(insertBeforeText(rawDescription, CARD_TEXT[0]), String.format(CARD_TEXT[1], EFFECT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ReactiveMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
