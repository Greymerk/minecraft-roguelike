package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.Spawner;


public class DungeonEniko extends DungeonBase {

  private static void pillar(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(start);
    end.add(Cardinal.UP, 3);
    RectSolid.fill(editor, rand, start, end, pillar, true, true);
    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(end);
      cursor.add(dir);
      stair.setOrientation(dir, true).set(editor, rand, cursor, true, false);
    }
  }

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    ITheme theme = settings.getTheme();
    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getPrimary().getStair();
    IBlockFactory walls = theme.getPrimary().getWall();
    IBlockFactory floor = theme.getPrimary().getFloor();
    Coord start;
    Coord end;
    Coord cursor;
    List<Coord> chests = new ArrayList<Coord>();

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(6, -1, 6));
    end.add(new Coord(-6, 4, -6));
    RectHollow.fill(editor, rand, start, end, walls, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(6, 4, 6));
    end.add(new Coord(-6, 5, -6));
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(3, 4, 3));
    end.add(new Coord(-3, 4, -3));
    RectSolid.fill(editor, rand, start, end, air, true, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-3, -1, -3));
    end.add(new Coord(3, -1, 3));
    RectSolid.fill(editor, rand, start, end, floor, true, true);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir, 5);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o, 2);
        pillar(editor, rand, theme, c);

        c = new Coord(cursor);
        c.add(o, 3);
        stair.setOrientation(Cardinal.reverse(dir), true).set(editor, c);
        c.add(o);
        stair.setOrientation(Cardinal.reverse(dir), true).set(editor, c);
        c.add(Cardinal.UP);
        chests.add(new Coord(c));
        c.add(Cardinal.reverse(o));
        chests.add(new Coord(c));
      }

      cursor.add(Cardinal.left(dir), 5);
      pillar(editor, rand, theme, cursor);

      if (Arrays.asList(entrances).contains(dir)) {
        start = new Coord(origin);
        start.add(Cardinal.DOWN);
        end = new Coord(start);
        start.add(Cardinal.left(dir));
        end.add(Cardinal.right(dir));
        end.add(dir, 6);
        RectSolid.fill(editor, rand, start, end, floor, true, true);
      }
    }

    Spawner.generate(editor, rand, settings, origin);
    Treasure.createChests(editor, rand, 1, chests, settings.getDifficulty(origin));

    return true;
  }

  public int getSize() {
    return 7;
  }
}
