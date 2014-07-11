package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeNether extends ThemeBase{

	public ThemeNether(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.nether_brick), 200);
		walls.addBlock(new MetaBlock(Blocks.netherrack), 20);
		walls.addBlock(new MetaBlock(Blocks.quartz_ore), 20);
		walls.addBlock(new MetaBlock(Blocks.soul_sand), 15);
		walls.addBlock(new MetaBlock(Blocks.coal_block), 5);

		MetaBlock stair = new MetaBlock(Blocks.nether_brick_stairs);
		
		MetaBlock pillar = new MetaBlock(Blocks.obsidian);
		
		this.walls = new BlockSet(walls, stair, pillar);
		this.decor = this.walls;

		this.segments = new WeightedRandomizer<Segment>();
		this.segments.add(new WeightedChoice<Segment>((Segment.SPAWNER), 2));
		this.segments.add(new WeightedChoice<Segment>((Segment.CHEST), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.NETHERSTRIPE), 2));
		this.segments.add(new WeightedChoice<Segment>((Segment.NETHERWART), 2));
		this.segments.add(new WeightedChoice<Segment>((Segment.INSET), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.SHELF), 1));
		this.segments.add(new WeightedChoice<Segment>((Segment.NETHERLAVA), 2));
		
		this.arch = Segment.NETHERARCH;
	}
}
