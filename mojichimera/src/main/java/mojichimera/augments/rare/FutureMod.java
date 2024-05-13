package mojichimera.augments.rare;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.util.PreviewedMod;
import CardAugments.patches.InterruptUseCardFieldPatches;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.cardmods.PromiseMod;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FutureMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(FutureMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int GOLD = 6;
    private int baseCost = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
        baseCost = card.cost;
        AbstractCard promise = new Slimed();
        CardModifierManager.addModifier(promise, new PromiseMod(baseCost));
        CardModifierManager.addModifier(promise, new PreviewedMod());
        MultiCardPreview.add(card, new AbstractCard[] { promise });
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);
        card.cost = 0;
        card.costForTurn = card.cost;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasStaticCost(card, 1)
                && AugmentHelper.isNormal(card)
                && !AugmentHelper.hasMultiPreviewMod(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        card.use(AbstractDungeon.player, target instanceof AbstractMonster ? (AbstractMonster)target : null);
        AbstractCard promise = null;
        for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
            if (CardModifierManager.hasModifier(c, PreviewedMod.ID))
                promise = c;
        }
        if (promise != null) {
//            addToBot(new MakeTempCardInDrawPileAction(promise, 1, true, true));
            addToBot(new MakeTempCardInDiscardAction(promise, 1));
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], this.baseCost));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new FutureMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
