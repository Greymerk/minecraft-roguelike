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

public class SegmentSewer extends SegmentBase {


  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    MetaBlock water = BlockType.get(BlockType.WATER_FLOWING);
    IStair stair = theme.getSecondary().getStair();

    Coord start;
    Coord end;

    Cardinal[] orth = Cardinal.orthogonal(dir);

    start = new Coord(origin);
    start.add(Cardinal.UP, 2);
    start.add(dir);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    stair.setOrientation(Cardinal.reverse(dir), true);
    RectSolid.fill(editor, rand, start, end, stair);

    start = new Coord(origin);
    start.add(Cardinal.DOWN);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    RectSolid.fill(editor, rand, start, end, air);
    start.add(Cardinal.DOWN);
    end.add(Cardinal.DOWN);
    RectSolid.fill(editor, rand, start, end, water);
  }
}
