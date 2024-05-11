package mojichimera.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/* original code
public boolean hasEnoughEnergy() {
    if (AbstractDungeon.actionManager.turnHasEnded) {
      this.cantUseMessage = TEXT[9];
      return false;
    }
    ...
}
 */

public class EndTurnPlayPatch {

    @SpirePatch(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class EndTurnPlayPatch1 {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
            if (AbstractDungeon.actionManager.turnHasEnded) {
                return SpireReturn.Return(true);
            } else {
                return SpireReturn.Continue();
            }
        }
    }

}