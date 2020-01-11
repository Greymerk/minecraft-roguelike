package greymerk.roguelike.worldgen.blocks.door;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface IDoor {

  void generate(IWorldEditor editor, Coord pos, Cardinal dir);

  void generate(IWorldEditor editor, Coord pos, Cardinal dir, boolean open);
}
