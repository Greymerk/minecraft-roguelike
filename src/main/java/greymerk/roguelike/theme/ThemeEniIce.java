package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.block.BlockQuartz;
import net.minecraft.init.Blocks;

public class ThemeEniIce extends ThemeBase{

	public ThemeEniIce(){
		
		MetaBlock ice = new MetaBlock(Blocks.packed_ice);
		MetaBlock purple = ColorBlock.get(ColorBlock.CLAY, DyeColor.PURPLE);
		
		BlockWeightedRandom light = new BlockWeightedRandom();
		light.addBlock(purple, 100);
		light.addBlock(new MetaBlock(Blocks.flowing_water), 5);
		light.addBlock(new MetaBlock(Blocks.lapis_block), 1);
		
		BlockWeightedRandom dark = new BlockWeightedRandom();
		dark.addBlock(new MetaBlock(Blocks.obsidian), 100);
		dark.addBlock(new MetaBlock(Blocks.flowing_lava), 5);
		dark.addBlock(new MetaBlock(Blocks.redstone_block), 1);
		
		BlockStripes floor = new BlockStripes();
		floor.addBlock(light);
		floor.addBlock(dark);
		
		//MetaBlock quartz = new MetaBlock(Blocks.quartz_block);
		MetaStair stair = new MetaStair(StairType.QUARTZ);
		MetaBlock quartzPillar = new MetaBlock(Blocks.quartz_block);
		quartzPillar.withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.LINES_Y);
		
		this.primary = new BlockSet(floor, ice, stair, quartzPillar);
		this.secondary =  new BlockSet(floor, ice, stair, quartzPillar);;
	}
}
