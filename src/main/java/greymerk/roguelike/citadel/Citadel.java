package greymerk.roguelike.citadel;

import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.util.mst.MinimumSpanningTree;
import greymerk.roguelike.worldgen.Coord;

import java.util.Random;

import net.minecraft.world.World;

public class Citadel {

	public static final int EDGE_LENGTH = 17;
	
	public static void generate(World world, int x, int z){
		
		Random rand = getRandom(world, x, z);
		
		MinimumSpanningTree mst = new MinimumSpanningTree(rand, 7, EDGE_LENGTH);
		//mst.generate(world, rand, new MetaBlock(Blocks.glowstone), new Coord(x, 100, z));
		
		CityGrounds.generate(world, rand, mst, Theme.getTheme(Theme.OAK), new Coord(x, 50, z));
	}
	
	
	public static Random getRandom(World world, int x, int z){
		long seed = world.getSeed() * x * z;
		Random rand = new Random();
		rand.setSeed(seed);
		return rand;
	}
}
