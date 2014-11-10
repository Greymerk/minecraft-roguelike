package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeMuddy extends ThemeBase{

	public ThemeMuddy(){
	
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(new MetaBlock(Blocks.flowing_water), 10);
		floor.addBlock(new MetaBlock(Blocks.clay), 3);
		floor.addBlock(new MetaBlock(Blocks.dirt), 1);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 50);
		walls.addBlock(new MetaBlock(Blocks.mossy_cobblestone), 30);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 1), 10);
		walls.addBlock(new MetaBlock(Blocks.dirt), 15);
		walls.addBlock(new MetaBlock(Blocks.clay), 30);
		walls.addBlock(new MetaBlock(Blocks.gravel), 15);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_stairs);
		
		MetaBlock pillar = new MetaBlock(Blocks.stonebrick, 2);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		MetaBlock segmentWall = Log.getLog(Log.DARKOAK);
		MetaBlock segmentStair = new MetaBlock(Blocks.dark_oak_stairs);

		
		this.secondary = new BlockSet(floor, segmentWall, segmentStair, segmentWall);
	}
}
