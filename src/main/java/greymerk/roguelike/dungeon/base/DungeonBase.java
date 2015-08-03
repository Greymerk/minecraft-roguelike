package greymerk.roguelike.dungeon.base;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;

public abstract class DungeonBase implements IDungeonRoom{

	@Override
	public abstract boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin);
	
	@Override
	public abstract int getSize();
	
	@Override
	public boolean validLocation(WorldEditor editor, Cardinal dir, int x, int y, int z){
		
		int size = getSize();
		List<Coord> box = editor.getRectHollow(x - size, y - 2, z - size, x + size, y + 5, z + size);
		
		for(Coord pos : box){
			MetaBlock b = editor.getBlock(pos);
			if(!b.getBlock().getMaterial().isSolid()) return false;
		}
		
		return true;
	}
}
