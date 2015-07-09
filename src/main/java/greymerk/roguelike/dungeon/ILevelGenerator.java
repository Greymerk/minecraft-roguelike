package greymerk.roguelike.dungeon;

import greymerk.roguelike.worldgen.Coord;

import java.util.List;

public interface ILevelGenerator {

	public void generate(Coord start, DungeonNode oldEnd);
	
	public List<DungeonNode> getNodes();
	
	public List<DungeonTunnel> getTunnels();
	
	public DungeonNode getEnd();
	
}
