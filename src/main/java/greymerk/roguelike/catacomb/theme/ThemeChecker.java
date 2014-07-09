package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.BlockFactoryCheckers;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeChecker extends ThemeBase{

	public ThemeChecker(){
	
		MetaBlock one = new MetaBlock(Blocks.obsidian);
		MetaBlock two = new MetaBlock(Blocks.quartz_block);
		
		IBlockFactory checks = new BlockFactoryCheckers(one, two);
		
		MetaBlock stair = new MetaBlock(Blocks.quartz_stairs);
		
		this.walls = new BlockSet(checks, stair, checks);
		
		this.decor = walls;
		
		this.segments = new WeightedRandomizer<Segment>();
		this.segments.add(new WeightedChoice<Segment>((Segment.SHELF), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.INSET), 1));
		
		this.arch = Segment.ARCH;
	}
}
