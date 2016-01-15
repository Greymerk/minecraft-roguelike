package greymerk.roguelike.worldgen.shapes;

import java.util.Iterator;
import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.WorldEditor;

public interface IShape{

	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid);
	
	public Iterator<Coord> iter();
}
