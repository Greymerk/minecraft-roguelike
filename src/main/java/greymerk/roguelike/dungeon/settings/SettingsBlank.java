package greymerk.roguelike.dungeon.settings;

import java.util.HashMap;

import greymerk.roguelike.treasure.loot.LootRuleManager;

public class SettingsBlank extends DungeonSettings{
	
	public SettingsBlank(){
		this.depth = DungeonSettings.MAX_NUM_LEVELS;
		
		levels = new HashMap<Integer, LevelSettings>();
		this.lootRules = new LootRuleManager();
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			levels.put(i, level);
		}
	}
	
}
