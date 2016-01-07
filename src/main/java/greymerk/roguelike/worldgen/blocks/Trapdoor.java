package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.init.Blocks;

public enum Trapdoor {

	OAK, IRON;
	
	public static MetaBlock get(Trapdoor type, Cardinal dir, boolean bottom, boolean open){
		
		MetaBlock block;
		
		switch(type){
		case OAK: block = new MetaBlock(Blocks.trapdoor); break;
		case IRON: block = new MetaBlock(Blocks.iron_trapdoor); break;
		default: block = new MetaBlock(Blocks.trapdoor); break;
		}
		
		block.withProperty(BlockTrapDoor.FACING, Cardinal.getFacing(dir));
		
		if(bottom){
			block.withProperty(BlockTrapDoor.HALF, BlockTrapDoor.DoorHalf.BOTTOM);
		}
		
		if(open){
			block.withProperty(BlockTrapDoor.OPEN, true);
		}
		
		return block;
		
	}
	
}
