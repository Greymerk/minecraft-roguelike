package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeDarkOak extends ThemeBase{

	public ThemeDarkOak(){
		
		MetaBlock walls = new MetaBlock(Blocks.planks, 5);
		MetaBlock stair = new MetaBlock(Blocks.dark_oak_stairs);
		MetaBlock pillar = Log.getLog(Log.DARKOAK);
		
		this.primary = new BlockSet(walls, stair, pillar);
		
		MetaBlock secondaryWalls = new MetaBlock(Blocks.planks, 2);
		
		this.secondary = new BlockSet(secondaryWalls, stair, pillar);

	}
}
