package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeEnder extends ThemeBase{

	public ThemeEnder(){
		
		BlockStripes floor = new BlockStripes();
		floor.addBlock(BlockType.get(BlockType.OBSIDIAN));
		floor.addBlock(BlockType.get(BlockType.END_STONE));
		
		MetaBlock walls = BlockType.get(BlockType.SANDSTONE);

		MetaStair stair = new MetaStair(StairType.SANDSTONE);
		MetaBlock pillar = BlockType.get(BlockType.OBSIDIAN);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		this.secondary =  primary;
	}
}
