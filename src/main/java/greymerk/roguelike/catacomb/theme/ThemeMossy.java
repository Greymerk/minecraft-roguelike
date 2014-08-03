package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeMossy extends ThemeBase{

	public ThemeMossy(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 100);
		walls.addBlock(new MetaBlock(Blocks.mossy_cobblestone), 30);
		walls.addBlock(new MetaBlock(Blocks.monster_egg, 1), 5);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 2), 10);
		walls.addBlock(new MetaBlock(Blocks.gravel), 15);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_stairs);
		
		this.walls = new BlockSet(walls, stair, walls);
		this.decor = this.walls;
	}
}
