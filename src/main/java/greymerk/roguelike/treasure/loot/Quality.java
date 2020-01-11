package greymerk.roguelike.treasure.loot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public enum Quality {

  WOOD,
  STONE,
  IRON,
  GOLD,
  DIAMOND;

  private static Map<Integer, IWeighted<Quality>> armourQuality;
  private static Map<Integer, IWeighted<Quality>> weaponQuality;
  private static Map<Integer, IWeighted<Quality>> toolQuality;

  static {
    armourQuality = new HashMap<>();
    weaponQuality = new HashMap<>();
    toolQuality = new HashMap<>();

    for (int i = 0; i < 5; ++i) {
      WeightedRandomizer<Quality> armour = new WeightedRandomizer<>();
      switch (i) {
        case 0:
          armour.add(new WeightedChoice<>(WOOD, 250));
          armour.add(new WeightedChoice<>(STONE, 50));
          armour.add(new WeightedChoice<>(IRON, 20));
          armour.add(new WeightedChoice<>(GOLD, 3));
          armour.add(new WeightedChoice<>(DIAMOND, 1));
          break;
        case 1:
          armour.add(new WeightedChoice<>(WOOD, 150));
          armour.add(new WeightedChoice<>(STONE, 30));
          armour.add(new WeightedChoice<>(IRON, 10));
          armour.add(new WeightedChoice<>(GOLD, 3));
          armour.add(new WeightedChoice<>(DIAMOND, 1));
          break;
        case 2:
          armour.add(new WeightedChoice<>(WOOD, 50));
          armour.add(new WeightedChoice<>(STONE, 30));
          armour.add(new WeightedChoice<>(IRON, 20));
          armour.add(new WeightedChoice<>(GOLD, 3));
          armour.add(new WeightedChoice<>(DIAMOND, 1));
          break;
        case 3:
          armour.add(new WeightedChoice<>(WOOD, 20));
          armour.add(new WeightedChoice<>(STONE, 10));
          armour.add(new WeightedChoice<>(IRON, 10));
          armour.add(new WeightedChoice<>(GOLD, 5));
          armour.add(new WeightedChoice<>(DIAMOND, 3));
          break;
        case 4:
          armour.add(new WeightedChoice<>(WOOD, 2));
          armour.add(new WeightedChoice<>(STONE, 3));
          armour.add(new WeightedChoice<>(IRON, 10));
          armour.add(new WeightedChoice<>(GOLD, 3));
          armour.add(new WeightedChoice<>(DIAMOND, 3));
          break;
      }
      armourQuality.put(i, armour);

      WeightedRandomizer<Quality> weapon = new WeightedRandomizer<>();
      switch (i) {
        case 0:
          weapon.add(new WeightedChoice<>(WOOD, 200));
          weapon.add(new WeightedChoice<>(STONE, 50));
          weapon.add(new WeightedChoice<>(IRON, 10));
          weapon.add(new WeightedChoice<>(GOLD, 3));
          weapon.add(new WeightedChoice<>(DIAMOND, 1));
          break;
        case 1:
          weapon.add(new WeightedChoice<>(WOOD, 100));
          weapon.add(new WeightedChoice<>(STONE, 30));
          weapon.add(new WeightedChoice<>(IRON, 10));
          weapon.add(new WeightedChoice<>(GOLD, 3));
          weapon.add(new WeightedChoice<>(DIAMOND, 1));
          break;
        case 2:
          weapon.add(new WeightedChoice<>(WOOD, 50));
          weapon.add(new WeightedChoice<>(STONE, 20));
          weapon.add(new WeightedChoice<>(IRON, 10));
          weapon.add(new WeightedChoice<>(GOLD, 3));
          weapon.add(new WeightedChoice<>(DIAMOND, 1));
          break;
        case 3:
          weapon.add(new WeightedChoice<>(WOOD, 1));
          weapon.add(new WeightedChoice<>(STONE, 3));
          weapon.add(new WeightedChoice<>(IRON, 5));
          weapon.add(new WeightedChoice<>(GOLD, 3));
          weapon.add(new WeightedChoice<>(DIAMOND, 1));
          break;
        case 4:
          weapon.add(new WeightedChoice<>(WOOD, 1));
          weapon.add(new WeightedChoice<>(STONE, 2));
          weapon.add(new WeightedChoice<>(IRON, 15));
          weapon.add(new WeightedChoice<>(GOLD, 5));
          weapon.add(new WeightedChoice<>(DIAMOND, 3));
          break;
      }
      weaponQuality.put(i, weapon);

      WeightedRandomizer<Quality> tool = new WeightedRandomizer<>();
      switch (i) {
        case 0:
          tool.add(new WeightedChoice<>(WOOD, 10));
          tool.add(new WeightedChoice<>(STONE, 20));
          tool.add(new WeightedChoice<>(IRON, 10));
          tool.add(new WeightedChoice<>(GOLD, 3));
          tool.add(new WeightedChoice<>(DIAMOND, 1));
          break;
        case 1:
          tool.add(new WeightedChoice<>(WOOD, 2));
          tool.add(new WeightedChoice<>(STONE, 10));
          tool.add(new WeightedChoice<>(IRON, 10));
          tool.add(new WeightedChoice<>(GOLD, 3));
          tool.add(new WeightedChoice<>(DIAMOND, 1));
          break;
        case 2:
          tool.add(new WeightedChoice<>(WOOD, 1));
          tool.add(new WeightedChoice<>(STONE, 5));
          tool.add(new WeightedChoice<>(IRON, 10));
          tool.add(new WeightedChoice<>(GOLD, 5));
          tool.add(new WeightedChoice<>(DIAMOND, 3));
          break;
        case 3:
          tool.add(new WeightedChoice<>(WOOD, 1));
          tool.add(new WeightedChoice<>(STONE, 3));
          tool.add(new WeightedChoice<>(IRON, 10));
          tool.add(new WeightedChoice<>(GOLD, 5));
          tool.add(new WeightedChoice<>(DIAMOND, 5));
          break;
        case 4:
          tool.add(new WeightedChoice<>(WOOD, 1));
          tool.add(new WeightedChoice<>(STONE, 2));
          tool.add(new WeightedChoice<>(IRON, 10));
          tool.add(new WeightedChoice<>(GOLD, 3));
          tool.add(new WeightedChoice<>(DIAMOND, 5));
          break;
      }
      toolQuality.put(i, tool);
    }

  }

  public static Quality get(Random rand, int level, Equipment type) {

    switch (type) {
      case SWORD:
      case BOW:
        return weaponQuality.get(level).get(rand);
      case HELMET:
      case CHEST:
      case LEGS:
      case FEET:
        return armourQuality.get(level).get(rand);
      case PICK:
      case AXE:
      case SHOVEL:
        return toolQuality.get(level).get(rand);
    }
    return null;
  }

  public static Quality get(int level) {
    switch (level) {
      case 0:
        return Quality.WOOD;
      case 1:
        return Quality.STONE;
      case 2:
        return Quality.IRON;
      case 3:
        return Quality.GOLD;
      case 4:
        return Quality.DIAMOND;
      default:
        return Quality.WOOD;
    }
  }

  public static Quality getArmourQuality(Random rand, int level) {
    return armourQuality.get(level).get(rand);
  }

  public static Quality getToolQuality(Random rand, int level) {
    return toolQuality.get(level).get(rand);
  }

  public static Quality getWeaponQuality(Random rand, int level) {
    return weaponQuality.get(level).get(rand);
  }


}
