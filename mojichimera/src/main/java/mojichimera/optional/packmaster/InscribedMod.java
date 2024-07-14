package mojichimera.optional.packmaster;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class InscribedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(InscribedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final String INSCRIBED_MODID = "anniv5:Inscribed";

    @Override
    public boolean validCard(AbstractCard card) {
        return allowOrbMods()
                && AugmentHelper.isPlayable(card)
                && !CardModifierManager.hasModifier(card, INSCRIBED_MODID);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!AbstractDungeon.player.orbs.isEmpty()) {
                    AbstractOrb orb = (AbstractOrb) AbstractDungeon.player.orbs.get(0);
                    if (!(orb instanceof EmptyOrbSlot)) {
                        orb.onStartOfTurn();
                        orb.onEndOfTurn();
                    }
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
    public AugmentRarity getModRarity() { return AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new InscribedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
