package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.shapes.RectHollow;

import net.minecraft.util.math.random.Random;

public class GreatHallRoom extends AbstractLargeRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos().copy();
		Random rand = editor.getRandom(origin);
		
		Coord start = origin.copy();
		int size = 15;
		start.add(Cardinal.NORTH, size);
		start.add(Cardinal.WEST, size);
		start.add(Cardinal.DOWN);
		Coord end = origin.copy();
		end.add(Cardinal.SOUTH, size);
		end.add(Cardinal.EAST, size);
		end.add(Cardinal.UP, 5);
		RectHollow box = new RectHollow(start, end);
		box.fill(editor, rand, this.getTheme().getPrimary().getWall());
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 12);
			Fragment.generate(Fragment.ARCH, editor, rand, theme, pos, dir);
		}
	}
	
	@Override
	public String getName() {
		return "";
	}

}
