package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.DungeonSettings;

public class SettingsSize extends DungeonSettings{
	
	public SettingsSize(){
		
		int[] numRooms = {15, 20, 30, 20, 10};
		int[] range = {50, 60, 80, 60, 40};
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setNumRooms(numRooms[i]);
			level.setRange(range[i]);
			levels.put(i, level);
		}
	}

	
}
