package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.init.Blocks;

public class ThemeIce extends ThemeBase{

	public ThemeIce(){
	
		MetaBlock walls = new MetaBlock(Blocks.snow);
		MetaStair stair = new MetaStair(StairType.QUARTZ);
		MetaBlock pillar = new MetaBlock(Blocks.packed_ice);
		
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary =  this.primary;
	}
}
