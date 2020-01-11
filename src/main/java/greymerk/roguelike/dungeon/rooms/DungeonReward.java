package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.ChestPlacementException;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonReward extends DungeonBase {

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ITheme theme = settings.getTheme();

    RectSolid.fill(editor, rand, new Coord(x - 7, y, z - 7), new Coord(x + 7, y + 5, z + 7), BlockType.get(BlockType.AIR));
    RectHollow.fill(editor, rand, new Coord(x - 8, y - 1, z - 8), new Coord(x + 8, y + 6, z + 8), theme.getPrimary().getWall(), false, true);
    RectSolid.fill(editor, rand, new Coord(x - 1, y + 4, z - 1), new Coord(x + 1, y + 5, z + 1), theme.getPrimary().getWall());

    Coord cursor;
    Coord start;
    Coord end;

    IStair stair = theme.getPrimary().getStair();

    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : Cardinal.orthogonal(dir)) {
        cursor = new Coord(x, y, z);
        cursor.add(dir, 7);
        cursor.add(orth, 2);
        start = new Coord(cursor);
        end = new Coord(start);
        end.add(Cardinal.UP, 5);
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);
        cursor.add(Cardinal.reverse(dir));
        stair.setOrientation(Cardinal.reverse(dir), false).set(editor, cursor);
        cursor.add(Cardinal.UP, 2);
        stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
        cursor.add(Cardinal.UP);
        start = new Coord(cursor);
        end = new Coord(start);
        end.add(Cardinal.UP, 2);
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);
        cursor.add(Cardinal.reverse(dir));
        stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
        cursor.add(Cardinal.UP);
        start = new Coord(cursor);
        end = new Coord(start);
        end.add(Cardinal.UP);
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);
        cursor.add(Cardinal.UP);
        cursor.add(Cardinal.reverse(dir));
        stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);

        start = new Coord(x, y, z);
        start.add(dir, 7);
        start.add(Cardinal.UP, 3);
        end = new Coord(start);
        end.add(Cardinal.UP, 2);
        end.add(orth);
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);
        start.add(Cardinal.reverse(dir));
        start.add(Cardinal.UP);
        end.add(Cardinal.reverse(dir));
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);
        start.add(Cardinal.reverse(dir));
        start.add(Cardinal.UP);
        end.add(Cardinal.reverse(dir));
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getWall(), true, true);

        cursor = new Coord(x, y, z);
        cursor.add(dir, 8);
        cursor.add(Cardinal.UP, 2);
        cursor.add(orth);
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, rand, cursor, true, false);
        cursor.add(Cardinal.reverse(dir));
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        cursor.add(Cardinal.UP);
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        cursor.add(Cardinal.UP);
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        cursor.add(Cardinal.UP);
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir), 2);
        stair.setOrientation(dir, true).set(editor, cursor);

        start = new Coord(x, y, z);
        start.add(dir, 7);
        start.add(orth, 3);
        start.add(Cardinal.UP, 3);
        end = new Coord(start);
        end.add(Cardinal.UP, 2);
        end.add(orth, 2);
        theme.getPrimary().getPillar().fill(editor, rand, new RectSolid(start, end));

        start.add(Cardinal.reverse(dir));
        start.add(Cardinal.UP);
        end.add(Cardinal.reverse(dir));
        RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());

        cursor = new Coord(x, y, z);
        cursor.add(dir, 7);
        cursor.add(orth, 3);
        stair.setOrientation(orth, false).set(editor, cursor);
        cursor.add(orth, 2);
        stair.setOrientation(Cardinal.reverse(orth), false).set(editor, cursor);
        cursor.add(Cardinal.UP, 2);
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);
        cursor.add(Cardinal.reverse(orth), 2);
        stair.setOrientation(orth, true).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        cursor.add(Cardinal.UP);
        stair.setOrientation(orth, true).set(editor, cursor);
        cursor.add(orth, 2);
        stair.setOrientation(Cardinal.reverse(orth), true).set(editor, cursor);
        cursor.add(Cardinal.reverse(dir));
        cursor.add(Cardinal.UP);
        end = new Coord(cursor);
        end.add(Cardinal.reverse(orth), 2);
        RectSolid.fill(editor, rand, cursor, end, stair.setOrientation(Cardinal.reverse(dir), true), true, true);
        cursor.add(Cardinal.UP);
        end.add(Cardinal.UP);
        RectSolid.fill(editor, rand, cursor, end, theme.getPrimary().getWall(), true, true);
        end.add(Cardinal.reverse(dir));
        stair.setOrientation(orth, true).set(editor, cursor);

        cursor = new Coord(x, y, z);
        cursor.add(dir, 7);
        cursor.add(orth, 4);
        cursor.add(Cardinal.DOWN);
        BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);

      }

      Cardinal o = Cardinal.left(dir);

      start = new Coord(x, y, z);
      start.add(dir, 6);
      start.add(o, 6);
      end = new Coord(start);
      end.add(dir);
      end.add(o);
      end.add(Cardinal.UP, 5);
      RectSolid.fill(editor, rand, start, end, theme.getPrimary().getPillar());

      cursor = new Coord(x, y, z);
      theme.getPrimary().getWall().set(editor, rand, cursor);
      cursor.add(dir);
      stair.setOrientation(dir, false).set(editor, cursor);
      cursor.add(o);
      stair.setOrientation(dir, false).set(editor, cursor);
      cursor.add(Cardinal.UP, 4);
      stair.setOrientation(dir, true).set(editor, cursor);
      cursor.add(Cardinal.reverse(o));
      stair.setOrientation(dir, true).set(editor, cursor);

    }

    cursor = new Coord(x, y, z);
    cursor.add(Cardinal.UP, 4);
    BlockType.get(BlockType.GLOWSTONE).set(editor, cursor);

    cursor = new Coord(x, y, z);
    cursor.add(Cardinal.UP);
    try {
      Treasure.generate(editor, rand, cursor, Treasure.REWARD, settings.getDifficulty(cursor));
    } catch (ChestPlacementException cpe) {
      // do nothing
    }

    return true;
  }

  @Override
  public int getSize() {
    return 10;
  }
}
