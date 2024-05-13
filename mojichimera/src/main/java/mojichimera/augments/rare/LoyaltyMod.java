package mojichimera.augments.rare;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.patches.EchoFieldPatches;
import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.util.MojiHelper;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class LoyaltyMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(LoyaltyMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int ECHOS = 1;
    public static final SpireField<Integer> playedTimes = new SpireField<>(() -> 0);

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasVariable(card, true)
                && AugmentHelper.isReplayable(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!card.purgeOnUse) {
                    playedTimes.set(card, playedTimes.get(card) + 1);
                    if (playedTimes.get(card) % 2 == 1) {
                        for (AbstractCard c : GetAllInBattleInstances.get(card.uuid)) {
                            EchoFieldPatches.EchoFields.echo.set(c, (Integer) EchoFieldPatches.EchoFields.echo.get(c) + ECHOS);
                        }
                    } else {
                        for (AbstractCard c : GetAllInBattleInstances.get(card.uuid)) {
                            EchoFieldPatches.EchoFields.echo.set(c, (Integer) EchoFieldPatches.EchoFields.echo.get(c) - ECHOS);
                        }
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], 2, ECHOS));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new LoyaltyMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    private boolean shouldGlow(AbstractCard card) {
        if (!MojiHelper.isInCombat()) return false;
        return playedTimes.get(card) % 2 == 1;
    }

    public CardBorderGlowManager.GlowInfo getGlowInfo() {
        return new CardBorderGlowManager.GlowInfo() {
            public boolean test(AbstractCard card) {
                return LoyaltyMod.this.hasThisMod(card) && LoyaltyMod.this.shouldGlow(card);
            }

            public Color getColor(AbstractCard card) {
                return Color.GOLD.cpy();
            }

            public String glowID() {
                return LoyaltyMod.ID + "Glow";
            }
        };
    }
}
