package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.Log;
import net.minecraft.init.Blocks;

public class ThemeEtho extends ThemeBase{

	public ThemeEtho(){
		
		MetaBlock floor = new MetaBlock(Blocks.grass);
		
		MetaBlock walls = new MetaBlock(Blocks.planks);

		MetaBlock stair = new MetaBlock(Blocks.oak_stairs);
		MetaBlock pillar = Log.getLog(Log.OAK);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		this.secondary =  primary;
	}
}
