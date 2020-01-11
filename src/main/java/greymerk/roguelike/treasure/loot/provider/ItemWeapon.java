package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;


public class ItemWeapon extends ItemBase {

  private Equipment type;
  private boolean enchant;
  private Quality quality;


  public ItemWeapon(int weight, int level) {
    super(weight, level);
  }

  public ItemWeapon(JsonObject data, int weight) throws Exception {
    super(weight);

    this.enchant = !data.has("ench") || data.get("ench").getAsBoolean();

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

    if (!data.has("level")) {
      throw new Exception("Weapon Loot requires a level");
    }
    this.level = data.get("level").getAsInt();
  }

  public static ItemStack getRandom(Random rand, int rank, boolean enchant) {

    if (rand.nextInt(10) == 0) {
      return ItemWeapon.getBow(rand, rank, enchant);
    } else {
      return ItemWeapon.getSword(rand, rank, enchant);
    }
  }

  public static ItemStack getBow(Random rand, int level, boolean enchant) {

    if (rand.nextInt(20 + (level * 10)) == 0) {
      return ItemSpecialty.getRandomItem(Equipment.BOW, rand, level);
    }

    ItemStack bow = new ItemStack(Items.BOW);

    if (enchant && rand.nextInt(6 - level) == 0) {
      Enchant.enchantItem(rand, bow, Enchant.getLevel(rand, level));
    }

    return bow;

  }

  public static ItemStack getSword(Random rand, int level, boolean enchant) {
    ItemStack sword;

    if (enchant && rand.nextInt(10 + (level * 10)) == 0) {
      return ItemSpecialty.getRandomItem(Equipment.SWORD, rand, level);
    }

    sword = pickSword(rand, level);

    if (enchant && rand.nextInt(6 - level) == 0) {
      Enchant.enchantItem(rand, sword, Enchant.getLevel(rand, level));
    }

    return sword;
  }

  public static ItemStack getSword(Random rand, int level, boolean enchant, Quality quality) {
    ItemStack sword = quality != null ? getSwordByQuality(quality) : pickSword(rand, level);
    return enchant ? Enchant.enchantItem(rand, sword, Enchant.getLevel(rand, level)) : sword;
  }

  private static ItemStack pickSword(Random rand, int level) {
    Quality quality = Quality.getWeaponQuality(rand, level);
    return getSwordByQuality(quality);
  }

  private static ItemStack getSwordByQuality(Quality quality) {
    switch (quality) {
      case DIAMOND:
        return new ItemStack(Items.DIAMOND_SWORD);
      case GOLD:
        return new ItemStack(Items.GOLDEN_SWORD);
      case IRON:
        return new ItemStack(Items.IRON_SWORD);
      case STONE:
        return new ItemStack(Items.STONE_SWORD);
      default:
        return new ItemStack(Items.WOODEN_SWORD);
    }
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    if (type != null) {
      switch (type) {
        case BOW:
          return getBow(rand, level, enchant);
        case SWORD:
          return getSword(rand, level, enchant, quality);
        default:
          return getSword(rand, level, enchant);
      }
    }
    return getRandom(rand, level, true);
  }


}
