package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeEniko2 extends ThemeBase{

	public ThemeEniko2(){
		
		BlockStripes floor = new BlockStripes();
		floor.addBlock(new MetaBlock(Blocks.stained_hardened_clay, 8));
		floor.addBlock(new MetaBlock(Blocks.stained_hardened_clay, 11));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stonebrick), 20);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 1), 10);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 2), 5);
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 3);
		walls.addBlock(new MetaBlock(Blocks.gravel), 1);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.double_stone_slab, 8);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		this.secondary =  primary;
	}
}
