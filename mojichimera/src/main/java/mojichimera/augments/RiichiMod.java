package mojichimera.augments;

import CardAugments.actions.AutoplayOnRandomEnemyAction;
import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.mojichimera;

public class RiichiMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(RiichiMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 1.5F;
    private boolean modMagic;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * MULTIPLIER;
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * MULTIPLIER;
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (this.modMagic)
            return magic * MULTIPLIER;
        return magic;
    }

    @Override
    public void onDrawn(AbstractCard card) {
        addToBot(new AutoplayOnRandomEnemyAction(card));
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage > 1 || card.baseBlock > 1 || cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 1)))
                && !AutoplayField.autoplay.get(card)
                && card.cost >= 0
                && cardCheck(card, c -> notRetain(c));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertBeforeText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new RiichiMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}