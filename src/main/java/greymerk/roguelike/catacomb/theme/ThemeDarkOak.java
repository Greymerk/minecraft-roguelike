package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.init.Blocks;

public class ThemeDarkOak extends ThemeBase{

	public ThemeDarkOak(){
		
		MetaBlock walls = new MetaBlock(Blocks.planks, 5);
		MetaBlock stair = new MetaBlock(Blocks.dark_oak_stairs);
		MetaBlock pillar = Log.getLog(Log.DARKOAK);
		
		this.walls = new BlockSet(walls, stair, pillar);
		
		MetaBlock secondaryWalls = new MetaBlock(Blocks.planks, 2);
		//MetaBlock secondaryStair = new MetaBlock(Blocks.birch_stairs);
		//MetaBlock secondaryPillar = Log.getLog(Log.DARKOAK);
		
		this.decor = new BlockSet(secondaryWalls, stair, pillar);
		
		this.segments = new ArrayList<Segment>();
		segments.addAll(Arrays.asList(Segment.SHELF, Segment.INSET));
		
		this.arch = Segment.ARCH;
	}
}
