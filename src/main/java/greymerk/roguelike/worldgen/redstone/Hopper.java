package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Hopper {

	public static void generate(World world, Cardinal dir, Coord pos){
		
		MetaBlock hopper = new MetaBlock(Blocks.hopper);
		
		int meta;
		
		switch(dir){
		case NORTH: meta = 2; break;
		case SOUTH: meta = 3; break;
		case WEST: meta = 4; break;
		case EAST: meta = 5; break;
		default: meta = 0;
		}
		
		hopper.setMeta(meta);
		
		hopper.setBlock(world, pos);
	}
}
