package mojichimera.augments.rare;

import CardAugments.cardmods.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mojichimera.damagemods.WallopIntentDamage;
import mojichimera.mojichimera;

public class SarcasticMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(SarcasticMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PERCENT = 25;
    private static final float EXTRA_MULTIPLIER = 0.25F;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.costForTurn = ++card.cost;
    }


    @Override
    public boolean validCard(AbstractCard card) {
        return card.type == AbstractCard.CardType.ATTACK
                && card.target == AbstractCard.CardTarget.ENEMY
                && card.cost >= 0;
    }

    @Override
    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage * (1.0F + count(card, target) * EXTRA_MULTIPLIER);
    }

    private int count(AbstractCard card, AbstractMonster target) {
        if (AbstractDungeon.player == null || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || !AbstractDungeon.player.hand.contains(card)) {
            return 0;
        }

        return (int) AbstractDungeon.player.powers.stream().filter(p -> p.type == AbstractPower.PowerType.BUFF).count()
                + (target == null ? 0 : (int) target.powers.stream().filter(p -> p.type == AbstractPower.PowerType.DEBUFF).count());
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PERCENT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new SarcasticMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}