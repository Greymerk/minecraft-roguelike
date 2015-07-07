package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.treasure.loot.LootSettings;

public class SettingsBasicLoot extends DungeonSettings{

	public SettingsBasicLoot(){
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setLoot(new LootSettings(i));
			levels.put(i, level);
		}
	}

	
}
