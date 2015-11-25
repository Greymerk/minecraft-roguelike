package greymerk.roguelike.dungeon.base;
import greymerk.roguelike.util.IWeighted;

import java.util.Random;


public class DungeonWeightedChoice implements IWeighted<DungeonRoom>, Comparable<IWeighted<?>>{

	DungeonRoom type;
	int chance;
	
	public DungeonWeightedChoice(DungeonRoom type, int chance){
		this.type = type;
		this.chance = chance;
	}
	
	public boolean choose(Random rand){
		return rand.nextInt(chance) == 0;
	}
	
	public IDungeonRoom getInstance(){
		return DungeonRoom.getInstance(type);
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
	public DungeonRoom get(Random rand) {
		return type;
	}
	
}
