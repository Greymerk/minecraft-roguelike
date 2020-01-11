package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonCorner extends DungeonBase {

  @Override
  public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    int x = origin.getX();
    int y = origin.getY();
    int z = origin.getZ();
    ITheme theme = settings.getTheme();

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory blocks = theme.getPrimary().getWall();
    IBlockFactory pillar = theme.getPrimary().getPillar();
    MetaBlock air = BlockType.get(BlockType.AIR);

    // fill air inside
    RectSolid.fill(editor, rand, new Coord(x - 2, y, z - 2), new Coord(x + 2, y + 3, z + 2), air);

    // shell
    RectHollow.fill(editor, rand, new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y + 4, z + 3), blocks, false, true);

    // floor
    RectSolid.fill(editor, rand, new Coord(x - 3, y - 1, z - 3), new Coord(x + 3, y - 1, z + 3), theme.getPrimary().getFloor(), false, true);

    Coord start;
    Coord end;
    Coord cursor;

    cursor = new Coord(x, y, z);
    cursor.add(Cardinal.UP, 4);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    blocks.set(editor, rand, cursor);

    for (Cardinal dir : Cardinal.directions) {

      cursor = new Coord(x, y, z);
      cursor.add(dir, 2);
      cursor.add(Cardinal.left(dir), 2);
      start = new Coord(cursor);
      cursor.add(Cardinal.UP, 2);
      end = new Coord(cursor);
      RectSolid.fill(editor, rand, start, end, pillar, true, true);
      cursor.add(Cardinal.UP, 1);
      blocks.set(editor, rand, cursor);

      cursor = new Coord(x, y, z);
      cursor.add(dir, 1);
      cursor.add(Cardinal.UP, 4);
      stair.setOrientation(Cardinal.reverse(dir), true);
      stair.set(editor, rand, cursor);

      for (Cardinal orth : Cardinal.orthogonal(dir)) {
        cursor = new Coord(x, y, z);
        cursor.add(dir, 2);
        cursor.add(orth, 1);
        cursor.add(Cardinal.UP, 3);
        stair.setOrientation(Cardinal.reverse(orth), true);
        stair.set(editor, rand, cursor);
      }
    }

    return true;
  }

  public int getSize() {
    return 4;
  }

}
