package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.init.Blocks;

public class ThemeEnder extends ThemeBase{

	public ThemeEnder(){
		
		BlockStripes floor = new BlockStripes();
		floor.addBlock(new MetaBlock(Blocks.obsidian));
		floor.addBlock(new MetaBlock(Blocks.end_stone));
		
		MetaBlock walls = new MetaBlock(Blocks.sandstone);

		MetaStair stair = new MetaStair(StairType.SANDSTONE);
		MetaBlock pillar = new MetaBlock(Blocks.obsidian);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		this.secondary =  primary;
	}
}
