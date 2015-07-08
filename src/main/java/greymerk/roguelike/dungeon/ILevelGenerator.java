package greymerk.roguelike.dungeon;

import java.util.List;

public interface ILevelGenerator {

	public void generate();
	
	public List<DungeonNode> getNodes();
	
	public List<DungeonTunnel> getTunnels();
	
}
