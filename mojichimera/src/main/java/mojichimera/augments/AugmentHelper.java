package mojichimera.augments;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import mojichimera.mojichimera;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.blue.Hyperbeam;
import com.megacrit.cardcrawl.cards.blue.RipAndTear;
import com.megacrit.cardcrawl.cards.blue.Streamline;
import com.megacrit.cardcrawl.cards.colorless.HandOfGreed;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.Feed;
import com.megacrit.cardcrawl.cards.red.Hemokinesis;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static mojichimera.mojichimera.makeID;

public class AugmentHelper {
    public static void register() {
        CardAugmentsMod.registerMod(mojichimera.getModID(), CardCrawlGame.languagePack.getUIString(makeID("ModConfigs")).TEXT[0]);
        new AutoAdd(mojichimera.getModID())
            .packageFilter("mojichimera.augments")
            .any(AbstractAugment.class, (info, abstractAugment) -> {
                CardAugmentsMod.registerAugment(abstractAugment, mojichimera.getModID());});
    }
}