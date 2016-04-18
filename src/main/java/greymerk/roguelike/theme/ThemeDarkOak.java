package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeDarkOak extends ThemeBase{

	public ThemeDarkOak(){
		
		MetaBlock walls = Wood.get(Wood.DARKOAK, WoodBlock.PLANK);
		MetaStair stair = new MetaStair(StairType.DARKOAK);
		MetaBlock pillar = Log.get(Wood.DARKOAK);
		
		this.primary = new BlockSet(walls, stair, pillar);
		MetaBlock secondaryWalls = Wood.get(Wood.DARKOAK, WoodBlock.PLANK);
		this.secondary = new BlockSet(secondaryWalls, stair, pillar);

	}
}
