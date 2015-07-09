package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeEnder extends ThemeBase{

	public ThemeEnder(){
		
		BlockStripes floor = new BlockStripes();
		floor.addBlock(new MetaBlock(Blocks.obsidian));
		floor.addBlock(new MetaBlock(Blocks.end_stone));
		
		MetaBlock walls = new MetaBlock(Blocks.sandstone);

		MetaBlock stair = new MetaBlock(Blocks.sandstone_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.obsidian);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		this.secondary =  primary;
	}
}
