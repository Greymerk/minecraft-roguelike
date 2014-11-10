package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.segment.Segment;
import greymerk.roguelike.catacomb.segment.SegmentGenerator;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;
import greymerk.roguelike.catacomb.settings.CatacombTowerSettings;
import greymerk.roguelike.catacomb.settings.SpawnCriteria;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.catacomb.tower.Tower;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.BiomeDictionary;

public class CatacombSettingsEthoTheme extends CatacombSettings{
	
	public CatacombSettingsEthoTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.FOREST);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.ROGUE, Theme.getTheme(Theme.ETHO));
		
		Theme[] themes = {Theme.ETHO, Theme.ETHO, Theme.CAVE, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			
			if(i == 0){
				level.setScatter(16);
				level.setRange(60);
				level.setNumRooms(10);
				
				DungeonFactory factory;
			
				factory = new DungeonFactory();
				factory.addRandom(Dungeon.BRICK, 20);
				factory.addRandom(Dungeon.ETHO, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
				
				SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 5);
				segments.add(Segment.WHEAT, 3);
				segments.add(Segment.FLOWERS, 2);
				level.setSegments(segments);
			}
			
			if(i == 1){
				level.setScatter(16);
				level.setRange(80);
				level.setNumRooms(20);
				
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.MESS);
				factory.addRandom(Dungeon.ETHO, 10);
				factory.addRandom(Dungeon.BRICK, 5);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
				
				SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 5);
				segments.add(Segment.WHEAT, 3);
				segments.add(Segment.FLOWERS, 2);
				segments.add(Segment.CHEST, 1);
				level.setSegments(segments);
			}
			
			if(i == 2){
				SegmentGenerator segments = new SegmentGenerator(Segment.CAVE);
				segments.add(Segment.CAVE, 1);
				level.setSegments(segments);
			}
			
			levels.put(i, level);
		}
	}
}
