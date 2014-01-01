package greymerk.roguelike.catacomb.segment;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.part.SegmentArch;
import greymerk.roguelike.catacomb.segment.part.SegmentInset;
import greymerk.roguelike.catacomb.segment.part.SegmentFire;
import greymerk.roguelike.catacomb.segment.part.SegmentShelf;
import greymerk.roguelike.worldgen.Cardinal;

import java.util.Random;

import net.minecraft.src.Tuple;

public enum Segment {

	ARCH, FIRE, SHELF, INSET;
	
	public static final Segment[] level0 = {FIRE, SHELF, INSET};
	public static final Segment[] level1 = {SHELF, INSET};
	
	public static ISegment getSegment(Segment choice){
		
		switch(choice){
		case ARCH: return new SegmentArch();
		case FIRE: return new SegmentFire();
		case SHELF: return new SegmentShelf();
		case INSET: return new SegmentInset();
		}
		
		return null;
		
	}
	
	public static ISegment pickSegment(Random rand, Cardinal dir, int x, int y, int z){

		Segment pillar;
		Segment[] spacers;
		
		switch(Catacomb.getLevel(y)){
		case 0:
			pillar = ARCH;
			spacers = level0;
			break;
		default: 
			pillar = ARCH;
			spacers = level1;
		}
	
		if((dir == Cardinal.NORTH || dir == Cardinal.SOUTH) && z % 3 == 0){
			if(z % 6 == 0){
				return getSegment(pillar);
			}
			
			return getSegment(spacers[rand.nextInt(spacers.length)]);
		}
		
		if((dir == Cardinal.WEST || dir == Cardinal.EAST) && x % 3 == 0){
			if(x % 6 == 0){
				return getSegment(pillar);
			}
			
			return getSegment(spacers[rand.nextInt(spacers.length)]);
		}
		
		return null;
	}
}
