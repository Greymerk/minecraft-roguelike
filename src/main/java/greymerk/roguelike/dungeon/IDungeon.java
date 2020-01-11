package greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Coord;

public interface IDungeon {

  void generate(ISettings setting, Coord pos);

  void spawnInChunk(Random rand, int chunkX, int chunkZ);

  Coord getPosition();

  List<IDungeonLevel> getLevels();

  List<ITreasureChest> getChests();

}
