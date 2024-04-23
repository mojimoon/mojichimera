package mojichimera.packmaster.augments;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.AutoAdd;
import mojichimera.mojichimera;

public class PackmasterAugmentHelper {
    public static void register() {
        new AutoAdd(mojichimera.getModID())
                .packageFilter("mojichimera.packmaster.augments")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment, mojichimera.getModID());
                });
    }
}
