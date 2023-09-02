package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.shapes.RectHollow;

import net.minecraft.util.math.random.Random;

public class GreatHallRoom extends AbstractLargeRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = new Coord(this.getWorldPos());
		Random rand = editor.getRandom(origin);
		
		Coord start = new Coord(origin);
		int size = 15;
		start.add(Cardinal.NORTH, size);
		start.add(Cardinal.WEST, size);
		start.add(Cardinal.DOWN);
		Coord end = new Coord(origin);
		end.add(Cardinal.SOUTH, size);
		end.add(Cardinal.EAST, size);
		end.add(Cardinal.UP, 5);
		RectHollow box = new RectHollow(start, end);
		box.fill(editor, rand, this.getTheme().getPrimary().getWall());
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = new Coord(origin);
			pos.add(dir, 12);
			this.door(editor, rand, theme, pos, dir);
		}
	}
	
	@Override
	public String getName() {
		return Room.HALL.name();
	}

}
