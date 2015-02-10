package greymerk.roguelike.catacomb.settings.builtin;

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

public class CatacombSettingsJungleTheme extends CatacombSettings{
	
	public CatacombSettingsJungleTheme(){
		
		this.numLevels = 2;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.JUNGLE);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.JUNGLE, Theme.getTheme(Theme.JUNGLE));
		
		Theme[] themes = {Theme.JUNGLE, Theme.MOSSY, Theme.MOSSY, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			
			SegmentGenerator segments;
			segments = new SegmentGenerator(Segment.ARCH);
			segments.add(Segment.DOOR, 8);
			segments.add(Segment.FIREPLACE, 2);
			segments.add(Segment.ARROW, 1);
			segments.add(Segment.CHEST, 1);
			segments.add(Segment.JUNGLE, 3);
			segments.add(Segment.SKULL, 3);
			segments.add(Segment.SPAWNER, 2);
			segments.add(Segment.SILVERFISH, 1);
			level.setSegments(segments);
			
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}
}
