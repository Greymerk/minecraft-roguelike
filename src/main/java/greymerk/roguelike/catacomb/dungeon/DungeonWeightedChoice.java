package greymerk.roguelike.catacomb.dungeon;
import greymerk.roguelike.util.IWeighted;

import java.util.Random;


public class DungeonWeightedChoice implements IWeighted<Dungeon>, Comparable<IWeighted<?>>{

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
	public int compareTo(IWeighted<?> other) {
		if(chance < other.getWeight()) return -1;
		if(chance > other.getWeight()) return 1;
		return 0;
	}

	@Override
	public int getWeight() {
		return chance;
	}

	@Override
	public Dungeon get(Random rand) {
		return type;
	}
	
}
