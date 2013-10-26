package greymerk.roguelike;

import java.util.Random;

import net.minecraft.src.Tuple;

public enum Segment {

	BRICKARCH, BRICKLAVA, BRICKSHELF;
	
	public static final Segment[] rank0 = {BRICKLAVA, BRICKSHELF};
	public static final Segment[] rank1 = {BRICKSHELF};
	
	public static ISegment getSegment(Segment choice){
		
		switch(choice){
		case BRICKARCH: return new SegmentBrickArch();
		case BRICKLAVA: return new SegmentBrickLava();
		case BRICKSHELF: return new SegmentBrickShelf();
		}
		
		return null;
		
	}
	
	public static ISegment pickSegment(Random rand, Cardinal dir, int x, int y, int z){

		Segment pillar;
		Segment[] spacers;
		
		switch(Dungeon.getRank(y)){
		case 0:
			pillar = BRICKARCH;
			spacers = rank0;
			break;
		default: 
			pillar = BRICKARCH;
			spacers = rank1;
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
