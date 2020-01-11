package greymerk.roguelike.treasure.loot;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;

import java.util.List;
import java.util.Random;

public enum Enchant {

  SHARPNESS,
  SMITE,
  ARTHOPODS,
  LOOTING,
  KNOCKBACK,
  FIREASPECT,
  AQUAAFFINITY,
  RESPIRATION,
  FEATHERFALLING,
  DEPTHSTRIDER,
  PROTECTION,
  BLASTPROTECTION,
  FIREPROTECTION,
  PROJECTILEPROTECTION,
  THORNS,
  UNBREAKING,
  EFFICIENCY,
  SILKTOUCH,
  FORTUNE,
  POWER,
  PUNCH,
  FLAME,
  INFINITY,
  LURE,
  LUCKOFTHESEA,
  MENDING;

  public static Enchantment getEnchant(Enchant type) {
    String name = getName(type);
    return Enchantment.getEnchantmentByLocation(name);
  }

  public static String getName(Enchant type) {
    switch (type) {
      case SHARPNESS:
        return "sharpness";
      case SMITE:
        return "smite";
      case ARTHOPODS:
        return "bane_of_arthropods";
      case LOOTING:
        return "looting";
      case KNOCKBACK:
        return "knockback";
      case FIREASPECT:
        return "fire_aspect";
      case AQUAAFFINITY:
        return "aqua_affinity";
      case RESPIRATION:
        return "respiration";
      case FEATHERFALLING:
        return "feather_falling";
      case DEPTHSTRIDER:
        return "depth_strider";
      case PROTECTION:
        return "protection";
      case BLASTPROTECTION:
        return "blast_protection";
      case FIREPROTECTION:
        return "fire_protection";
      case PROJECTILEPROTECTION:
        return "projectile_protection";
      case THORNS:
        return "thorns";
      case UNBREAKING:
        return "unbreaking";
      case EFFICIENCY:
        return "efficiency";
      case SILKTOUCH:
        return "silk_touch";
      case FORTUNE:
        return "fortune";
      case POWER:
        return "power";
      case PUNCH:
        return "punch";
      case FLAME:
        return "flame";
      case INFINITY:
        return "infinity";
      case LURE:
        return "lure";
      case LUCKOFTHESEA:
        return "luck_of_the_sea";
      case MENDING:
        return "mending";
      default:
        return "efficiency";
    }
  }

  public static int getLevel(Random rand, int level) {

    switch (level) {
      case 4:
        return 30 + rand.nextInt(10);
      case 3:
        return 15 + rand.nextInt(15);
      case 2:
        return 5 + rand.nextInt(15);
      case 1:
        return 1 + rand.nextInt(10);
      case 0:
        return 1 + rand.nextInt(5);
      default:
        return 1;
    }
  }

  public static boolean canEnchant(EnumDifficulty difficulty, Random rand, int level) {

    if (difficulty == null) {
      difficulty = EnumDifficulty.NORMAL;
    }

    switch (difficulty) {
      case PEACEFUL:
        return false;
      case EASY:
        return rand.nextInt(6) == 0;
      case NORMAL:
        return level >= 1 && rand.nextInt(4) == 0;
      case HARD:
        return rand.nextBoolean();
    }

    return false;
  }

  public static ItemStack enchantItem(Random rand, ItemStack item, int enchantLevel) {

    if (item == null) {
      return null;
    }

    List<EnchantmentData> enchants = null;
    try {
      enchants = EnchantmentHelper.buildEnchantmentList(rand, item, enchantLevel, false);
    } catch (NullPointerException e) {
      throw e;
    }

    boolean isBook = item.getItem() == Items.BOOK;

    if (isBook) {
      item = new ItemStack(Items.ENCHANTED_BOOK);
      if (enchants.size() > 1) {
        enchants.remove(rand.nextInt(enchants.size()));
      }
    }

    for (EnchantmentData toAdd : enchants) {
      if (isBook) {
        ItemEnchantedBook.addEnchantment(item, toAdd);
      } else {
        item.addEnchantment(toAdd.enchantment, toAdd.enchantmentLevel);
      }
    }

    return item;
  }
}
