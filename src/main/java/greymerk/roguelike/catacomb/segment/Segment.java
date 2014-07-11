package greymerk.roguelike.catacomb.segment;

import greymerk.roguelike.catacomb.segment.part.SegmentArch;
import greymerk.roguelike.catacomb.segment.part.SegmentBooks;
import greymerk.roguelike.catacomb.segment.part.SegmentChest;
import greymerk.roguelike.catacomb.segment.part.SegmentDoor;
import greymerk.roguelike.catacomb.segment.part.SegmentFireArch;
import greymerk.roguelike.catacomb.segment.part.SegmentFirePlace;
import greymerk.roguelike.catacomb.segment.part.SegmentFlowers;
import greymerk.roguelike.catacomb.segment.part.SegmentInset;
import greymerk.roguelike.catacomb.segment.part.SegmentJungle;
import greymerk.roguelike.catacomb.segment.part.SegmentMossyArch;
import greymerk.roguelike.catacomb.segment.part.SegmentMushrooms;
import greymerk.roguelike.catacomb.segment.part.SegmentNetherArch;
import greymerk.roguelike.catacomb.segment.part.SegmentNetherLava;
import greymerk.roguelike.catacomb.segment.part.SegmentNetherStripes;
import greymerk.roguelike.catacomb.segment.part.SegmentNetherWart;
import greymerk.roguelike.catacomb.segment.part.SegmentShelf;
import greymerk.roguelike.catacomb.segment.part.SegmentSilverfish;
import greymerk.roguelike.catacomb.segment.part.SegmentSkull;
import greymerk.roguelike.catacomb.segment.part.SegmentSpawner;
import greymerk.roguelike.catacomb.segment.part.SegmentTomb;
import greymerk.roguelike.catacomb.segment.part.SegmentWheat;

public enum Segment {

	ARCH, FIREARCH, FIREPLACE, SHELF, INSET, MOSSYARCH, MUSHROOM, NETHERARCH,
	NETHERSTRIPE, NETHERWART, NETHERLAVA, JUNGLE, BOOKS, SPAWNER, 
	WHEAT, TOMB, CHEST, SILVERFISH, SKULL, FLOWERS, DOOR;
	
	
	public static ISegment getSegment(Segment choice){
		
		switch(choice){
		case ARCH: return new SegmentArch();
		case FIREARCH: return new SegmentFireArch();
		case FIREPLACE: return new SegmentFirePlace();
		case SHELF: return new SegmentShelf();
		case INSET: return new SegmentInset();
		case MOSSYARCH: return new SegmentMossyArch();
		case MUSHROOM: return new SegmentMushrooms();
		case NETHERARCH: return new SegmentNetherArch();
		case NETHERSTRIPE: return new SegmentNetherStripes();
		case NETHERWART: return new SegmentNetherWart();
		case NETHERLAVA: return new SegmentNetherLava();
		case JUNGLE: return new SegmentJungle();
		case BOOKS: return new SegmentBooks();
		case SPAWNER: return new SegmentSpawner();
		case WHEAT: return new SegmentWheat();
		case TOMB: return new SegmentTomb();
		case CHEST: return new SegmentChest();
		case SILVERFISH: return new SegmentSilverfish();
		case SKULL: return new SegmentSkull();
		case FLOWERS: return new SegmentFlowers();
		case DOOR: return new SegmentDoor();
		}
		
		return null;
		
	}
}
