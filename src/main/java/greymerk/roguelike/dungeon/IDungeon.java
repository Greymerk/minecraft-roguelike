package greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IDungeon {

	public void generate(WorldEditor editor, ISettings setting, int x, int z);
	
	public void spawnInChunk(WorldEditor editor	, Random rand, int x, int z);
	
	public List<DungeonNode> getNodes();
	
	public List<IDungeonLevel> getLevels();
	
	public List<ITreasureChest> getChests();
	
	public List<Coord> getChestLocations();
	
	public List<Coord> getSpawnerLocations();
}
