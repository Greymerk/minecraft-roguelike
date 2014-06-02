package greymerk.roguelike.worldgen;

public enum Log {

	OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK;
	
	public static MetaBlock getLog(Log type, Cardinal dir, boolean onSide){
		return new MetaBlock(getBlockId(type), getMeta(type, dir, onSide));
	}
	
	public static MetaBlock getLog(Log type){
		return new MetaBlock(getBlockId(type));
	}
	
	public static int getBlockId(Log type){
		switch(type){
		case OAK: return 17;
		case SPRUCE: return 17;
		case BIRCH: return 17;
		case JUNGLE: return 17;
		case ACACIA: return 162;
		case DARKOAK: return 162;
		default: return 0;
		}
	}
	
	public static int getMeta(Log type, Cardinal dir, boolean onSide){
		
		int orientation;
		
		if(onSide){
			if(dir == Cardinal.NORTH || dir == Cardinal.SOUTH){
				orientation = 8;
			} else {
				orientation = 4;
			}
		} else {
			orientation = 0;
		}
		
		switch(type){
		case OAK: return orientation;
		case SPRUCE: return orientation + 1;
		case BIRCH: return orientation + 2;
		case JUNGLE: return orientation + 3;
		case ACACIA: return orientation;
		case DARKOAK: return orientation + 1;
		default: return 0;
		}
		
	}
	
	
}
