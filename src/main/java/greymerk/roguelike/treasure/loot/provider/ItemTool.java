package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;

public class ItemTool extends ItemBase {

  private Equipment type;
  private boolean enchant;
  private Quality quality;

  public ItemTool(int weight, int level) {
    super(weight, level);
  }

  public ItemTool(JsonObject data, int weight) throws Exception {
    super(weight);

    this.enchant = !data.has("ench") || data.get("ench").getAsBoolean();

    if (!data.has("level")) {
      throw new Exception("Tool Loot requires a level");
    }
    this.level = data.get("level").getAsInt();


    if (data.has("equipment")) {
      try {
        this.type = Equipment.valueOf(data.get("equipment").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Equipment as: " + data.get("equipment").getAsString());
      }
    }

    if (data.has("quality")) {
      try {
        this.quality = Quality.valueOf(data.get("quality").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Quality as: " + data.get("quality").getAsString());
      }
    }
  }

  public static ItemStack getTool(Random rand, int level, Quality quality, Equipment type, boolean enchant) {
    ItemStack tool = Equipment.get(type, quality == null ? Quality.get(level) : quality);
    return enchant ? Enchant.enchantItem(rand, tool, Enchant.getLevel(rand, level)) : tool;
  }

  public static ItemStack getRandom(Random rand, int level, boolean enchant) {

    if (enchant && rand.nextInt(20 + (level * 10)) == 0) {
      switch (rand.nextInt(3)) {
        case 0:
          return ItemSpecialty.getRandomItem(Equipment.PICK, rand, level);
        case 1:
          return ItemSpecialty.getRandomItem(Equipment.AXE, rand, level);
        case 2:
          return ItemSpecialty.getRandomItem(Equipment.SHOVEL, rand, level);
      }
    }

    ItemStack tool = pickTool(rand, level);

    if (enchant && rand.nextInt(6 - level) == 0) {
      Enchant.enchantItem(rand, tool, Enchant.getLevel(rand, level));
    }

    return tool;
  }

  private static ItemStack pickTool(Random rand, int rank) {

    switch (rand.nextInt(3)) {
      case 0:
        return pickPick(rand, rank);
      case 1:
        return pickAxe(rand, rank);
      case 2:
        return pickShovel(rand, rank);
      default:
        return pickPick(rand, rank);
    }
  }

  private static ItemStack pickAxe(Random rand, int level) {
    Quality quality = Quality.getToolQuality(rand, level);
    switch (quality) {
      case DIAMOND:
        return new ItemStack(Items.DIAMOND_AXE);
      case GOLD:
        return new ItemStack(Items.GOLDEN_AXE);
      case IRON:
        return new ItemStack(Items.IRON_AXE);
      case STONE:
        return new ItemStack(Items.STONE_AXE);
      default:
        return new ItemStack(Items.WOODEN_AXE);
    }
  }

  private static ItemStack pickShovel(Random rand, int level) {

    Quality quality = Quality.getToolQuality(rand, level);
    switch (quality) {
      case DIAMOND:
        return new ItemStack(Items.DIAMOND_SHOVEL);
      case GOLD:
        return new ItemStack(Items.GOLDEN_SHOVEL);
      case IRON:
        return new ItemStack(Items.IRON_SHOVEL);
      case STONE:
        return new ItemStack(Items.STONE_SHOVEL);
      default:
        return new ItemStack(Items.WOODEN_SHOVEL);
    }
  }

  private static ItemStack pickPick(Random rand, int level) {

    Quality quality = Quality.getToolQuality(rand, level);
    switch (quality) {
      case DIAMOND:
        return new ItemStack(Items.DIAMOND_PICKAXE);
      case GOLD:
        return new ItemStack(Items.GOLDEN_PICKAXE);
      case IRON:
        return new ItemStack(Items.IRON_PICKAXE);
      case STONE:
        return new ItemStack(Items.STONE_PICKAXE);
      default:
        return new ItemStack(Items.WOODEN_PICKAXE);
    }
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    if (type != null) {
      return getTool(rand, level, quality, type, enchant);
    }

    return getRandom(rand, level, true);
  }


}
