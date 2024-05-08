package mojichimera.augments.rare;

import CardAugments.cardmods.AbstractAugment;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class ChargeMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ChargeMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 0.5F;
    private static final int PERCENT = 50;
    public static final SpireField<Integer> otherCardsPlayed = new SpireField<>(() -> 0);
    private boolean modMagic;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * (1.0F + otherCardsPlayed.get(card) * MULTIPLIER);
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * (1.0F + otherCardsPlayed.get(card) * MULTIPLIER);
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (this.modMagic)
            return magic * (1.0F + otherCardsPlayed.get(card) * MULTIPLIER);
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage > 0 || card.baseBlock > 0 || card.baseMagicNumber > 0)
                && (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL)
                && cardCheck(card, c -> doesntExhaust(c))
                && card.cost != -2;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                otherCardsPlayed.set(card, 0);
                this.isDone = true;
            }
        });
    }

    @Override
    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
        if (AbstractDungeon.player.hand.contains(card) && otherCard.type == card.type)
            otherCardsPlayed.set(card, otherCardsPlayed.get(card) + 1);
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
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ChargeMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
