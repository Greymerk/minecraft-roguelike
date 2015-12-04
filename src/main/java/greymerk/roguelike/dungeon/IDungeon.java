package greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.treasure.ITreasureChest;

public interface IDungeon {

	public void generate(ISettings setting, int x, int z);
	
	public void spawnInChunk(Random rand, int x, int z);
	
	public List<ITreasureChest> getChests();
	
}
