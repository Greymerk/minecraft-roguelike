package greymerk.roguelike.worldgen;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class Bed {

	public static void generate(World world, Cardinal dir, Coord pos){
		
		Coord cursor = new Coord(pos);
		
		int meta;
		
		switch(Cardinal.reverse(dir)){
		case SOUTH: meta = 0; break;
		case WEST: meta = 1; break;
		case NORTH: meta = 2; break;
		case EAST: meta = 3; break;
		default: meta = 0; break;
		}
		
		MetaBlock head = new MetaBlock(Blocks.bed, meta + 8);
		head.setBlock(world, cursor);
		
		MetaBlock foot = new MetaBlock(Blocks.bed, meta);
		cursor.add(dir);
		foot.setBlock(world, cursor);
	}
}
