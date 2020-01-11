package greymerk.roguelike.worldgen.filter;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.Shape;

public class CobwebFilter implements IFilter {

  @Override
  public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box) {
    for (Coord pos : box.getShape(Shape.RECTSOLID)) {
      if (rand.nextInt(60) != 0) {
        continue;
      }
      if (!editor.isAirBlock(pos)) {
        continue;
      }
      if (!validLocation(editor, pos)) {
        continue;
      }

      generate(editor, rand, pos, rand.nextInt(2) + 2);
    }
  }

  private boolean validLocation(IWorldEditor editor, Coord pos) {
    for (Cardinal dir : Cardinal.values()) {
      Coord cursor = new Coord(pos);
      cursor.add(dir);
      if (!editor.isAirBlock(cursor)) {
        return true;
      }
    }
    return false;
  }

  private void generate(IWorldEditor editor, Random rand, Coord pos, int count) {
    if (!editor.isAirBlock(pos)) {
      return;
    }
    if (count <= 0) {
      return;
    }

    BlockType.get(BlockType.WEB).set(editor, pos);

    for (int i = 0; i < 2; ++i) {
      Cardinal dir = Cardinal.values()[rand.nextInt(Cardinal.values().length)];
      Coord cursor = new Coord(pos);
      cursor.add(dir);
      generate(editor, rand, cursor, count - 1);
    }
  }
}
