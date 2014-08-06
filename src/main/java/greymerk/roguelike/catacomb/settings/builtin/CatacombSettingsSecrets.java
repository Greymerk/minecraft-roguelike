package greymerk.roguelike.catacomb.settings.builtin;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.SecretFactory;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;

public class CatacombSettingsSecrets extends CatacombSettings{

	public CatacombSettingsSecrets(){
		for(int i = 0; i < 5; ++i){
			
			SecretFactory factory = new SecretFactory();
			List<Dungeon> rooms;
			switch(i){
			case 0:
				factory.addRoom(Dungeon.FIREWORK);
				factory.addRoom(Dungeon.BEDROOM, 2);
				break;
			case 1:
				rooms = new ArrayList<Dungeon>();
				rooms.add(Dungeon.AVIDYA);
				rooms.add(Dungeon.ETHO);
				rooms.add(Dungeon.BTEAM);
				factory.addRoom(rooms, 1);
				break;
			case 2:
				factory.addRoom(Dungeon.ENIKO);
				break;
			case 3:
				rooms = new ArrayList<Dungeon>();
				rooms.add(Dungeon.BAJ);
				rooms.add(Dungeon.NEBRIS);
				factory.addRoom(rooms, 1);
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
