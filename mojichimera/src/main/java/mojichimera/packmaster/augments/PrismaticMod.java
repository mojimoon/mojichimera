package mojichimera.packmaster.augments;

import CardAugments.cardmods.AbstractAugment;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import thePackmaster.cards.prismaticpack.PrismaticUtil;

import java.util.Iterator;
import java.util.List;

public class PrismaticMod extends AbstractAugment {
    public static final String ID = mojichimera.makePackmasterID(PrismaticMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.cost != -2);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        this.addToBot(new AbstractGameAction() {
            public void update() {
                List<AbstractCard> cards = PrismaticUtil.getRandomDifferentColorCardInCombat((AbstractCard.CardType)null, (AbstractCard.CardRarity)null, EFFECT);
                Iterator<?> var2 = ((List<?>) cards).iterator();

                while(var2.hasNext()) {
                    AbstractCard c = (AbstractCard)var2.next();
                    this.addToTop(new MakeTempCardInHandAction(c));
                }

                this.isDone = true;
            }
        });
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], EFFECT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PrismaticMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

}
