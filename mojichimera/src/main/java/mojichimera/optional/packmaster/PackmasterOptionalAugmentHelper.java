package mojichimera.optional.packmaster;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.AutoAdd;
import mojichimera.mojichimera;

public class PackmasterOptionalAugmentHelper {
    public static void register() {
        new AutoAdd(mojichimera.getModID())
                .packageFilter("mojichimera.optional.packmaster")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment, mojichimera.getModID());
                });
    }
}
