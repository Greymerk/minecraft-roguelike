package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class RuinTower implements ITower {

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

    IBlockFactory air = BlockType.get(BlockType.AIR);
    IBlockFactory blocks = theme.getPrimary().getWall();
    IStair stair = theme.getPrimary().getStair();
    Coord floor = Tower.getBaseCoord(editor, origin);

    Coord cursor;
    Coord start;
    Coord end;

    RectSolid.fill(editor, rand,
        new Coord(origin.getX() - 4, floor.getY() + 1, origin.getZ() - 4),
        new Coord(origin.getX() + 4, floor.getY() + 3, origin.getZ() + 4),
        air);

    RectSolid.fill(editor, rand,
        new Coord(origin.getX() - 3, floor.getY() - 5, origin.getZ() - 3),
        new Coord(origin.getX() + 3, floor.getY(), origin.getZ() + 3),
        blocks);

    RectSolid.fill(editor, rand,
        new Coord(origin.getX() - 2, origin.getY() + 10, origin.getZ() - 2),
        new Coord(origin.getX() + 2, floor.getY() - 1, origin.getZ() + 2),
        blocks, false, true);

    for (int i = floor.getY(); i >= origin.getY(); --i) {
      editor.spiralStairStep(rand, new Coord(origin.getX(), i, origin.getZ()), stair, theme.getPrimary().getPillar());
    }

    for (Cardinal dir : Cardinal.directions) {
      for (Cardinal orth : Cardinal.orthogonal(dir)) {
        cursor = new Coord(floor);
        cursor.add(dir, 4);
        cursor.add(orth);
        RectSolid.fill(editor, rand,
            new Coord(cursor),
            new Coord(cursor.getX(), cursor.getY() + 1 + rand.nextInt(3), cursor.getZ()),
            blocks);
        cursor.add(orth);
        RectSolid.fill(editor, rand,
            new Coord(cursor),
            new Coord(cursor.getX(), cursor.getY() + 1 + rand.nextInt(2), cursor.getZ()),
            blocks);
      }

      start = new Coord(floor);
      start.add(Cardinal.DOWN);
      start.add(dir, 4);
      end = new Coord(start.getX(), origin.getY() + 10, start.getZ());
      start.add(Cardinal.left(dir), 2);
      end.add(Cardinal.right(dir), 2);
      RectSolid.fill(editor, rand, start, end, blocks, true, false);

      cursor = new Coord(floor);
      cursor.add(dir, 3);
      cursor.add(Cardinal.left(dir), 3);
      RectSolid.fill(editor, rand,
          new Coord(cursor.getX(), origin.getY() + 20, cursor.getZ()),
          new Coord(cursor.getX(), floor.getY() + 2 + rand.nextInt(4), cursor.getZ()),
          blocks, true, false);
    }
  }
}
