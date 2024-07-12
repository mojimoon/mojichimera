package mojichimera.augments.common;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.patches.EchoFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class WorshipMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(WorshipMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 2;

    @Override
    public void onInitialApplication(AbstractCard card) {
        EchoFieldPatches.EchoFields.echo.set(card, (Integer) EchoFieldPatches.EchoFields.echo.get(card) + 1);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isSkill(card)
                && AugmentHelper.isPlayable(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!card.purgeOnUse) {
            addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player, (AbstractPower) new WeakPower((AbstractCreature) AbstractDungeon.player, EFFECT, false)));
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new WorshipMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
