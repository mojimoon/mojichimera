package mojichimera.augments.uncommon;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.patches.EchoFieldPatches;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.util.MojiHelper;

import java.util.Iterator;

//@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class ForeDoomedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ForeDoomedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 2;
//    public static final SpireField<Integer> cursesInHand = new SpireField<>(() -> 0);

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card)
                && AugmentHelper.hasVariable(card, true)
                && AugmentHelper.isNormal(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                if (!card.purgeOnUse) {
                    for (int i = 0; i < EFFECT; i++) {
                        addToTop(new MakeTempCardInHandAction(AbstractDungeon.returnRandomCurse(), 1));
                    }
                }
                this.isDone = true;
            }
        });
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!card.purgeOnUse) {
//                    EchoFieldPatches.EchoFields.echo.set(card, (Integer) EchoFieldPatches.EchoFields.echo.get(card) - cursesInHand.get(card));
//                    cursesInHand.set(card, 0);
                    int curseInHand = (int) AbstractDungeon.player.hand.group.stream().filter(c -> c.type == AbstractCard.CardType.CURSE).count();
                    for (int i = 0; i < curseInHand; i++) {
                        AbstractCard copy = card.makeStatEquivalentCopy();
                        copy.purgeOnUse = true;
                        if (target != null && target instanceof AbstractMonster && !target.isDeadOrEscaped()) {
                            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(copy, (AbstractMonster) target, card.energyOnUse, true, true));
                        } else {
                            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(copy, true, card.energyOnUse, true, true));
                        }
                    }
                }
                this.isDone = true;
            }
        });
    }

//    @Override
//    public void onUpdate(AbstractCard card) {
//        if (MojiHelper.isInCombat()) {
//            int curses = EFFECT;
//            Iterator handIterator = AbstractDungeon.player.hand.group.iterator();
//
//            while (handIterator.hasNext()) {
//                AbstractCard c = (AbstractCard)handIterator.next();
//                if (c.type == AbstractCard.CardType.CURSE) {
//                    curses++;
//                }
//            }
//
//            if (curses != cursesInHand.get(card)) {
//                EchoFieldPatches.EchoFields.echo.set(card, (Integer) EchoFieldPatches.EchoFields.echo.get(card) - cursesInHand.get(card) + curses);
//                cursesInHand.set(card, curses);
//            }
//        }
//    }

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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ForeDoomedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
