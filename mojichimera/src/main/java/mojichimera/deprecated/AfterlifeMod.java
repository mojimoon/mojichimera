package mojichimera.deprecated;

import CardAugments.cardmods.AbstractAugment;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

@Deprecated
public class AfterlifeMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(AfterlifeMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (cardCheck(card, c -> (notRetain(c) && notEthereal(c) && notExhaust(c)))) && card.cost >= 0 && card.cost <= 2;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    }

//    @Override
//    public void onExhausted(AbstractCard card) {
//        addToBot((AbstractGameAction)new NewQueueCardAction(card, true, false, true));
//    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        addToBot((AbstractGameAction)new NewQueueCardAction(card, true, false, true));
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new AfterlifeMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
