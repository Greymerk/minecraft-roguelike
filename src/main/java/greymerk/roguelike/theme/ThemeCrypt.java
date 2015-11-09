package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class ThemeCrypt extends ThemeBase{

	public ThemeCrypt(){
	
		MetaBlock cracked = new MetaBlock(Blocks.stonebrick);
		cracked.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		MetaBlock mossy = new MetaBlock(Blocks.stonebrick);
		mossy.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
		BlockJumble stone = new BlockJumble();
		stone.addBlock(new MetaBlock(Blocks.stonebrick));
		stone.addBlock(cracked);
		stone.addBlock(mossy);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(stone, 100);
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 10);
		walls.addBlock(new MetaBlock(Blocks.gravel), 5);
		
		MetaBlock andesite = new MetaBlock(Blocks.stone);
		andesite.withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.ANDESITE);
		MetaBlock smoothAndesite = new MetaBlock(Blocks.stone);
		smoothAndesite.withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.ANDESITE_SMOOTH);
		BlockFactoryCheckers pillar = new BlockFactoryCheckers(andesite, smoothAndesite);

		MetaStair stair = new MetaStair(StairType.STONEBRICK);
		
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = this.primary;

	}
}
