package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Lever {

	public static void generate(World world, Cardinal dir, Coord pos, boolean active){
		
		int meta;
		
		switch(dir){
		case EAST: meta = 1; break;
		case WEST: meta = 2; break;
		case SOUTH: meta = 3; break;
		case NORTH: meta = 4; break;
		case UP: meta = 5; break;
		case DOWN: meta = 7; break;
		
		default: meta = 5; break;
		}
		
		if(active){
			meta += 8;
		}
		
		
		MetaBlock lever = new MetaBlock(Blocks.lever.getStateFromMeta(meta));
		
		lever.setBlock(world, pos);
	}
	
}
