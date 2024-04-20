package mojichimera.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.HeelHookAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.Iterator;

public class PourSaltMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(PourSaltMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public static final int EFFECT = 1;
    private static final Class<?> WEAK_POWER = com.megacrit.cardcrawl.powers.WeakPower.class;

    @Override
    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.target == AbstractCard.CardTarget.ENEMY && card.cost != -2;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
//        addToBot(new AbstractGameAction() {
//            @Override
//            public void update() {
//                if (target != null && target.hasPower("Weakened")) {
//                    this.addToBot(new DrawCardAction(AbstractDungeon.player, EFFECT));
//                    this.addToBot(new GainEnergyAction(EFFECT));
//                }
//                this.isDone = true;
//            }
//        });
        this.addToBot(new HeelHookAction(target, new DamageInfo(AbstractDungeon.player, 0, DamageInfo.DamageType.THORNS)));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.contains(CARD_TEXT[2])) {
            return rawDescription.replace(CARD_TEXT[2], String.format(CARD_TEXT[1], EFFECT + 1));
        } else {
            return insertAfterText(rawDescription, String.format(CARD_TEXT[0], EFFECT));
        }
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PourSaltMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    public boolean shouldGlow(AbstractCard card) {

        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (!m.isDeadOrEscaped() && m.hasPower("Weakened")) {
                return true;
            }
        }
        return false;
    }

    public CardBorderGlowManager.GlowInfo getGlowInfo() {
        return new CardBorderGlowManager.GlowInfo() {
            public boolean test(AbstractCard card) {
                return PourSaltMod.this.hasThisMod(card) && PourSaltMod.this.shouldGlow(card);
            }

            public Color getColor(AbstractCard card) {
                return Color.GOLD.cpy();
            }

            public String glowID() {
                return IndignantMod.ID + "Glow";
            }
        };
    }
}
