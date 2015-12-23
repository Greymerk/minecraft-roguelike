package greymerk.roguelike.dungeon.towers;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public enum Tower {

	ROGUE, ENIKO, ETHO, PYRAMID, JUNGLE, WITCH, HOUSE, BUNKER;
	
	public static ITower get(Tower type){
		
		switch(type){
		case ROGUE: return new RogueTower();
		case ENIKO: return new EniTower();
		case ETHO: return new EthoTower();
		case PYRAMID: return new PyramidTower();
		case JUNGLE: return new JungleTower();
		case WITCH: return new WitchTower();
		case HOUSE: return new HouseTower();
		case BUNKER: return new BunkerTower();
		default: return new RogueTower();
		}
	}
	
	public static Coord getBaseCoord(WorldEditor editor, int x, int y, int z){
		
		Coord cursor = new Coord(x, 128, z);

		while(cursor.getY() > 60){
			if(editor.validGroundBlock(cursor)) break;
			cursor.add(Cardinal.DOWN);
		}

		cursor.add(Cardinal.UP);
		
		int yOffset = cursor.getY() - y;

		if(yOffset < 14){
			yOffset = 14;
		}
		
		return new Coord(x, y + yOffset, z);
	}

	
}
