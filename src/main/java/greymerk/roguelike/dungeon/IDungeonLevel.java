package greymerk.roguelike.dungeon;

import java.util.Collection;
import java.util.List;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Coord;

public interface IDungeonLevel {

	public LevelSettings getSettings();
	
	public List<DungeonNode> getNodes();
	
	public List<DungeonTunnel> getTunnels();

	public boolean inRange(Coord pos);
	
	boolean hasNearbyNode(Coord pos);

	public Collection<? extends ITreasureChest> getChests();
	
}
