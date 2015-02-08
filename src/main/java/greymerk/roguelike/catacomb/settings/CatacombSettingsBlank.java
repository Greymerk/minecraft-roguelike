package greymerk.roguelike.catacomb.settings;

import java.util.HashMap;

public class CatacombSettingsBlank extends CatacombSettings{
	
	public CatacombSettingsBlank(){
		this.numLevels = CatacombSettings.MAX_NUM_LEVELS;
		
		levels = new HashMap<Integer, CatacombLevelSettings>();
		
		for(int i = 0; i < 5; ++i){
			levels.put(i, new CatacombLevelSettings());
		}
	}
	
}
