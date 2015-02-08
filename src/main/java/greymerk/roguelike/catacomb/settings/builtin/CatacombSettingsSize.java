package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;

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
