package greymerk.roguelike.worldgen.redstone;

import java.util.Random;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class Comparator {
	
	public static void generate(IWorldEditor world, Random rand, Cardinal dir, boolean subtraction, Coord pos){
		
		int meta = 0;
		
		switch(dir){
		case NORTH: break;
		case EAST: meta = 1; break;
		case SOUTH: meta = 2; break;
		case WEST: meta = 3; break;
		default:
		}
		
		meta += subtraction ? 5 : 0;
		
		MetaBlock comparator = new MetaBlock(Blocks.unpowered_comparator, meta);
		comparator.set(world, pos);
	}
	
}
