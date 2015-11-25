package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public enum SilverfishBlock {

	STONE, COBBLE, STONEBRICK, STONEBRICK_MOSSY, STONEBRICK_CRACKED, STONEBRICK_CHISELED;
	
	public static MetaBlock get(SilverfishBlock type){
		
		
		
		int meta;
		
		switch(type){
		case STONE: meta = 0; break;
		case COBBLE: meta = 1; break;
		case STONEBRICK: meta = 2; break;
		case STONEBRICK_MOSSY: meta = 3; break;
		case STONEBRICK_CRACKED: meta = 4; break;
		case STONEBRICK_CHISELED: meta = 5; break;
		default: meta = 0; break;
		}
		
		return new MetaBlock(Blocks.monster_egg, meta);		
	}
	
	public static IBlockFactory getJumble(){
		
		BlockJumble jumble = new BlockJumble();
		
		for(SilverfishBlock type : SilverfishBlock.values()){
			jumble.addBlock(get(type));
		}
		
		return jumble;
		
	}
	
}
