package greymerk.roguelike.treasure.loot;

import java.util.Random;

public enum Quality {
	
	WOOD, STONE, IRON, GOLD, DIAMOND;
	
	public static Quality getQuality(Random rand, int level) {

		switch(level){
		
		case 4:
			if(rand.nextInt(20) == 0){
				return GOLD;
			}
			
			if(rand.nextInt(5) == 0){
				return DIAMOND;
			}

			return IRON;
		
		case 3:
			
			if(rand.nextInt(30) == 0){
				return GOLD;
			}
			
			if(rand.nextInt(20) == 0){
				return DIAMOND;
			}

			return IRON;
		
		case 2:
			
			if(rand.nextInt(40) == 0){
				return DIAMOND;
			}
			
			if(rand.nextInt(20) == 0){
				return GOLD;
			}
			
			if(rand.nextInt(3) == 0){
				return STONE;
			}
						
			return IRON;
			
		case 1:
			
			if(rand.nextInt(100) == 0){
				return DIAMOND;
			}
			
			if(rand.nextInt(50) == 0){
				return GOLD;
			}
			
			if(rand.nextInt(10) == 0){
				return IRON;
			}
			
			if(rand.nextInt(5) == 0){
				return WOOD;
			}
			
			return STONE; 
			
		case 0:
			
			if(rand.nextInt(30) == 0){
				return IRON;
			}
			
			if(rand.nextInt(5) == 0){
				return STONE;
			}
			
			return WOOD;
			
		default:
			return WOOD;
		}
	}

}
