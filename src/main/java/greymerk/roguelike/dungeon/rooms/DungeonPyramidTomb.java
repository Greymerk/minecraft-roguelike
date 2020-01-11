package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

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
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.Spawner;

public class DungeonPyramidTomb extends DungeonBase {

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {


    ITheme theme = settings.getTheme();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    IBlockFactory blocks = theme.getPrimary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);


    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);

    start.add(Cardinal.NORTH, 6);
    start.add(Cardinal.WEST, 6);
    end.add(Cardinal.SOUTH, 6);
    end.add(Cardinal.EAST, 6);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air, true, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.add(Cardinal.UP, 3);
    start.add(Cardinal.NORTH, 4);
    start.add(Cardinal.WEST, 4);
    end.add(Cardinal.SOUTH, 4);
    end.add(Cardinal.EAST, 4);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air, true, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.add(Cardinal.UP, 5);
    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.WEST, 3);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.EAST, 3);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air, true, true);

    start = new Coord(origin);
    end = new Coord(origin);

    start.add(Cardinal.UP, 7);
    start.add(Cardinal.NORTH, 2);
    start.add(Cardinal.WEST, 2);
    end.add(Cardinal.SOUTH, 2);
    end.add(Cardinal.EAST, 2);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air, true, true);

    // outer walls
    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.NORTH, 7);
    start.add(Cardinal.WEST, 7);
    end.add(Cardinal.SOUTH, 7);
    end.add(Cardinal.EAST, 7);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.UP, 3);
    RectHollow.fill(editor, rand, start, end, blocks, false, true);

    // floor
    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(Cardinal.NORTH, 6);
    start.add(Cardinal.WEST, 6);
    end.add(Cardinal.SOUTH, 6);
    end.add(Cardinal.EAST, 6);
    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());

    // pillars

    for (Cardinal dir : Cardinal.directions) {


      cursor = new Coord(origin);
      cursor.add(dir, 5);
      cursor.add(Cardinal.UP, 3);
      ceilingTiles(editor, rand, theme, 9, Cardinal.reverse(dir), cursor);

      start = new Coord(origin);
      start.add(dir, 5);
      start.add(Cardinal.left(dir), 5);
      end = new Coord(start);
      end.add(Cardinal.UP, 3);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);

      for (Cardinal o : Cardinal.orthogonal(dir)) {
        start = new Coord(origin);
        start.add(dir, 5);
        start.add(o);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar, true, true);

        start.add(o, 2);
        end = new Coord(start);
        end.add(Cardinal.UP, 3);
        RectSolid.fill(editor, rand, start, end, pillar, true, true);
      }
    }

    // ceiling top
    start = new Coord(origin);
    start.add(Cardinal.UP, 8);
    end = new Coord(start);
    start.add(Cardinal.NORTH);
    start.add(Cardinal.WEST);
    end.add(Cardinal.SOUTH);
    end.add(Cardinal.EAST);
    RectSolid.fill(editor, rand, start, end, blocks, true, true);

    sarcophagus(editor, rand, settings, entrances[0], origin);

    return true;
  }

  private void ceilingTiles(IWorldEditor editor, Random rand, ITheme theme, int width, Cardinal dir, Coord origin) {

    if (width < 1) {
      return;
    }

    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor;

    Coord start = new Coord(origin);
    Coord end = new Coord(origin);
    start.add(Cardinal.left(dir), width / 2);
    end.add(Cardinal.right(dir), width / 2);
    RectSolid.fill(editor, rand, start, end, air, true, true);
    start.add(Cardinal.UP);
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);

    for (Cardinal o : Cardinal.orthogonal(dir)) {
      for (int i = 0; i <= width / 2; ++i) {
        if ((width / 2) % 2 == 0) {
          cursor = new Coord(origin);
          cursor.add(o, i);
          if (i % 2 == 0) {
            tile(editor, rand, theme, dir, cursor);
          }
        } else {
          cursor = new Coord(origin);
          cursor.add(o, i);
          if (i % 2 == 1) {
            tile(editor, rand, theme, dir, cursor);
          }
        }
      }
    }

    cursor = new Coord(origin);
    cursor.add(dir);
    cursor.add(Cardinal.UP);
    ceilingTiles(editor, rand, theme, (width - 2), dir, cursor);
  }

  private void tile(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
    IStair stair = theme.getPrimary().getStair();
    stair.setOrientation(dir, true).set(editor, origin);
    Coord cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    theme.getPrimary().getPillar().set(editor, rand, cursor);
  }


  private void sarcophagus(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    IStair stair = new MetaStair(StairType.QUARTZ);
    MetaBlock blocks = BlockType.get(BlockType.QUARTZ);

    Coord cursor;

    cursor = new Coord(origin);
    blocks.set(editor, cursor);
    cursor.add(Cardinal.UP);
    try {
      Treasure.generate(editor, rand, cursor, Treasure.ORE, Dungeon.getLevel(cursor.getY()));
    } catch (ChestPlacementException cpe) {
      // do nothing
    }
    cursor.add(Cardinal.UP);
    blocks.set(editor, cursor);

    for (Cardinal end : Cardinal.orthogonal(dir)) {

      cursor = new Coord(origin);
      cursor.add(end);
      blocks.set(editor, cursor);
      cursor.add(Cardinal.UP);
      Spawner.generate(editor, rand, settings, cursor, Spawner.ZOMBIE);
      cursor.add(Cardinal.UP);
      blocks.set(editor, cursor);

      cursor = new Coord(origin);
      cursor.add(end, 2);
      stair.setOrientation(end, false).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(end, true).set(editor, cursor);
      cursor.add(Cardinal.UP);
      stair.setOrientation(end, false).set(editor, cursor);

      for (Cardinal side : Cardinal.orthogonal(end)) {

        cursor = new Coord(origin);
        cursor.add(side);
        stair.setOrientation(side, false).set(editor, cursor);
        cursor.add(Cardinal.UP);
        stair.setOrientation(side, true).set(editor, cursor);
        cursor.add(Cardinal.UP);
        stair.setOrientation(side, false).set(editor, cursor);

        cursor = new Coord(origin);
        cursor.add(side);
        cursor.add(end);
        stair.setOrientation(side, false).set(editor, cursor);
        cursor.add(Cardinal.UP);
        stair.setOrientation(side, true).set(editor, cursor);
        cursor.add(Cardinal.UP);
        stair.setOrientation(side, false).set(editor, cursor);

        cursor = new Coord(origin);
        cursor.add(side);
        cursor.add(end, 2);
        stair.setOrientation(side, false).set(editor, cursor);
        cursor.add(Cardinal.UP);
        stair.setOrientation(side, true).set(editor, cursor);
        cursor.add(Cardinal.UP);
        stair.setOrientation(side, false).set(editor, cursor);
      }
    }
  }

  @Override
  public int getSize() {
    return 8;
  }


}
