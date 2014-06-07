package greymerk.roguelike.catacomb.dungeon;

import java.util.ArrayList;
import java.util.List;

public class RoomSet {
	
	private static final int NUM_LEVELS = 5;
	private List<IDungeonFactory> levels;
	
	public RoomSet(){
		levels = new ArrayList<IDungeonFactory>();
		
		for(int i = 0; i < NUM_LEVELS; ++i){
			levels.add(DungeonFactoryProvider.getByLevel(i));
		}
	}
	
	public IDungeonFactory get(int level){
		return levels.get(level);
	}
}
