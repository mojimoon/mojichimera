package mojichimera.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import mojichimera.mojichimera;
import mojichimera.util.TextureLoader;

import static mojichimera.mojichimera.makePowerPath;

public class LoseFocusPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = mojichimera.makeID(LoseFocusPower.class.getSimpleName());

//    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final PowerStrings TEXT = new PowerStrings();

    // something has gone wrong with the localization so this is a temporary fix
    static {
        if (Settings.language.toString().toLowerCase().equals("zhs")) {
            TEXT.NAME = "集中下降";
            TEXT.DESCRIPTIONS = new String[] {"在你的回合结束时，失去 #b", " 点 #y集中 。"};
        } else {
            TEXT.NAME = "Focus Down";
            TEXT.DESCRIPTIONS = new String[] {"At the end of this turn, lose #b", " #yFocus."};
        }
    }

    public LoseFocusPower(AbstractCreature owner, int amount) {
        this.name = TEXT.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.region128 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath("LosePowerPower84.png")), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath("LosePowerPower32.png")), 0, 0, 32, 32);
        updateDescription();
    }

    public void updateDescription() {
        this.description = TEXT.DESCRIPTIONS[0] + this.amount + TEXT.DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, -this.amount), -this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public AbstractPower makeCopy() {
        return new LoseFocusPower(this.owner, this.amount);
    }
}

