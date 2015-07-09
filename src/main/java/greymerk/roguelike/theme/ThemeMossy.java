package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class ThemeMossy extends ThemeBase{

	public ThemeMossy(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 100);
		walls.addBlock(new MetaBlock(Blocks.mossy_cobblestone), 30);
		
		MetaBlock egg = new MetaBlock(Blocks.monster_egg);
		egg.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.COBBLESTONE);
		walls.addBlock(egg, 5);
		
		MetaBlock mossy = new MetaBlock(Blocks.stonebrick);
		mossy.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
		walls.addBlock(mossy, 10);
		walls.addBlock(new MetaBlock(Blocks.gravel), 15);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_stairs);
		
		this.primary = new BlockSet(walls, stair, walls);
		this.secondary = this.primary;
	}
}
