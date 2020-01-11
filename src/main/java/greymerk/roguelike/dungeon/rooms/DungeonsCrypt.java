package greymerk.roguelike.dungeon.rooms;

import java.util.Arrays;
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
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.Spawner;

public class DungeonsCrypt extends DungeonBase {

  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    ITheme theme = settings.getTheme();
    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory walls = theme.getPrimary().getWall();
    IBlockFactory floor = theme.getPrimary().getFloor();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-3, 0, -3));
    end.add(new Coord(3, 4, 3));
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-9, -1, -9));
    end.add(new Coord(9, -1, 9));
    RectSolid.fill(editor, rand, start, end, floor);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-9, 5, -9));
    end.add(new Coord(9, 6, 9));
    RectSolid.fill(editor, rand, start, end, walls, false, true);

    for (Cardinal dir : Cardinal.directions) {

      List<?> doorways = Arrays.asList(entrances);

      if (doorways.contains(dir) && doorways.contains(Cardinal.left(dir))) {
        start = new Coord(origin);
        end = new Coord(origin);
        start.add(dir, 3);
        end.add(Cardinal.left(dir), 5);
        end.add(dir, 5);
        end.add(Cardinal.UP, 4);
        RectSolid.fill(editor, rand, start, end, air);
      }

      if (doorways.contains(dir)) {
        // doorway air
        start = new Coord(origin);
        end = new Coord(origin);
        start.add(dir, 3);
        start.add(Cardinal.left(dir), 2);
        end.add(dir, 8);
        end.add(Cardinal.right(dir), 2);
        end.add(Cardinal.UP, 4);
        RectSolid.fill(editor, rand, start, end, air);

        for (Cardinal o : Cardinal.orthogonal(dir)) {
          if (doorways.contains(o)) {

            cursor = new Coord(origin);
            cursor.add(dir, 7);
            cursor.add(o, 3);
            cursor.add(Cardinal.UP);

            crypt(editor, rand, settings, cursor, o);
          } else {

            start = new Coord(origin);
            end = new Coord(origin);
            start.add(dir, 4);
            start.add(o, 3);
            end.add(dir, 8);
            end.add(o, 8);
            end.add(Cardinal.UP, 4);
            RectSolid.fill(editor, rand, start, end, air);

            cursor = new Coord(origin);
            cursor.add(dir, 6);
            cursor.add(o, 3);
            cursor.add(Cardinal.UP);

            sarcophagus(editor, rand, settings, cursor, o);
          }
        }

      } else {
        cursor = new Coord(origin);
        cursor.add(dir, 4);
        mausoleumWall(editor, rand, settings, cursor, dir);
      }

      cursor = new Coord(origin);
      cursor.add(dir, 3);
      cursor.add(Cardinal.left(dir), 3);
      pillar(editor, rand, settings, cursor);

      start = new Coord(origin);
      start.add(dir, 8);
      start.add(Cardinal.UP, 4);
      end = new Coord(start);
      start.add(Cardinal.left(dir), 2);
      end.add(Cardinal.right(dir), 2);
      stair.setOrientation(Cardinal.reverse(dir), true);
      RectSolid.fill(editor, rand, start, end, stair, true, false);
    }

    return true;
  }

  private void sarcophagus(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ITheme theme = settings.getTheme();

    IBlockFactory walls = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    start.add(dir, 5);
    end = new Coord(start);
    start.add(Cardinal.left(dir), 2);
    end.add(Cardinal.right(dir), 2);
    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(origin);
    cursor.add(dir, 5);
    cursor.add(Cardinal.UP, 3);
    stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);

    start = new Coord(origin);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      start = new Coord(origin);
      start.add(Cardinal.DOWN);
      start.add(dir);
      start.add(o, 3);
      end = new Coord(start);
      end.add(dir, 4);
      end.add(Cardinal.UP, 4);
      RectSolid.fill(editor, rand, start, end, walls);

      cursor = new Coord(origin);
      cursor.add(Cardinal.DOWN);
      cursor.add(dir, 5);
      cursor.add(o, 2);
      pillar(editor, rand, settings, cursor);

      start = new Coord(origin);
      start.add(Cardinal.UP, 3);
      start.add(o, 2);
      end = new Coord(start);
      end.add(dir, 3);
      stair.setOrientation(Cardinal.reverse(o), true);
      RectSolid.fill(editor, rand, start, end, stair);
    }

    cursor = new Coord(origin);
    tomb(editor, rand, settings, cursor, dir);

    cursor.add(Cardinal.UP);
    stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);
    cursor.add(Cardinal.DOWN, 2);
    stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    cursor.add(dir);
    walls.set(editor, rand, cursor);
    cursor.add(dir);
    walls.set(editor, rand, cursor);
    cursor.add(dir);
    stair.setOrientation(dir, false).set(editor, cursor);
    cursor.add(Cardinal.UP);
    stair.setOrientation(dir, true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    stair.setOrientation(dir, false).set(editor, cursor);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.DOWN);
      cursor.add(o);
      start = new Coord(cursor);
      end = new Coord(cursor);
      end.add(dir, 3);
      stair.setOrientation(o, false);
      RectSolid.fill(editor, rand, start, end, stair);
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      stair.setOrientation(o, true);
      RectSolid.fill(editor, rand, start, end, stair);
      start.add(Cardinal.UP);
      end.add(Cardinal.UP);
      stair.setOrientation(o, false);
      RectSolid.fill(editor, rand, start, end, stair);
    }

  }

  private void crypt(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ITheme theme = settings.getTheme();

    IBlockFactory walls = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    start.add(Cardinal.left(dir));
    end = new Coord(origin);
    end.add(Cardinal.UP, 3);
    end.add(Cardinal.right(dir));
    end.add(dir, 3);

    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(origin);
    cursor.add(Cardinal.reverse(dir));
    cursor.add(Cardinal.UP, 2);
    stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    cursor.add(Cardinal.UP);
    walls.set(editor, rand, cursor);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.reverse(dir));
      cursor.add(Cardinal.UP);
      cursor.add(o);
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      walls.set(editor, rand, cursor);
      cursor.add(Cardinal.UP);
      walls.set(editor, rand, cursor);

      start = new Coord(origin);
      start.add(Cardinal.UP, 3);
      start.add(Cardinal.reverse(dir), 2);
      start.add(o, 2);
      end = new Coord(start);
      end.add(dir, 7);
      stair.setOrientation(o, true);
      RectSolid.fill(editor, rand, start, end, stair, true, false);
    }

    start = new Coord(origin);
    start.add(Cardinal.UP, 3);
    start.add(Cardinal.reverse(dir), 2);
    end = new Coord(start);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    stair.setOrientation(Cardinal.reverse(dir), true);
    RectSolid.fill(editor, rand, start, end, stair);

    tomb(editor, rand, settings, origin, dir);
  }

  private void mausoleumWall(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ITheme theme = settings.getTheme();
    IBlockFactory walls = theme.getPrimary().getWall();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.left(dir), 3);
    end.add(Cardinal.right(dir), 3);
    end.add(dir, 4);
    end.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, walls);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    tomb(editor, rand, settings, cursor, dir);

    cursor.add(Cardinal.UP, 2);
    tomb(editor, rand, settings, cursor, dir);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP);
      cursor.add(o, 2);
      tomb(editor, rand, settings, cursor, dir);

      cursor.add(Cardinal.UP, 2);
      tomb(editor, rand, settings, cursor, dir);
    }

  }

  private void pillar(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin) {

    ITheme theme = settings.getTheme();

    IBlockFactory walls = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    start = new Coord(origin);
    end = new Coord(origin);
    end.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, walls);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(end);
      cursor.add(dir);
      stair.setOrientation(dir, true);
      stair.set(editor, rand, cursor, true, false);
    }
  }

  private void tomb(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    ITheme theme = settings.getTheme();
    Coord cursor;

    IStair stair = theme.getPrimary().getStair();
    MetaBlock tombStone = BlockType.get(BlockType.QUARTZ);
    MetaBlock air = BlockType.get(BlockType.AIR);

    cursor = new Coord(origin);
    cursor.add(dir, 2);
    cursor.add(Cardinal.UP);
    stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);

    cursor.add(Cardinal.reverse(dir));
    stair.setOrientation(dir, true).set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(dir, 2);
    RectSolid.fill(editor, rand, origin, cursor, air);

    if (rand.nextInt(4) == 0) {
      return;
    }

    cursor = new Coord(origin);
    tombStone.set(editor, cursor);

    if (rand.nextInt(5) != 0) {
      return;
    }

    cursor.add(dir);
    Spawner spawnerType = rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE;
    Spawner.generate(editor, rand, settings, cursor, spawnerType);

    cursor.add(dir);
    Treasure[] types = {Treasure.ARMOUR, Treasure.WEAPONS};
    Treasure chestType = types[rand.nextInt(types.length)];
    try {
      Treasure.generate(editor, rand, cursor, chestType, settings.getDifficulty(cursor), false);
    } catch (ChestPlacementException cpe) {
      // do nothing
    }

  }

  public int getSize() {
    return 10;
  }
}
