package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import net.minecraft.block.BlockQuartz;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public class ThemeEniQuartz extends ThemeBase{

	public ThemeEniQuartz(){
		
		BlockWeightedRandom red = new BlockWeightedRandom();
		red.addBlock(ColorBlock.get(Blocks.stained_hardened_clay, EnumDyeColor.RED), 100);
		red.addBlock(new MetaBlock(Blocks.flowing_lava), 5);
		red.addBlock(new MetaBlock(Blocks.redstone_block), 1);
		
		BlockStripes floor = new BlockStripes();
		floor.addBlock(red);
		floor.addBlock(new MetaBlock(Blocks.obsidian));
		
		MetaBlock walls = new MetaBlock(Blocks.brick_block);		
		
		MetaBlock stair = new MetaBlock(Blocks.brick_stairs);
		
		
		this.primary = new BlockSet(floor, walls, stair, walls);
		
		MetaBlock quartz = new MetaBlock(Blocks.quartz_block);
		MetaBlock quartzStair = new MetaBlock(Blocks.quartz_stairs);
		MetaBlock quartzPillar = new MetaBlock(Blocks.quartz_block);
		quartzPillar.withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.LINES_Y);
		
		this.secondary =  new BlockSet(floor, quartz, quartzStair, quartzPillar);;
	}
}
