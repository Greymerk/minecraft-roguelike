package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.BiomeDictionary;

public class SettingsColdTheme extends DungeonSettings{
	
	public SettingsColdTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.SNOWY);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new TowerSettings(Tower.ROGUE, Theme.getTheme(Theme.ICE));
		
		Theme[] themes = {Theme.ICE, Theme.ICE, Theme.CRYPT, Theme.CRYPT, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
		
		{
			SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
			segments.add(Segment.SHELF, 3);
			segments.add(Segment.INSET, 3);
			segments.add(Segment.DOOR, 10);
			segments.add(Segment.BOOKS, 1);
			segments.add(Segment.CHEST, 1);
			segments.add(Segment.SPAWNER, 1);
			
			LevelSettings level = levels.get(0);
			level.setSegments(segments);
		}

		{
			SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
			segments.add(Segment.SHELF, 5);
			segments.add(Segment.INSET, 5);
			segments.add(Segment.SKULL, 1);
			segments.add(Segment.CHEST, 1);
			segments.add(Segment.SPAWNER, 1);
			
			LevelSettings level = levels.get(3);
			level.setSegments(segments);
		}
	}
}
