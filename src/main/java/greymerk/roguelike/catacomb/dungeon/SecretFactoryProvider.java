package greymerk.roguelike.catacomb.dungeon;

public class SecretFactoryProvider {

	public static SecretFactory getFactory(int level){
		
		SecretFactory factory = new SecretFactory();
		
		switch(level){
		case 0:
			factory.addRoom(Dungeon.BTEAM);
		case 1:
			factory.addRoom(Dungeon.AVIDYA, 2);
		case 2:
		case 3:
		case 4:
		default:
			return factory;
		}
		
		
	}
	
	
}
