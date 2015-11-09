package greymerk.roguelike.theme;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.init.Blocks;

public class ThemeNether extends ThemeBase{

	public ThemeNether(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.nether_brick), 200);
		walls.addBlock(new MetaBlock(Blocks.netherrack), 20);
		walls.addBlock(new MetaBlock(Blocks.quartz_ore), 20);
		walls.addBlock(new MetaBlock(Blocks.soul_sand), 15);
		walls.addBlock(new MetaBlock(Blocks.coal_block), 5);

		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(walls, 2000);
		floor.addBlock(new MetaBlock(Blocks.redstone_block), 50);
		if (RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS)) floor.addBlock(new MetaBlock(Blocks.gold_block), 2);
		if (RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS)) floor.addBlock(new MetaBlock(Blocks.diamond_block), 1);
		
		MetaStair stair = new MetaStair(StairType.NETHERBRICK);
		
		MetaBlock pillar = new MetaBlock(Blocks.obsidian);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		this.secondary = this.primary;

	}
}
