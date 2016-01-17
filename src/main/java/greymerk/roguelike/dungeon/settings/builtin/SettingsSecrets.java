package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;

public class SettingsSecrets extends DungeonSettings{

	public SettingsSecrets(){
		for(int i = 0; i < 5; ++i){
			
			SecretFactory factory = new SecretFactory();

			switch(i){
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				break;
			}
			
			LevelSettings level = new LevelSettings();
			level.setSecrets(factory);
			levels.put(i, level);
		}
	}
}
