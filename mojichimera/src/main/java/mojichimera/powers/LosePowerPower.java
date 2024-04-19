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

public class LosePowerPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = mojichimera.makeID(LoseFocusPower.class.getSimpleName());

//    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final PowerStrings TEXT = new PowerStrings();
    private String basePowerName;
    private String basePowerID;
    private int thisId;
    private int thisTurn;
    private static int idCounter = 0;

    // something has gone wrong with the localization so this is a temporary fix
    static {
        if (Settings.language.toString().toLowerCase().equals("zhs")) {
            TEXT.NAME = "下降";
            TEXT.DESCRIPTIONS = new String[] {"在你的回合结束时，失去 #b", " 点 #y", " 。剩余 #b", " 回合。"};
        } else {
            TEXT.NAME = "Down";
            TEXT.DESCRIPTIONS = new String[] {"At the end of this turn, lose #b", " #y", ". Expires in #b", " turns."};
        }
    }

    public LosePowerPower(AbstractCreature owner, AbstractPower powerToLose, int amount, int turn) { // amount is negative
        this.name = TEXT.NAME;
        this.thisId = idCounter++;
        this.ID = "LosePowerPower" + this.thisId;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.region128 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath("LosePowerPower84.png")), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath("LosePowerPower32.png")), 0, 0, 32, 32);
        this.basePowerName = powerToLose.name;
        this.basePowerID = powerToLose.ID;
        this.thisTurn = turn;
        updateDescription();
    }

    public void updateDescription() {
        this.description = TEXT.DESCRIPTIONS[0] + (-this.amount) + TEXT.DESCRIPTIONS[1] + this.basePowerName + TEXT.DESCRIPTIONS[2] + this.thisTurn + TEXT.DESCRIPTIONS[3];
    }

    @Override
    public void atStartOfTurn() {
        flash();
        AbstractPower powerToLose = null;
        for (AbstractPower power : this.owner.powers) {
            if (power.ID.equals(this.basePowerID)) {
                powerToLose = power;
                break;
            }
        }
        if (powerToLose != null) {
            Class<? extends AbstractPower> powerClass = powerToLose.getClass();
            try {
                AbstractPower newPower = powerClass.getConstructor(AbstractCreature.class, int.class).newInstance(this.owner, this.amount);
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, newPower, this.amount));
                this.thisTurn--;
                updateDescription();
                if (this.thisTurn <= 0) {
                    this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
        }
    }

    public AbstractPower makeCopy() {
        return new LoseFocusPower(this.owner, this.amount);
    }
}

