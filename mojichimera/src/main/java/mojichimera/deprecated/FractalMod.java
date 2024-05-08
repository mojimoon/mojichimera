package mojichimera.deprecated;

import CardAugments.cardmods.AbstractAugment;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class FractalMod extends AbstractAugment {
    public static final String ID = mojichimera.makePackmasterID(FractalMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public static final SpireField<Integer> generations = new SpireField<>(() -> 0);
    private static final int COPY = 2;
    private static final int PERCENT = 33;
    private static final int COST = 1;
    private static final float MULTIPLIER = 0.6666667F;
    private boolean modMagic;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;

        card.cost -= generations.get(card) * COST;
        if (card.cost < 0)
            card.cost = 0;
        card.costForTurn = card.cost;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return (float) (damage * Math.pow(MULTIPLIER, generations.get(card)));
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return (float) (block * Math.pow(MULTIPLIER, generations.get(card)));
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (this.modMagic)
            return (float) (magic * Math.pow(MULTIPLIER, generations.get(card)));
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage > 0 || card.baseBlock > 0 || cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
                && card.cost >= 0;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractCard selfCopy = card.makeStatEquivalentCopy();
        generations.set(selfCopy, generations.get(card) + 1);
        if (!CardModifierManager.hasModifier(selfCopy, ExhaustMod.ID)) {
            CardModifierManager.addModifier(selfCopy, new ExhaustMod());
        }
        try {
            Class<?> echoedEtherealMod = Class.forName("thePackmaster.cardmodifiers.energyandechopack.EchoedEtherealMod");
            Class<?> glowEchoMod = Class.forName("thePackmaster.cardmodifiers.energyandechopack.GlowEchoMod");
            if (!CardModifierManager.hasModifier(selfCopy, echoedEtherealMod.getField("ID").get(null).toString())) {
                CardModifierManager.addModifier(selfCopy, (AbstractCardModifier)echoedEtherealMod.newInstance());
            }
            if (!CardModifierManager.hasModifier(selfCopy, glowEchoMod.getField("ID").get(null).toString())) {
                CardModifierManager.addModifier(selfCopy, (AbstractCardModifier)glowEchoMod.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < COPY; i++)
            addToBot(new MakeTempCardInHandAction(selfCopy, false, true));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
    return insertAfterText(rawDescription, String.format(CARD_TEXT[0], COPY, PERCENT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new FractalMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
