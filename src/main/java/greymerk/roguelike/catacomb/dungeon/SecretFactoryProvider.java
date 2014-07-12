package greymerk.roguelike.catacomb.dungeon;

import java.util.Random;

public class SecretFactoryProvider {

	public static SecretFactory getFactory(Random rand, int level){
		
		SecretFactory factory = new SecretFactory();
		
		switch(level){
		case 0:
			factory.addRoom(Dungeon.FIREWORK);
			factory.addRoom(Dungeon.BEDROOM, 2);
			break;
		case 1:
			if(rand.nextInt(10) == 0) factory.addRoom(Dungeon.AVIDYA);
			if(rand.nextInt(10) == 0) factory.addRoom(Dungeon.ETHO);
			if(rand.nextInt(10) == 0) factory.addRoom(Dungeon.BTEAM);
			break;
		case 2:
			if(rand.nextInt(10) == 0) factory.addRoom(Dungeon.ENIKO);
			break;
		case 3:
			if(rand.nextInt(10) == 0) factory.addRoom(Dungeon.BAJ);
			if(rand.nextInt(10) == 0) factory.addRoom(Dungeon.NEBRIS);
			break;
		case 4:
			break;
		default:
			break;
		}
		
		return factory;
	}
}
