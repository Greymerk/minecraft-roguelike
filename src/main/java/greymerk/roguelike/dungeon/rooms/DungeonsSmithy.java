package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.ChestPlacementException;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.Anvil;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.redstone.Hopper;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonsSmithy extends DungeonBase {

  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    ITheme theme = settings.getTheme();

    Coord cursor;

    Cardinal dir = entrances[0];

    clearBoxes(editor, rand, theme, dir, origin);

    cursor = new Coord(origin);
    cursor.add(dir, 6);
    sideRoom(editor, rand, settings, dir, cursor);
    anvilRoom(editor, rand, settings, dir, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.reverse(dir), 6);
    sideRoom(editor, rand, settings, dir, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.reverse(dir), 9);
    MetaBlock air = BlockType.get(BlockType.AIR);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP);
    air.set(editor, cursor);

    mainRoom(editor, rand, settings, dir, origin);

    return true;
  }

  private void sideRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();

    Coord cursor;
    Coord start;
    Coord end;

    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();

    for (Cardinal side : Cardinal.orthogonal(dir)) {

      start = new Coord(origin);
      start.add(Cardinal.UP, 3);
      end = new Coord(start);
      start.add(side, 2);
      start.add(Cardinal.reverse(dir), 2);
      end.add(side, 3);
      end.add(dir, 2);
      RectSolid.fill(editor, rand, start, end, wall);

      start.add(dir);
      end = new Coord(start);
      end.add(dir, 2);
      RectSolid.fill(editor, rand, start, end, stair.setOrientation(Cardinal.reverse(side), true));

      for (Cardinal o : Cardinal.orthogonal(side)) {
        start = new Coord(origin);
        start.add(side, 3);
        start.add(o, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, pillar);

        cursor = new Coord(end);
        cursor.add(Cardinal.reverse(side));
        stair.setOrientation(Cardinal.reverse(side), true).set(editor, cursor);
        cursor.add(Cardinal.UP);
        cursor.add(Cardinal.reverse(side));
        stair.setOrientation(Cardinal.reverse(side), true).set(editor, cursor);

        cursor = new Coord(end);
        cursor.add(Cardinal.reverse(o));
        stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
      }
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    overheadLight(editor, settings, cursor);
  }

  private void clearBoxes(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

    IBlockFactory wall = theme.getPrimary().getWall();


    Coord cursor;
    Coord start;
    Coord end;

    // end room
    cursor = new Coord(origin);
    cursor.add(dir, 6);

    start = new Coord(cursor);
    start.add(Cardinal.DOWN);
    start.add(dir, 3);
    start.add(Cardinal.left(dir), 4);

    end = new Coord(cursor);
    end.add(Cardinal.UP, 4);
    end.add(Cardinal.reverse(dir), 3);
    end.add(Cardinal.right(dir), 4);

    RectHollow.fill(editor, rand, start, end, wall);

    // entrance
    cursor = new Coord(origin);
    cursor.add(Cardinal.reverse(dir), 6);

    start = new Coord(cursor);
    start.add(Cardinal.DOWN);
    start.add(dir, 3);
    start.add(Cardinal.left(dir), 4);

    end = new Coord(cursor);
    end.add(Cardinal.UP, 4);
    end.add(Cardinal.reverse(dir), 3);
    end.add(Cardinal.right(dir), 4);

    RectHollow.fill(editor, rand, start, end, wall);

    // middle

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    start.add(Cardinal.left(dir), 6);
    start.add(Cardinal.reverse(dir), 4);

    end = new Coord(origin);
    end.add(Cardinal.UP, 6);
    end.add(Cardinal.right(dir), 6);
    end.add(dir, 4);

    RectHollow.fill(editor, rand, start, end, wall, false, true);

  }

  private void mainRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(dir, 3);
    start.add(Cardinal.UP, 4);
    end = new Coord(start);
    start.add(Cardinal.left(dir), 5);
    end.add(Cardinal.right(dir), 5);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, wall);
    start.add(Cardinal.reverse(dir), 6);
    end.add(Cardinal.reverse(dir), 6);
    RectSolid.fill(editor, rand, start, end, wall);

    for (Cardinal side : Cardinal.orthogonal(dir)) {
      for (Cardinal o : Cardinal.orthogonal(side)) {
        cursor = new Coord(origin);
        cursor.add(side, 2);
        cursor.add(o, 3);
        mainPillar(editor, rand, theme, o, cursor);
        cursor.add(side, 3);
        mainPillar(editor, rand, theme, o, cursor);
      }
    }

    cursor = new Coord(origin);
    smelterSide(editor, rand, settings, Cardinal.left(dir), origin);
    fireplace(editor, rand, Cardinal.right(dir), origin);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 6);
    overheadLight(editor, settings, cursor);

  }

  private void mainPillar(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, pillar);
    cursor = new Coord(end);
    cursor.add(Cardinal.left(dir));
    stair.setOrientation(Cardinal.left(dir), true).set(editor, cursor);
    cursor = new Coord(end);
    cursor.add(Cardinal.right(dir));
    stair.setOrientation(Cardinal.right(dir), true).set(editor, cursor);
    cursor = new Coord(end);
    cursor.add(Cardinal.reverse(dir));
    stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    wall.set(editor, rand, cursor);
    cursor.add(Cardinal.reverse(dir));
    stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    cursor.add(Cardinal.UP);
    start = new Coord(cursor);
    end = new Coord(cursor);
    end.add(dir, 2);
    RectSolid.fill(editor, rand, start, end, wall);
    cursor = new Coord(end);
    cursor.add(Cardinal.left(dir));
    stair.setOrientation(Cardinal.left(dir), true).set(editor, cursor);
    cursor = new Coord(end);
    cursor.add(Cardinal.right(dir));
    stair.setOrientation(Cardinal.right(dir), true).set(editor, cursor);
  }


  private void smelterSide(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(dir, 5);
    end = new Coord(start);
    start.add(Cardinal.left(dir), 2);
    end.add(Cardinal.right(dir), 2);
    RectSolid.fill(editor, rand, start, end, wall);
    start.add(Cardinal.reverse(dir));
    end.add(Cardinal.reverse(dir));
    IStair stair = theme.getPrimary().getStair();
    stair = stair.setOrientation(Cardinal.reverse(dir), false);
    RectSolid.fill(editor, rand, start, end, stair);


    for (Cardinal o : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(dir, 3);
      cursor.add(o);
      smelter(editor, rand, dir, cursor);

      cursor.add(o, 2);
      wall.set(editor, rand, cursor);
      cursor.add(dir);
      wall.set(editor, rand, cursor);
    }
  }

  private void smelter(IWorldEditor editor, Random rand, Cardinal dir, Coord origin) {
    Coord cursor;
    try {
      Treasure.generate(editor, rand, origin, Treasure.EMPTY, 1, false);
    } catch (ChestPlacementException cpe) {
      // do nothing
    }
    cursor = new Coord(origin);
    cursor.add(dir, 2);
    cursor.add(Cardinal.UP, 2);
    try {
      Treasure.generate(editor, rand, cursor, Treasure.EMPTY, 1, false);
    } catch (ChestPlacementException cpe) {
      // do nothing
    }
    cursor.add(Cardinal.UP);
    cursor.add(Cardinal.reverse(dir));
    try {
      Treasure.generate(editor, rand, cursor, Treasure.EMPTY, 1, false);
    } catch (ChestPlacementException cpe) {
      // do nothing
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    cursor.add(dir);
    Furnace.generate(editor, Cardinal.reverse(dir), cursor);

    cursor = new Coord(origin);
    cursor.add(dir);
    Hopper.generate(editor, Cardinal.reverse(dir), cursor);

    cursor.add(dir);
    cursor.add(Cardinal.UP);
    Hopper.generate(editor, Cardinal.reverse(dir), cursor);

    cursor.add(Cardinal.reverse(dir));
    cursor.add(Cardinal.UP);
    Hopper.generate(editor, Cardinal.DOWN, cursor);
  }

  private void fireplace(IWorldEditor editor, Random rand, Cardinal dir, Coord origin) {

    IStair stair = new MetaStair(StairType.BRICK);
    MetaBlock brick = BlockType.get(BlockType.BRICK);
    MetaBlock brickSlab = Slab.get(Slab.BRICK);
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(dir, 4);
    end = new Coord(start);
    start.add(Cardinal.DOWN);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    end.add(dir, 2);
    end.add(Cardinal.UP, 5);

    RectSolid.fill(editor, rand, start, end, brick);

    start = new Coord(origin);
    start.add(dir, 5);
    end = new Coord(start);
    end.add(Cardinal.UP, 5);
    RectSolid.fill(editor, rand, start, end, air);
    cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    cursor.add(dir, 4);
    air.set(editor, cursor);

    for (Cardinal side : Cardinal.orthogonal(dir)) {

      cursor = new Coord(origin);
      cursor.add(dir, 4);
      cursor.add(side);

      stair.setOrientation(Cardinal.reverse(side), false).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.reverse(side), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(side, false).set(editor, cursor);
      cursor.add(Cardinal.UP);
      bars.set(editor, cursor);
      cursor.add(Cardinal.UP);
      bars.set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(side, true).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(dir, 3);
      cursor.add(side);
      stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);
      cursor.add(side);
      stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);
      cursor.add(side);
      brick.set(editor, cursor);
      cursor.add(dir);
      brick.set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.reverse(side), false).set(editor, cursor);
      cursor.add(Cardinal.reverse(dir));
      stair.setOrientation(Cardinal.reverse(side), false).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(dir, 4);
      cursor.add(side, 2);
      brick.set(editor, cursor);
      cursor.add(dir);
      brick.set(editor, cursor);
      cursor.add(Cardinal.UP);
      brick.set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);
      cursor.add(Cardinal.DOWN);
      cursor.add(Cardinal.reverse(dir));
      stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(dir, 3);
      cursor.add(Cardinal.UP, 5);
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);

    }

    MetaBlock netherrack = BlockType.get(BlockType.NETHERRACK);
    MetaBlock fire = BlockType.get(BlockType.FIRE);

    start = new Coord(origin);
    start.add(dir, 5);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    RectSolid.fill(editor, rand, start, end, netherrack);
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, fire);

    cursor = new Coord(origin);
    cursor.add(dir, 3);
    brickSlab.set(editor, cursor);
    cursor.add(dir);
    brickSlab.set(editor, cursor);

  }

  private void anvilRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {

    ITheme theme = settings.getTheme();
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory wall = theme.getPrimary().getWall();


    Coord cursor;
    Coord start;
    Coord end;

    MetaBlock anvil = Anvil.get(
        (RogueConfig.getBoolean(RogueConfig.GENEROUS)
            ? Anvil.NEW_ANVIL
            : Anvil.DAMAGED_ANVIL), Cardinal.left(dir));
    cursor = new Coord(origin);
    cursor.add(dir);
    anvil.set(editor, cursor);

    start = new Coord(origin);
    start.add(Cardinal.right(dir), 2);
    end = new Coord(start);
    start.add(dir, 2);
    end.add(Cardinal.reverse(dir), 2);
    stair.setOrientation(Cardinal.left(dir), false);
    RectSolid.fill(editor, rand, start, end, stair);

    cursor = new Coord(origin);
    cursor.add(Cardinal.right(dir), 3);
    wall.set(editor, rand, cursor);
    cursor.add(dir);
    BlockType.get(BlockType.WATER_FLOWING).set(editor, cursor);
    cursor.add(Cardinal.reverse(dir), 2);
    BlockType.get(BlockType.LAVA_FLOWING).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.left(dir), 3);
    start = new Coord(cursor);
    end = new Coord(start);
    start.add(dir);
    end.add(Cardinal.reverse(dir));
    stair.setOrientation(Cardinal.right(dir), true);
    RectSolid.fill(editor, rand, start, end, stair);
    cursor.add(Cardinal.UP);
    try {
      Treasure.generate(editor, rand, cursor, Treasure.SMITH, Dungeon.getLevel(cursor.getY()));
    } catch (ChestPlacementException cpe) {
      // do nothing
    }

    cursor = new Coord(origin);
  }


  private void overheadLight(IWorldEditor editor, LevelSettings settings, Coord origin) {

    ITheme theme = settings.getTheme();
    IStair stair = theme.getPrimary().getStair();

    Coord cursor;

    BlockType.get(BlockType.AIR).set(editor, origin);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir);
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      cursor.add(Cardinal.left(dir));
      stair.set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 2);
    BlockType.get(BlockType.REDSTONE_BLOCK).set(editor, cursor);
    cursor.add(Cardinal.DOWN);
    BlockType.get(BlockType.REDSTONE_LAMP_LIT).set(editor, cursor);
  }

  public int getSize() {
    return 9;
  }

}
