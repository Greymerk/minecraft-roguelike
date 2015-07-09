package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.Log;
import net.minecraft.init.Blocks;

public class ThemeMineShaft extends ThemeBase{

	public ThemeMineShaft(){
		
		BlockJumble floor = new BlockJumble();
		floor.addBlock(new MetaBlock(Blocks.gravel));
		floor.addBlock(new MetaBlock(Blocks.dirt));
		floor.addBlock(new MetaBlock(Blocks.cobblestone));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stone), 50);
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 15);
		walls.addBlock(new MetaBlock(Blocks.redstone_ore), 1);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.cobblestone_wall);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		MetaBlock walls2 = new MetaBlock(Blocks.planks);
		MetaBlock stair2 = new MetaBlock(Blocks.oak_stairs);
		MetaBlock pillar2 = Log.getLog(Log.OAK);
		
		this.secondary = new BlockSet(floor, walls2, stair2, pillar2);;
	}
}
