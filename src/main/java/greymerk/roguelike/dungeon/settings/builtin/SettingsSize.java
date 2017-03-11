package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.DungeonSettings;

public class SettingsSize extends DungeonSettings{
	
	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "size");
	
	public SettingsSize(){
		
		this.id = ID;
		
		int[] numRooms = {10, 15, 30, 20, 15};
		int[] range = {40, 50, 80, 60, 50};
		
		for(int i = 0; i < 5; ++i){
			LevelSettings level = new LevelSettings();
			level.setNumRooms(numRooms[i]);
			level.setRange(range[i]);
			level.setScatter(12);
			levels.put(i, level);
		}
	}

	
}
