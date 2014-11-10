package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeSewer extends ThemeBase{

	public ThemeSewer(){
		
		BlockWeightedRandom wall = new BlockWeightedRandom();
		wall.addBlock(new MetaBlock(Blocks.stonebrick), 10);
		wall.addBlock(new MetaBlock(Blocks.stonebrick, 1), 4);
		wall.addBlock(new MetaBlock(Blocks.stonebrick, 2), 1);
		
		MetaBlock floor = new MetaBlock(Blocks.stonebrick);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		
		
		this.primary = new BlockSet(floor, wall, stair, wall);
		
		this.secondary = this.primary;
	}
}
