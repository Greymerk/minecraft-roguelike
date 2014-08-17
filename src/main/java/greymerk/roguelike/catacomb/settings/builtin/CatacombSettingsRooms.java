package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.dungeon.DungeonFactory;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;

public class CatacombSettingsRooms extends CatacombSettings{

	public CatacombSettingsRooms(){
		for(int i = 0; i < 5; ++i){
			
			DungeonFactory factory;
			
			switch(i){
			case 0:
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.REWARD);
				factory.addSingle(Dungeon.FIRE);
				factory.addRandom(Dungeon.BRICK, 10);
				factory.addRandom(Dungeon.CORNER, 3);
				break;
			case 1:
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.MUSIC);
				factory.addSingle(Dungeon.SMITH);
				factory.addSingle(Dungeon.PIT);
				factory.addSingle(Dungeon.ENCHANT);
				factory.addSingle(Dungeon.LAB);
				factory.addByRatio(Dungeon.MESS, 30);
				factory.addByRatio(Dungeon.STORAGE, 30);
				factory.addRandom(Dungeon.CORNER, 10);
				factory.addRandom(Dungeon.BRICK, 3);
				break;
			case 2:
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.OSSUARY);
				factory.addSingle(Dungeon.CREEPER);
				factory.addSingle(Dungeon.FIRE);
				factory.addByRatio(Dungeon.PRISON, 10);
				factory.addByRatio(Dungeon.CRYPT, 10);
				factory.addByRatio(Dungeon.PIT, 10);
				factory.addByRatio(Dungeon.STORAGE, 30);
				factory.addRandom(Dungeon.CORNER, 10);
				factory.addRandom(Dungeon.BRICK, 3);
				break;	
			case 3:
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.OSSUARY);
				factory.addSingle(Dungeon.ENDER);
				factory.addByRatio(Dungeon.CRYPT, 15);
				factory.addByRatio(Dungeon.PRISON, 15);
				factory.addByRatio(Dungeon.SPIDER, 15);
				factory.addByRatio(Dungeon.CREEPER, 15);
				factory.addByRatio(Dungeon.FIRE, 20);
				factory.addByRatio(Dungeon.STORAGE, 30);
				factory.addRandom(Dungeon.CORNER, 100);
				factory.addRandom(Dungeon.BRICK, 30);
				factory.addRandom(Dungeon.SPIDER, 10);
				factory.addRandom(Dungeon.SLIME, 10);
				factory.addRandom(Dungeon.PIT, 10);
				break;
			case 4:
				factory = new DungeonFactory();
				factory.addSingle(Dungeon.OBSIDIAN);
				factory.addByRatio(Dungeon.FIRE, 30);
				factory.addByRatio(Dungeon.NETHERFORT, 20);
				factory.addByRatio(Dungeon.SLIME, 15);
				factory.addByRatio(Dungeon.STORAGE, 30);
				factory.addRandom(Dungeon.CORNER, 100);
				factory.addRandom(Dungeon.NETHER, 30);
				factory.addRandom(Dungeon.SLIME, 10);
				factory.addRandom(Dungeon.SPIDER, 10);
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
