package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeMossy extends ThemeBase{

	public ThemeMossy(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 100);
		walls.addBlock(new MetaBlock(Blocks.mossy_cobblestone), 30);
		walls.addBlock(new MetaBlock(Blocks.monster_egg, 1), 5);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 2), 10);
		walls.addBlock(new MetaBlock(Blocks.gravel), 15);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_stairs);
		
		this.walls = new BlockSet(walls, stair, walls);
		this.decor = this.walls;

		this.segments = new WeightedRandomizer<Segment>();
		this.segments.add(new WeightedChoice<Segment>((Segment.SILVERFISH), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.MUSHROOM), 2));
		this.segments.add(new WeightedChoice<Segment>((Segment.SHELF), 3));
		this.segments.add(new WeightedChoice<Segment>((Segment.INSET), 5));
		this.segments.add(new WeightedChoice<Segment>((Segment.SKULL), 2));
		this.segments.add(new WeightedChoice<Segment>((Segment.SPAWNER), 2));
		this.segments.add(new WeightedChoice<Segment>((Segment.CHEST), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.TOMB), 1));
		
		this.arch = Segment.MOSSYARCH;
	}
}
