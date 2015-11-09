package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class ThemeSnow extends ThemeBase{

	public ThemeSnow(){
	
		MetaBlock cracked = new MetaBlock(Blocks.stonebrick);
		cracked.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		MetaBlock mossy = new MetaBlock(Blocks.stonebrick);
		mossy.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stonebrick), 10);
		walls.addBlock(cracked, 1);
		walls.addBlock(mossy, 1);
		
		MetaStair stair = new MetaStair(StairType.STONEBRICK);
		MetaBlock pillar = Log.getLog(Wood.SPRUCE);
		
		this.primary = new BlockSet(walls, stair, pillar);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.snow);
		MetaStair SegmentStair = new MetaStair(StairType.BRICK);
		MetaBlock pillar2 = new MetaBlock(Blocks.brick_block);
		
		this.secondary =  new BlockSet(SegmentWall, SegmentStair, pillar2);
	}
}
