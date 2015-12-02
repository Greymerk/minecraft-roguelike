package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.LootSettings;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.BiomeDictionary;

public class SettingsMountainTheme extends DungeonSettings{
	
	public SettingsMountainTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.MOUNTAIN);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new TowerSettings(Tower.ENIKO, Theme.getTheme(Theme.OAK));
		
		Theme[] themes = {Theme.ENIKO, Theme.ENIKO2, Theme.SEWER, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			
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
