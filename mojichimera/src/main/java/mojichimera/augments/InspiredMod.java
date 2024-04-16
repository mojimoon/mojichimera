package mojichimera.augments;

import CardAugments.cardmods.AbstractAugment;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class InspiredMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(InspiredMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int UPGRADE_MAGIC = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (card.upgraded)
            return magic + getEffectiveUpgrades(card) * UPGRADE_MAGIC;
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return cardCheck(card, c -> upgradesMagic());
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new InspiredMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
