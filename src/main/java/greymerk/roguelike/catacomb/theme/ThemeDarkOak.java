package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeDarkOak extends ThemeBase{

	public ThemeDarkOak(){
		
		MetaBlock walls = new MetaBlock(Blocks.planks, 5);
		MetaBlock stair = new MetaBlock(Blocks.dark_oak_stairs);
		MetaBlock pillar = Log.getLog(Log.DARKOAK);
		
		this.walls = new BlockSet(walls, stair, pillar);
		
		MetaBlock secondaryWalls = new MetaBlock(Blocks.planks, 2);
		
		this.decor = new BlockSet(secondaryWalls, stair, pillar);
		
		this.segments = new WeightedRandomizer<Segment>();
		this.segments.add(new WeightedChoice<Segment>((Segment.BOOKS), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.CHEST), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.SPAWNER), 2));
		this.segments.add(new WeightedChoice<Segment>((Segment.FLOWERS), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.INSET), 4));
		this.segments.add(new WeightedChoice<Segment>((Segment.SHELF), 4));
		
		this.arch = Segment.ARCH;
	}
}
