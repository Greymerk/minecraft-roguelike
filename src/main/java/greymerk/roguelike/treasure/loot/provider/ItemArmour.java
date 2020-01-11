package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.Slot;

public class ItemArmour extends ItemBase {

  private Equipment type;
  private boolean enchant;
  private Quality quality;

  public ItemArmour(int weight, int level) {
    super(weight, level);
  }

  public ItemArmour(JsonObject data, int weight) throws Exception {
    super(weight);

    this.enchant = !data.has("ench") || data.get("ench").getAsBoolean();
    if (!data.has("level")) {
      throw new Exception("Armour requires a level");
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
      this.level = data.get("level").getAsInt();

      try {
        this.quality = Quality.valueOf(data.get("quality").getAsString().toUpperCase());
      } catch (Exception e) {
        throw new Exception("No such Quality as: " + data.get("quality").getAsString());
      }
    }
  }

  public static ItemStack get(Random rand, int level, Quality quality, Equipment type, boolean enchant) {
    ItemStack tool = Equipment.get(type, quality == null ? Quality.get(level) : quality);
    return enchant ? Enchant.enchantItem(rand, tool, Enchant.getLevel(rand, level)) : tool;
  }

  public static ItemStack getRandom(Random rand, int level, boolean enchant) {
    return getRandom(rand, level,
        Slot.getSlotByNumber(rand.nextInt(4) + 1),
        enchant ? Enchant.getLevel(rand, level) : 0);
  }

  public static ItemStack getRandom(Random rand, int level, Slot slot, boolean enchant) {
    return getRandom(rand, level, slot, enchant ? Enchant.getLevel(rand, level) : 0);
  }

  @SuppressWarnings("incomplete-switch")
  public static ItemStack getRandom(Random rand, int level, Slot slot, int enchantLevel) {

    if (enchantLevel > 0 && rand.nextInt(20 + (level * 10)) == 0) {
      switch (slot) {
        case HEAD:
          return ItemSpecialty.getRandomItem(Equipment.HELMET, rand, level);
        case CHEST:
          return ItemSpecialty.getRandomItem(Equipment.CHEST, rand, level);
        case LEGS:
          return ItemSpecialty.getRandomItem(Equipment.LEGS, rand, level);
        case FEET:
          return ItemSpecialty.getRandomItem(Equipment.FEET, rand, level);
      }
    }

    ItemStack item = get(rand, slot, Quality.getArmourQuality(rand, level));

    if (enchantLevel > 0) {
      Enchant.enchantItem(rand, item, enchantLevel);
    }

    return item;

  }

  @SuppressWarnings("incomplete-switch")
  public static ItemStack get(Random rand, Slot slot, Quality quality) {

    switch (slot) {

      case HEAD:
        switch (quality) {

          case DIAMOND:
            return new ItemStack(Items.DIAMOND_HELMET);
          case GOLD:
            return new ItemStack(Items.GOLDEN_HELMET);
          case IRON:
            return new ItemStack(Items.IRON_HELMET);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_HELMET);
          default:
            ItemStack item = new ItemStack(Items.LEATHER_HELMET);
            dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
            return item;
        }

      case FEET:
        switch (quality) {

          case DIAMOND:
            return new ItemStack(Items.DIAMOND_BOOTS);
          case GOLD:
            return new ItemStack(Items.GOLDEN_BOOTS);
          case IRON:
            return new ItemStack(Items.IRON_BOOTS);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_BOOTS);
          default:
            ItemStack item = new ItemStack(Items.LEATHER_BOOTS);
            dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
            return item;
        }

      case CHEST:
        switch (quality) {

          case DIAMOND:
            return new ItemStack(Items.DIAMOND_CHESTPLATE);
          case GOLD:
            return new ItemStack(Items.GOLDEN_CHESTPLATE);
          case IRON:
            return new ItemStack(Items.IRON_CHESTPLATE);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_CHESTPLATE);
          default:
            ItemStack item = new ItemStack(Items.LEATHER_CHESTPLATE);
            dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
            return item;
        }
      case LEGS:
        switch (quality) {

          case DIAMOND:
            return new ItemStack(Items.DIAMOND_LEGGINGS);
          case GOLD:
            return new ItemStack(Items.GOLDEN_LEGGINGS);
          case IRON:
            return new ItemStack(Items.IRON_LEGGINGS);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_LEGGINGS);
          default:
            ItemStack item = new ItemStack(Items.LEATHER_LEGGINGS);
            dyeArmor(item, rand.nextInt(256), rand.nextInt(255), rand.nextInt(255));
            return item;
        }
    }
    return null;
  }

  public static ItemStack dyeArmor(ItemStack armor, int r, int g, int b) {

    int color = r << 16 | g << 8 | b << 0;

    NBTTagCompound nbtdata = armor.getTagCompound();

    if (nbtdata == null) {
      nbtdata = new NBTTagCompound();
      armor.setTagCompound(nbtdata);
    }

    NBTTagCompound nbtDisplay = nbtdata.getCompoundTag("display");

    if (!nbtdata.hasKey("display")) {
      nbtdata.setTag("display", nbtDisplay);
    }

    nbtDisplay.setInteger("color", color);

    return armor;
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    if (type != null || quality != null) {
      return get(rand, level, quality, type, enchant);
    }
    return getRandom(rand, level, true);
  }


}
