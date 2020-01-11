package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Crops;

public class SegmentNetherWart extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    IStair step = theme.getSecondary().getStair();
    IBlockFactory wall = theme.getSecondary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor;

    cursor = new Coord(origin);
    cursor.add(dir, 2);
    air.set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    air.set(editor, cursor);
    cursor = new Coord(origin);
    cursor.add(dir, 5);


    cursor = new Coord(origin);
    cursor.add(dir, 3);
    BlockType.get(BlockType.FENCE_NETHER_BRICK).set(editor, cursor);
    cursor.add(Cardinal.UP, 1);
    BlockType.get(BlockType.FENCE_NETHER_BRICK).set(editor, cursor);

    for (Cardinal orth : Cardinal.orthogonal(dir)) {
      step.setOrientation(Cardinal.reverse(orth), true);
      cursor = new Coord(origin);
      cursor.add(dir, 2);
      cursor.add(orth, 1);
      cursor.add(Cardinal.UP, 1);
      step.set(editor, cursor);
      cursor.add(Cardinal.UP, 1);
      wall.set(editor, rand, cursor);
      cursor.add(Cardinal.reverse(orth), 1);
      wall.set(editor, rand, cursor);
      cursor.add(Cardinal.DOWN, 2);
      Crops.get(Crops.NETHERWART).set(editor, cursor);
      cursor.add(orth, 1);
      Crops.get(Crops.NETHERWART).set(editor, cursor);
      cursor.add(Cardinal.DOWN, 1);
      BlockType.get(BlockType.SOUL_SAND).set(editor, cursor);
      cursor.add(Cardinal.reverse(orth), 1);
      BlockType.get(BlockType.SOUL_SAND).set(editor, cursor);
    }

  }

}
