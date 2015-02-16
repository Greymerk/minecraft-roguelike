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
import greymerk.roguelike.treasure.loot.LootSettings;

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
				level.setDifficulty(4);
				level.setLoot(new LootSettings(4));
				
				DungeonFactory factory;
			
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.TREETHO);
				factory.addSingle(Dungeon.TREETHO);
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
			
			levels.put(i, level);
			
		}
	}
}