package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;


public class DungeonMess extends DungeonBase {

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory panel = theme.getSecondary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IStair stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    // clear air
    start = new Coord(origin);
    start.add(new Coord(-8, -1, -8));
    end = new Coord(origin);
    end.add(new Coord(8, 5, 8));
    RectHollow.fill(editor, rand, start, end, wall, false, true);

    start = new Coord(origin);
    start.add(new Coord(-2, 5, -2));
    end = new Coord(origin);
    end.add(new Coord(2, 5, 2));
    RectSolid.fill(editor, rand, start, end, panel, false, true);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(dir, 3);
      start.add(Cardinal.left(dir), 3);
      end = new Coord(start);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);

      for (Cardinal d : Cardinal.directions) {
        cursor = new Coord(end);
        cursor.add(d);
        stair.setOrientation(d, true).set(editor, cursor);
      }

      start = new Coord(origin);
      start.add(dir, 3);
      start.add(Cardinal.UP, 4);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 3);
      end.add(Cardinal.right(dir), 3);
      RectSolid.fill(editor, rand, start, end, wall, true, true);


      Cardinal[] corner = new Cardinal[]{dir, Cardinal.left(dir)};
      if (entrances.length == 4 && dir == entrances[0]) {
        supplyCorner(editor, rand, settings, corner, origin);
      } else {
        corner(editor, rand, settings, corner, origin);
      }

      doorway(editor, rand, settings, dir, origin);

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 4);
      cursor.add(dir);
      stair.setOrientation(dir, true).set(editor, cursor);
      cursor.add(dir);
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    }

    List<Cardinal> nonDoors = new ArrayList<Cardinal>();
    for (Cardinal dir : Cardinal.directions) {
      if (!Arrays.asList(entrances).contains(dir)) {
        nonDoors.add(dir);
      }
    }

    Collections.shuffle(nonDoors);

    switch (nonDoors.size()) {
      case 3:
        sideTable(editor, rand, settings, nonDoors.get(2), origin);
      case 2:
        fireplace(editor, rand, settings, nonDoors.get(1), origin);
      case 1:
        supplies(editor, rand, settings, nonDoors.get(0), origin);
      default:
    }


    return false;
  }

  private void supplyCorner(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory panel = theme.getSecondary().getWall();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(entrances[0], 7);
    start.add(entrances[1], 7);
    end = new Coord(start);
    end.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    start = new Coord(origin);
    start.add(entrances[0], 4);
    start.add(entrances[1], 4);
    start.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, panel, true, true);

    cursor = new Coord(origin);
    cursor.add(entrances[0], 5);
    cursor.add(entrances[1], 5);
    cursor.add(entrances[0], 2);
    cursor.add(Cardinal.UP);
    try {
      Treasure.generate(editor, rand, cursor, Treasure.FOOD, settings.getDifficulty(cursor));
    } catch (ChestPlacementException cpe) {
      // do nothing
    }

    cursor = new Coord(origin);
    cursor.add(entrances[0], 5);
    cursor.add(entrances[1], 5);
    cursor.add(entrances[1], 2);
    Furnace.generate(editor, true, Cardinal.reverse(entrances[1]), cursor);

    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.add(entrances[0], 3);
      cursor.add(entrances[1], 3);
      cursor.add(dir, 4);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);
      cursor.add(Cardinal.UP, 3);
      cursor.add(Cardinal.reverse(dir));
      wall.set(editor, rand, cursor);
      cursor.add(Cardinal.DOWN);
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(Cardinal.reverse(dir));
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(Cardinal.reverse(dir));
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.add(dir, 3);
      RectSolid.fill(editor, rand, start, end, wall, true, true);

      cursor = new Coord(origin);
      cursor.add(entrances[0], 5);
      cursor.add(entrances[1], 5);
      cursor.add(dir, 2);
      start = new Coord(cursor);
      start.add(Cardinal.left(dir));
      end = new Coord(cursor);
      end.add(Cardinal.right(dir));
      stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.UP, 2);
      end.add(Cardinal.UP, 2);
      stair.setOrientation(Cardinal.right(dir), true).set(editor, start);
      stair.setOrientation(Cardinal.left(dir), true).set(editor, end);
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, wall, true, true);
      start.add(dir);
      end.add(dir);
      end.add(Cardinal.DOWN, 3);
      RectSolid.fill(editor, rand, start, end, panel, false, true);
    }
  }

  private void corner(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory panel = theme.getSecondary().getWall();
    IStair stair = theme.getPrimary().getStair();
    IStair table = theme.getSecondary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(entrances[0], 7);
    start.add(entrances[1], 7);
    end = new Coord(start);
    end.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    start = new Coord(origin);
    start.add(entrances[0], 4);
    start.add(entrances[1], 4);
    start.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, panel, true, true);

    cursor = new Coord(origin);
    cursor.add(entrances[0], 4);
    cursor.add(entrances[1], 4);
    table.setOrientation(Cardinal.reverse(entrances[0]), true).set(editor, cursor);
    cursor.add(entrances[1]);
    table.setOrientation(entrances[1], true).set(editor, cursor);
    cursor.add(entrances[0]);
    table.setOrientation(entrances[0], true).set(editor, cursor);
    cursor.add(Cardinal.reverse(entrances[1]));
    table.setOrientation(Cardinal.reverse(entrances[1]), true).set(editor, cursor);


    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.add(entrances[0], 3);
      cursor.add(entrances[1], 3);
      cursor.add(dir, 4);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);
      cursor.add(Cardinal.UP, 3);
      cursor.add(Cardinal.reverse(dir));
      wall.set(editor, rand, cursor);
      cursor.add(Cardinal.DOWN);
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(Cardinal.reverse(dir));
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(Cardinal.reverse(dir));
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.add(dir, 3);
      RectSolid.fill(editor, rand, start, end, wall, true, true);

      cursor = new Coord(origin);
      cursor.add(entrances[0], 5);
      cursor.add(entrances[1], 5);
      cursor.add(dir, 2);
      start = new Coord(cursor);
      start.add(Cardinal.left(dir));
      end = new Coord(cursor);
      end.add(Cardinal.right(dir));
      stair.setOrientation(Cardinal.reverse(dir), false).fill(editor, rand, new RectSolid(start, end));
      start.add(Cardinal.UP, 2);
      end.add(Cardinal.UP, 2);
      stair.setOrientation(Cardinal.right(dir), true).set(editor, start);
      stair.setOrientation(Cardinal.left(dir), true).set(editor, end);
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      RectSolid.fill(editor, rand, start, end, wall, true, true);
      start.add(dir);
      end.add(dir);
      end.add(Cardinal.DOWN, 3);
      RectSolid.fill(editor, rand, start, end, panel, false, true);
    }
  }

  private void doorway(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IBlockFactory panel = theme.getSecondary().getWall();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(dir, 7);
    start.add(Cardinal.UP, 3);
    end = new Coord(start);
    end.add(Cardinal.UP);
    start.add(Cardinal.left(dir), 2);
    end.add(Cardinal.right(dir), 2);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(dir, 7);
      cursor.add(o, 2);
      cursor.add(Cardinal.UP, 2);
      stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);

      cursor.add(Cardinal.reverse(dir));
      cursor.add(Cardinal.UP, 2);
      stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
      cursor.add(Cardinal.reverse(o));
      stair.setOrientation(o, true).set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.add(dir, 6);
    cursor.add(Cardinal.UP, 3);
    stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    wall.set(editor, rand, cursor);
    cursor.add(Cardinal.reverse(dir));
    stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    stair.setOrientation(dir, true).set(editor, cursor);

    start = new Coord(origin);
    start.add(Cardinal.UP, 5);
    start.add(dir, 4);
    end = new Coord(start);
    end.add(dir);
    start.add(Cardinal.left(dir), 2);
    end.add(Cardinal.right(dir), 2);
    RectSolid.fill(editor, rand, start, end, panel, false, true);


  }

  private void fireplace(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory wall = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
    MetaBlock netherrack = BlockType.get(BlockType.NETHERRACK);
    MetaBlock fire = BlockType.get(BlockType.FIRE);
    MetaBlock air = BlockType.get(BlockType.AIR);
    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(dir, 7);
    end = new Coord(start);
    start.add(Cardinal.left(dir), 2);
    end.add(Cardinal.right(dir), 2);
    end.add(Cardinal.UP, 2);
    end.add(dir);
    RectSolid.fill(editor, rand, start, end, wall, true, true);

    start = new Coord(origin);
    start.add(dir, 7);
    end = new Coord(start);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air);

    cursor = new Coord(origin);
    cursor.add(dir, 6);
    bars.set(editor, cursor);
    cursor.add(dir);
    cursor.add(Cardinal.DOWN);
    netherrack.set(editor, cursor);
    cursor.add(Cardinal.UP);
    fire.set(editor, cursor);
    cursor.add(Cardinal.UP);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP);
    air.set(editor, cursor);
    cursor.add(Cardinal.reverse(dir));
    stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(dir, 6);
      cursor.add(o);
      bars.set(editor, cursor);
      cursor.add(o);
      wall.set(editor, rand, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(o, false).set(editor, cursor);
      cursor.add(Cardinal.reverse(o));
      stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
      cursor.add(dir);
      stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      cursor.add(Cardinal.reverse(dir));
      stair.setOrientation(o, false).set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(dir, 6);
      cursor.add(o);
      bars.set(editor, cursor);
      cursor.add(dir);
      cursor.add(Cardinal.DOWN);
      netherrack.set(editor, cursor);
      cursor.add(Cardinal.UP);
      fire.set(editor, cursor);
    }
  }

  private void supplies(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IStair stair = theme.getPrimary().getStair();
    Coord cursor;


    cursor = new Coord(origin);
    cursor.add(dir, 7);
    stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    try {
      Treasure.generate(editor, rand, cursor, Treasure.FOOD, settings.getDifficulty(origin));
    } catch (ChestPlacementException cpe) {
      // do nothing
    }
    cursor.add(Cardinal.left(dir));
    Furnace.generate(editor, dir, cursor);
    cursor.add(Cardinal.right(dir), 2);
    BlockType.get(BlockType.CRAFTING_TABLE).set(editor, cursor);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(dir, 7);
      cursor.add(o);
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      cursor.add(o);
      stair.setOrientation(Cardinal.reverse(o), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(Cardinal.reverse(o), false).set(editor, cursor);
    }


  }

  private void sideTable(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IStair table = theme.getSecondary().getStair();
    Coord cursor = new Coord(origin);

    cursor.add(dir, 5);
    table.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    cursor.add(dir);
    table.setOrientation(dir, true).set(editor, cursor);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(dir, 5);
      cursor.add(o);
      table.setOrientation(o, true).set(editor, cursor);
      cursor.add(dir);
      table.setOrientation(o, true).set(editor, cursor);
    }
  }

  public int getSize() {
    return 10;
  }
}
