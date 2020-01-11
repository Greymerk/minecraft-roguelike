package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.util.TextFormat;

public class ItemSpecialty extends ItemBase {

  private Equipment type;
  private Quality quality;

  public ItemSpecialty(int weight, int level) {
    super(weight, level);
  }

  public ItemSpecialty(JsonObject data, int weight) throws Exception {
    super(weight);
    if (!data.has("level")) {
      throw new Exception("Item requires a level");
    }
    this.level = data.get("level").getAsInt();

    if (data.has("quality")) {
      try {
        this.quality = Quality.valueOf(data.get("quality").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Quality as: " + data.get("quality").getAsString());
      }
    }

    if (data.has("equipment")) {
      try {
        this.type = Equipment.valueOf(data.get("equipment").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Equipment as: " + data.get("equipment").getAsString());
      }
    }
  }

  public ItemSpecialty(int weight, int level, Equipment type, Quality q) {
    super(weight, level);
    this.type = type;
    this.quality = q;
  }

  public ItemSpecialty(int weight, int level, Quality q) {
    super(weight, level);
    this.quality = q;
  }

  public static ItemStack getRandomItem(Random rand, int level) {
    return getRandomItem(Equipment.values()[rand.nextInt(Equipment.values().length)], rand, level);
  }

  public static ItemStack getRandomItem(Equipment type, Random rand, int level) {
    return getRandomItem(type, rand, Quality.get(rand, level, type));
  }

  public static ItemStack getRandomItem(Equipment type, Random rand, Quality quality) {

    switch (type) {

      case SWORD:
        return getSword(rand, quality);
      case BOW:
        return getBow(rand, quality);
      case HELMET:
        return getHelmet(rand, quality);
      case CHEST:
        return getChest(rand, quality);
      case LEGS:
        return getLegs(rand, quality);
      case FEET:
        return getBoots(rand, quality);
      case PICK:
        return getPick(rand, quality);
      case AXE:
        return getAxe(rand, quality);
      case SHOVEL:
        return getShovel(rand, quality);
      default:
        return null;
    }
  }

  public static ItemStack getRandomArmour(Random rand, Quality quality) {
    switch (rand.nextInt(4)) {
      case 0:
        return getRandomItem(Equipment.HELMET, rand, quality);
      case 1:
        return getRandomItem(Equipment.CHEST, rand, quality);
      case 2:
        return getRandomItem(Equipment.LEGS, rand, quality);
      case 3:
        return getRandomItem(Equipment.FEET, rand, quality);
      default:
        return null;
    }
  }

  public static ItemStack getRandomTool(Random rand, Quality quality) {
    switch (rand.nextInt(3)) {
      case 0:
        return getRandomItem(Equipment.PICK, rand, quality);
      case 1:
        return getRandomItem(Equipment.AXE, rand, quality);
      case 2:
        return getRandomItem(Equipment.SHOVEL, rand, quality);
      default:
        return null;
    }
  }

  private static ItemStack getShovel(Random rand, Quality quality) {
    ItemStack item;
    if (quality == Quality.DIAMOND) {
      item = new ItemStack(Items.DIAMOND_SHOVEL);
      item.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 3 + rand.nextInt(3));
      item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
      Loot.setItemName(item, "Soul Spade");
      return item;
    } else {
      item = new ItemStack(Items.IRON_SHOVEL);
      item.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 1 + rand.nextInt(2));
      item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
      Loot.setItemName(item, "Grave Spade");
      return item;
    }
  }

  private static ItemStack getAxe(Random rand, Quality quality) {

    ItemStack item;
    if (quality == Quality.DIAMOND) {
      item = new ItemStack(Items.DIAMOND_AXE);
      item.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 3 + rand.nextInt(3));
      item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
      Loot.setItemName(item, "Crystal Head Axe");
      return item;
    } else {
      item = new ItemStack(Items.IRON_AXE);
      item.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 1 + rand.nextInt(2));
      item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
      Loot.setItemName(item, "Woodland Hatchet");
      return item;
    }
  }

  private static ItemStack getPick(Random rand, Quality quality) {

    ItemStack item;

    if (quality == Quality.DIAMOND) {
      item = new ItemStack(Items.DIAMOND_PICKAXE);
      item.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 3 + rand.nextInt(3));
      item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
      if (rand.nextInt(10) == 0) {
        item.addEnchantment(Enchant.getEnchant(Enchant.SILKTOUCH), 1);
        Loot.setItemName(item, "Crystal Pick of Precision");
        return item;
      }
      if (rand.nextInt(10) == 0) {
        item.addEnchantment(Enchant.getEnchant(Enchant.FORTUNE), 2 + rand.nextInt(2));
        Loot.setItemName(item, "Crystal Pick of Prospecting");
        return item;
      }

      if (rand.nextInt(5) == 0) {
        item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
      }

      Loot.setItemName(item, "Crystal Pick");
      return item;
    } else {
      item = new ItemStack(Items.IRON_PICKAXE);
      item.addEnchantment(Enchant.getEnchant(Enchant.EFFICIENCY), 1 + rand.nextInt(2));
      item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
      if (rand.nextInt(10) == 0) {
        item.addEnchantment(Enchant.getEnchant(Enchant.SILKTOUCH), 1);
        Loot.setItemName(item, "Case Hardened Pick of Precision");
        return item;
      }
      if (rand.nextInt(10) == 0) {
        item.addEnchantment(Enchant.getEnchant(Enchant.FORTUNE), 1 + rand.nextInt(3));
        Loot.setItemName(item, "Case Hardened Pick of Prospecting");
        return item;
      }

      if (rand.nextInt(5) == 0) {
        item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
      }

      Loot.setItemName(item, "Case Hardened Pick");
      return item;
    }


  }

  private static ItemStack getSword(Random rand, Quality quality) {

    ItemStack item;
    if (quality == Quality.DIAMOND) {
      item = new ItemStack(Items.DIAMOND_SWORD);
      item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 3 + rand.nextInt(3));
      if (rand.nextInt(10) == 0) {
        item.addEnchantment(Enchant.getEnchant(Enchant.LOOTING), 2 + rand.nextInt(2));
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
        Loot.setItemName(item, "Eldritch Blade of Plundering");
        Loot.setItemLore(item, "The loot taker", TextFormat.DARKGREEN);
        return item;
      }
      if (rand.nextInt(10) == 0) {
        item.addEnchantment(Enchant.getEnchant(Enchant.FIREASPECT), 2 + rand.nextInt(2));
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
        Loot.setItemName(item, "Eldritch Blade of the Inferno");
        Loot.setItemLore(item, "From the fiery depths", TextFormat.DARKGREEN);
        return item;
      }
      item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), quality == Quality.DIAMOND ? 3 : 1 + rand.nextInt(2));
      item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
      Loot.setItemName(item, "Eldritch Blade");
      Loot.setItemLore(item, "Rune Etched", TextFormat.DARKGREEN);
      return item;
    } else {
      item = new ItemStack(Items.IRON_SWORD);
      if (rand.nextBoolean()) {
        item.addEnchantment(Enchant.getEnchant(Enchant.SHARPNESS), 1);
      }
      item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 3);
      item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
      Loot.setItemName(item, "Tempered Blade");
      Loot.setItemLore(item, "Highly Durable", TextFormat.DARKGREEN);
      return item;
    }

  }

  private static ItemStack getBow(Random rand, Quality quality) {

    ItemStack item = new ItemStack(Items.BOW);

    switch (quality) {
      case WOOD:
      case STONE:
        item.addEnchantment(Enchant.getEnchant(Enchant.POWER), 1 + rand.nextInt(3));
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 1);
        Loot.setItemName(item, "Yew Longbow");
        Loot.setItemLore(item, "Superior craftsmanship", TextFormat.DARKGREEN);
        return item;
      case IRON:
        item.addEnchantment(Enchant.getEnchant(Enchant.POWER), 1 + rand.nextInt(3));
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 1 + rand.nextInt(3));
        Loot.setItemName(item, "Laminated Bow");
        Loot.setItemLore(item, "Highly polished", TextFormat.DARKGREEN);
        return item;
      case GOLD:
        item.addEnchantment(Enchant.getEnchant(Enchant.POWER), 3 + rand.nextInt(3));
        if (rand.nextBoolean()) {
          item.addEnchantment(Enchant.getEnchant(Enchant.INFINITY), 1);
        }
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), 1 + rand.nextInt(3));
        Loot.setItemName(item, "Recurve Bow");
        Loot.setItemLore(item, "Beautifully crafted", TextFormat.DARKGREEN);
        return item;
      case DIAMOND:
        item.addEnchantment(Enchant.getEnchant(Enchant.POWER), 3 + rand.nextInt(3));
        item.addEnchantment(Enchant.getEnchant(Enchant.FLAME), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.INFINITY), 1);
        item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));
        item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
        Loot.setItemName(item, "Eldritch Bow");
        Loot.setItemLore(item, "Warm to the touch", TextFormat.DARKGREEN);
        return item;
      default:
        return null;
    }
  }

  private static ItemStack getHelmet(Random rand, Quality quality) {
    ItemStack item;

    String canonical = "";

    switch (quality) {
      case WOOD:
        item = new ItemStack(Items.LEATHER_HELMET);
        ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
        canonical = "Bonnet";
        break;
      case STONE:
        item = new ItemStack(Items.CHAINMAIL_HELMET);
        canonical = "Coif";
        break;
      case IRON:
      case GOLD:
        item = new ItemStack(Items.IRON_HELMET);
        canonical = "Sallet";
        break;
      case DIAMOND:
        item = new ItemStack(Items.DIAMOND_HELMET);
        canonical = "Helm";
        break;
      default:
        item = new ItemStack(Items.LEATHER_HELMET);
    }


    String suffix = "";

    if (rand.nextInt(20) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
      item.addEnchantment(Enchant.getEnchant(Enchant.RESPIRATION), 3);
      item.addEnchantment(Enchant.getEnchant(Enchant.AQUAAFFINITY), 1);
      suffix = "of Diving";
    } else if (rand.nextInt(3) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Deflection";
    } else {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Defense";
    }

    item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));

    if (rand.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
    }

    String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
    Loot.setItemName(item, name);
    return item;
  }

  private static ItemStack getBoots(Random rand, Quality quality) {
    ItemStack item;

    String canonical = "";

    switch (quality) {
      case WOOD:
        item = new ItemStack(Items.LEATHER_BOOTS);
        ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
        canonical = "Shoes";
        break;
      case STONE:
        item = new ItemStack(Items.CHAINMAIL_BOOTS);
        canonical = "Greaves";
        break;
      case IRON:
      case GOLD:
        item = new ItemStack(Items.IRON_BOOTS);
        canonical = "Sabatons";
        break;
      case DIAMOND:
        item = new ItemStack(Items.DIAMOND_BOOTS);
        canonical = "Boots";
        break;
      default:
        item = new ItemStack(Items.LEATHER_BOOTS);
        canonical = "Shoes";
    }

    String suffix = "";

    if (rand.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Warding";
    } else if (rand.nextInt(5) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
      item.addEnchantment(Enchant.getEnchant(Enchant.FEATHERFALLING), quality == Quality.DIAMOND ? 4 : 1 + rand.nextInt(3));
      suffix = "of Lightness";
    } else if (rand.nextInt(3) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Deflection";
    } else {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Defense";
    }

    item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));

    if (rand.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
    }

    String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
    Loot.setItemName(item, name);
    return item;
  }

  private static ItemStack getLegs(Random rand, Quality quality) {
    ItemStack item;

    String canonical = "";

    switch (quality) {
      case WOOD:
        item = new ItemStack(Items.LEATHER_LEGGINGS);
        ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
        canonical = "Pantaloons";
        break;
      case STONE:
        item = new ItemStack(Items.CHAINMAIL_LEGGINGS);
        canonical = "Chausses";
        break;
      case IRON:
      case GOLD:
        item = new ItemStack(Items.IRON_LEGGINGS);
        canonical = "Leg-plates";
        break;
      case DIAMOND:
        item = new ItemStack(Items.DIAMOND_LEGGINGS);
        canonical = "Leggings";
        break;
      default:
        item = new ItemStack(Items.LEATHER_LEGGINGS);
    }

    String suffix = "";

    if (rand.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.FIREPROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Warding";
    } else if (rand.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Integrity";
    } else if (rand.nextInt(3) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Deflection";
    } else {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Defense";
    }

    item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));

    if (rand.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
    }

    String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
    Loot.setItemName(item, name);
    return item;
  }

  private static ItemStack getChest(Random rand, Quality quality) {
    ItemStack item;

    String canonical = "";

    switch (quality) {
      case WOOD:
        item = new ItemStack(Items.LEATHER_CHESTPLATE);
        ItemArmour.dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
        canonical = "Tunic";
        break;
      case STONE:
        item = new ItemStack(Items.CHAINMAIL_CHESTPLATE);
        canonical = "Hauberk";
        break;
      case IRON:
      case GOLD:
        item = new ItemStack(Items.IRON_CHESTPLATE);
        canonical = "Cuirass";
        break;
      case DIAMOND:
        item = new ItemStack(Items.DIAMOND_CHESTPLATE);
        canonical = "Plate";
        break;
      default:
        item = new ItemStack(Items.LEATHER_CHESTPLATE);
        canonical = "Tunic";
    }

    String suffix = "";

    if (rand.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.FIREPROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Flamewarding";
    } else if (rand.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.BLASTPROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Integrity";
    } else if (rand.nextInt(3) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROJECTILEPROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Deflection";
    } else {
      item.addEnchantment(Enchant.getEnchant(Enchant.PROTECTION), getProtectionLevel(quality, rand));
      suffix = "of Defense";
    }

    item.addEnchantment(Enchant.getEnchant(Enchant.UNBREAKING), getUnbreakingLevel(quality, rand));

    if (rand.nextInt(10) == 0) {
      item.addEnchantment(Enchant.getEnchant(Enchant.MENDING), 1);
    }

    String name = getArmourPrefix(quality) + " " + canonical + " " + suffix;
    Loot.setItemName(item, name);
    return item;
  }

  private static int getUnbreakingLevel(Quality quality, Random rand) {
    return quality == Quality.DIAMOND ? 3 : 1 + rand.nextInt(2);
  }

  private static int getProtectionLevel(Quality quality, Random rand) {

    int value = 1;

    switch (quality) {
      case WOOD:
        if (rand.nextInt(3) == 0) {
          value++;
        }
        break;
      case STONE:
        if (rand.nextBoolean()) {
          value++;
        }
        break;
      case IRON:
      case GOLD:
        value += rand.nextInt(3);
        break;
      case DIAMOND:
        value += 2 + rand.nextInt(2);
        break;
    }

    return value;
  }

  private static String getArmourPrefix(Quality quality) {

    switch (quality) {
      case WOOD:
        return "Surplus";
      case STONE:
        return "Riveted";
      case IRON:
        return "Gothic";
      case GOLD:
        return "Jewelled";
      case DIAMOND:
        return "Crystal";
      default:
        return "Strange";
    }
  }

  @Override
  public ItemStack get(Random rand) {
    Equipment t = this.type == null ? Equipment.values()[rand.nextInt(Equipment.values().length)] : this.type;
    Quality q = this.quality == null ? Quality.get(rand, level, t) : this.quality;
    return getRandomItem(t, rand, q);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {

    Quality q;
    switch (level) {
      case 0:
        q = Quality.WOOD;
        break;
      case 1:
        q = Quality.STONE;
        break;
      case 2:
        q = Quality.IRON;
        break;
      case 3:
        q = Quality.GOLD;
        break;
      case 4:
        q = Quality.DIAMOND;
        break;
      default:
        q = Quality.WOOD;
        break;
    }

    return getRandomItem(Equipment.values()[rand.nextInt(Equipment.values().length)], rand, q);
  }
}
