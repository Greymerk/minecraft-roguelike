package greymerk.roguelike.worldgen.shapes;

import java.util.Iterator;
import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.WorldEditor;

public class RectSolid implements IShape {

	private Coord start;
	private Coord end;
	
	public RectSolid(Coord start, Coord end){
		this.start = start;
		this.end = end;
	}
	
	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end, IBlockFactory block){
		fill(editor, rand, start, end, block, true, true);
	}
	
	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end, IBlockFactory block, boolean fillAir, boolean replaceSolid){
		Coord c1 = new Coord(start);
		Coord c2 = new Coord(end);
		
		Coord.correct(c1, c2);
		
		for(int x = c1.getX(); x <= c2.getX(); x++){
			for(int y = c1.getY(); y <= c2.getY(); y++){
				for(int z = c1.getZ(); z <= c2.getZ(); z++){
					editor.setBlock(rand, new Coord(x, y, z), block, fillAir, replaceSolid);
				}
			}
		}
	}
	
	@Override
	public void fill(IWorldEditor editor, Random rand, IBlockFactory block, boolean fillAir, boolean replaceSolid) {
		fill(editor, rand, start, end, block, fillAir, replaceSolid);
	}

	@Override
	public Iterator<Coord> iter() {
		return null;
	}

}
