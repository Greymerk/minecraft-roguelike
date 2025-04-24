package com.greymerk.roguelike.editor.shapes;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.editor.Coord;

class LineTest {
	
	@Test
	void testLine() {
		Coord start = Coord.ZERO;
		Coord end = Coord.of(0,0,5);
		Line line = new Line(start, end);
		line.start.equals(start);
		line.end.equals(end);
	}

	@Test
	void testGet() {
		Coord start = Coord.ZERO;
		Coord end = Coord.of(0,0,3);
		Line line = new Line(start, end);
		List<Coord> coords = line.get();
		assert(coords.contains(Coord.ZERO));
		assert(coords.contains(Coord.of(0,0,1)));
		assert(coords.contains(Coord.of(0,0,2)));
		assert(coords.contains(Coord.of(0,0,3)));
		
	}

	@Test
	void testIterator() {
		Coord start = Coord.ZERO;
		Coord end = Coord.of(0,0,3);
		Line line = new Line(start, end);
		List<Coord> coords = new ArrayList<Coord>();
		for(Coord c : line) {
			coords.add(c);
		}
		assert(coords.size() == 4);
		assert(coords.contains(Coord.ZERO));
		assert(coords.contains(Coord.of(0,0,1)));
		assert(coords.contains(Coord.of(0,0,2)));
		assert(coords.contains(Coord.of(0,0,3)));
	}

}
