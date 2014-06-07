package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeStone extends ThemeBase{

	public ThemeStone(){
	
		BlockJumble stone = new BlockJumble();
		stone.addBlock(new MetaBlock(Block.stoneBrick.blockID));
		stone.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1));
		stone.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(stone, 100);
		walls.addBlock(new MetaBlock(Block.cobblestone.blockID), 10);
		walls.addBlock(new MetaBlock(Block.gravel.blockID), 5);
		
		MetaBlock stair = new MetaBlock(Block.stairsStoneBrick.blockID);
		
		this.walls = new BlockSet(walls, stair, walls);
		this.decor = this.walls;

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
