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

public class ThemeEniQuartz extends ThemeBase{

	public ThemeEniQuartz(){
		
		BlockWeightedRandom red = new BlockWeightedRandom();
		red.addBlock(ColorBlock.get(ColorBlock.CLAY, DyeColor.RED), 100);
		red.addBlock(new MetaBlock(Blocks.flowing_lava), 5);
		red.addBlock(new MetaBlock(Blocks.redstone_block), 1);
		
		BlockStripes floor = new BlockStripes();
		floor.addBlock(red);
		floor.addBlock(new MetaBlock(Blocks.obsidian));
		
		MetaBlock walls = new MetaBlock(Blocks.brick_block);		
		
		MetaStair stair = new MetaStair(StairType.BRICK);
		
		
		this.primary = new BlockSet(floor, walls, stair, walls);
		
		MetaBlock quartz = new MetaBlock(Blocks.quartz_block);
		MetaStair quartzStair = new MetaStair(StairType.QUARTZ);
		MetaBlock quartzPillar = new MetaBlock(Blocks.quartz_block);
		quartzPillar.withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.LINES_Y);
		
		this.secondary =  new BlockSet(floor, quartz, quartzStair, quartzPillar);;
	}
}
