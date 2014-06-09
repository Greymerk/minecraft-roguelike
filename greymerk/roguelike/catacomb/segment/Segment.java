package greymerk.roguelike.catacomb.segment;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.part.SegmentArch;
import greymerk.roguelike.catacomb.segment.part.SegmentInset;
import greymerk.roguelike.catacomb.segment.part.SegmentFire;
import greymerk.roguelike.catacomb.segment.part.SegmentJungle;
import greymerk.roguelike.catacomb.segment.part.SegmentMossyArch;
import greymerk.roguelike.catacomb.segment.part.SegmentMossyMushrooms;
import greymerk.roguelike.catacomb.segment.part.SegmentNetherArch;
import greymerk.roguelike.catacomb.segment.part.SegmentNetherLava;
import greymerk.roguelike.catacomb.segment.part.SegmentNetherStripes;
import greymerk.roguelike.catacomb.segment.part.SegmentNetherWart;
import greymerk.roguelike.catacomb.segment.part.SegmentShelf;
import greymerk.roguelike.worldgen.Cardinal;

import java.util.Random;

import net.minecraft.src.Tuple;

public enum Segment {

	ARCH, FIRE, SHELF, INSET, MOSSYARCH, MOSSYMUSHROOM, NETHERARCH, NETHERSTRIPE, NETHERWART, NETHERLAVA, JUNGLE;
	
	public static final Segment[] level0 = {FIRE, SHELF, INSET};
	public static final Segment[] level1 = {SHELF, INSET};
	public static final Segment[] level2 = {SHELF, INSET};
	public static final Segment[] level3 = {MOSSYMUSHROOM, SHELF, INSET};
	public static final Segment[] level4 = {INSET, NETHERSTRIPE};
	
	public static ISegment getSegment(Segment choice){
		
		switch(choice){
		case ARCH: return new SegmentArch();
		case FIRE: return new SegmentFire();
		case SHELF: return new SegmentShelf();
		case INSET: return new SegmentInset();
		case MOSSYARCH: return new SegmentMossyArch();
		case MOSSYMUSHROOM: return new SegmentMossyMushrooms();
		case NETHERARCH: return new SegmentNetherArch();
		case NETHERSTRIPE: return new SegmentNetherStripes();
		case NETHERWART: return new SegmentNetherWart();
		case NETHERLAVA: return new SegmentNetherLava();
		case JUNGLE: return new SegmentJungle();
		}
		
		return null;
		
	}
}
