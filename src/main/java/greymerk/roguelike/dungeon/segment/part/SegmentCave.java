package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentCave extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);

    IBlockFactory wall = theme.getPrimary().getWall();
    BlockJumble fill = new BlockJumble();
    fill.addBlock(air);
    fill.addBlock(wall);


    Cardinal[] orth = Cardinal.orthogonal(dir);

    Coord cursor = new Coord(origin);
    Coord start;
    Coord end;

    start = new Coord(cursor);
    start.add(Cardinal.UP, 2);
    start.add(dir);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    RectSolid.fill(editor, rand, start, end, fill);
    start.add(dir);
    end.add(dir);
    RectSolid.fill(editor, rand, start, end, fill);
    start.add(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, fill);
    start.add(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, fill);

  }
}
