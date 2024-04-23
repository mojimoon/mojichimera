package mojichimera.packmaster.augments;

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
import thePackmaster.cardmodifiers.energyandechopack.EchoedEtherealMod;
import thePackmaster.cardmodifiers.energyandechopack.GlowEchoMod;

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
        if (CardModifierManager.hasModifier(card, REF_MOD_ID)) {
            return;
        }

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
            CardModifierManager.addModifier(selfCopy, new EchoedEtherealMod());
            CardModifierManager.addModifier(selfCopy, new GlowEchoMod());
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
        if (CardModifierManager.hasModifier(card, REF_MOD_ID)) {
            return rawDescription;
        }
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new VirusMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
