package mojichimera.powers;

import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mojichimera.augments.AugmentHelper;
import mojichimera.augments.special.NamelessMod;
import mojichimera.mojichimera;

public class SteelProtocolPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = mojichimera.makeID(SteelProtocolPower.class.getSimpleName());

    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

//    private static final PowerStrings TEXT = new PowerStrings();
    private static final int PERCENT = 50;

//    static {
//        if (Settings.language.toString().toLowerCase().equals("zhs")) {
//            TEXT.NAME = "钢铁协议";
//            TEXT.DESCRIPTIONS = new String[] {"本回合的下 #b%1$s 张数值增加 #b%2$s%% 。"};
//        } else {
//            TEXT.NAME = "Steel Protocol";
//            TEXT.DESCRIPTIONS = new String[] {"Increase card values by #b%2$s%% for the next #b%1$s cards."};
//        }
//    }

    public SteelProtocolPower(AbstractCreature owner, int amount) {
        this.name = TEXT.NAME;
        this.ID = POWER_ID;
        this.amount = amount;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.loadRegion("master_protect");
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(TEXT.DESCRIPTIONS[0], this.amount, PERCENT);

        if (this.amount == 1 && this.description.contains("cards")) {
            this.description = this.description.replace("cards", "card");
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (AugmentHelper.reachesVariable(card, 2) && AugmentHelper.isNormal(card)) {
            this.amount--;
            CardModifierManager.addModifier(card, new NamelessMod());
            this.updateDescription();
        }

        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new SteelProtocolPower(owner, amount);
    }
}
