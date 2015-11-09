package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;
import net.minecraft.init.Blocks;

public class ThemeEtho extends ThemeBase{

	public ThemeEtho(){
		
		MetaBlock floor = new MetaBlock(Blocks.grass);
		
		MetaBlock walls = Wood.get(WoodBlock.PLANK);

		MetaStair stair = new MetaStair(StairType.OAK);
		MetaBlock pillar = Wood.get(WoodBlock.LOG);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		this.secondary =  primary;
	}
}
