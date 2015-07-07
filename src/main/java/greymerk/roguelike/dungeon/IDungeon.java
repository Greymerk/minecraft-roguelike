package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Coord;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public interface IDungeon {

	public void generate(World world, ISettings setting, int x, int z);
	
	public void spawnInChunk(World world, Random rand, int x, int z);
	
	public List<DungeonNode> getNodes();
	
	public List<IDungeonLevel> getLevels();
	
	public List<ITreasureChest> getChests();
	
	public List<Coord> getChestLocations();
	
	public List<Coord> getSpawnerLocations();
}
