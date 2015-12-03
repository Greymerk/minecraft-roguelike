package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeHouse extends ThemeBase{

	public ThemeHouse(){
		
		MetaBlock walls = Wood.get(Wood.OAK, WoodBlock.PLANK);
		MetaStair stair = new MetaStair(StairType.OAK);
		MetaBlock pillar = Log.getLog(Wood.OAK);
		MetaBlock floor = walls;
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		MetaBlock secondaryWalls = BlockType.get(BlockType.GRANITE_POLISHED);
		MetaBlock secondaryFloor = BlockType.get(BlockType.DIORITE_POLISHED);
		this.secondary = new BlockSet(secondaryFloor, secondaryWalls, stair, secondaryWalls);

	}
}
