package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Repeater {

	private static final int MAX_DELAY = 3;
	
	public static void generate(World world, Random rand, Cardinal dir, int delay, Coord pos){
		
		
		int meta = 0;
		
		switch(dir){
		case NORTH: break;
		case EAST: meta = 1; break;
		case SOUTH: meta = 2; break;
		case WEST: meta = 3; break;
		}
		
		if(delay > 0){
			meta += 3 + (delay > MAX_DELAY ? MAX_DELAY : delay);
		}
		
		MetaBlock repeater = new MetaBlock(Blocks.unpowered_repeater, meta);
		repeater.setBlock(world, pos);
		world.scheduleBlockUpdate(pos.getX(), pos.getY(), pos.getZ(), Blocks.unpowered_repeater, 1);
	}
	
}
