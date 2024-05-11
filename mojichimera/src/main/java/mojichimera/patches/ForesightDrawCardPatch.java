package mojichimera.patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/* original code
  private void endActionWithFollowUp() {
    this.isDone = true;
    if (this.followUpAction != null)
      addToTop(this.followUpAction);
  }
 */

public class ForesightDrawCardPatch {

    @SpirePatch(clz = DrawCardAction.class, method = "endActionWithFollowUp")
    public static class ForesightDrawCardPatch1 {
        @SpirePrefixPatch
        public static void Prefix(DrawCardAction __instance) {
            int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
            if (count > 0) {
                AbstractCard card = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(count - 1);
                if (CardModifierManager.hasModifier(card, "mojichimera:ForesightMod")) {
                    for (final AbstractCard c : __instance.drawnCards) {
                        c.updateCost(-1);
                    }
                }
            }
        }
    }

}