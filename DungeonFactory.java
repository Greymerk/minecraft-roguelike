package greymerk.roguelike;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DungeonFactory implements IDungeonFactory {

	private List<Dungeon> singles;
	private LinkedList<DungeonWeightedChoice> types;
	private int rank;
	private Random rand;
	
	public DungeonFactory(Random rand, int rank, Dungeon base){
		singles = new ArrayList<Dungeon>();
		types = new LinkedList<DungeonWeightedChoice>();
		addRandom(base, 1);
		this.rank = rank;
		this.rand = rand;
	}
	
	public void addSingle(Dungeon toAdd){
		singles.add(toAdd);
	}
	
	public void addRandom(Dungeon type, int chance){
		types.addFirst(new DungeonWeightedChoice(type, chance));
		
	}
	
	@Override
	public IDungeon get() {
		
		if(!(singles.isEmpty())){
			return Dungeon.getInstance(singles.remove(0));
		}
		
		for(DungeonWeightedChoice choice : types){
			if(choice.choose(rand)){
				return choice.getInstance();
			}
		}
		
		return types.get(types.size() - 1).getInstance();
	}

}
