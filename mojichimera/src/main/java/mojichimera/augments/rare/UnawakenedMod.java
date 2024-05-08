package mojichimera.augments.rare;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.util.PreviewedMod;
import CardAugments.util.Wiz;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.augments.special.AwakenedMod;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.powers.NextTurnAddToHandPower;

public class UnawakenedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(UnawakenedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int TURN = 2;
    private static final int COPY = 1;
    private boolean inherentHack;

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.inherentHack = true;
        AbstractCard preview = card.makeStatEquivalentCopy();
        this.inherentHack = false;
        CardModifierManager.removeModifiersById(preview, ID, true);
        CardModifierManager.addModifier(preview, (AbstractCardModifier)new AwakenedMod());
        CardModifierManager.addModifier(preview, (AbstractCardModifier)new PreviewedMod());
        MultiCardPreview.add(card, new AbstractCard[] { preview });
        if (!card.isEthereal) {
            CardModifierManager.addModifier(card, new EtherealMod());
        }
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.baseDamage > 0 || card.baseBlock > 0 || cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
                && (card.cost != -2 && cardCheck(card, c -> notExhaust(c)))
                && !AugmentHelper.hasMultiPreviewModsExcept(card, ID);
    }

    @Override
    public void onExhausted(AbstractCard card) {
        card.flash(Color.RED.cpy());
        AbstractCard copy = null;
        for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
            if (CardModifierManager.hasModifier(c, PreviewedMod.ID)) {
                copy = c;
                break;
            }
        }
        if (copy != null)
            Wiz.applyToSelf(new NextTurnAddToHandPower(AbstractDungeon.player, copy, TURN, COPY));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], TURN));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new UnawakenedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    public boolean isInherent(AbstractCard card) {
        return this.inherentHack;
    }
}
