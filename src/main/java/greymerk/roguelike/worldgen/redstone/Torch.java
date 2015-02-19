package greymerk.roguelike.worldgen.redstone;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public enum Torch {

	REDSTONE, WOODEN;
	
	public static void generate(World world, Torch type, Cardinal dir, Coord pos){
		
		Block name;
		switch(type){
		case REDSTONE: name = Blocks.redstone_torch; break;
		default: name = Blocks.torch;
		}
		
		MetaBlock torch = new MetaBlock(name);
		torch.withProperty(BlockTorch.FACING_PROP, Cardinal.getFacing(dir));
		
		torch.setBlock(world, pos);
		
	}
	
}
