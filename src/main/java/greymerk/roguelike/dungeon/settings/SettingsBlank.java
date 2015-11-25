package greymerk.roguelike.dungeon.settings;

import java.util.HashMap;

public class SettingsBlank extends DungeonSettings{
	
	public SettingsBlank(){
		this.depth = DungeonSettings.MAX_NUM_LEVELS;
		
		levels = new HashMap<Integer, LevelSettings>();
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			levels.put(i, level);
		}
	}
	
}
