package greymerk.roguelike.dungeon.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;

public class SettingsRandom extends DungeonSettings{

	public SettingsRandom(Random rand){
		
		this.towerSettings = new TowerSettings(
				Tower.getRandom(rand),
				Theme.getRandom(rand));
		
		Map<Integer, LevelSettings> levels = new HashMap<Integer, LevelSettings>();
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			
			level.setDifficulty(i);
			level.setGenerator(LevelGenerator.CLASSIC);
			level.setNumRooms(15);
			level.setRange(60);
			
			DungeonFactory rooms = DungeonFactory.getRandom(rand, 8);
			level.setRooms(rooms);
			
			level.setScatter(15);
			
			SecretFactory secrets = SecretFactory.getRandom(rand, 2);
			level.setSecrets(secrets);
			
			SegmentGenerator segments = SegmentGenerator.getRandom(rand, 12);
			level.setSegments(segments);
			
			level.setTheme(Theme.getRandom(rand));
			levels.put(i, level);
		}
		
		this.levels = levels;
		
	}
	
	
}
