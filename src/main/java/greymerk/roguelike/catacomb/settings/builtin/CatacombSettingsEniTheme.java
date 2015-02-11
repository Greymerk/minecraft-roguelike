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

public class CatacombSettingsEniTheme extends CatacombSettings{
	
	public CatacombSettingsEniTheme(){
		
		this.numLevels = 3;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.MOUNTAIN);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.ENIKO, Theme.getTheme(Theme.OAK));
		
		Theme[] themes = {Theme.ENIKO, Theme.ENIKO2, Theme.SEWER, Theme.MOSSY, Theme.ENIICE};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			
			if(i == 0){
				level.setScatter(16);
				level.setRange(60);
				level.setNumRooms(10);
				level.setDifficulty(3);
				
				DungeonFactory factory;
			
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.LIBRARY);
				factory.addSingle(Dungeon.MESS);
				factory.addRandom(Dungeon.BRICK, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
				
				SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 5);
				segments.add(Segment.ANKH, 2);
				segments.add(Segment.FLOWERS, 2);
				level.setSegments(segments);
			}
			
			if(i == 1){
				level.setScatter(16);
				level.setRange(80);
				level.setNumRooms(20);
				level.setDifficulty(3);
				
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.LIBRARY);
				factory.addSingle(Dungeon.LIBRARY);
				factory.addSingle(Dungeon.ENIKO);
				factory.addSingle(Dungeon.ENDER);
				factory.addSingle(Dungeon.FIRE);
				factory.addSingle(Dungeon.MESS);
				factory.addSingle(Dungeon.LAB);
				factory.addRandom(Dungeon.BRICK, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
			}
			
			if(i == 2){
				level.setDifficulty(4);
				
				SegmentGenerator segments = new SegmentGenerator(Segment.SEWERARCH);
				segments.add(Segment.SEWER, 7);
				segments.add(Segment.SEWERDRAIN, 4);
				segments.add(Segment.SEWERDOOR, 2);
				level.setSegments(segments);
				
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.SPIDER);
				factory.addRandom(Dungeon.SLIME, 2);
				factory.addRandom(Dungeon.BRICK, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
			}
			
			levels.put(i, level);
		}
	}
}
