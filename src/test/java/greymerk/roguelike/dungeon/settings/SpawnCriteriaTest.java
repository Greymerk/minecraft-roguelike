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
		
		boolean valid = SpawnCriteria.isValidDimension(dim, wl, bl);		
		assertTrue(!valid);
	}

}
