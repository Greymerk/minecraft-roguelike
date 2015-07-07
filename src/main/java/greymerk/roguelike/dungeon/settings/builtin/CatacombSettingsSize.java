package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.dungeon.settings.CatacombSettings;

public class CatacombSettingsSize extends CatacombSettings{
	
	public CatacombSettingsSize(){
		
		int[] numRooms = {15, 20, 30, 20, 10};
		int[] range = {50, 60, 80, 60, 40};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setNumRooms(numRooms[i]);
			level.setRange(range[i]);
			levels.put(i, level);
		}
	}

	
}
