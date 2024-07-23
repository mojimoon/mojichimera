package mojichimera.optional.packmaster;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.cardmods.EchoedEtherealMod;
import mojichimera.cardmods.GlowEchoMod;
import mojichimera.mojichimera;

public class VirusMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(VirusMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    public VirusMod() {
        this.priority = 1000;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost >= 0
                && AugmentHelper.isEchoValid(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int count = 0;
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c != card) {
                        addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                        count++;
                    }
                }

                if (count > 0) {
                    AbstractCard copy = card.makeStatEquivalentCopy();
                    CardModifierManager.removeModifiersById(copy, ID, true);
                    CardModifierManager.addModifier(copy, new ExhaustMod());
                    CardModifierManager.addModifier(copy, new EchoedEtherealMod());
                    CardModifierManager.addModifier(copy, new GlowEchoMod());
                    addToBot(new MakeTempCardInHandAction(copy, count));
                }

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
    public AugmentRarity getModRarity() { return AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new VirusMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
