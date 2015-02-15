package greymerk.roguelike.worldgen;


public enum Cardinal {

	NORTH, EAST, WEST, SOUTH, UP, DOWN;
	
	public static Cardinal[] directions = {NORTH, EAST, SOUTH, WEST};
	
	public static int getBlockMeta(Cardinal dir){
		switch(dir){
		case NORTH: return 2;
		case EAST: return 1;
		case WEST: return 0;
		case SOUTH: return 3;
		default: return 0;
		}
	}
	
	
	public static Cardinal reverse(Cardinal dir){
		switch(dir){
		case NORTH: return SOUTH;
		case EAST: return WEST;
		case WEST: return EAST;
		case SOUTH: return NORTH;
		default: return null;
		}
	}

	public static Cardinal[] getOrthogonal(Cardinal dir) {
		
		switch(dir){
		case NORTH: return new Cardinal[] {WEST, EAST};
		case SOUTH: return new Cardinal[] {EAST, WEST};
		case EAST: return new Cardinal[] {NORTH, SOUTH};
		case WEST: return new Cardinal[] {SOUTH, NORTH};
		}
		
		return null;
	}
}
