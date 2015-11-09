package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class ThemeSewer extends ThemeBase{

	public ThemeSewer(){
		
		MetaBlock cracked = new MetaBlock(Blocks.stonebrick);
		cracked.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		MetaBlock mossy = new MetaBlock(Blocks.stonebrick);
		mossy.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
		
		BlockWeightedRandom wall = new BlockWeightedRandom();
		wall.addBlock(new MetaBlock(Blocks.stonebrick), 10);
		wall.addBlock(mossy, 4);
		wall.addBlock(cracked, 1);
		
		MetaBlock floor = new MetaBlock(Blocks.stonebrick);
		
		MetaStair stair = new MetaStair(StairType.STONEBRICK);
		
		
		this.primary = new BlockSet(floor, wall, stair, wall);
		
		this.secondary = this.primary;
	}
}
