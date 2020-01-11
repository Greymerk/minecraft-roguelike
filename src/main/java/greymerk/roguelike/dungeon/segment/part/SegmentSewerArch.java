package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentSewerArch extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    IStair stair = theme.getSecondary().getStair();
    stair.setOrientation(Cardinal.reverse(dir), true);
    MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
    MetaBlock air = BlockType.get(BlockType.AIR);
    MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
    MetaBlock mossy = BlockType.get(BlockType.COBBLESTONE_MOSSY);

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = Cardinal.orthogonal(dir);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 3);
    mossy.set(editor, rand, cursor, false, true);
    cursor.add(Cardinal.UP);
    water.set(editor, rand, cursor, false, true);

    cursor = new Coord(origin);
    cursor.add(dir, 2);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    stair.set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(dir, 2);
    bars.set(editor, cursor);
    cursor.add(Cardinal.UP);
    bars.set(editor, cursor);

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    RectSolid.fill(editor, rand, start, end, air);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, water);

    for (Cardinal o : orth) {
      cursor = new Coord(origin);
      cursor.add(o, 1);
      cursor.add(dir, 2);
      theme.getSecondary().getPillar().set(editor, rand, cursor);
      cursor.add(Cardinal.UP, 1);
      theme.getSecondary().getPillar().set(editor, rand, cursor);
      cursor.add(Cardinal.UP, 1);
      theme.getPrimary().getWall().set(editor, rand, cursor);
      cursor.add(Cardinal.reverse(dir), 1);
      stair.set(editor, cursor);
    }
  }
}
