package greymerk.roguelike.dungeon.base;

import java.util.Random;

public interface IDungeonFactory {

	public IDungeonRoom get(Random rand);
	
}
