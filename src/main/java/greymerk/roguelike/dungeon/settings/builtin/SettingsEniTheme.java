package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.theme.Theme;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.treasure.loot.LootSettings;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.BiomeDictionary;

public class SettingsEniTheme extends DungeonSettings{
	
	public SettingsEniTheme(){
		
		this.depth = 3;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.MOUNTAIN);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new TowerSettings(Tower.ENIKO, Theme.getTheme(Theme.OAK));
		
		Theme[] themes = {Theme.ENIKO, Theme.ENIKO2, Theme.SEWER, Theme.MOSSY, Theme.ENIICE};
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			
			if(i == 0){
				level.setScatter(16);
				level.setRange(60);
				level.setNumRooms(10);
				level.setDifficulty(3);
				level.setLoot(new LootSettings(3));
				
				DungeonFactory factory;
			
				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.LIBRARY);
				factory.addSingle(DungeonRoom.MESS);
				factory.addSingle(DungeonRoom.ENCHANT);
				factory.addRandom(DungeonRoom.BRICK, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
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
				level.setLoot(new LootSettings(3));
				
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.LIBRARY);
				factory.addSingle(DungeonRoom.LIBRARY);
				factory.addSingle(DungeonRoom.ENIKO);
				factory.addSingle(DungeonRoom.ENDER);
				factory.addSingle(DungeonRoom.FIRE);
				factory.addSingle(DungeonRoom.MESS);
				factory.addSingle(DungeonRoom.LAB);
				factory.addRandom(DungeonRoom.BRICK, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
				level.setRooms(factory);
			}
			
			if(i == 2){
				level.setDifficulty(4);
				level.setLoot(new LootSettings(4));
				
				SegmentGenerator segments = new SegmentGenerator(Segment.SEWERARCH);
				segments.add(Segment.SEWER, 7);
				segments.add(Segment.SEWERDRAIN, 4);
				segments.add(Segment.SEWERDOOR, 2);
				level.setSegments(segments);
				
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.SPIDER);
				factory.addRandom(DungeonRoom.SLIME, 2);
				factory.addRandom(DungeonRoom.BRICK, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
				level.setRooms(factory);
			}
			
			levels.put(i, level);
		}
	}
}
