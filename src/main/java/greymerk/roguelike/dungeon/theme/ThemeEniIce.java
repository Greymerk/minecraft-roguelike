package greymerk.roguelike.dungeon.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import net.minecraft.block.BlockQuartz;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public class ThemeEniIce extends ThemeBase{

	public ThemeEniIce(){
		
		MetaBlock ice = new MetaBlock(Blocks.packed_ice);
		MetaBlock purple = ColorBlock.get(Blocks.stained_hardened_clay, EnumDyeColor.PURPLE);
		
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
		MetaBlock quartzStair = new MetaBlock(Blocks.quartz_stairs);
		MetaBlock quartzPillar = new MetaBlock(Blocks.quartz_block);
		quartzPillar.withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.LINES_Y);
		
		this.primary = new BlockSet(floor, ice, quartzStair, quartzPillar);
		this.secondary =  new BlockSet(floor, ice, quartzStair, quartzPillar);;
	}
}
