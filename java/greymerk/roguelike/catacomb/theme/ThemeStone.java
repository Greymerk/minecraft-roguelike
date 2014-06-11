package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ThemeStone extends ThemeBase{

	public ThemeStone(){
	
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

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
