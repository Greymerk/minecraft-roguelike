package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.catacomb.segment.SegmentGenerator;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;

public class CatacombSettingsSegments extends CatacombSettings{

	public CatacombSettingsSegments(){
		for(int i = 0; i < 5; ++i){
			
			SegmentGenerator segments;
			
			switch(i){
			case 0:
				segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 12);
				segments.add(Segment.LAMP, 2);
				segments.add(Segment.FIREPLACE, 2);
				segments.add(Segment.FLOWERS, 1);
				break;
			case 1:
				segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.SHELF, 1);
				segments.add(Segment.INSET, 3);
				segments.add(Segment.DOOR, 5);
				segments.add(Segment.BOOKS, 1);
				segments.add(Segment.CHEST, 1);
				segments.add(Segment.SPAWNER, 1);
				break;
			case 2:
				segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.SHELF, 6);
				segments.add(Segment.INSET, 6);
				segments.add(Segment.SKULL, 1);
				segments.add(Segment.TOMB, 3);
				segments.add(Segment.CELL, 1);
				segments.add(Segment.CHEST, 1);
				segments.add(Segment.SPAWNER, 1);
				segments.add(Segment.FIREPLACE, 1);
				break;
			case 3:
				segments = new SegmentGenerator(Segment.MOSSYARCH);
				segments.add(Segment.SHELF, 3);
				segments.add(Segment.INSET, 3);
				segments.add(Segment.SILVERFISH, 1);
				segments.add(Segment.ARROW, 5);
				segments.add(Segment.SKULL, 2);
				segments.add(Segment.CHEST, 1);
				segments.add(Segment.CELL, 2);
				segments.add(Segment.SPAWNER, 1);
				segments.add(Segment.TOMB, 1);
				break;
			case 4:
				segments = new SegmentGenerator(Segment.NETHERARCH);
				segments.add(Segment.NETHERLAVA, 3);
				segments.add(Segment.NETHERSTRIPE, 3);
				segments.add(Segment.NETHERWART, 3);
				segments.add(Segment.CHEST, 1);
				segments.add(Segment.SPAWNER, 1);
				break;
			default:
				segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.SHELF, 1);
				break;
			}
			
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setSegments(segments);
			levels.put(i, level);
			
		}
	}
}
