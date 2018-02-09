package greymerk.roguelike.dungeon;

import java.util.List;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.worldgen.IBounded;

public interface ILevelLayout {
	
	public List<IBounded> getBoundingBoxes();
	
	public List<DungeonNode> getNodes();
	
	public List<DungeonTunnel> getTunnels();
	
	public DungeonNode getStart();
	
	public DungeonNode getEnd();
	
	public boolean hasEmptyRooms();
	
	public DungeonNode getBestFit(IDungeonRoom room);
	
}
