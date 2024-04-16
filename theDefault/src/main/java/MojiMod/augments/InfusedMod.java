package MojiMod.augments;

import CardAugments.cardmods.AbstractAugment;
import MojiMod.MojiMod;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InfusedMod extends AbstractAugment {
    public static final String ID = MojiMod.makeID(InfusedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static int upgrade_damage = 0;
    private static int upgrade_block = 0;
    private static final float UPGRADE_MULTIPLIER = 1.5F;

    @Override
    public void onInitialApplication(AbstractCard card) {
        AbstractCard upgradeCheck = card.makeCopy();
        upgradeCheck.upgrade();
        if (card.baseDamage > 0) {
            if (upgradeCheck.baseDamage > card.baseDamage) {
                upgrade_damage = upgradeCheck.baseDamage - card.baseDamage;
            } else {
                upgrade_damage = 0;
            }
        } else {
            upgrade_damage = -1;
        }

        if (card.baseBlock > 0) {
            if (upgradeCheck.baseBlock > card.baseBlock) {
                upgrade_block = upgradeCheck.baseBlock - card.baseBlock;
            } else {
                upgrade_block = 0;
            }
        } else {
            upgrade_block = -1;
        }
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        if (upgrade_damage > 0) {
            card.baseDamage += (int)Math.floor(upgrade_damage * UPGRADE_MULTIPLIER);
            card.upgradedDamage = true;
        }

        if (upgrade_block > 0) {
            card.baseBlock += (int)Math.floor(upgrade_block * UPGRADE_MULTIPLIER);
            card.upgradedBlock = true;
        }
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return !card.upgraded
                && cardCheck(card, c -> upgradesDamage() || upgradesBlock());
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
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new InfusedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}