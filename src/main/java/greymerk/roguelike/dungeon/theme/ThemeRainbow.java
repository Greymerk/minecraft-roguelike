package greymerk.roguelike.dungeon.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.Log;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public class ThemeRainbow extends ThemeBase{

	public ThemeRainbow(){
	
		BlockStripes rainbow = new BlockStripes();
		for(int i = 1; i < 16; ++i){
			MetaBlock clay = new MetaBlock(Blocks.stained_hardened_clay);
			clay.withProperty(BlockColored.COLOR, EnumDyeColor.values()[i]);
			rainbow.addBlock(clay);
		}
		
		MetaBlock stair = new MetaBlock(Blocks.acacia_stairs);
		
		MetaBlock pillar = Log.getLog(Log.ACACIA); 
		
		MetaBlock planks = new MetaBlock(Blocks.planks);
		planks.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.ACACIA);
		
		this.primary = new BlockSet(rainbow, stair, pillar);
		
		this.secondary = new BlockSet(planks, stair, pillar);;
		

	}
}
