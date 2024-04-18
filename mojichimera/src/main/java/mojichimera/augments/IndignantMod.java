package mojichimera.augments;

import CardAugments.cardmods.AbstractAugment;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.mojichimera;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

public class IndignantMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(IndignantMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PERCENT = 50;
    private static final float MULTIPLIER = 1.5F;

    @Override
    public void onInitialApplication(AbstractCard card) {
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.color == AbstractCard.CardColor.PURPLE
                && card.type == AbstractCard.CardType.ATTACK
                && card.cost > -2;
    }

    @Override
    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (AbstractDungeon.player.stance.ID.equals("Wrath")) {
            return damage * MULTIPLIER;
        }
        return damage;
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
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new IndignantMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
