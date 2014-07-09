package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeRainbow extends ThemeBase{

	public ThemeRainbow(){
	
		BlockStripes rainbow = new BlockStripes();
		for(int i = 1; i < 16; ++i){
			rainbow.addBlock(new MetaBlock(Blocks.stained_hardened_clay, i));
		}
		
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		
		BlockFactoryCheckers pillar = new BlockFactoryCheckers(new MetaBlock(Blocks.obsidian), new MetaBlock(Blocks.quartz_block)); 
		
		this.walls = new BlockSet(rainbow, stair, pillar);
		
		this.decor = new BlockSet(new MetaBlock(Blocks.glowstone), stair, new MetaBlock(Blocks.emerald_block));;
		
		this.segments = new WeightedRandomizer<Segment>();
		this.segments.add(new WeightedChoice<Segment>((Segment.SHELF), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.INSET), 1));
		
		this.arch = Segment.ARCH;
	}
}
