package mojichimera.augments.uncommon;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.patches.EchoFieldPatches;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class VolatileMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(VolatileMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int MIN_EFFECT = -1;
    private static final int MAX_EFFECT = 2;
    public static final SpireField<Integer> nextPlayEchoes = new SpireField<>(() -> -999);
    public static final SpireField<Integer> debt = new SpireField<>(() -> 0);

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasVariable(card, true)
                && AugmentHelper.isPlayable(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!card.purgeOnUse) {
                    if (nextPlayEchoes.get(card) != -999) {
                        EchoFieldPatches.EchoFields.echo.set(card, (Integer) EchoFieldPatches.EchoFields.echo.get(card) - nextPlayEchoes.get(card));
                        nextPlayEchoes.set(card, -999);
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void onUpdate(AbstractCard card) {
        if (CardCrawlGame.isInARun() && AbstractDungeon.player.hand.contains(card)) {
            if (nextPlayEchoes.get(card) == -999) {
                int echo = AbstractDungeon.cardRandomRng.random(MIN_EFFECT, MAX_EFFECT);
                int result = EchoFieldPatches.EchoFields.echo.get(card) + echo;
                if (result < 0) {
                    debt.set(card, debt.get(card) - result);
                    echo -= result;
                    result = 0;
                }
                if (result > 0 && debt.get(card) > 0) {
                    int pay = Math.min(debt.get(card), result);
                    echo -= pay;
                    result -= pay;
                    debt.set(card, debt.get(card) - pay);
                }
                EchoFieldPatches.EchoFields.echo.set(card, result);
                nextPlayEchoes.set(card, echo);
            }
        }
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], MIN_EFFECT + 1, MAX_EFFECT + 1));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new VolatileMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
