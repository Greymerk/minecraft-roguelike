package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeMossy extends ThemeBase{

	public ThemeMossy(){
	
		BlockRandomizer walls = new BlockRandomizer(new MetaBlock(Block.cobblestone.blockID));
		walls.addBlock(new MetaBlock(Block.cobblestoneMossy.blockID), 3);
		walls.addBlock(new MetaBlock(Block.silverfish.blockID, 1), 5);
		walls.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 10);
		walls.addBlock(new MetaBlock(Block.gravel.blockID), 60);
		
		MetaBlock stair = new MetaBlock(Block.stairsCobblestone.blockID);
		
		this.walls = new BlockSet(walls, stair, walls);
		this.decor = this.walls;

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.MOSSYMUSHROOM, Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.MOSSYARCH;
	}
}
