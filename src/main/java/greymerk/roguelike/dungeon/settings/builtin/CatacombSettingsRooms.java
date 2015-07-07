package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.dungeon.settings.CatacombSettings;

public class CatacombSettingsRooms extends CatacombSettings{

	public CatacombSettingsRooms(){
		for(int i = 0; i < 5; ++i){
			
			DungeonFactory factory;
			
			switch(i){
			case 0:
				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.CAKE);
				factory.addSingle(DungeonRoom.FIRE);
				factory.addRandom(DungeonRoom.BRICK, 10);
				factory.addRandom(DungeonRoom.CORNER, 3);
				break;
			case 1:
				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.MUSIC);
				factory.addSingle(DungeonRoom.PIT);
				factory.addSingle(DungeonRoom.MESS);
				factory.addRandom(DungeonRoom.CORNER, 10);
				factory.addRandom(DungeonRoom.BRICK, 3);
				break;
			case 2:
				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.OSSUARY);
				factory.addSingle(DungeonRoom.CREEPER);
				factory.addSingle(DungeonRoom.FIRE);
				factory.addByRatio(DungeonRoom.PRISON, 5);
				factory.addByRatio(DungeonRoom.CRYPT, 10);
				factory.addByRatio(DungeonRoom.PIT, 7);
				factory.addRandom(DungeonRoom.CORNER, 10);
				factory.addRandom(DungeonRoom.BRICK, 3);
				break;	
			case 3:
				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.OSSUARY);
				factory.addSingle(DungeonRoom.ENDER);
				factory.addByRatio(DungeonRoom.CRYPT, 15);
				factory.addByRatio(DungeonRoom.PRISON, 15);
				factory.addByRatio(DungeonRoom.SPIDER, 15);
				factory.addByRatio(DungeonRoom.CREEPER, 15);
				factory.addByRatio(DungeonRoom.FIRE, 20);
				factory.addRandom(DungeonRoom.CORNER, 100);
				factory.addRandom(DungeonRoom.BRICK, 30);
				factory.addRandom(DungeonRoom.SPIDER, 10);
				factory.addRandom(DungeonRoom.SLIME, 10);
				factory.addRandom(DungeonRoom.PIT, 10);
				break;
			case 4:
				factory = new DungeonFactory();
				factory.addSingle(DungeonRoom.OBSIDIAN);
				factory.addByRatio(DungeonRoom.FIRE, 30);
				factory.addByRatio(DungeonRoom.NETHERFORT, 20);
				factory.addByRatio(DungeonRoom.SLIME, 15);
				factory.addRandom(DungeonRoom.CORNER, 100);
				factory.addRandom(DungeonRoom.NETHER, 30);
				factory.addRandom(DungeonRoom.SLIME, 10);
				factory.addRandom(DungeonRoom.SPIDER, 10);
				break;
			default:
				factory = new DungeonFactory();
				break;
			}
			
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setRooms(factory);
			levels.put(i, level);
		}
	}
}
