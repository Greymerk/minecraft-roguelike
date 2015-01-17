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

public class CatacombSettingsMesaTheme extends CatacombSettings{
	
	public CatacombSettingsMesaTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.MESA);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.ENIKO, Theme.getTheme(Theme.RAINBOW));
		
		Theme[] themes = {Theme.RAINBOW, Theme.RAINBOW, Theme.CRYPT, Theme.CRYPT, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
		
		SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
		segments.add(Segment.SHELF, 5);
		segments.add(Segment.INSET, 5);
		segments.add(Segment.SKULL, 1);
		segments.add(Segment.CHEST, 1);
		segments.add(Segment.SPAWNER, 1);
		
		CatacombLevelSettings level3 = levels.get(3);
		level3.setSegments(segments);
	}
}
