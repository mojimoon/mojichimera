package mojichimera.augments.uncommon;

import CardAugments.cardmods.AbstractAugment;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class PerseverantMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(PerseverantMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    public static final float MULTIPLIER = 2.0F;

    @Override
    public boolean validCard(AbstractCard card) {
        return card.baseBlock > 0 && card.cost > -2;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hand.contains(card)) {
            return block;
        }
        return block * (MULTIPLIER - (float)AbstractDungeon.player.currentHealth / (float)AbstractDungeon.player.maxHealth);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PerseverantMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
