package mojichimera.deprecated;

import CardAugments.powers.AbstractEasyPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mojichimera.mojichimera;
import mojichimera.util.TextureLoader;

import static mojichimera.mojichimera.makePowerPath;

@Deprecated
public class LosePowerPower extends AbstractEasyPower {
    public static String TEXT_ID = mojichimera.makeID(LosePowerPower.class.getSimpleName());

    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(TEXT_ID);

    private final AbstractPower powerToLose;

    public LosePowerPower(AbstractCreature owner, AbstractPower powerToLose, int amount) {
        super(strings.NAME + powerToLose.name, AbstractPower.PowerType.DEBUFF, false, owner, amount);
        this.region48 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath("LoseFocusPower32.png")), 0, 0, 32, 32);
        this.region128 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(makePowerPath("LoseFocusPower84.png")), 0, 0, 84, 84);
        this.powerToLose = powerToLose;
        updateDescription();
    }

    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, Color.RED.cpy());
    }

    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, this.powerToLose.ID, this.amount));
        addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public void updateDescription() {
        if (this.powerToLose == null) {
            this.description = "???";
        } else {
            this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1] + this.powerToLose.name + strings.DESCRIPTIONS[2];
        }
    }
}