package mojichimera.augments;

import CardAugments.cardmods.AbstractAugment;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class HappyMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(HappyMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PER = 3;
    private static final int EFFECT = 1;
    public static final SpireField<Integer> drawnTimes = new SpireField<>(() -> 0);

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost > 0;
    }

    @Override
    public void onDrawn(AbstractCard card) {
        drawnTimes.set(card, drawnTimes.get(card) + 1);
        if (drawnTimes.get(card) % PER == 0) {
            card.updateCost(-EFFECT);
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PER));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new HappyMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
