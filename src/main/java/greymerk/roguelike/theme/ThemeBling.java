package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeBling extends ThemeBase{

	public ThemeBling(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.iron_block), 10);
		walls.addBlock(new MetaBlock(Blocks.gold_block), 3);
		walls.addBlock(new MetaBlock(Blocks.emerald_block), 10);
		walls.addBlock(new MetaBlock(Blocks.diamond_block), 20);
		
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.lapis_block);
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = this.primary;
	}
}
