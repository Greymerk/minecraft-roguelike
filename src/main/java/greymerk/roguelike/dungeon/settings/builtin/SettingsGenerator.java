package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;

public class SettingsGenerator extends DungeonSettings {

	public SettingsGenerator(){
		LevelGenerator[] generator = {
				LevelGenerator.CLASSIC,
				LevelGenerator.MST,
				LevelGenerator.MST,
				LevelGenerator.CLASSIC,
				LevelGenerator.CLASSIC
				};
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setGenerator(generator[i]);
			levels.put(i, level);
		}
	}
	
}
