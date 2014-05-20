package greymerk.roguelike.catacomb.dungeon;


import java.util.Random;

public class DungeonWeightedChoice implements Comparable{

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

	@Override
	public int compareTo(Object o) {
		DungeonWeightedChoice other = (DungeonWeightedChoice)o;
		if(chance < other.chance) return -1;
		if(chance > other.chance) return 1;
		return 0;
	}
	
}
