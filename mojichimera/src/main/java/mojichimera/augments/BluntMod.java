package mojichimera.augments;

import CardAugments.cardmods.AbstractAugment;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.purple.*;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BluntMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(BluntMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float BLOCK_MULTIPLIER = 0.0F;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost = 0;
        card.costForTurn = 0;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * BLOCK_MULTIPLIER;
        return block;
    }

    private boolean isInWhitelist(AbstractCard card) {
        return card instanceof Armaments // 武装
            || card instanceof SecondWind // 重振精神
            || card instanceof Backflip // 后空翻
            || card instanceof Blur // 残影
            || card instanceof ConserveBattery // 充电
            || card instanceof Hologram // 全息影像
            || card instanceof Evaluate // 评估
            || card instanceof EmptyBody // 化体为空
            || card instanceof Swivel // 旋身
            || card instanceof DeceiveReality; // 欺骗现实
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.cost > 0)
                && (card.baseBlock > 0)
                && (card.baseDamage > 0 || card.baseMagicNumber > 0 || isInWhitelist(card));
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
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new BluntMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
