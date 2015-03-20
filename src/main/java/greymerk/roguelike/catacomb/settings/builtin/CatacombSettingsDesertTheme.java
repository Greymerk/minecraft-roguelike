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

public class CatacombSettingsDesertTheme extends CatacombSettings{
	
	public CatacombSettingsDesertTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.SANDY);
		this.criteria.setBiomeTypes(biomes);
		this.criteria.setWeight(8);
		
		this.towerSettings = new CatacombTowerSettings(Tower.ROGUE, Theme.getTheme(Theme.PYRAMID));
		
		Theme[] themes = {Theme.SANDSTONE, Theme.SANDSTONE, Theme.CRYPT, Theme.CRYPT, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			
			if(i == 0){
				
				SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 20);
				segments.add(Segment.ANKH, 3);
				segments.add(Segment.SKULL, 2);
				segments.add(Segment.TOMB, 1);
				level.setSegments(segments);
				
				DungeonFactory factory = new DungeonFactory();
				factory.addSingle(Dungeon.CAKE);
				factory.addSingle(Dungeon.PYRAMIDTOMB);
				factory.addRandom(Dungeon.BRICK, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
			}
			
			levels.put(i, level);
		}
	}
}