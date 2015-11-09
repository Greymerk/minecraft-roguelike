package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;
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
		
		MetaStair stair = new MetaStair(StairType.COBBLE);
		MetaBlock pillar = new MetaBlock(Blocks.cobblestone_wall);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		MetaBlock walls2 = new MetaBlock(Blocks.planks);
		MetaStair stair2 = new MetaStair(StairType.OAK);
		MetaBlock pillar2 = Wood.get(WoodBlock.LOG);
		
		this.secondary = new BlockSet(floor, walls2, stair2, pillar2);;
	}
}
