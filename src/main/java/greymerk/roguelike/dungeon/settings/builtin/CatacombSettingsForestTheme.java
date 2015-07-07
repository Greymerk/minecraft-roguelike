package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.dungeon.settings.CatacombSettings;
import greymerk.roguelike.dungeon.settings.CatacombTowerSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.theme.Theme;
import greymerk.roguelike.dungeon.towers.Tower;

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
				factory.addSingle(DungeonRoom.CAKE);
				factory.addSingle(DungeonRoom.DARKHALL);
				factory.addRandom(DungeonRoom.BRICK, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
				level.setRooms(factory);
			}
			
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}
}