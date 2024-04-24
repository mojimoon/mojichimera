package packmasteraugments.augments;

import CardAugments.cardmods.AbstractAugment;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class VirusMod extends AbstractAugment {
    public static final String ID = mojichimera.makePackmasterID(VirusMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final String REF_MOD_ID = "anniv5:EchoedEtherealMod";

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost != -2;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c != card) {
                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                count++;
            }
        }
        if (count > 0) {
            AbstractCard selfCopy = card.makeStatEquivalentCopy();
            CardModifierManager.addModifier(selfCopy, new ExhaustMod());
            CardModifierManager.removeModifiersById(selfCopy, ID, true);
            try {
//                 CardModifierManager.addModifier(selfCopy, new thePackmaster.cardmodifiers.energyandechopack.EchoedEtherealMod());
//                 CardModifierManager.addModifier(selfCopy, new thePackmaster.cardmodifiers.energyandechopack.GlowEchoMod());
                Class<?> echoedEtherealMod = Class.forName("thePackmaster.cardmodifiers.energyandechopack.EchoedEtherealMod");
                Class<?> glowEchoMod = Class.forName("thePackmaster.cardmodifiers.energyandechopack.GlowEchoMod");
                CardModifierManager.addModifier(selfCopy, (AbstractCardModifier)echoedEtherealMod.newInstance());
                CardModifierManager.addModifier(selfCopy, (AbstractCardModifier)glowEchoMod.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            addToBot(new MakeTempCardInHandAction(selfCopy, count));
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
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new VirusMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
