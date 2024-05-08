package mojichimera.deprecated;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.util.Wiz;
import basemod.cardmods.ExhaustMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import CardAugments.cardmods.util.PreviewedMod;
import CardAugments.patches.InterruptUseCardFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.powers.FreePower;

public class ReleaseMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ReleaseMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private boolean inherentHack = true;
    private static final int COPY = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.inherentHack = true;
        AbstractCard preview = card.makeStatEquivalentCopy();
        this.inherentHack = false;
        CardModifierManager.addModifier(preview, (AbstractCardModifier)new PreviewedMod());
        MultiCardPreview.add(card, new AbstractCard[] { preview });
        card.costForTurn = ++card.cost;
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);

        if (!card.exhaust) {
            CardModifierManager.addModifier(card, (AbstractCardModifier)new ExhaustMod());
        }
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
                    copy.use(AbstractDungeon.player, target instanceof AbstractMonster ? (AbstractMonster)target : null);
//                    Wiz.applyToSelf(new NextTurnStartPlayPower(AbstractDungeon.player, copy, TURN, COPY));
                    Wiz.applyToSelf(new FreePower(AbstractDungeon.player, copy, COPY));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage > 1 || card.baseBlock > 1 || cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 1)))
                && card.cost != -2 && noShenanigans(card)
                && !AugmentHelper.hasMultiPreviewModsExcept(card, ReleaseMod.ID);
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
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ReleaseMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    public boolean isInherent(AbstractCard card) {
        return this.inherentHack;
    }
}
