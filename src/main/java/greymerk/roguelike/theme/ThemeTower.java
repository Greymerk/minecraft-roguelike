package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class ThemeTower extends ThemeBase{

	public ThemeTower(){
	
		MetaBlock cracked = new MetaBlock(Blocks.stonebrick);
		cracked.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		MetaBlock mossy = new MetaBlock(Blocks.stonebrick);
		mossy.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
		
		MetaBlock p = new MetaBlock(Blocks.stone.getStateFromMeta(6));
		
		BlockJumble stone = new BlockJumble();
		stone.addBlock(new MetaBlock(Blocks.stonebrick));
		stone.addBlock(cracked);
		stone.addBlock(mossy);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.air), 5);
		walls.addBlock(stone, 30);
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 10);
		walls.addBlock(new MetaBlock(Blocks.gravel), 5);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		
		this.primary = new BlockSet(walls, stair, p);
		this.secondary = this.primary;

	}
}
