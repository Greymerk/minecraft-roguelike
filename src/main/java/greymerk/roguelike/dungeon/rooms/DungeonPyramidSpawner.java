package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.Spawner;

public class DungeonPyramidSpawner extends DungeonBase {

  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();

    ITheme theme = settings.getTheme();

    IBlockFactory blocks = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    MetaBlock air = BlockType.get(BlockType.AIR);

    // fill air inside
    RectSolid.fill(editor, rand, new Coord(x - 3, y, z - 3), new Coord(x + 3, y + 3, z + 3), air);


    // shell
    RectHollow.fill(editor, rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y + 4, z + 4), blocks, false, true);
    RectSolid.fill(editor, rand, new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3), blocks, false, true);
    RectSolid.fill(editor, rand, new Coord(x - 2, y + 4, z - 2), new Coord(x + 2, y + 4, z + 2), air);

    RectSolid.fill(editor, rand, new Coord(x - 4, y - 1, z - 4), new Coord(x + 4, y - 1, z + 4), theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(x, y, z);
    cursor.add(Cardinal.UP, 5);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    blocks.set(editor, rand, cursor);

    cursor = new Coord(x, y, z);
    cursor.add(Cardinal.UP, 5);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP);
    air.set(editor, cursor);

    // Chests
    List<Coord> space = new ArrayList<Coord>();

    for (Cardinal dir : Cardinal.directions) {

      // pillar
      cursor = new Coord(x, y, z);
      cursor.add(dir, 3);
      cursor.add(Cardinal.left(dir), 3);
      start = new Coord(cursor);
      cursor.add(Cardinal.UP, 3);
      end = new Coord(cursor);
      RectSolid.fill(editor, rand, start, end, pillar);
      cursor.add(Cardinal.UP, 1);
      blocks.set(editor, rand, cursor);

      cursor = new Coord(x, y, z);
      cursor.add(Cardinal.UP, 4);
      cursor.add(dir, 2);
      blocks.set(editor, rand, cursor);
      cursor.add(Cardinal.left(dir), 2);
      blocks.set(editor, rand, cursor);

      cursor = new Coord(x, y, z);
      cursor.add(Cardinal.UP, 5);
      cursor.add(Cardinal.left(dir));
      air.set(editor, cursor);
      cursor.add(Cardinal.UP);
      air.set(editor, cursor);

      for (Cardinal orth : Cardinal.orthogonal(dir)) {

        cursor = new Coord(x, y, z);
        cursor.add(Cardinal.UP, 3);
        cursor.add(orth);
        cursor.add(dir, 3);
        blocks.set(editor, rand, cursor);

        cursor = new Coord(x, y, z);
        cursor.add(Cardinal.UP, 4);
        cursor.add(dir, 2);
        blocks.set(editor, rand, cursor);

        cursor = new Coord(x, y, z);
        cursor.add(dir, 3);
        cursor.add(orth, 2);
        space.add(cursor);
      }
    }

    List<Treasure> types = new ArrayList<Treasure>(Arrays.asList(Treasure.ARMOUR, Treasure.WEAPONS, Treasure.TOOLS));
    Treasure.createChests(editor, rand, 1, space, types, Dungeon.getLevel(origin.getY()));

    Spawner.generate(editor, rand, settings, new Coord(x, y, z));

    return true;
  }

  public boolean isValidDungeonLocation(IWorldEditor editor, int x, int y, int z) {
    return false;
  }

  public int getSize() {
    return 4;
  }
}
