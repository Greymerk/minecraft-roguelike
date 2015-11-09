package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;
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
		
		MetaStair stair = new MetaStair(StairType.STONEBRICK);

		this.primary = new BlockSet(walls, stair, walls);
		
		
		MetaBlock pillar = Wood.get(WoodBlock.LOG);
		MetaBlock segmentWall = Wood.get(Wood.OAK, WoodBlock.PLANK);
		MetaStair segmentStair = new MetaStair(StairType.OAK);
		
		this.secondary =  new BlockSet(segmentWall, segmentStair, pillar);
	}
}
