package mojichimera.augments.common;

import CardAugments.cardmods.AbstractAugment;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CreditMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(CreditMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int GOLD = 250;
    private static final int UPGRADE_GOLD = 150;

    @Override
    public boolean atBattleStartPreDraw(AbstractCard card) {
        boolean activated = shouldActivate(card);
        if (activated) {
            card.cost--;
            if (card.cost < 0) {
                card.cost = 0;
            }
            card.costForTurn = card.cost;
        }
        return activated;
    }

    @Override
    public void onCreatedMidCombat(AbstractCard card) {
        atBattleStartPreDraw(card);
    }

    private boolean shouldActivate(AbstractCard card) {
        if (!AugmentHelper.isInCombat()) {
            return false;
        }
        return AbstractDungeon.player.gold >= (card.upgraded ? UPGRADE_GOLD : GOLD);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasStaticCost(card, 1);
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], (card.upgraded ? UPGRADE_GOLD : GOLD)));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new CreditMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
