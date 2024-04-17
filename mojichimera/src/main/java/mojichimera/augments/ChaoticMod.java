package mojichimera.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.mojichimera;
import com.megacrit.cardcrawl.random.Random;
import java.util.HashMap;

import java.util.UUID;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class ChaoticMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ChaoticMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER_MIN = 0.5F;
    private static final float MULTIPLIER_MAX = 2.0F;
    private static final Random rng = new Random();
    public static final SpireField<Integer> damageMap = new SpireField<>(() -> 0);
    public static final SpireField<Integer> blockMap = new SpireField<>(() -> 0);
    public static final SpireField<Integer> magicMap = new SpireField<>(() -> 0);

    @Override
    public void onInitialApplication(AbstractCard card) {
        try {
            if (card.baseDamage > 0)
                damageMap.set(card, rng.random((int)Math.ceil(card.baseDamage * MULTIPLIER_MIN), (int)Math.ceil(card.baseDamage * MULTIPLIER_MAX)));
            if (card.baseBlock > 0)
                blockMap.set(card, rng.random((int)Math.ceil(card.baseBlock * MULTIPLIER_MIN), (int)Math.ceil(card.baseBlock * MULTIPLIER_MAX)));
            if (card.baseMagicNumber > 0)
                magicMap.set(card, rng.random((int)Math.ceil(card.baseMagicNumber * MULTIPLIER_MIN), (int)Math.ceil(card.baseMagicNumber * MULTIPLIER_MAX)));
        } catch (Exception e) {
            Random rng = new Random();
            if (card.baseDamage > 0)
                card.baseDamage = rng.random((int)Math.ceil(card.baseDamage * MULTIPLIER_MIN), (int)Math.ceil(card.baseDamage * MULTIPLIER_MAX));
            if (card.baseBlock > 0)
                card.baseBlock = rng.random((int)Math.ceil(card.baseBlock * MULTIPLIER_MIN), (int)Math.ceil(card.baseBlock * MULTIPLIER_MAX));
            if (card.baseMagicNumber > 0)
                card.baseMagicNumber = rng.random((int)Math.ceil(card.baseMagicNumber * MULTIPLIER_MIN), (int)Math.ceil(card.baseMagicNumber * MULTIPLIER_MAX));
        }
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (damageMap.get(card) > 0)
            return damageMap.get(card);
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (blockMap.get(card) > 0)
            return blockMap.get(card);
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (magicMap.get(card) > 0)
            return magicMap.get(card);
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage > 0 || card.baseBlock > 0 || card.baseMagicNumber > 0);
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
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ChaoticMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
