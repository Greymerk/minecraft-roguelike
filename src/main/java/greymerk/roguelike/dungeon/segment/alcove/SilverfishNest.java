package greymerk.roguelike.dungeon.segment.alcove;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.segment.IAlcove;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.SilverfishBlock;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.spawners.Spawner;

public class SilverfishNest implements IAlcove {

  private static int RECESSED = 6;

  @Override
  public void generate(IWorldEditor editor, Random rand, LevelSettings settings, Coord origin, Cardinal dir) {

    Coord corridor = new Coord(origin);
    Coord centre = new Coord(origin);
    centre.add(dir, RECESSED);

    nest(editor, rand, centre.getX(), centre.getY(), centre.getZ());

    Coord start = new Coord(corridor);
    start.add(Cardinal.UP);

    Coord end = new Coord(centre);
    end.add(Cardinal.UP);
    end.add(Cardinal.reverse(dir), 1);

    RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
    Spawner.generate(editor, rand, settings, centre, Spawner.SILVERFISH);

  }

  @Override
  public boolean isValidLocation(IWorldEditor editor, Coord origin, Cardinal dir) {

    Coord centre = new Coord(origin);
    centre.add(dir, RECESSED);
    int x = centre.getX();
    int y = centre.getY();
    int z = centre.getZ();

    List<Coord> toCheck = new RectSolid(new Coord(x - 2, y + 1, z - 2), new Coord(x + 2, y + 1, z + 2)).get();

    for (Coord c : toCheck) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }

  private void nest(IWorldEditor editor, Random rand, int x, int y, int z) {
    BlockWeightedRandom fish = new BlockWeightedRandom();
    IBlockFactory egg = SilverfishBlock.getJumble();
    fish.addBlock(egg, 20);
    fish.addBlock(BlockType.get(BlockType.SOUL_SAND), 5);
    RectHollow.fill(editor, rand, new Coord(x - 2, y, z - 2), new Coord(x + 2, y + 3, z + 2), fish);

    fish.set(editor, rand, new Coord(x - 1, y + 2, z));
    fish.set(editor, rand, new Coord(x + 1, y + 2, z));
    fish.set(editor, rand, new Coord(x, y + 2, z - 1));
    fish.set(editor, rand, new Coord(x, y + 2, z + 1));
    fish.set(editor, rand, new Coord(x, y + 1, z));

  }
}
