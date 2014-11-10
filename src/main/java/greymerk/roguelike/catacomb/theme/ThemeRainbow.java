package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeRainbow extends ThemeBase{

	public ThemeRainbow(){
	
		BlockStripes rainbow = new BlockStripes();
		for(int i = 1; i < 16; ++i){
			rainbow.addBlock(new MetaBlock(Blocks.stained_hardened_clay, i));
		}
		
		MetaBlock stair = new MetaBlock(Blocks.acacia_stairs);
		
		MetaBlock pillar = Log.getLog(Log.ACACIA); 
		
		MetaBlock planks = new MetaBlock(Blocks.planks, 4);
		
		this.primary = new BlockSet(rainbow, stair, pillar);
		
		this.secondary = new BlockSet(planks, stair, pillar);;
		

	}
}
