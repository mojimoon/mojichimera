package MojiMod.augments;

import CardAugments.actions.ImmediateExhaustCardAction;
import CardAugments.cardmods.AbstractAugment;
import CardAugments.util.Wiz;
import MojiMod.MojiMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SchrodingerMod extends AbstractAugment {
    public static final String ID = MojiMod.makeID(SchrodingerMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PROBABILITY = 50;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost = 0;
        card.costForTurn = 0;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.cost > 0 && cardCheck(card, c -> doesntExhaust(c)))
                && (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        boolean spoonProc = AbstractDungeon.cardRandomRng.randomBoolean();
        if (!spoonProc) {
            Wiz.atb((AbstractGameAction)new ImmediateExhaustCardAction(card));
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PROBABILITY));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new SchrodingerMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
