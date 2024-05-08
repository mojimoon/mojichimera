package mojichimera.augments.uncommon;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.DynvarCarrier;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class RebateMod extends AbstractAugment implements DynvarCarrier {
    public static final String ID = mojichimera.makeID(RebateMod.class.getSimpleName());
    public static final String DESCRIPTION_KEY = "!" + ID + "!";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int GOLD = 6;
    private static final int UPGRADE_GOLD = 3;
    public boolean modified;
    public boolean upgraded;

    public int getBaseVal(AbstractCard card) {
        return GOLD + getEffectiveUpgrades(card) * UPGRADE_GOLD;
    }

    /* AbstractAugment */
    @Override
    public void onInitialApplication(AbstractCard card) {
        if (!card.isEthereal)
            CardModifierManager.addModifier(card, new EtherealMod());
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.cost != -2 && cardCheck(card, c -> notExhaust(c) && notRetain(c)));
    }

    @Override
    public void onExhausted(AbstractCard card) {
        card.flash(Color.RED.cpy());
        addToBot(new AbstractGameAction() {
            public void update() {
                isDone = true;
                AbstractDungeon.player.gainGold(getBaseVal(card));
            }
        });
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        card.initializeDescription();
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
//        if (rawDescription.contains(CARD_TEXT[0])) {
            return insertAfterText(rawDescription, String.format(CARD_TEXT[1], new Object[] { DESCRIPTION_KEY }));
//        }
//        return insertAfterText(insertBeforeText(rawDescription, CARD_TEXT[0]), String.format(CARD_TEXT[1], new Object[] { DESCRIPTION_KEY }));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new RebateMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    /* DynvarCarrier */
    @Override
    public String key() { return ID; }

    @Override
    public int val(AbstractCard card) { return getBaseVal(card); }

    @Override
    public int baseVal(AbstractCard card) { return getBaseVal(card); }

    @Override
    public boolean modified(AbstractCard card) { return this.modified; }

    @Override
    public boolean upgraded(AbstractCard card) {
        this.modified = (card.timesUpgraded != 0 || card.upgraded);
        this.upgraded = (card.timesUpgraded != 0 || card.upgraded);
        return this.upgraded;
    }
}
