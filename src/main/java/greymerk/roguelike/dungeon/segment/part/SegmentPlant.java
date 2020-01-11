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
import greymerk.roguelike.worldgen.blocks.TallPlant;
import greymerk.roguelike.worldgen.blocks.Trapdoor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentPlant extends SegmentBase {

  @Override
  protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {

    MetaBlock air = BlockType.get(BlockType.AIR);
    IStair stair = theme.getSecondary().getStair();

    Coord cursor = new Coord(origin);
    Coord start;
    Coord end;

    Cardinal[] orth = Cardinal.orthogonal(dir);

    cursor.add(dir, 2);
    start = new Coord(cursor);
    start.add(orth[0], 1);
    end = new Coord(cursor);
    end.add(orth[1], 1);
    end.add(Cardinal.UP, 2);
    RectSolid.fill(editor, rand, start, end, air);

    start.add(dir, 1);
    end.add(dir, 1);
    RectSolid.fill(editor, rand, start, end, theme.getSecondary().getWall(), false, true);

    cursor.add(Cardinal.UP, 2);
    for (Cardinal d : orth) {
      Coord c = new Coord(cursor);
      c.add(d, 1);
      stair.setOrientation(Cardinal.reverse(d), true);
      stair.set(editor, c);
    }

    cursor = new Coord(origin);
    cursor.add(dir, 2);
    plant(editor, rand, theme, cursor);

  }

  private void plant(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {
    Coord cursor;
    BlockType.get(BlockType.DIRT_PODZOL).set(editor, origin);

    for (Cardinal dir : Cardinal.directions) {
      cursor = new Coord(origin);
      cursor.add(dir);
      Trapdoor.get(Trapdoor.OAK, Cardinal.reverse(dir), true, true).set(editor, rand, cursor, true, false);
    }

    cursor = new Coord(origin);
    cursor.add(Cardinal.UP);
    TallPlant[] plants = new TallPlant[]{TallPlant.FERN, TallPlant.ROSE, TallPlant.PEONY};
    TallPlant.generate(editor, plants[rand.nextInt(plants.length)], cursor);
  }
}
