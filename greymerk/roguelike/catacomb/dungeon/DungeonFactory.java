package greymerk.roguelike.catacomb.dungeon;


import greymerk.roguelike.config.RogueConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DungeonFactory implements IDungeonFactory {

	private List<Dungeon> singles;
	private LinkedList<DungeonWeightedChoice> types;
	private Random rand;
	
	public DungeonFactory(Random rand, Dungeon base){
		singles = new ArrayList<Dungeon>();
		types = new LinkedList<DungeonWeightedChoice>();
		addRandom(base, 1);
		this.rand = rand;
	}
	
	public void addSingle(Dungeon toAdd){
		singles.add(toAdd);
	}
	
	public void addByRatio(Dungeon toAdd, int rate){
		if(rate <= 0) return;
		int max = RogueConfig.getInt(RogueConfig.LEVELMAXROOMS);
		int numRooms = max / rate;
		
		if (numRooms == 0){
			addSingle(toAdd);
			return;
		}
		
		for(int i = 0; i < numRooms; ++i){
			addSingle(toAdd);
		}
	}
	
	public void addRandom(Dungeon type, int chance){
		types.addFirst(new DungeonWeightedChoice(type, chance));
		Collections.sort(types);
		Collections.reverse(types);
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
		
		return types.getLast().getInstance();
	}
}
