package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeIce extends ThemeBase{

	public ThemeIce(){
	
		MetaBlock walls = new MetaBlock(Blocks.snow);
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.packed_ice);
		
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary =  this.primary;
	}
}
