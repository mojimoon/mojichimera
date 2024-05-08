package mojichimera.augments.rare;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.util.Wiz;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (!card.isEthereal) {
            CardModifierManager.addModifier(card, new EtherealMod());
        }
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (cardCheck(card, c -> notExhaust(c)));
    }

    @Override
    public void onExhausted(AbstractCard card) {
        card.flash(Color.RED.cpy());
        AbstractCard copy = card.makeStatEquivalentCopy();
        Wiz.applyToSelf(new NextTurnAddToHandPower(AbstractDungeon.player, card, TURN, COPY, true));
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
}
