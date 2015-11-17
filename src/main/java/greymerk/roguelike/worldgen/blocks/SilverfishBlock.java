package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.init.Blocks;

public enum SilverfishBlock {

	STONE, COBBLE, STONEBRICK, STONEBRICK_MOSSY, STONEBRICK_CRACKED, STONEBRICK_CHISELED;
	
	public static MetaBlock get(SilverfishBlock type){
		
		MetaBlock block = new MetaBlock(Blocks.monster_egg);
		
		switch(type){
		case STONE: block.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.STONE); break;
		case COBBLE: block.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.COBBLESTONE); break;
		case STONEBRICK: block.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.STONEBRICK); break;
		case STONEBRICK_MOSSY: block.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.MOSSY_STONEBRICK); break;
		case STONEBRICK_CRACKED: block.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.CRACKED_STONEBRICK); break;
		case STONEBRICK_CHISELED: block.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.CHISELED_STONEBRICK); break;
		default: block.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.STONE); break;
		}
		
		return block;
		
	}
	
	public static IBlockFactory getJumble(){
		
		BlockJumble jumble = new BlockJumble();
		
		for(SilverfishBlock type : SilverfishBlock.values()){
			jumble.addBlock(get(type));
		}
		
		return jumble;
		
	}
	
}
