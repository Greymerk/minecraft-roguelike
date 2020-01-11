package greymerk.roguelike.dungeon.base;

import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.shapes.RectHollow;

public abstract class DungeonBase implements IDungeonRoom, Comparable<DungeonBase> {

  @Override
  public abstract boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin);

  @Override
  public abstract int getSize();

  @Override
  public boolean validLocation(IWorldEditor editor, Cardinal dir, Coord pos) {

    int size = getSize();
    Coord start = new Coord(pos.getX() - size, pos.getY() - 2, pos.getZ() - size);
    Coord end = new Coord(pos.getX() + size, pos.getY() + 5, pos.getZ() + size);

    for (Coord cursor : new RectHollow(start, end)) {
      MetaBlock b = editor.getBlock(cursor);
      if (!b.getMaterial().isSolid()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int compareTo(DungeonBase other) {
    return this.getSize() - other.getSize();
  }
}
