package mojichimera.packmaster.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.helpers.CardModifierManager;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import javax.smartcardio.Card;

public class EchoMod extends AbstractAugment {
    public static final String ID = mojichimera.makePackmasterID(EchoMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final String REF_MOD_ID = "anniv5:EcholMod";

    @Override
    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.addModifier(card, new thePackmaster.cardmodifiers.energyandechopack.EchoMod());
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost != -2 && !CardModifierManager.hasModifier(card, REF_MOD_ID);
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new EchoMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
