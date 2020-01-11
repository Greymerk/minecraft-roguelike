package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.redstone.Lever;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentLamp extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    IStair stair = theme.getPrimary().getStair();
    IBlockFactory wall = theme.getPrimary().getWall();
    MetaBlock air = BlockType.get(BlockType.AIR);

    Coord cursor;
    Coord start;
    Coord end;

    Cardinal[] orth = Cardinal.orthogonal(dir);

    start = new Coord(origin);
    start.add(dir, 2);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.add(Cardinal.UP, 3);
    end = new Coord(start);
    start.add(dir);
    start.add(orth[0]);
    end.add(Cardinal.reverse(dir));
    end.add(orth[1]);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(origin);
    start.add(dir, 3);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    end.add(dir, 2);
    end.add(Cardinal.UP, 6);
    RectSolid.fill(editor, rand, start, end, wall);
    start = new Coord(end);
    start.add(Cardinal.DOWN, 2);
    start.add(Cardinal.reverse(dir), 6);
    start.add(orth[0], 2);
    RectSolid.fill(editor, rand, start, end, wall);

    for (Cardinal side : orth) {

      cursor = new Coord(origin);
      cursor.add(dir, 2);
      cursor.add(side);
      stair.setOrientation(Cardinal.reverse(side), false).set(editor, cursor);
      cursor.add(Cardinal.UP, 2);
      stair.setOrientation(Cardinal.reverse(side), true).set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP, 4);
    overheadLight(editor, rand, theme, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    cursor.add(dir, 2);

    Coord lever = new Coord(cursor);
    cursor.add(dir);
    ColorBlock.get(ColorBlock.CLAY, DyeColor.ORANGE).set(editor, cursor);
    Lever.generate(editor, Cardinal.reverse(dir), lever, false);
    cursor.add(dir);
    Torch.generate(editor, Torch.REDSTONE, dir, cursor);
    cursor.add(Cardinal.UP, 2);
    Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
    cursor.add(Cardinal.UP, 2);
    start = new Coord(cursor);
    end = new Coord(start);
    end.add(Cardinal.reverse(dir), 3);
    MetaBlock wire = BlockType.get(BlockType.REDSTONE_WIRE);
    RectSolid.fill(editor, rand, start, end, wire);
  }

  private void overheadLight(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

    IStair stair = theme.getPrimary().getStair();

    Coord cursor;

    BlockType.get(BlockType.AIR).set(editor, origin);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir);
      stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
      cursor.add(Cardinal.orthogonal(dir)[0]);
      stair.set(editor, cursor);
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    BlockType.get(BlockType.REDSTONE_LAMP).set(editor, cursor);
  }


}
