package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.dungeon.SecretFactory;
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
		
		this.numLevels = 1;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.FOREST);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.ETHO, Theme.getTheme(Theme.ETHOTOWER));
		
		Theme[] themes = {Theme.ETHO, Theme.ETHO, Theme.MINESHAFT, Theme.MINESHAFT, Theme.CAVE};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			level.setDifficulty(3);
			
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
				
				SecretFactory secrets = new SecretFactory();
				List<Dungeon> rooms;
				rooms = new ArrayList<Dungeon>();
				rooms.add(Dungeon.BTEAM);
				rooms.add(Dungeon.AVIDYA);
				secrets.addRoom(rooms, 1);
				secrets.addRoom(Dungeon.FIREWORK);
				level.setSecrets(secrets);
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
			
			if(i == 2 || i == 3){
				SegmentGenerator segments = new SegmentGenerator(Segment.MINESHAFT);
				segments.add(Segment.MINESHAFT, 1);
				level.setSegments(segments);
				
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addRandom(Dungeon.BRICK, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
			}
			
			if(i == 4){
				SegmentGenerator segments = new SegmentGenerator(Segment.CAVE);
				segments.add(Segment.CAVE, 1);
				level.setSegments(segments);
				
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addRandom(Dungeon.BRICK, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
			}
			
			levels.put(i, level);
			
		}
	}
}