package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.Log;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class ThemeOak extends ThemeBase{

	public ThemeOak(){
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stonebrick), 30);
		MetaBlock cracked = new MetaBlock(Blocks.stonebrick);
		cracked.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		walls.addBlock(cracked, 20);
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 5);
		walls.addBlock(new MetaBlock(Blocks.gravel), 1);
		
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);

		
		this.primary = new BlockSet(walls, stair, walls);
		
		
		MetaBlock pillar = Log.getLog(Log.OAK);
		MetaBlock SegmentWall = new MetaBlock(Blocks.planks);
		MetaBlock SegmentStair = new MetaBlock(Blocks.oak_stairs);
		
		this.secondary =  new BlockSet(SegmentWall, SegmentStair, pillar);
	}
}
