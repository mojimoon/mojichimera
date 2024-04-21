package mojichimera.augments;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import mojichimera.mojichimera;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static mojichimera.mojichimera.makeID;

public class AugmentHelper {
    public static void register() {
        CardAugmentsMod.registerMod(mojichimera.getModID(), CardCrawlGame.languagePack.getUIString(makeID("ModConfigs")).TEXT[0]);
        new AutoAdd(mojichimera.getModID())
                .packageFilter("mojichimera.augments")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment, mojichimera.getModID());});

        // Bans
        // Peaceful
        CardAugmentsMod.registerCustomBan(PeacefulMod.ID, c -> c instanceof Hemokinesis); // 御血术
        CardAugmentsMod.registerCustomBan(PeacefulMod.ID, c -> c instanceof Feed); // 狂宴
        CardAugmentsMod.registerCustomBan(PeacefulMod.ID, c -> c instanceof Streamline); // 精简改良
        CardAugmentsMod.registerCustomBan(PeacefulMod.ID, c -> c instanceof RipAndTear); // 狂乱撕扯
        CardAugmentsMod.registerCustomBan(PeacefulMod.ID, c -> c instanceof Hyperbeam); // 超能光束
        CardAugmentsMod.registerCustomBan(PeacefulMod.ID, c -> c instanceof WindmillStrike); // 旋转打击
        CardAugmentsMod.registerCustomBan(PeacefulMod.ID, c -> c instanceof Wish); // 许愿
        CardAugmentsMod.registerCustomBan(PeacefulMod.ID, c -> c instanceof RitualDagger); // 仪式匕首
        CardAugmentsMod.registerCustomBan(PeacefulMod.ID, c -> c instanceof HandOfGreed); // 贪婪之手

        // Tranquil
        CardAugmentsMod.registerCustomBan(TranquilMod.ID, c -> c instanceof Indignation); // 义愤填膺
        CardAugmentsMod.registerCustomBan(TranquilMod.ID, c -> c instanceof InnerPeace); // 内心宁静
        CardAugmentsMod.registerCustomBan(TranquilMod.ID, c -> c instanceof FearNoEvil); // 不惧妖邪

        // Peace
        CardAugmentsMod.registerCustomBan(PeaceMod.ID, c -> c instanceof Indignation); // 义愤填膺
        CardAugmentsMod.registerCustomBan(PeaceMod.ID, c -> c instanceof InnerPeace); // 内心宁静
        CardAugmentsMod.registerCustomBan(PeaceMod.ID, c -> c instanceof FearNoEvil); // 不惧妖邪

        // Inspired
        CardAugmentsMod.registerCustomBan(InspiredMod.ID, c -> c instanceof Hemokinesis); // 御血术

        // Reinforced
        CardAugmentsMod.registerCustomBan(ReinforcedMod.ID, c -> c instanceof Stack); // 堆栈

        // Blunt
        CardAugmentsMod.registerCustomBan(BluntMod.ID, c -> c instanceof Wish); // 许愿

        // Pocket
        CardAugmentsMod.registerCustomBan(PocketMod.ID, c -> c instanceof Wish); // 许愿

        // Strange
        CardAugmentsMod.registerCustomBan(StrangeMod.ID, c -> c instanceof DeusExMachina); // 机械降神

        // Pocket
        CardAugmentsMod.registerCustomBan(PocketMod.ID, c -> c instanceof Ragnarok); // 诸神之黄昏
        CardAugmentsMod.registerCustomBan(PocketMod.ID, c -> c instanceof ForceField); // 力场

        // Reactive
        CardAugmentsMod.registerCustomBan(ReactiveMod.ID, c -> c instanceof Hemokinesis); // 御血术

        // Foresight
        CardAugmentsMod.registerCustomBan(ForesightMod.ID, c -> c instanceof FTL); // 超光速
        CardAugmentsMod.registerCustomBan(ForesightMod.ID, c -> c instanceof Scrape); // 刮削
        CardAugmentsMod.registerCustomBan(ForesightMod.ID, c -> c instanceof CutThroughFate); // 斩破命运

        // BondMod
        CardAugmentsMod.registerCustomBan(BondMod.ID, c -> c instanceof Hemokinesis); // 御血术
    }
}
