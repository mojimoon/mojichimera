package mojichimera.augments.uncommon;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.util.PreviewedMod;
import CardAugments.patches.InterruptUseCardFieldPatches;
import CardAugments.util.PortraitHelper;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.powers.SadisticProtocolPower;
import mojichimera.util.MojiHelper;

public class SadisticMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(SadisticMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private boolean inherentHack = true;
    private static final int EFFECT = 1;

    public SadisticMod() {
        this.priority = 1000;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.inherentHack = true;
        AbstractCard preview = card.makeStatEquivalentCopy();
        this.inherentHack = false;
        CardModifierManager.addModifier(preview, (AbstractCardModifier)new PreviewedMod());
        MultiCardPreview.add(card, new AbstractCard[] { preview });
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);
        card.target = AbstractCard.CardTarget.NONE;
        if (card.type != AbstractCard.CardType.POWER) {
            card.type = AbstractCard.CardType.POWER;
            PortraitHelper.setMaskedPortrait(card);
        }
        card.costForTurn = ++card.cost;
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
            if (CardModifierManager.hasModifier(c, PreviewedMod.ID)) {
                c.upgrade();
                c.initializeDescription();
            }
        }
        card.initializeDescription();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard preview = null;
                for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
                    if (CardModifierManager.hasModifier(c, PreviewedMod.ID))
                        preview = c;
                }
                if (preview != null) {
                    AbstractCard copy = preview.makeStatEquivalentCopy();
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SadisticProtocolPower(AbstractDungeon.player, copy, EFFECT), EFFECT));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isAttackOrSkill(card)
                && AugmentHelper.hasStaticCost(card)
                && !AugmentHelper.hasMultiPreviewMod(card, SadisticMod.ID)
                && AugmentHelper.isPowerizeValid(card);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertBeforeText(MojiHelper.removeExhaustInDescription(rawDescription), CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new SadisticMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    public boolean isInherent(AbstractCard card) {
        return this.inherentHack;
    }
}
