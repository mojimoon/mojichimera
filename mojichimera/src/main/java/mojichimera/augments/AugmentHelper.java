package mojichimera.augments;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.rare.BundledMod;
import CardAugments.cardmods.rare.ExplosiveMod;
import CardAugments.cardmods.rare.InfiniteMod;
import CardAugments.cardmods.rare.InvertedMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mojichimera.augments.common.*;
import mojichimera.augments.rare.*;
import mojichimera.augments.common.InspiredMod;
import mojichimera.augments.uncommon.TranquilMod;
import mojichimera.mojichimera;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static CardAugments.cardmods.AbstractAugment.*;
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

        // ReactiveMod
        CardAugmentsMod.registerCustomBan(ReactiveMod.ID, c -> c instanceof DeusExMachina); // 机械降神

        // RushdownMod
        CardAugmentsMod.registerCustomBan(RushdownMod.ID, c -> c instanceof Wish); // 许愿
        CardAugmentsMod.registerCustomBan(RushdownMod.ID, c -> c instanceof Conclude); // 结末
        CardAugmentsMod.registerCustomBan(RushdownMod.ID, c -> c instanceof FearNoEvil); // 不惧妖邪

        // EmbraceMod
        CardAugmentsMod.registerCustomBan(EmbraceMod.ID, c -> c instanceof Hemokinesis); // 御血术
        CardAugmentsMod.registerCustomBan(EmbraceMod.ID, c -> c instanceof Wish); // 许愿
        CardAugmentsMod.registerCustomBan(EmbraceMod.ID, c -> c instanceof Conclude); // 结末
    }

    public static boolean isInCombat() {
        return AbstractDungeon.player != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    public static boolean hasMultiPreviewModsExcept(AbstractCard card, String ...modid) {
        String[] mods = new String[] {
                BundledMod.ID,
                ExplosiveMod.ID,
                InfiniteMod.ID,
                PastMod.ID,
                SkillizedMod.ID,
                UnawakenedMod.ID,
                StormMod.ID,
                EmbraceMod.ID
        };

        for (String id : modid) {
            for (int i = 0; i < mods.length; i++) {
                if (mods[i] != null && mods[i].equals(id)) {
                    mods[i] = null;
                    break;
                }
            }
        }

        for (String id : mods) {
            if (CardModifierManager.hasModifier(card, id))
                return true;
        }

        return false;
    }

    public static boolean hasChangeTypeMod(AbstractCard card) {
        String[] mods = new String[] {
                InvertedMod.ID,
                BundledMod.ID,
                ExplosiveMod.ID,
                InfiniteMod.ID,
                SkillizedMod.ID,
                StormMod.ID,
                EmbraceMod.ID
        };

        for (String id : mods) {
            if (CardModifierManager.hasModifier(card, id))
                return true;
        }

        return false;
    }

    public static boolean hasDamage(AbstractCard card) {
        return card.baseDamage > 0;
    }

    public static boolean reachesDamage(AbstractCard card, int threshold) {
        return card.baseDamage >= threshold;
    }

    public static boolean hasBlock(AbstractCard card) {
        return card.baseBlock > 0;
    }

    public static boolean reachesBlock(AbstractCard card, int threshold) {
        return card.baseBlock >= threshold;
    }

    public static boolean hasMagic(AbstractCard card) {
        return cardCheck(card, c -> c.baseMagicNumber > 0 && doesntDowngradeMagic());
    }

    public static boolean reachesMagic(AbstractCard card, int threshold) {
        return cardCheck(card, c -> c.baseMagicNumber >= threshold && doesntDowngradeMagic());
    }

    public static boolean hasDamageOrBlock(AbstractCard card) {
        return card.baseDamage > 0 || card.baseBlock > 0;
    }

    public static boolean reachesDamageOrBlock(AbstractCard card, int threshold) {
        return card.baseDamage >= threshold || card.baseBlock >= threshold;
    }

    public static boolean hasVariable(AbstractCard card, boolean ...doesntCareDowngrade) {
        if (doesntCareDowngrade.length > 0 && doesntCareDowngrade[0])
            return card.baseMagicNumber > 0 || card.baseDamage > 0 || card.baseBlock > 0;
        return hasDamage(card) || hasBlock(card) || hasMagic(card);
    }

    public static boolean reachesVariable(AbstractCard card, int threshold) {
        return reachesDamage(card, threshold) || reachesBlock(card, threshold) || reachesMagic(card, threshold);
    }

    public static boolean isPlayable(AbstractCard card) {
        return card.cost != -2;
    }

    public static boolean hasStaticCost(AbstractCard card, int ...cost) {
        if (cost.length > 0)
            return cardCheck(card, c -> c.cost >= cost[0] && doesntUpgradeCost());
        return cardCheck(card, c -> c.cost >= 0 && doesntUpgradeCost());
    }
    public static boolean isReplayable(AbstractCard card) {
        return cardCheck(card, c -> isAttackOrSkill(c) && notExhaust(c) && isPlayable(c));
    }

    public static boolean isEtherealValid(AbstractCard card) {
        return cardCheck(card, c -> notExhaust(c) && notRetain(c));
    }

    public static boolean isRetainValid(AbstractCard card) {
        return cardCheck(card, c -> notEthereal(c));
    }

    public static boolean isAttack(AbstractCard card) {
        return card.type == AbstractCard.CardType.ATTACK;
    }

    public static boolean isSkill(AbstractCard card) {
        return card.type == AbstractCard.CardType.SKILL;
    }

    public static boolean isPower(AbstractCard card) {
        return card.type == AbstractCard.CardType.POWER;
    }

    public static boolean isAttackOrSkill(AbstractCard card) {
        return card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL;
    }

    public static boolean isNormal(AbstractCard card) {
        return card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL || card.type == AbstractCard.CardType.POWER;
    }

    public static String removeExhaustInDescription(String rawDescription) {
        final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(StrangeMod.ID).EXTRA_TEXT;
        if (rawDescription.contains(CARD_TEXT[3])) {
            return rawDescription.replace(CARD_TEXT[3], CARD_TEXT[4]);
        }
        if (rawDescription.contains(CARD_TEXT[1])) {
            return rawDescription.replace(CARD_TEXT[1], CARD_TEXT[2]);
        }
        return rawDescription;
    }
}
