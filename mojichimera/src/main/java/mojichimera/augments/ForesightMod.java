package mojichimera.augments;

import CardAugments.cardmods.AbstractAugment;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.actions.ReduceDrawnCardsCostAction;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;

import java.util.Iterator;

public class ForesightMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ForesightMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.costForTurn = ++card.cost;
    }

    private boolean isInWhitelist(AbstractCard card) {
        return card instanceof CalculatedGamble // 计算下注
            || card instanceof Violence; // 暴力
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.cost >= 0 && cardCheck(card, c -> doesntUpgradeCost() && reachesMagic(3))
                && drawsCards(card)
                && !usesAction(card, EmptyDeckShuffleAction.class))
                || isInWhitelist(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (AbstractDungeon.player.hasPower("No Draw")) {
            return;
        }
        if (card instanceof Expertise) {
            addToBot((AbstractGameAction) new ReduceDrawnCardsCostAction(card.magicNumber + 1 - AbstractDungeon.player.hand.size(), AbstractDungeon.player.hand.size(), EFFECT));
        } else if (card instanceof CalculatedGamble) {
            addToBot((AbstractGameAction) new ReduceDrawnCardsCostAction(AbstractDungeon.player.hand.size() - 1, AbstractDungeon.player.hand.size(), EFFECT));
        } else if (card instanceof Violence) {
            addToBot((AbstractGameAction) new ReduceDrawnCardsCostAction(card.magicNumber, AbstractDungeon.player.hand.size(), EFFECT, AbstractCard.CardType.ATTACK));
        } else if (InnerPeaceCheck(card) && ImpatienceCheck(card)) {
            addToBot((AbstractGameAction) new ReduceDrawnCardsCostAction(card.magicNumber, AbstractDungeon.player.hand.size(), EFFECT));
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], EFFECT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ForesightMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    private boolean InnerPeaceCheck(AbstractCard card) {
        if (card instanceof InnerPeace) {
            return AbstractDungeon.player.stance.ID.equals("Calm");
        } else {
            return true;
        }
    }

    private boolean ImpatienceCheck(AbstractCard card) {
        if (card instanceof Impatience) {
            Iterator var1 = AbstractDungeon.player.hand.group.iterator();

            AbstractCard c;
            do {
                if (!var1.hasNext()) {
                    return true;
                }

                c = (AbstractCard)var1.next();
            } while(c.type != AbstractCard.CardType.ATTACK);

            return false;
        } else {
            return true;
        }
    }
}
