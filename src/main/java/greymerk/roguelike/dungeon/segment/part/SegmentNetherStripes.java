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
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentNetherStripes extends SegmentBase {


  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    IStair step = theme.getSecondary().getStair();

    Coord start;
    Coord end;
    Coord cursor;
    MetaBlock air = BlockType.get(BlockType.AIR);

    cursor = new Coord(origin);
    cursor.add(dir, 2);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    air.set(editor, cursor);
    cursor = new Coord(origin);
    cursor.add(dir, 5);
    boolean isAir = editor.isAirBlock(cursor);
    boolean isLava = rand.nextInt(5) == 0;


    MetaBlock slab = Slab.get(Slab.NETHERBRICK, false, false, false);
    cursor = new Coord(origin);
    cursor.add(dir, 2);
    slab.set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    slab.set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    slab.set(editor, cursor);

    for (Cardinal orth : Cardinal.orthogonal(dir)) {
      start = new Coord(origin);
      start.add(dir, 3);
      end = new Coord(start);
      start.add(orth, 1);
      start.add(Cardinal.UP, 3);
      end.add(Cardinal.DOWN, 2);
      if (isLava && !isAir) {
        RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.LAVA_FLOWING), false, true);
      }

      step.setOrientation(Cardinal.reverse(orth), true);
      cursor = new Coord(origin);
      cursor.add(dir, 2);
      cursor.add(orth, 1);
      step.set(editor, cursor);
      cursor.add(Cardinal.UP, 1);
      step.set(editor, cursor);
      cursor.add(Cardinal.UP, 1);
      step.set(editor, cursor);
      cursor.add(Cardinal.reverse(dir), 1);
      step.set(editor, cursor);
    }
  }
}