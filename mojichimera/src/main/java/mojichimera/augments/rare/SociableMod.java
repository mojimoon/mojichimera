package mojichimera.augments.rare;

import CardAugments.cardmods.AbstractAugment;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SociableMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(SociableMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PERCENT = 20;
    private static final float EXTRA_MULTIPLIER = 0.2F;

    @Override
    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * getMultiplier(card);
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * getMultiplier(card);
        return block;
    }

    private float getMultiplier(AbstractCard card) {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hand.contains(card)) {
            return 1.0F;
        }
        float multiplier = 1.0F;
        for (AbstractCard otherCard : AbstractDungeon.player.hand.group) {
            if (otherCard != card && otherCard.type == card.type) {
                multiplier += EXTRA_MULTIPLIER;
            }
        }
        return multiplier;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage > 1 || card.baseBlock > 1)
                && (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PERCENT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new SociableMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
