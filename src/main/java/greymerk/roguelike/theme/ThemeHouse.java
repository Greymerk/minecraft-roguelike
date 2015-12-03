package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeHouse extends ThemeBase{

	public ThemeHouse(){
		
		BlockWeightedRandom stone = new BlockWeightedRandom();
		stone.addBlock(BlockType.get(BlockType.COBBLESTONE), 20);
		stone.addBlock(BlockType.get(BlockType.ANDESITE), 4);
		stone.addBlock(BlockType.get(BlockType.GRAVEL), 1);
		
		IBlockFactory walls = stone;
		MetaStair stair = new MetaStair(StairType.COBBLE);
		MetaBlock pillar = Log.getLog(Wood.OAK);
		MetaBlock floor = BlockType.get(BlockType.ANDESITE_POLISHED);
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		MetaBlock secondaryWalls = Wood.get(Wood.OAK, WoodBlock.PLANK);
		MetaBlock secondaryFloor = secondaryWalls;
		MetaStair secondaryStair = new MetaStair(StairType.OAK);
		MetaBlock secondaryPillar = Log.getLog(Wood.OAK);
		this.secondary = new BlockSet(secondaryFloor, secondaryWalls, secondaryStair, secondaryPillar);

	}
}
