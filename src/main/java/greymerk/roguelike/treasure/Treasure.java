package greymerk.roguelike.treasure;


import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import static java.util.Collections.shuffle;
import static java.util.Collections.singletonList;

public enum Treasure {

  ARMOUR,
  WEAPONS,
  BLOCKS,
  ENCHANTING,
  FOOD,
  ORE,
  POTIONS,
  STARTER,
  TOOLS,
  SUPPLIES,
  SMITH,
  MUSIC,
  REWARD,
  EMPTY,
  BREWING;

  private static final List<Treasure> common = new ArrayList<>(Arrays.asList(TOOLS, ARMOUR, WEAPONS));

  public static ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, Treasure type, int level, boolean trapped) throws ChestPlacementException {
    ITreasureChest chest = new TreasureChest(type);
    return chest.generate(editor, rand, pos, level, trapped);
  }

  public static ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) throws ChestPlacementException {
    Treasure type = getChestType(rand, level);
    return generate(editor, rand, pos, type, level, trapped);
  }

  public static ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, Treasure type, int level) throws ChestPlacementException {
    return generate(editor, rand, pos, type, level, false);
  }

  public static List<ITreasureChest> generate(IWorldEditor editor, Random rand, List<Coord> space, Treasure type, int level) {
    return createChests(editor, rand, 1, space, new ArrayList<>(singletonList(type)), level);
  }

  public static void createChests(IWorldEditor editor, Random rand, int numChests, List<Coord> space, int level) {
    createChests(editor, rand, numChests, space, level, false);
  }

  public static void createChests(IWorldEditor editor, Random rand, int numChests, List<Coord> space, int level, boolean trapped) {
    shuffle(space, rand);

    space.stream()
        .limit(numChests)
        .filter(block -> isValidChestSpace(editor, block))
        .forEach(block -> safeGenerate(editor, rand, level, block));
  }

  private static void safeGenerate(IWorldEditor editor, Random rand, int level, Coord block) {
    try {
      generate(editor, rand, block, getChestType(rand, level), level);
    } catch (ChestPlacementException ignored) {
    }
  }

  public static List<ITreasureChest> createChests(IWorldEditor editor, Random rand, int numChests, List<Coord> space, List<Treasure> types, int level) {

    List<ITreasureChest> chests = new ArrayList<>();

    shuffle(space, rand);

    int count = 0;

    for (Coord block : space) {

      if (count == numChests) {
        return chests;
      }

      if (isValidChestSpace(editor, block)) {
        try {
          ITreasureChest chest = generate(editor, rand, block, types.get(rand.nextInt(types.size())), level);
          chests.add(chest);
          count++;
        } catch (ChestPlacementException cpe) {
          // do nothing
        }
      }
    }

    return chests;
  }

  private static Treasure getChestType(Random rand, int level) {
    return common.get(rand.nextInt(common.size()));
  }

  public static boolean isValidChestSpace(IWorldEditor editor, Coord pos) {

    if (!editor.isAirBlock(pos)) {
      return false;
    }

    Coord cursor;
    cursor = new Coord(pos);
    cursor.add(Cardinal.DOWN);

    if (!editor.getBlock(cursor).getMaterial().isSolid()) {
      return false;
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(pos);
      cursor.add(dir);
      if (editor.getBlock(cursor).getBlock() == Blocks.CHEST) {
        return false;
      }
    }

    return true;
  }
}
