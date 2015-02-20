package greymerk.roguelike.worldgen.redstone;


import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public enum Torch {

	REDSTONE, WOODEN;
	
	public static void generate(World world, Torch type, Cardinal dir, Coord pos){
		
		Block name = type == REDSTONE ? Blocks.redstone_torch : Blocks.torch; 
		
		
		
		int meta;
		switch(dir){
		case EAST: meta = 1; break;
		case WEST: meta = 2; break;
		case SOUTH: meta = 3; break;
		case NORTH: meta = 4; break;
		default: meta = 5; break;
		}
		
		MetaBlock torch = new MetaBlock(name.getStateFromMeta(meta));
		
		torch.setBlock(world, pos);
		
	}
	
}
