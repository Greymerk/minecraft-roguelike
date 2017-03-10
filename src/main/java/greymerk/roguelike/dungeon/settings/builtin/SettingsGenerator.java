package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.LevelGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;

public class SettingsGenerator extends DungeonSettings {

	public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "generator");
	
	public SettingsGenerator(){
		
		this.id = ID;
		
		LevelGenerator[] generator = {
				LevelGenerator.CLASSIC,
				LevelGenerator.CLASSIC,
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
