package mojichimera.augments.common;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.mojichimera;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class HappyMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(HappyMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PER = 3;
    private static final int UPGRADE_PER = 2;
    private static final int EFFECT = 1;
    public static final SpireField<Integer> drawnTimes = new SpireField<>(() -> 0);

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost > 0
                && cardCheck(card, c -> notRetain(c) && notExhaust(c));
    }

    @Override
    public void onDrawn(AbstractCard card) {
        drawnTimes.set(card, drawnTimes.get(card) + 1);
        if (drawnTimes.get(card) % (card.upgraded ? UPGRADE_PER : PER) == 0) {
            card.updateCost(-EFFECT);
        }
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        card.initializeDescription();
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], card.upgraded ? UPGRADE_PER : PER));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new HappyMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
