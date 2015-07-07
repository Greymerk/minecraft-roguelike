package greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public interface ILevelGenerator {

	public void generate(World world, Random rand);
	
	public List<DungeonNode> getNodes();
	
	public List<DungeonTunnel> getTunnels();
	
	public DungeonNode getEnd();
	
}
