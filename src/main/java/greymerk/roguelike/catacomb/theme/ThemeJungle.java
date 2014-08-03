package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeJungle extends ThemeBase{

	public ThemeJungle(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.mossy_cobblestone), 50);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 1), 30);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 2), 20);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 3), 15);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_stairs);
		
		MetaBlock pillar = new MetaBlock(Blocks.log, 3);
		
		
		
		this.walls = new BlockSet(walls, stair, pillar);
		this.decor = new BlockSet(new MetaBlock(Blocks.stonebrick, 3), stair, pillar);
	}
}
