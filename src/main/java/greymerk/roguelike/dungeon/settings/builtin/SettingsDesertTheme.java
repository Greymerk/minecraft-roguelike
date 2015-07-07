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

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.BiomeDictionary;

public class SettingsDesertTheme extends DungeonSettings{
	
	public SettingsDesertTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.SANDY);
		this.criteria.setBiomeTypes(biomes);
		this.criteria.setWeight(8);
		
		this.towerSettings = new TowerSettings(Tower.ROGUE, Theme.getTheme(Theme.PYRAMID));
		
		Theme[] themes = {Theme.SANDSTONE, Theme.SANDSTONE, Theme.CRYPT, Theme.CRYPT, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			
			if(i == 0){
				
				SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 20);
				segments.add(Segment.ANKH, 3);
				segments.add(Segment.SKULL, 2);
				segments.add(Segment.TOMB, 1);
				level.setSegments(segments);
				
				DungeonFactory factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.CAKE);
				factory.addSingle(DungeonRoom.PYRAMIDTOMB);
				factory.addRandom(DungeonRoom.BRICK, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
				level.setRooms(factory);
			}
			
			levels.put(i, level);
		}
	}
}