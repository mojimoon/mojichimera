package packmasteraugments.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;

import java.util.Iterator;
import java.util.List;

public class PrismaticMod extends AbstractAugment {
    public static final String ID = mojichimera.makePackmasterID(PrismaticMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        this.addToBot(new AbstractGameAction() {
            public void update() {
                try {
//                    List<?> cards = thePackmaster.cards.prismaticpack.PrismaticUtil.getRandomDifferentColorCardInCombat((AbstractCard.CardType)null, (AbstractCard.CardRarity)null, EFFECT);
                    Class<?> prismaticUtil = Class.forName("thePackmaster.cards.prismaticpack.PrismaticUtil");
                    List<?> cards = (List<?>)prismaticUtil.getMethod("getRandomDifferentColorCardInCombat", AbstractCard.CardType.class, AbstractCard.CardRarity.class, int.class).invoke(null, null, null, EFFECT);
                    Iterator<?> var2 = (cards).iterator();

                    while(var2.hasNext()) {
                        AbstractCard c = (AbstractCard)var2.next();
                        if (!c.isEthereal)
                            CardModifierManager.addModifier(c, new EtherealMod());
                        this.addToTop(new MakeTempCardInHandAction(c));
                    }

                    this.isDone = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    this.isDone = true;
                }
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
