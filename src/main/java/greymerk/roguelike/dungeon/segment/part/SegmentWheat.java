package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentWheat extends SegmentBase {


  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    Coord cursor;
    Coord start;
    Coord end;

    cursor = new Coord(origin);
    cursor.add(Cardinal.DOWN);
    cursor.add(dir, 3);
    BlockType.get(BlockType.WATER_FLOWING).set(editor, cursor);

    Cardinal[] orth = Cardinal.orthogonal(dir);
    start = new Coord(origin);
    start.add(dir, 2);
    end = new Coord(start);
    start.add(orth[0]);
    end.add(orth[1]);
    start.add(Cardinal.UP, 2);
    end.add(dir);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall());

    start = new Coord(origin);
    start.add(dir, 2);
    end = new Coord(start);
    start.add(orth[0], 1);
    end.add(orth[1], 1);
    end.add(Cardinal.UP, 1);
    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
    start.add(Cardinal.DOWN, 1);
    end.add(Cardinal.DOWN, 2);

    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.FARMLAND));
    start.add(Cardinal.UP, 1);
    end.add(Cardinal.UP, 1);
    BlockJumble crops = new BlockJumble();
    crops.addBlock(Crops.get(Crops.WHEAT));
    crops.addBlock(Crops.get(Crops.CARROTS));
    crops.addBlock(Crops.get(Crops.POTATOES));
    RectSolid.fill(editor, rand, start, end, crops);

    cursor = new Coord(origin);
    cursor.add(dir, 3);
    cursor.add(Cardinal.UP, 1);
    MetaBlock pumpkin = Crops.getPumpkin(Cardinal.reverse(dir), true);
    pumpkin.set(editor, cursor);

    IStair stair = theme.getSecondary().getStair();

    for (Cardinal d : orth) {
      cursor = new Coord(origin);
      cursor.add(dir, 2);
      cursor.add(d, 1);
      cursor.add(Cardinal.UP, 1);
      stair.setOrientation(Cardinal.reverse(d), true);
      stair.set(editor, cursor);
    }
  }
}
