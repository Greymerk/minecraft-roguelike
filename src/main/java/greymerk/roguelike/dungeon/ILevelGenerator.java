package greymerk.roguelike.dungeon;

import greymerk.roguelike.worldgen.Coord;

public interface ILevelGenerator {

	public void generate(Coord start);
	
	public ILevelLayout getLayout();
	
}
