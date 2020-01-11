package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentMushrooms extends SegmentBase {

  private BlockWeightedRandom mushrooms;


  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal wallDirection, ITheme theme, Coord origin) {

    IStair stair = theme.getSecondary().getStair();
    MetaBlock air = BlockType.get(BlockType.AIR);

    mushrooms = new BlockWeightedRandom();
    mushrooms.addBlock(FlowerPot.getFlower(FlowerPot.REDMUSHROOM), 3);
    mushrooms.addBlock(FlowerPot.getFlower(FlowerPot.BROWNMUSHROOM), 3);
    mushrooms.addBlock(air, 10);

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = Cardinal.orthogonal(wallDirection);
    start = new Coord(origin);
    start.add(wallDirection, 2);
    end = new Coord(start);
    start.add(orth[0], 1);
    end.add(orth[1], 1);
    end.add(Cardinal.UP, 1);
    RectSolid.fill(editor, rand, start, end, air);
    start.add(Cardinal.DOWN, 1);
    end.add(Cardinal.DOWN, 2);

    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.MYCELIUM));
    start.add(Cardinal.UP, 1);
    end.add(Cardinal.UP, 1);
    RectSolid.fill(editor, rand, start, end, mushrooms);

    for (Cardinal d : orth) {
      cursor = new Coord(origin);
      cursor.add(wallDirection, 2);
      cursor.add(d, 1);
      cursor.add(Cardinal.UP, 1);
      stair.setOrientation(Cardinal.reverse(d), true);
      stair.set(editor, cursor);
    }

  }
}
