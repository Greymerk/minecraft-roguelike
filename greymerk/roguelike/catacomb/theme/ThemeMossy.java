package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeMossy extends ThemeBase{

	public ThemeMossy(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Block.cobblestone.blockID), 100);
		walls.addBlock(new MetaBlock(Block.cobblestoneMossy.blockID), 30);
		walls.addBlock(new MetaBlock(Block.silverfish.blockID, 1), 5);
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 10);
		walls.addBlock(new MetaBlock(Block.gravel.blockID), 15);
		
		MetaBlock stair = new MetaBlock(Block.stairsCobblestone.blockID);
		
		this.walls = new BlockSet(walls, stair, walls);
		this.decor = this.walls;

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.MOSSYMUSHROOM, Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.MOSSYARCH;
	}
}
