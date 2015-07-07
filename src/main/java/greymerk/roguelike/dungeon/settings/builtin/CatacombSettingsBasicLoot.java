package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.dungeon.settings.CatacombSettings;
import greymerk.roguelike.treasure.loot.LootSettings;

public class CatacombSettingsBasicLoot extends CatacombSettings{

	public CatacombSettingsBasicLoot(){
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setLoot(new LootSettings(i));
			levels.put(i, level);
		}
	}

	
}
