package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class DungeonsSlime extends DungeonBase {

	public boolean generate(IWorldEditor editor, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		return false;
	}
	
	public boolean isValidDungeonLocation(IWorldEditor editor, int originX, int originY, int originZ) {
		return false;
	}
	
	public int getSize(){
		return 8;
	}
}
