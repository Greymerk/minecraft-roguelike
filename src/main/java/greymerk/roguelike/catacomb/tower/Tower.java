package greymerk.roguelike.catacomb.tower;

public enum Tower {

	ROGUE, ENIKO;
	
	public static ITower get(Tower type){
		
		switch(type){
		case ROGUE: return new RogueTower();
		case ENIKO: return new EniTower();
		default: return new RogueTower();
		}
	}
	
}
