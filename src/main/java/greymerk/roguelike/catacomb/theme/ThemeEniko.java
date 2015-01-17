package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeEniko extends ThemeBase{

	public ThemeEniko(){
		
		BlockStripes floor = new BlockStripes();
		floor.addBlock(new MetaBlock(Blocks.stained_hardened_clay));
		floor.addBlock(new MetaBlock(Blocks.stained_hardened_clay, 3));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stonebrick), 100);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 1), 1);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 2), 5);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.double_stone_slab, 8);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		this.secondary =  primary;
	}
}
