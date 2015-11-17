package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemePyramid  extends ThemeBase{

	public ThemePyramid(){
		
		MetaBlock smooth = BlockType.get(BlockType.SANDSTONE_SMOOTH);
		MetaBlock chisel = BlockType.get(BlockType.SANDSTONE_CHISELED);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(smooth, 100);
		walls.addBlock(BlockType.get(BlockType.SANDSTONE), 10);
		walls.addBlock(chisel, 5);
		walls.addBlock(BlockType.get(BlockType.SAND), 5);
		
		MetaStair stair = new MetaStair(StairType.SANDSTONE);
		
		MetaBlock pillar = smooth;
		
		this.primary = new BlockSet(walls, stair, pillar);
		
		MetaBlock SegmentWall = chisel;
		
		this.secondary =  new BlockSet(SegmentWall, stair, pillar);

	}
	
}
