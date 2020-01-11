package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
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

public class DungeonsPrison extends DungeonBase {

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    Coord cursor;

    this.largeRoom(editor, rand, settings, origin);

    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.add(dir, 6);
      this.sideRoom(editor, rand, settings, cursor, dir);
    }

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir, 3);
      cursor.add(Cardinal.left(dir), 3);
      pillar(editor, rand, settings, cursor, 4);
    }

    for (Cardinal dir : Cardinal.directions) {
      List<Cardinal> doors = new ArrayList<>();

      if (Arrays.asList(entrances).contains(dir)) {
        doors.add(Cardinal.right(dir));
      }

      if (Arrays.asList(entrances).contains(Cardinal.left(dir))) {
        doors.add(Cardinal.reverse(dir));
      }

      if (doors.isEmpty()) {
        continue;
      }

      cursor = new Coord(origin);
      cursor.add(dir, 6);
      cursor.add(Cardinal.left(dir), 6);

      cell(editor, rand, settings, cursor, doors, rand.nextBoolean());
    }

    return true;
  }

  public void largeRoom(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin) {
    Coord start;
    Coord end;
    Coord cursor;

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = settings.getTheme().getPrimary().getStair();

    IBlockFactory wall = settings.getTheme().getPrimary().getWall();
    start = new Coord(origin);
    start.add(Cardinal.UP, 6);
    end = new Coord(start);
    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.EAST, 3);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.WEST, 3);
    RectSolid.fill(editor, rand, start, end, wall, false, true);

    IBlockFactory floor = settings.getTheme().getPrimary().getFloor();
    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.EAST, 3);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.WEST, 3);
    RectSolid.fill(editor, rand, start, end, floor, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.WEST, 3);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.EAST, 3);
    end.add(Cardinal.UP, 4);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.NORTH, 4);
    start.add(Cardinal.WEST, 4);
    end.add(Cardinal.SOUTH, 4);
    end.add(Cardinal.EAST, 4);
    end.add(Cardinal.UP, 5);
    settings.getTheme().getPrimary().getWall().fill(editor, rand, new RectHollow(start, end), false, true);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir, 3);
      cursor.add(Cardinal.left(dir), 3);
      pillar(editor, rand, settings, cursor, 4);
    }

    start = new Coord(origin);
    start.add(Cardinal.UP, 5);
    end = new Coord(start);
    start.add(Cardinal.NORTH);
    start.add(Cardinal.EAST);
    end.add(Cardinal.SOUTH);
    end.add(Cardinal.WEST);
    RectSolid.fill(editor, rand, start, end, air);


    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 5);
      cursor.add(dir, 2);
      air.set(editor, cursor);
      for (Cardinal o : Cardinal.orthogonal(dir)) {
        Coord c = new Coord(cursor);
        c.add(o);
        stair.setOrientation(Cardinal.reverse(o), true).set(editor, c);
      }

      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 6);
      air.set(editor, cursor);
      cursor.add(dir, 1);
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
    }
  }

  private void sideRoom(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    Coord start;
    Coord end;
    Coord cursor;

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = settings.getTheme().getPrimary().getStair();
    int height = 3;

    IBlockFactory wall = settings.getTheme().getPrimary().getWall();
    start = new Coord(origin);
    start.add(Cardinal.UP, 6);
    end = new Coord(start);
    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.EAST, 3);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.WEST, 3);
    RectSolid.fill(editor, rand, start, end, wall, false, true);

    IBlockFactory floor = settings.getTheme().getPrimary().getFloor();
    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.EAST, 3);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.WEST, 3);
    RectSolid.fill(editor, rand, start, end, floor, false, true);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.NORTH, 3);
    start.add(Cardinal.WEST, 3);
    end.add(Cardinal.SOUTH, 3);
    end.add(Cardinal.EAST, 3);
    end.add(Cardinal.UP, height);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.NORTH, 4);
    start.add(Cardinal.WEST, 4);
    end.add(Cardinal.SOUTH, 4);
    end.add(Cardinal.EAST, 4);
    end.add(Cardinal.UP, height + 1);
    settings.getTheme().getPrimary().getWall().fill(editor, rand, new RectHollow(start, end), false, true);

    start = new Coord(origin);
    start.add(Cardinal.UP, 4);
    end = new Coord(start);
    start.add(dir);
    end.add(Cardinal.reverse(dir), 3);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    end.add(Cardinal.UP);
    RectSolid.fill(editor, rand, start, end, air, true, true);

    for (Cardinal d : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(d, 3);
      cursor.add(dir, 3);
      pillar(editor, rand, settings, cursor, height);
    }

    start = new Coord(origin);
    start.add(Cardinal.UP, 4);
    end = new Coord(start);
    start.add(Cardinal.left(dir));
    end.add(Cardinal.right(dir));
    start.add(Cardinal.reverse(dir), 3);
    end.add(dir, 2);

    for (Cardinal d : Cardinal.orthogonal(dir)) {
      cursor = new Coord(origin);
      cursor.add(Cardinal.UP, 4);
      start = new Coord(cursor);
      start.add(d, 2);
      end = new Coord(start);
      start.add(Cardinal.left(d), 3);
      end.add(Cardinal.right(d), 3);
      stair.setOrientation(Cardinal.reverse(d), true).fill(editor, rand, new RectSolid(start, end));

      cursor.add(Cardinal.UP, 1);
      start = new Coord(cursor);
      start.add(d);
      end = new Coord(start);
      start.add(Cardinal.left(d), 3);
      end.add(Cardinal.right(d), 3);
      stair.setOrientation(Cardinal.reverse(d), true).fill(editor, rand, new RectSolid(start, end));
    }


    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    start = new Coord(cursor);
    start.add(dir, 2);
    end = new Coord(start);
    start.add(Cardinal.left(dir), 2);
    end.add(Cardinal.right(dir), 2);
    stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));

    cursor.add(Cardinal.UP, 1);
    air.set(editor, cursor);
    start = new Coord(cursor);
    start.add(dir, 1);
    end = new Coord(start);
    start.add(Cardinal.left(dir), 1);
    end.add(Cardinal.right(dir), 1);
    stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, new RectSolid(start, end));
  }

  private void pillar(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, int height) {
    Coord cursor;
    IBlockFactory pillar = settings.getTheme().getPrimary().getPillar();
    IStair stair = settings.getTheme().getPrimary().getStair();

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, height - 1);
    editor.fillDown(rand, new Coord(cursor), pillar);
    cursor.add(Cardinal.UP);
    pillar.set(editor, rand, cursor);
    for (Cardinal dir : Cardinal.directions) {
      cursor.add(dir);
      stair.setOrientation(dir, true).set(editor, rand, cursor, true, false);
    }
  }

  private void cell(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, List<Cardinal> entrances, boolean occupied) {

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(origin);
    cursor.add(Cardinal.DOWN);
    if (editor.isAirBlock(cursor)) {
      return;
    }

    IBlockFactory wall = settings.getTheme().getPrimary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);
    MetaBlock bar = BlockType.get(BlockType.IRON_BAR);

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(Cardinal.DOWN);
    start.add(Cardinal.NORTH, 2);
    start.add(Cardinal.WEST, 2);
    end.add(Cardinal.SOUTH, 2);
    end.add(Cardinal.EAST, 2);
    end.add(Cardinal.UP, 4);
    RectHollow.fill(editor, rand, start, end, wall, false, true);

    IBlockFactory floor = settings.getTheme().getPrimary().getFloor();
    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(Cardinal.NORTH, 1);
    start.add(Cardinal.EAST, 1);
    end.add(Cardinal.SOUTH, 1);
    end.add(Cardinal.WEST, 1);
    RectSolid.fill(editor, rand, start, end, floor, false, true);

    for (Cardinal dir : entrances) {
      cursor = new Coord(origin);
      cursor.add(dir, 2);
      start = new Coord(cursor);
      end = new Coord(cursor);
      start.add(Cardinal.left(dir));
      end.add(Cardinal.right(dir));
      end.add(Cardinal.UP, 2);
      RectSolid.fill(editor, rand, start, end, bar);

      if (rand.nextBoolean()) {
        air.set(editor, cursor);
        cursor.add(Cardinal.UP);
        air.set(editor, cursor);
      }
    }

    if (occupied) {
      if (rand.nextBoolean()) {
        Spawner.generate(editor, rand, settings, origin, Spawner.SKELETON);
      } else {
        Spawner.generate(editor, rand, settings, origin, Spawner.ZOMBIE);
      }

    }
  }

  public int getSize() {
    return 12;
  }
}
