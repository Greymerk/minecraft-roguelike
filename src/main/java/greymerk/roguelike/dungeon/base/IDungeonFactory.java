package greymerk.roguelike.dungeon.base;

import java.util.Random;

public interface IDungeonFactory {

  IDungeonRoom get(Random rand);

}
