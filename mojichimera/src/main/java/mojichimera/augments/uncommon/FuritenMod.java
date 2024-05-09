package mojichimera.augments.uncommon;

import CardAugments.cardmods.AbstractAugment;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.augments.common.PreemptiveMod;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class FuritenMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(FuritenMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 1.5F;
    private static final int PERCENT = 50;
//    private boolean modMagic;

//    @Override
//    public void onInitialApplication(AbstractCard card) {
//        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
//            this.modMagic = true;
//    }

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

//    @Override
//    public float modifyBaseMagic(float magic, AbstractCard card) {
//        if (this.modMagic)
//            return magic * getMultiplier(card);
//        return magic;
//    }

    private boolean hasPlayedThisTurn(AbstractCard card) {
        for (final AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.uuid.equals(card.uuid)) {
                return false;
            }
        }
        return true;
    }

    private float getMultiplier(AbstractCard card) {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hand.contains(card)) {
            return 1.0F;
        }
        return hasPlayedThisTurn(card) ? 1.0F : MULTIPLIER;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card)
                && AugmentHelper.reachesDamageOrBlock(card, 2);
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PreemptiveMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    private boolean shouldGlow(AbstractCard card) {
        return !hasPlayedThisTurn(card);
    }

    public CardBorderGlowManager.GlowInfo getGlowInfo() {
        return new CardBorderGlowManager.GlowInfo() {
            public boolean test(AbstractCard card) {
                return FuritenMod.this.hasThisMod(card) && FuritenMod.this.shouldGlow(card);
            }

            public Color getColor(AbstractCard card) {
                return Color.GOLD.cpy();
            }

            public String glowID() {
                return FuritenMod.ID + "Glow";
            }
        };
    }
}
