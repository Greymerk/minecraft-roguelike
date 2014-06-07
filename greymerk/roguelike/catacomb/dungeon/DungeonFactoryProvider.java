package greymerk.roguelike.catacomb.dungeon;

import java.util.Random;

public enum DungeonFactoryProvider {

	STARTOAK, SPRUCE, DESERT, JUNGLE, STONE, MOSSY, NETHER;
	
	public static IDungeonFactory getFactory(DungeonFactoryProvider type){
		
		DungeonFactory factory;
		
		switch(type){
		case STARTOAK:
			factory = new DungeonFactory();
			factory.addSecret(Dungeon.SLIME, 5);
			factory.addSecret(Dungeon.FIRE, 10);
			factory.addRandom(Dungeon.BRICK, 1);
			factory.addSingle(Dungeon.CAKE);
			break;
		case SPRUCE:
			factory = new DungeonFactory();
			factory.addSecret(Dungeon.CORNER, 50);
			factory.addSecret(Dungeon.ETHO, 10);
			factory.addSecret(Dungeon.BTEAM, 10);
			factory.addSecret(Dungeon.AVIDYA, 10);
			factory.addSecret(Dungeon.ASHLEA, 10);
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
		case STONE:
			factory = new DungeonFactory();
			factory.addSecret(Dungeon.CORNER, 50);
			factory.addSecret(Dungeon.ENIKO, 10);
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
		case MOSSY:
			factory = new DungeonFactory();
			factory.addSecret(Dungeon.CORNER, 50);
			factory.addSecret(Dungeon.BAJ, 10);
			factory.addSecret(Dungeon.NEBRIS, 10);
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
		case NETHER:
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
		}

		return factory;
	}
	
	public static IDungeonFactory getByLevel(int level){
		
		switch(level){
		case 0: return getFactory(STARTOAK); 
		case 1: return getFactory(SPRUCE);
		case 2: return getFactory(STONE);
		case 3: return getFactory(MOSSY);
		case 4: return getFactory(NETHER);
		default: return getFactory(STONE);
		}
	}
}
