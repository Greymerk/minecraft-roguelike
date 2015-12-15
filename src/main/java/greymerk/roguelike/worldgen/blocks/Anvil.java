package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockAnvil;
import net.minecraft.init.Blocks;

public enum Anvil {

	NEW_ANVIL, DAMAGED_ANVIL, VERY_DAMAGED_ANVIL;
	
	public static MetaBlock get(Anvil damage, Cardinal dir){
		
		MetaBlock anvil = new MetaBlock(Blocks.anvil);
		
		switch(damage){
		case NEW_ANVIL: anvil.withProperty(BlockAnvil.DAMAGE, 0); break;
		case DAMAGED_ANVIL: anvil.withProperty(BlockAnvil.DAMAGE, 1); break;
		case VERY_DAMAGED_ANVIL: anvil.withProperty(BlockAnvil.DAMAGE, 2); break;
		default:
		}
		
		anvil.withProperty(BlockAnvil.FACING, Cardinal.getFacing(dir));
		
		return anvil;
	}
	
}
