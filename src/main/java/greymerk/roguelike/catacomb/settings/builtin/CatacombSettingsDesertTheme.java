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
		
		this.numLevels = 1;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.SANDY);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new CatacombTowerSettings(Tower.PYRAMID, Theme.getTheme(Theme.PYRAMID));
		
		SegmentGenerator segments;
		segments = new SegmentGenerator(Segment.SQUAREARCH);
		segments.add(Segment.INSET, 6);
		segments.add(Segment.SHELF, 6);
		segments.add(Segment.ANKH, 2);
		segments.add(Segment.CHEST, 1);
		segments.add(Segment.SKULL, 2);
		segments.add(Segment.TOMB, 3);
		
		DungeonFactory factory;
		factory = new DungeonFactory();
		factory.addSingle(Dungeon.PYRAMIDTOMB);
		factory.addSingle(Dungeon.PYRAMIDTOMB);
		factory.addRandom(Dungeon.BRICK, 3);
		factory.addRandom(Dungeon.CORNER, 3);
		
		CatacombLevelSettings level = new CatacombLevelSettings();
		level.setTheme(Theme.getTheme(Theme.SANDSTONE));
		level.setSegments(segments);
		level.setRooms(factory);
		level.setDifficulty(4);
		
		for(int i = 0; i < 5; ++i){
			levels.put(i, level);
		}
	}
}
