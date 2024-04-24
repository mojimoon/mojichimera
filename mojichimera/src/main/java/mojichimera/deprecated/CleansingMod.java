package mojichimera.deprecated;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.actions.RandomExhaustCardTypeAction;
import mojichimera.mojichimera;

public class CleansingMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(CleansingMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;
    private static final int UPGRADE_EFFECT = 2;

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost != -2;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (card.upgraded) {
            addToBot(new RandomExhaustCardTypeAction(new AbstractCard.CardType[]{AbstractCard.CardType.CURSE, AbstractCard.CardType.STATUS}, new CardGroup[]{AbstractDungeon.player.drawPile, AbstractDungeon.player.hand, AbstractDungeon.player.discardPile}, UPGRADE_EFFECT));
        } else {
            addToBot(new RandomExhaustCardTypeAction(new AbstractCard.CardType[]{AbstractCard.CardType.CURSE, AbstractCard.CardType.STATUS}, new CardGroup[]{AbstractDungeon.player.drawPile}, EFFECT));
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
        if (card.upgraded) {
            return insertAfterText(rawDescription, String.format(CARD_TEXT[1], UPGRADE_EFFECT));
        } else {
            return insertAfterText(rawDescription, String.format(CARD_TEXT[0], EFFECT));
        }
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new CleansingMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
