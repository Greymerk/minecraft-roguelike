package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.SecretFactory;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;

public class CatacombSettingsSecrets extends CatacombSettings{

	public CatacombSettingsSecrets(){
		for(int i = 0; i < 5; ++i){
			
			SecretFactory factory = new SecretFactory();
			
			switch(i){
			case 0:
				factory.addRoom(Dungeon.FIREWORK);
				factory.addRoom(Dungeon.BEDROOM, 2);
				break;
			case 1:
				factory.addRoom(Dungeon.AVIDYA);
				factory.addRoom(Dungeon.ETHO);
				factory.addRoom(Dungeon.BTEAM);
				break;
			case 2:
				factory.addRoom(Dungeon.ENIKO);
				break;
			case 3:
				factory.addRoom(Dungeon.BAJ);
				factory.addRoom(Dungeon.NEBRIS);
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
