package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeCave extends ThemeBase{

	public ThemeCave(){
		
		BlockJumble floor = new BlockJumble();
		floor.addBlock(new MetaBlock(Blocks.gravel));
		floor.addBlock(new MetaBlock(Blocks.dirt));
		floor.addBlock(new MetaBlock(Blocks.cobblestone));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stone), 1000);
		walls.addBlock(new MetaBlock(Blocks.dirt), 100);
		walls.addBlock(new MetaBlock(Blocks.gravel), 50);
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 20);
		walls.addBlock(new MetaBlock(Blocks.coal_ore), 10);
		walls.addBlock(new MetaBlock(Blocks.iron_ore), 5);
		walls.addBlock(new MetaBlock(Blocks.emerald_ore), 2);
		walls.addBlock(new MetaBlock(Blocks.diamond_ore), 1);
		walls.addBlock(new MetaBlock(Blocks.flowing_water), 3);
		walls.addBlock(new MetaBlock(Blocks.flowing_lava), 1);

		MetaBlock stair = new MetaBlock(Blocks.stone_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.cobblestone);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		this.secondary =  primary;
	}
}
