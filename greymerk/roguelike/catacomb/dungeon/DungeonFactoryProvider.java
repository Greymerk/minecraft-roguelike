package greymerk.roguelike.catacomb.dungeon;

import java.util.Random;

public class DungeonFactoryProvider {

	public static IDungeonFactory getFactory(Random rand, int level){
		
		DungeonFactory factory;
		int choice = rand.nextInt(10);
		
		switch(level){
		case 0:
			factory = new DungeonFactory(rand, Dungeon.BRICK);
			factory.addSingle(Dungeon.CAKE);
			factory.addSingle(Dungeon.MESS);
			factory.addSingle(Dungeon.SMITH);
			factory.addSingle(Dungeon.CRYPT);
			factory.addSingle(Dungeon.FIRE);
			break;
		case 1:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			choice = rand.nextInt(10);
			if(choice == 0) factory.addSingle(Dungeon.ETHO);
			if(choice == 1) factory.addSingle(Dungeon.BTEAM);
			if(choice == 2) factory.addSingle(Dungeon.AVIDYA);
			if(choice == 3) factory.addSingle(Dungeon.ASHLEA);
			factory.addSingle(Dungeon.MUSIC);
			factory.addSingle(Dungeon.PIT);
			factory.addSingle(Dungeon.ENCHANT);
			factory.addSingle(Dungeon.LAB);
			factory.addByRatio(Dungeon.MESS, 30);
			factory.addByRatio(Dungeon.STORAGE, 30);
			factory.addRandom(Dungeon.BRICK, 3);
			break;
		case 2:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			choice = rand.nextInt(10);
			if(choice == 0) factory.addSingle(Dungeon.ENIKO);
			factory.addSingle(Dungeon.OSSUARY);
			factory.addSingle(Dungeon.CREEPER);
			factory.addSingle(Dungeon.FIRE);
			factory.addByRatio(Dungeon.PRISON, 10);
			factory.addByRatio(Dungeon.CRYPT, 10);
			factory.addByRatio(Dungeon.PIT, 10);
			factory.addByRatio(Dungeon.STORAGE, 30);
			factory.addRandom(Dungeon.BRICK, 3);
			break;	
		case 3:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			if(choice == 0) factory.addSingle(Dungeon.BAJ);
			if(choice == 1)factory.addSingle(Dungeon.NEBRIS);
			factory.addSingle(Dungeon.OSSUARY);
			factory.addSingle(Dungeon.ENDER);
			factory.addByRatio(Dungeon.CRYPT, 15);
			factory.addByRatio(Dungeon.PRISON, 15);
			factory.addByRatio(Dungeon.SPIDER, 15);
			factory.addByRatio(Dungeon.CREEPER, 15);
			factory.addByRatio(Dungeon.FIRE, 20);
			factory.addByRatio(Dungeon.STORAGE, 30);
			factory.addRandom(Dungeon.BRICK, 3);
			factory.addRandom(Dungeon.SPIDER, 20);
			factory.addRandom(Dungeon.SLIME, 20);
			factory.addRandom(Dungeon.PIT, 20);

			break;
		case 4:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
			factory.addSingle(Dungeon.OBSIDIAN);
			factory.addByRatio(Dungeon.FIRE, 30);
			factory.addByRatio(Dungeon.NETHERFORT, 20);
			factory.addByRatio(Dungeon.SLIME, 15);
			factory.addByRatio(Dungeon.STORAGE, 30);
			factory.addRandom(Dungeon.NETHER, 3);
			factory.addRandom(Dungeon.SLIME, 20);
			factory.addRandom(Dungeon.SPIDER, 20);

			break;
		default:
			factory = new DungeonFactory(rand, Dungeon.CORNER);
		}

		return factory;
	}	
}
