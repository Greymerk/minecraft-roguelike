package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockLayers;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeRainbow extends ThemeBase{

	public ThemeRainbow(){
	
		BlockLayers rainbow = new BlockLayers();
		for(DyeColor color : DyeColor.values()){
			MetaBlock clay = ColorBlock.get(ColorBlock.CLAY, color);
			rainbow.addBlock(clay);
		}
		
		MetaStair stair = new MetaStair(StairType.ACACIA);
		MetaBlock pillar = Wood.get(Wood.ACACIA, WoodBlock.LOG); 
		MetaBlock planks = Wood.get(Wood.ACACIA, WoodBlock.PLANK);
		
		this.primary = new BlockSet(rainbow, stair, pillar);
		this.secondary = new BlockSet(planks, stair, pillar);

	}
}
