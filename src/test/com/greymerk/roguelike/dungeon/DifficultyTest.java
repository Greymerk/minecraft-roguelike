package com.greymerk.roguelike.dungeon;

import org.junit.jupiter.api.Test;

class DifficultyTest {

	@Test
	void testGreaterThan() {
		assert(!Difficulty.HARD.gt(Difficulty.HARDEST));
		assert(!Difficulty.HARD.gt(Difficulty.HARD));
		assert(Difficulty.HARD.gt(Difficulty.EASY));
	}
	
	@Test
	void testLessThan() {
		assert(Difficulty.HARD.lt(Difficulty.HARDEST));
		assert(!Difficulty.HARD.lt(Difficulty.HARD));
		assert(!Difficulty.HARD.lt(Difficulty.EASY));
	}
	
	@Test
	void testFrom() {
		assert(Difficulty.from(-1) == Difficulty.EASIEST);
		assert(Difficulty.from(0) == Difficulty.EASIEST);
		assert(Difficulty.from(1) == Difficulty.EASY);
		assert(Difficulty.from(2) == Difficulty.MEDIUM);
		assert(Difficulty.from(3) == Difficulty.HARD);
		assert(Difficulty.from(4) == Difficulty.HARDEST);
		assert(Difficulty.from(5) == Difficulty.HARDEST);
	}

}
