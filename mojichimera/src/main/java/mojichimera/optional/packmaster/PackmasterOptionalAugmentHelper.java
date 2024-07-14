package mojichimera.optional.packmaster;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.AutoAdd;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import mojichimera.cardmods.GlowEchoMod;
import mojichimera.mojichimera;

public class PackmasterOptionalAugmentHelper {
    public static void register() {
        new AutoAdd(mojichimera.getModID())
                .packageFilter("mojichimera.optional.packmaster")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment, mojichimera.getModID());
                });

        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            public boolean test(AbstractCard card) {
                return CardModifierManager.hasModifier(card, GlowEchoMod.ID);
            }

            public Color getColor(AbstractCard card) {
                return Color.SLATE.cpy();
            }

            public String glowID() {
                return GlowEchoMod.ID;
            }
        });
    }
}
