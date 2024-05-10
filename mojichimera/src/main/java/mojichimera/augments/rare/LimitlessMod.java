package mojichimera.augments.rare;

import CardAugments.cardmods.AbstractAugment;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LimitlessMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(LimitlessMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float EXTRA_MULTIPLIER = 0.025F;
    private boolean modMagic;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * getMultiplier();
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * getMultiplier();
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (this.modMagic)
            return magic * getMultiplier();
        return magic;
    }

    private float getMultiplier() {
        if (AbstractDungeon.player == null)
            return 1.0F;
        return 1.0F + AbstractDungeon.floorNum * EXTRA_MULTIPLIER;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasVariable(card)
                && AugmentHelper.isNormal(card)
                && ((AbstractDungeon.player == null) || (AbstractDungeon.actNum < 3));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new LimitlessMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
