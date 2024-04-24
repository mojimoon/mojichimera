package packmasteraugments.augments;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.AutoAdd;
import mojichimera.mojichimera;
import thePackmaster.ThePackmaster;

public class PackmasterAugmentHelper {
    public static void register() {
        CardAugmentsMod.registerOrbCharacter(ThePackmaster.Enums.THE_PACKMASTER);
        new AutoAdd(mojichimera.getModID())
                .packageFilter("packmasteraugments.augments")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment, mojichimera.getModID());
                });
    }
}
