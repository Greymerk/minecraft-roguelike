package greymerk.roguelike.catacomb.dungeon;

import java.util.Random;

public interface IDungeonFactory {

	public IDungeon get(Random rand);
	
}
