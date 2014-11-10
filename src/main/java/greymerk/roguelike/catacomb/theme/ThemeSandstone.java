package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeSandstone extends ThemeBase{

	public ThemeSandstone(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.sandstone), 100);
		walls.addBlock(new MetaBlock(Blocks.sand), 5);
		
		MetaBlock stair = new MetaBlock(Blocks.sandstone_stairs);
		
		MetaBlock pillar = new MetaBlock(Blocks.sandstone, 2);
		
		this.primary = new BlockSet(walls, stair, pillar);
		
		MetaBlock SegmentWall = new MetaBlock(Blocks.sandstone, 1);
		
		this.secondary =  new BlockSet(SegmentWall, stair, pillar);

	}
}
