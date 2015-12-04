package greymerk.roguelike.dungeon.settings.builtin;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import net.minecraftforge.common.BiomeDictionary;

public class SettingsPyramidTheme extends DungeonSettings{
	
	public SettingsPyramidTheme(){
		
		this.depth = 1;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.SANDY);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new TowerSettings(Tower.PYRAMID, Theme.getTheme(Theme.PYRAMID));
		
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
		factory.addSingle(DungeonRoom.PYRAMIDTOMB);
		factory.addSingle(DungeonRoom.PYRAMIDTOMB);
		factory.addRandom(DungeonRoom.BRICK, 3);
		factory.addRandom(DungeonRoom.CORNER, 3);
		
		LevelSettings level = new LevelSettings();
		level.setTheme(Theme.getTheme(Theme.SANDSTONE));
		level.setSegments(segments);
		level.setRooms(factory);
		level.setDifficulty(3);
		
		SecretFactory secrets = new SecretFactory();
		level.setSecrets(secrets);
		
		for(int i = 0; i < 5; ++i){
			levels.put(i, level);
		}
	}
}
