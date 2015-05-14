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

public class CatacombSettingsForestTheme extends CatacombSettings{
	
	public CatacombSettingsForestTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.FOREST);
		this.criteria.setWeight(6);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.ROGUE, Theme.getTheme(Theme.ETHO));
		
		Theme[] themes = {Theme.ETHO, Theme.OAK, Theme.CRYPT, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			
			if(i == 0){
				
				SegmentGenerator segments = new SegmentGenerator(Segment.ARCH);
				segments.add(Segment.DOOR, 8);
				segments.add(Segment.LAMP, 2);
				segments.add(Segment.FLOWERS, 1);
				segments.add(Segment.WHEAT, 2);
				level.setSegments(segments);
				
				DungeonFactory factory = new DungeonFactory();
				factory.addSingle(Dungeon.CAKE);
				factory.addSingle(Dungeon.DARKHALL);
				factory.addRandom(Dungeon.BRICK, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				level.setRooms(factory);
			}
			
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}
}