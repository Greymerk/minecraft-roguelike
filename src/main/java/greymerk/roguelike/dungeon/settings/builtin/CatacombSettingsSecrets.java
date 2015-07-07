package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.dungeon.settings.CatacombSettings;

public class CatacombSettingsSecrets extends CatacombSettings{

	public CatacombSettingsSecrets(){
		for(int i = 0; i < 5; ++i){
			
			SecretFactory factory = new SecretFactory();

			switch(i){
			case 0:
				factory.addRoom(DungeonRoom.BEDROOM, 2);
				break;
			case 1:
				factory.addRoom(DungeonRoom.SMITH);
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
			
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setSecrets(factory);
			levels.put(i, level);
		}
	}
}
