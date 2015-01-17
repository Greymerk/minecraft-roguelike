package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeEthoTower extends ThemeBase{

	public ThemeEthoTower(){
		
		MetaBlock primaryWall = new MetaBlock(Blocks.stained_hardened_clay, 9);
		
		MetaBlock stair = new MetaBlock(Blocks.sandstone_stairs);

		
		MetaBlock secondaryWall = new MetaBlock(Blocks.sandstone, 2);
		
		this.primary = new BlockSet(primaryWall, primaryWall, stair, primaryWall);
		
		this.secondary = new BlockSet(secondaryWall, secondaryWall, stair, secondaryWall);;
	}

}
