package me.darki.konas.util;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class PotionUtil
extends Enum {
    public static PotionUtil Swiftness = new PotionUtil("Swiftness", 0, new Item[]{Items.NETHER_WART, Items.SUGAR});
    public static PotionUtil SwiftnessLong = new PotionUtil("SwiftnessLong", 1, new Item[]{Items.NETHER_WART, Items.SUGAR, Items.REDSTONE});
    public static PotionUtil SwiftnessII = new PotionUtil("SwiftnessII", 2, new Item[]{Items.NETHER_WART, Items.SUGAR, Items.GLOWSTONE_DUST});
    public static PotionUtil Slowness = new PotionUtil("Slowness", 3, new Item[]{Items.NETHER_WART, Items.SUGAR, Items.FERMENTED_SPIDER_EYE});
    public static PotionUtil SlownessLong = new PotionUtil("SlownessLong", 4, new Item[]{Items.NETHER_WART, Items.SUGAR, Items.FERMENTED_SPIDER_EYE, Items.REDSTONE});
    public static PotionUtil SlownessII = new PotionUtil("SlownessII", 5, new Item[]{Items.NETHER_WART, Items.SUGAR, Items.FERMENTED_SPIDER_EYE, Items.GLOWSTONE_DUST});
    public static PotionUtil JumpBoost = new PotionUtil("JumpBoost", 6, new Item[]{Items.NETHER_WART, Items.RABBIT_FOOT});
    public static PotionUtil JumpBoostLong = new PotionUtil("JumpBoostLong", 7, new Item[]{Items.NETHER_WART, Items.RABBIT_FOOT, Items.REDSTONE});
    public static PotionUtil JumpBoostII = new PotionUtil("JumpBoostII", 8, new Item[]{Items.NETHER_WART, Items.RABBIT_FOOT, Items.GLOWSTONE_DUST});
    public static PotionUtil Strength = new PotionUtil("Strength", 9, new Item[]{Items.NETHER_WART, Items.BLAZE_POWDER});
    public static PotionUtil StrengthLong = new PotionUtil("StrengthLong", 10, new Item[]{Items.NETHER_WART, Items.BLAZE_POWDER, Items.REDSTONE});
    public static PotionUtil StrengthII = new PotionUtil("StrengthII", 11, new Item[]{Items.NETHER_WART, Items.BLAZE_POWDER, Items.GLOWSTONE_DUST});
    public static PotionUtil Healing = new PotionUtil("Healing", 12, new Item[]{Items.NETHER_WART, Items.SPECKLED_MELON});
    public static PotionUtil HealingII = new PotionUtil("HealingII", 13, new Item[]{Items.NETHER_WART, Items.SPECKLED_MELON, Items.GLOWSTONE_DUST});
    public static PotionUtil Harming = new PotionUtil("Harming", 14, new Item[]{Items.NETHER_WART, Items.SPECKLED_MELON, Items.FERMENTED_SPIDER_EYE});
    public static PotionUtil HarmingII = new PotionUtil("HarmingII", 15, new Item[]{Items.NETHER_WART, Items.SPECKLED_MELON, Items.FERMENTED_SPIDER_EYE, Items.GLOWSTONE_DUST});
    public static PotionUtil Poison = new PotionUtil("Poison", 16, new Item[]{Items.NETHER_WART, Items.SPIDER_EYE});
    public static PotionUtil PoisonLong = new PotionUtil("PoisonLong", 17, new Item[]{Items.NETHER_WART, Items.SPIDER_EYE, Items.REDSTONE});
    public static PotionUtil PoisonII = new PotionUtil("PoisonII", 18, new Item[]{Items.NETHER_WART, Items.SPIDER_EYE, Items.GLOWSTONE_DUST});
    public static PotionUtil Regeneration = new PotionUtil("Regeneration", 19, new Item[]{Items.NETHER_WART, Items.GHAST_TEAR});
    public static PotionUtil RegenerationLong = new PotionUtil("RegenerationLong", 20, new Item[]{Items.NETHER_WART, Items.GHAST_TEAR, Items.REDSTONE});
    public static PotionUtil RegenerationII = new PotionUtil("RegenerationII", 21, new Item[]{Items.NETHER_WART, Items.GHAST_TEAR, Items.GLOWSTONE_DUST});
    public static PotionUtil FireResistance = new PotionUtil("FireResistance", 22, new Item[]{Items.NETHER_WART, Items.MAGMA_CREAM});
    public static PotionUtil FireResistanceLong = new PotionUtil("FireResistanceLong", 23, new Item[]{Items.NETHER_WART, Items.MAGMA_CREAM, Items.REDSTONE});
    public static PotionUtil NightVision = new PotionUtil("NightVision", 24, new Item[]{Items.NETHER_WART, Items.GOLDEN_CARROT});
    public static PotionUtil NightVisionLong = new PotionUtil("NightVisionLong", 25, new Item[]{Items.NETHER_WART, Items.GOLDEN_CARROT, Items.REDSTONE});
    public static PotionUtil Invisibility = new PotionUtil("Invisibility", 26, new Item[]{Items.NETHER_WART, Items.GOLDEN_CARROT, Items.FERMENTED_SPIDER_EYE});
    public static PotionUtil InvisibilityLong = new PotionUtil("InvisibilityLong", 27, new Item[]{Items.NETHER_WART, Items.GOLDEN_CARROT, Items.FERMENTED_SPIDER_EYE, Items.REDSTONE});
    public static PotionUtil Weakness = new PotionUtil("Weakness", 28, new Item[]{Items.FERMENTED_SPIDER_EYE});
    public static PotionUtil WeaknessLong = new PotionUtil("WeaknessLong", 29, new Item[]{Items.FERMENTED_SPIDER_EYE, Items.REDSTONE});
    public static PotionUtil[] Field2032;
    public Item[] Field2031;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public PotionUtil() {
        void var3_1;
        void var2_-1;
        void var1_-1;
        this.Field2031 = var3_1;
    }

    public static PotionUtil Method1853(String string) {
        return Enum.valueOf(PotionUtil.class, string);
    }

    static {
        Field2032 = new PotionUtil[]{Swiftness, SwiftnessLong, SwiftnessII, Slowness, SlownessLong, SlownessII, JumpBoost, JumpBoostLong, JumpBoostII, Strength, StrengthLong, StrengthII, Healing, HealingII, Harming, HarmingII, Poison, PoisonLong, PoisonII, Regeneration, RegenerationLong, RegenerationII, FireResistance, FireResistanceLong, NightVision, NightVisionLong, Invisibility, InvisibilityLong, Weakness, WeaknessLong};
    }

    public static PotionUtil[] Method1854() {
        return Field2032.clone();
    }
}