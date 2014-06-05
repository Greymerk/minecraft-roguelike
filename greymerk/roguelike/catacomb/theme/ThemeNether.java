package greymerk.roguelike.catacomb.theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.src.Block;

public class ThemeNether extends ThemeBase{

	public ThemeNether(){
	
		BlockRandomizer walls = new BlockRandomizer(new MetaBlock(Block.netherBrick.blockID));
		walls.addBlock(new MetaBlock(Block.netherrack.blockID), 10);
		walls.addBlock(new MetaBlock(Block.oreNetherQuartz.blockID), 20);
		walls.addBlock(new MetaBlock(Block.slowSand.blockID), 30);
		walls.addBlock(new MetaBlock(Block.coalBlock.blockID), 100);

		MetaBlock stair = new MetaBlock(Block.stairsNetherBrick.blockID);
		
		this.walls = new BlockSet(walls, stair, walls);
		this.decor = this.walls;

		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.INSET, Segment.NETHERSTRIPE));
		
		this.arch = Segment.NETHERARCH;
	}
}
