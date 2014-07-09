package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.init.Blocks;

public class ThemeCrypt extends ThemeBase{

	public ThemeCrypt(){
	
		BlockJumble stone = new BlockJumble();
		stone.addBlock(new MetaBlock(Blocks.stonebrick));
		stone.addBlock(new MetaBlock(Blocks.stonebrick, 1));
		stone.addBlock(new MetaBlock(Blocks.stonebrick, 2));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(stone, 100);
		walls.addBlock(new MetaBlock(Blocks.cobblestone), 10);
		walls.addBlock(new MetaBlock(Blocks.gravel), 5);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		
		this.walls = new BlockSet(walls, stair, walls);
		this.decor = this.walls;

		this.segments = new WeightedRandomizer<Segment>();
		this.segments.add(new WeightedChoice<Segment>((Segment.SHELF), 8));
		this.segments.add(new WeightedChoice<Segment>((Segment.INSET), 8));
		this.segments.add(new WeightedChoice<Segment>((Segment.TOMB), 4));
		this.segments.add(new WeightedChoice<Segment>((Segment.FIREPLACE), 1));
		
		this.arch = Segment.ARCH;
	}
}
