package mojichimera.augments.special;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

import static CardAugments.CardAugmentsMod.applyWeightedCardMod;
import static CardAugments.CardAugmentsMod.rollRarity;

public class ChimericMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ChimericMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card.type != AbstractCard.CardType.POWER && !card.exhaust) {
            CardModifierManager.addModifier(card, new ExhaustMod());
        }
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new AbstractGameAction() {
            public void update() {
                isDone = true;
                for (AbstractCard otherCard : AbstractDungeon.player.hand.group) {
                    if (otherCard != card) {
                        applyWeightedCardMod(otherCard, rollRarity(otherCard.rarity), 0);
                    }
                }
            }
        });
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
//        if (card.type == AbstractCard.CardType.POWER) {
//            return insertAfterText(rawDescription, CARD_TEXT[1]);
//        } else {
//            return insertAfterText(rawDescription, CARD_TEXT[0]);
//        }
        return insertAfterText(rawDescription, CARD_TEXT[1]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.SPECIAL; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ChimericMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
