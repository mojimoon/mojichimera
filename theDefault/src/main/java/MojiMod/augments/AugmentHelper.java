package MojiMod.augments;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import MojiMod.MojiMod;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static MojiMod.MojiMod.logger;
import static MojiMod.MojiMod.makeID;

public class AugmentHelper {
    public static void register() {
        CardAugmentsMod.registerMod(MojiMod.getModID(), CardCrawlGame.languagePack.getUIString(makeID("ModConfigs")).TEXT[0]);
        new AutoAdd(MojiMod.getModID())
                .packageFilter("MojiMod.augments")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment, MojiMod.getModID());});
    }
}