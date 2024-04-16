package MojiMod.augments;

import CardAugments.cardmods.AbstractAugment;
import MojiMod.MojiMod;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InspiredMod extends AbstractAugment {
    public static final String ID = MojiMod.makeID(InspiredMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static int upgrade_magic = 0;
    private static final int UPGRADE_MULTIPLIER = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
        AbstractCard upgradeCheck = card.makeCopy();
        upgradeCheck.upgrade();
        if (card.baseMagicNumber > 0) {
            if (upgradeCheck.baseMagicNumber > card.baseMagicNumber) {
                upgrade_magic = upgradeCheck.baseMagicNumber - card.baseMagicNumber;
            } else {
                upgrade_magic = 0;
            }
        } else {
            upgrade_magic = -1;
        }
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        if (upgrade_magic > 0) {
            card.baseMagicNumber += upgrade_magic * UPGRADE_MULTIPLIER;
            card.upgradedMagicNumber = true;
        }
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return !card.upgraded
                && cardCheck(card, c -> upgradesMagic() && doesntDowngradeMagic());
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