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
//    private static int upgrade_magic = 0;
//    private static final int UPGRADE_MULTIPLIER = 1;
    private static final int UPGRADE_MAGIC = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
//        upgradeCheck.upgrade();
//        if (card.baseMagicNumber > 0) {
//            if (upgradeCheck.baseMagicNumber > card.baseMagicNumber) {
//                upgrade_magic = upgradeCheck.baseMagicNumber - card.baseMagicNumber;
//            } else {
//                upgrade_magic = 0;
//            }
//        } else {
//            upgrade_magic = -1;
//        }
    }

//    @Override
//    public void onUpgradeCheck(AbstractCard card) {
//        if (upgrade_magic > 0) {
//            card.baseMagicNumber += upgrade_magic * UPGRADE_MULTIPLIER;
//            card.upgradedMagicNumber = true;
//        }
//    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
//        if (card.upgraded && upgrade_magic > 0)
//            return magic + upgrade_magic * UPGRADE_MULTIPLIER;
        if (card.upgraded)
            return magic + UPGRADE_MAGIC;
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
//        return !card.upgraded
//                && cardCheck(card, c -> upgradesMagic());
        return cardCheck(card, c -> upgradesMagic());
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() {
        return AbstractAugment.AugmentRarity.UNCOMMON;
    }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new InspiredMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}