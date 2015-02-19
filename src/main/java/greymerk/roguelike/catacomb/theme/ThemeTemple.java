package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.init.Blocks;

public class ThemeTemple extends ThemeBase{

	public ThemeTemple(){
		
		MetaBlock prismLight = new MetaBlock(Blocks.prismarine.getStateFromMeta(BlockPrismarine.BRICKSMETA));
		MetaBlock prismDark = new MetaBlock(Blocks.prismarine.getStateFromMeta(BlockPrismarine.DARKMETA));
		MetaBlock prismRough = new MetaBlock(Blocks.prismarine.getStateFromMeta(BlockPrismarine.ROUGHMETA));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(prismLight, 5);
		walls.addBlock(prismDark, 10);
		walls.addBlock(prismRough, 5);
		walls.addBlock(new MetaBlock(Blocks.sea_lantern), 3);
		
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		MetaBlock pillar = prismDark;
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = this.primary;
	}
	
}
