package com.greymerk.roguelike.dungeon.room;

import org.junit.jupiter.api.Test;

import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.IWorldEditor;

class AbstractMediumRoomTest {

	@Test
	void testGetCells() {
		TestRoom room = new TestRoom();
		Cardinal dir = Cardinal.NORTH;
		CellManager cells = room.getCells(dir);
		System.out.println(cells);
		
	}
	
	private class TestRoom extends AbstractMediumRoom implements IRoom{

		@Override
		public void generate(IWorldEditor editor) {
			
		}

		@Override
		public String getName() {
			return null;
		}
		
	}

}
