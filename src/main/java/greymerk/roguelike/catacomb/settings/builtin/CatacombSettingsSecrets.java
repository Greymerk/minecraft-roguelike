package greymerk.roguelike.catacomb.settings.builtin;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.SecretFactory;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;
import greymerk.roguelike.config.RogueConfig;

public class CatacombSettingsSecrets extends CatacombSettings{

	public CatacombSettingsSecrets(){
		for(int i = 0; i < 5; ++i){
			
			SecretFactory factory = new SecretFactory();
			List<Dungeon> rooms;
			switch(i){
			case 0:
				if(RogueConfig.getBoolean(RogueConfig.GENEROUS)) factory.addRoom(Dungeon.FIREWORK);
				factory.addRoom(Dungeon.BEDROOM, 2);
				break;
			case 1:
				rooms = new ArrayList<Dungeon>();
				rooms.add(Dungeon.BTEAM);
				rooms.add(Dungeon.AVIDYA);
				rooms.add(Dungeon.ETHO);
				rooms.add(Dungeon.ENIKO);
				factory.addRoom(rooms, 1);
				break;
			case 2:
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
