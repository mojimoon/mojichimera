package mojichimera.packmaster.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.helpers.CardModifierManager;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class InscribedMod extends AbstractAugment {
    public static final String ID = mojichimera.makePackmasterID(InscribedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final String REF_MOD_ID = "anniv5:Inscribed";

    @Override
    public void onInitialApplication(AbstractCard card) {
        // CardModifierManager.addModifier(card, new thePackmaster.cardmodifiers.InscribedMod(true, true));
        try {
            Class<?> clazz = Class.forName("thePackmaster.cardmodifiers.InscribedMod");
            CardModifierManager.addModifier(card, (AbstractCardModifier)clazz.getConstructor(boolean.class, boolean.class).newInstance(true, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return allowOrbMods() && !CardModifierManager.hasModifier(card, REF_MOD_ID);
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
