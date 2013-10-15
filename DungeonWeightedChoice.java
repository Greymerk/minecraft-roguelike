package greymerk.roguelike;

import java.util.Random;

public class DungeonWeightedChoice {

	Dungeon type;
	int chance;
	
	public DungeonWeightedChoice(Dungeon type, int chance){
		this.type = type;
		this.chance = chance;
	}
	
	public boolean choose(Random rand){
		return rand.nextInt(chance) == 0;
	}
	
	public IDungeon getInstance(){
		return Dungeon.getInstance(type);
	}
	
}
