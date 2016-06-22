package greymerk.roguelike.dungeon.settings;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SpawnCriteriaTest {

	@Test
	public void overworld(){
		boolean valid;
		int dim = 0;
		List<Integer> wl = new ArrayList<Integer>();
		wl.add(0);
		List<Integer> bl = new ArrayList<Integer>();
		
		valid = SpawnCriteria.isValidDimension(dim, wl, bl);		
		assertTrue(valid);
		
	}
	
	@Test
	public void notInNether(){
		
		int dim = -1;
		List<Integer> wl = new ArrayList<Integer>();
		wl.add(0);
		List<Integer> bl = new ArrayList<Integer>();
		
		assertFalse(SpawnCriteria.isValidDimension(dim, wl, bl));
	}

	@Test
	public void whiteListSeveral(){
		List<Integer> wl = new ArrayList<Integer>();
		wl.add(5);
		wl.add(8);
		wl.add(12);
		List<Integer> bl = new ArrayList<Integer>();
		
		assertFalse(SpawnCriteria.isValidDimension(0, wl, bl)); // not overworld
		assertFalse(SpawnCriteria.isValidDimension(-1, wl, bl)); // not nether
		assertFalse(SpawnCriteria.isValidDimension(1, wl, bl)); // not end
		assertFalse(SpawnCriteria.isValidDimension(15, wl, bl));
		assertTrue(SpawnCriteria.isValidDimension(5, wl, bl));
		assertTrue(SpawnCriteria.isValidDimension(8, wl, bl));
		assertTrue(SpawnCriteria.isValidDimension(12, wl, bl));
	}
	
}
