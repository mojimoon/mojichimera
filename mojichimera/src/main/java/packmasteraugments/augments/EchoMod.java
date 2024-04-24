package packmasteraugments.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class EchoMod extends AbstractAugment {
    public static final String ID = mojichimera.makePackmasterID(EchoMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final String REF_MOD_ID = "anniv5:EchoMod";

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractCard selfCopy = card.makeStatEquivalentCopy();
        if (!CardModifierManager.hasModifier(selfCopy, ExhaustMod.ID)) {
            CardModifierManager.addModifier(selfCopy, new ExhaustMod());
        }
        try {
//            if (!CardModifierManager.hasModifier(selfCopy, thePackmaster.cardmodifiers.energyandechopack.EchoedEtherealMod.ID)) {
//                CardModifierManager.addModifier(selfCopy, (AbstractCardModifier) new thePackmaster.cardmodifiers.energyandechopack.EchoedEtherealMod());
//            }
//            if (!CardModifierManager.hasModifier(selfCopy, thePackmaster.cardmodifiers.energyandechopack.GlowEchoMod.ID)) {
//                CardModifierManager.addModifier(selfCopy, (AbstractCardModifier) new thePackmaster.cardmodifiers.energyandechopack.GlowEchoMod());
//            }
            Class<?> echoedEtherealMod = Class.forName("thePackmaster.cardmodifiers.energyandechopack.EchoedEtherealMod");
            Class<?> glowEchoMod = Class.forName("thePackmaster.cardmodifiers.energyandechopack.GlowEchoMod");
            if (!CardModifierManager.hasModifier(selfCopy, echoedEtherealMod.getField("ID").get(null).toString())) {
                CardModifierManager.addModifier(selfCopy, (AbstractCardModifier)echoedEtherealMod.newInstance());
            }
            if (!CardModifierManager.hasModifier(selfCopy, glowEchoMod.getField("ID").get(null).toString())) {
                CardModifierManager.addModifier(selfCopy, (AbstractCardModifier)glowEchoMod.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        addToBot(new MakeTempCardInHandAction(selfCopy));
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost != -2 && !CardModifierManager.hasModifier(card, REF_MOD_ID);
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new EchoMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
