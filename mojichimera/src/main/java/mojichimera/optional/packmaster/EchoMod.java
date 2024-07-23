package mojichimera.optional.packmaster;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.augments.AugmentHelper;
import mojichimera.cardmods.EchoedEtherealMod;
import mojichimera.cardmods.GlowEchoMod;
import mojichimera.mojichimera;

public class EchoMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(EchoMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final String ECHO_MODID = "anniv5:EchoMod";

    public EchoMod() {
        this.priority = 1000;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost > 0
                && !CardModifierManager.hasModifier(card, ECHO_MODID)
                && AugmentHelper.isEchoValid(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard copy = card.makeStatEquivalentCopy();
                CardModifierManager.addModifier(copy, new ExhaustMod());
                CardModifierManager.addModifier(copy, new EchoedEtherealMod());
                CardModifierManager.addModifier(copy, new GlowEchoMod());
                addToBot(new MakeTempCardInHandAction(copy));

                this.isDone = true;
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
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AugmentRarity getModRarity() { return AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new EchoMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
