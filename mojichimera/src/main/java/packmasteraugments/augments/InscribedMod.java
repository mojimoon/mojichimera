package packmasteraugments.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class InscribedMod extends AbstractAugment {
    public static final String ID = mojichimera.makePackmasterID(InscribedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final String INSCRIBED_MODID = "anniv5:Inscribed";

    @Override
    public void onInitialApplication(AbstractCard card) {
//         CardModifierManager.addModifier(card, new thePackmaster.cardmodifiers.InscribedMod(true, true));
        try {
            Class<?> clazz = Class.forName("thePackmaster.cardmodifiers.InscribedMod");
            CardModifierManager.addModifier(card, (AbstractCardModifier)clazz.getConstructor(boolean.class, boolean.class).newInstance(true, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return allowOrbMods()
                && AugmentHelper.isPlayable(card)
                && !CardModifierManager.hasModifier(card, INSCRIBED_MODID);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new InscribedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
