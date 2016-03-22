package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;

public class SettingsForestTheme extends DungeonSettings{
	
	public SettingsForestTheme(){
		
		this.criteria = new SpawnCriteria();
		this.criteria.addBiome("forest");
		
		this.towerSettings = new TowerSettings(Tower.HOUSE, Theme.getTheme(Theme.HOUSE));
		
		for(int i = 0; i < 5; ++i){
			
			LevelSettings level = new LevelSettings();
			SecretFactory secrets = new SecretFactory();
			DungeonFactory rooms;
			
			switch(i){
			case 0:
				secrets.addRoom(DungeonRoom.BEDROOM);
				secrets.addRoom(DungeonRoom.SMITH);
				secrets.addRoom(DungeonRoom.ENCHANT);
				break;
			case 1:
				rooms = new DungeonFactory();
				rooms.addSingle(DungeonRoom.MUSIC);
				rooms.addSingle(DungeonRoom.PIT);
				rooms.addSingle(DungeonRoom.MESS);
				rooms.addSingle(DungeonRoom.LAB);
				rooms.addRandom(DungeonRoom.CORNER, 10);
				rooms.addRandom(DungeonRoom.BRICK, 3);
				level.setRooms(rooms);
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
			
			level.setSecrets(secrets);
			levels.put(i, level);
		}
		
	}
}