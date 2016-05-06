package greymerk.roguelike.worldgen;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoordTest {

	@Test
	public void addNothing() {
		Coord test = new Coord(0,0,0);
		test.add(new Coord(0,0,0));
		assertTrue(test.equals(new Coord(0,0,0)));
		
		test = new Coord(-10, 10, -10);
		test.add(new Coord(0,0,0));
		assertTrue(test.equals(new Coord(-10, 10, -10)));
	}
	
	@Test
	public void addDirection(){
		Coord test = new Coord(0,0,0);
		test.add(Cardinal.NORTH);
		assertTrue(test.equals(new Coord(0,0,-1)));
		
		test = new Coord(0,0,0);
		test.add(Cardinal.SOUTH);
		assertTrue(test.equals(new Coord(0,0,1)));
		
		test = new Coord(0,0,0);
		test.add(Cardinal.WEST);
		assertTrue(test.equals(new Coord(-1,0,0)));
		
		test = new Coord(0,0,0);
		test.add(Cardinal.EAST);
		assertTrue(test.equals(new Coord(1,0,0)));
		
		test = new Coord(0,0,0);
		test.add(Cardinal.DOWN);
		assertTrue(test.equals(new Coord(0,-1,0)));
		
		test = new Coord(0,0,0);
		test.add(Cardinal.UP);
		assertTrue(test.equals(new Coord(0,1,0)));
		
		test = new Coord(0,0,0);
		test.add(Cardinal.NORTH, 5);
		assertTrue(test.equals(new Coord(0,0,-5)));
		
		test = new Coord(0,0,0);
		test.add(Cardinal.SOUTH, 5);
		assertTrue(test.equals(new Coord(0,0,5)));
		
		test = new Coord(0,0,0);
		test.add(Cardinal.NORTH, -5);
		assertTrue(test.equals(new Coord(0,0,5)));
		
		test = new Coord(0,0,0);
		test.add(Cardinal.SOUTH, -5);
		assertTrue(test.equals(new Coord(0,0,-5)));
	}
	
	@Test
	public void addCoord(){
		Coord test;
		
		test = new Coord(0,0,0);
		test.add(new Coord(5, 5, 5));
		assertTrue(test.equals(new Coord(5, 5, 5)));
		
		test = new Coord(-10,0,0);
		test.add(new Coord(100, 0, 0));
		assertTrue(test.equals(new Coord(90, 0, 0)));
		
	}
	
	@Test
	public void sub(){
		Coord test;
		test = new Coord(0,0,0);
		test.sub(new Coord(5, 5, 5));
		assertTrue(test.equals(new Coord(-5, -5, -5)));
		
		test = new Coord(100,0,0);
		test.sub(new Coord(10, 0, 0));
		assertTrue(test.equals(new Coord(90, 0, 0)));
		
	}
}
