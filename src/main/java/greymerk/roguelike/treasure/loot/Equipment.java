package greymerk.roguelike.treasure.loot;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public enum Equipment {

  SWORD,
  BOW,
  HELMET,
  CHEST,
  LEGS,
  FEET,
  PICK,
  AXE,
  SHOVEL;

  public static Equipment[] armour = new Equipment[]{HELMET, CHEST, LEGS, FEET};

  public static String getName(Equipment type, Quality quality) {

    String qualityName;
    String itemName;

    switch (type) {
      case SWORD:
        itemName = "sword";
        break;
      case BOW:
        return "minecraft:bow";
      case HELMET:
        itemName = "helmet";
        break;
      case CHEST:
        itemName = "chestplate";
        break;
      case LEGS:
        itemName = "leggings";
        break;
      case FEET:
        itemName = "boots";
        break;
      case PICK:
        itemName = "pickaxe";
        break;
      case AXE:
        itemName = "axe";
        break;
      case SHOVEL:
        itemName = "shovel";
        break;
      default:
        return "minecraft:stick";
    }

    if (Arrays.asList(Equipment.armour).contains(type)) {
      switch (quality) {
        case WOOD:
          qualityName = "leather";
          break;
        case STONE:
          qualityName = "chainmail";
          break;
        case IRON:
          qualityName = "iron";
          break;
        case GOLD:
          qualityName = "golden";
          break;
        case DIAMOND:
          qualityName = "diamond";
          break;
        default:
          return "minecraft:stick";
      }
    } else {
      switch (quality) {
        case WOOD:
          qualityName = "wooden";
          break;
        case STONE:
          qualityName = "stone";
          break;
        case IRON:
          qualityName = "iron";
          break;
        case GOLD:
          qualityName = "golden";
          break;
        case DIAMOND:
          qualityName = "diamond";
          break;
        default:
          return "minecraft:stick";
      }
    }

    return "minecraft:" + qualityName + "_" + itemName;
  }

  public static ItemStack get(Equipment type, Quality quality) {
    switch (type) {
      case SWORD:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.WOODEN_SWORD);
          case STONE:
            return new ItemStack(Items.STONE_SWORD);
          case IRON:
            return new ItemStack(Items.IRON_SWORD);
          case GOLD:
            return new ItemStack(Items.GOLDEN_SWORD);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_SWORD);
          default:
        }
      case BOW:
        return new ItemStack(Items.BOW);
      case HELMET:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.LEATHER_HELMET);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_HELMET);
          case IRON:
            return new ItemStack(Items.IRON_HELMET);
          case GOLD:
            return new ItemStack(Items.GOLDEN_HELMET);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_HELMET);
          default:
        }
      case CHEST:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.LEATHER_CHESTPLATE);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_CHESTPLATE);
          case IRON:
            return new ItemStack(Items.IRON_CHESTPLATE);
          case GOLD:
            return new ItemStack(Items.GOLDEN_CHESTPLATE);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_CHESTPLATE);
          default:
        }
      case LEGS:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.LEATHER_LEGGINGS);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_LEGGINGS);
          case IRON:
            return new ItemStack(Items.IRON_LEGGINGS);
          case GOLD:
            return new ItemStack(Items.GOLDEN_LEGGINGS);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_LEGGINGS);
          default:
        }
      case FEET:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.LEATHER_BOOTS);
          case STONE:
            return new ItemStack(Items.CHAINMAIL_BOOTS);
          case IRON:
            return new ItemStack(Items.IRON_BOOTS);
          case GOLD:
            return new ItemStack(Items.GOLDEN_BOOTS);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_BOOTS);
          default:
        }
      case PICK:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.WOODEN_PICKAXE);
          case STONE:
            return new ItemStack(Items.STONE_PICKAXE);
          case IRON:
            return new ItemStack(Items.IRON_PICKAXE);
          case GOLD:
            return new ItemStack(Items.GOLDEN_PICKAXE);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_PICKAXE);
          default:
        }
      case AXE:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.WOODEN_AXE);
          case STONE:
            return new ItemStack(Items.STONE_AXE);
          case IRON:
            return new ItemStack(Items.IRON_AXE);
          case GOLD:
            return new ItemStack(Items.GOLDEN_AXE);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_AXE);
          default:
        }
      case SHOVEL:
        switch (quality) {
          case WOOD:
            return new ItemStack(Items.WOODEN_SHOVEL);
          case STONE:
            return new ItemStack(Items.STONE_SHOVEL);
          case IRON:
            return new ItemStack(Items.IRON_SHOVEL);
          case GOLD:
            return new ItemStack(Items.GOLDEN_SHOVEL);
          case DIAMOND:
            return new ItemStack(Items.DIAMOND_SHOVEL);
          default:
        }
      default:
        return new ItemStack(Items.STICK);
    }
  }
}
