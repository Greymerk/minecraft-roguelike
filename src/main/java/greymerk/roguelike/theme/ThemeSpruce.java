package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.Log;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class ThemeSpruce extends ThemeBase{

	public ThemeSpruce(){
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stonebrick), 20);
		MetaBlock cracked = new MetaBlock(Blocks.stonebrick);
		cracked.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		walls.addBlock(cracked, 10);
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 5);
		walls.addBlock(new MetaBlock(Blocks.gravel), 1);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		
		this.primary = new BlockSet(walls, stair, walls);
		
		MetaBlock spruce = new MetaBlock(Blocks.planks);
		spruce.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.SPRUCE);
		MetaBlock SegmentWall = spruce;
		MetaBlock SegmentStair = new MetaBlock(Blocks.spruce_stairs);
		
		MetaBlock pillar = Log.getLog(Log.SPRUCE, Cardinal.DOWN);
		this.secondary =  new BlockSet(SegmentWall, SegmentStair, pillar);

	}
}
