package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeEniIce extends ThemeBase{

	public ThemeEniIce(){
		
		MetaBlock ice = new MetaBlock(Blocks.packed_ice);
		MetaBlock purple = new MetaBlock(Blocks.stained_hardened_clay, 3);
		
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
		MetaBlock quartzPillar = new MetaBlock(Blocks.quartz_block, 2);
		
		this.primary = new BlockSet(floor, ice, quartzStair, quartzPillar);
		this.secondary =  new BlockSet(floor, ice, quartzStair, quartzPillar);;
	}
}
