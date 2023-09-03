package com.greymerk.roguelike.dungeon.fragment.parts;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class ArchWay implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		Coord start = origin.copy();
		start.add(dir, 3);
		Coord end = start.copy();
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 2);
		end.add(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		
		start = origin.copy();
		start.add(dir, 3);
		start.add(Cardinal.DOWN);
		end = start.copy();
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 2);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = new Coord(origin);
			pos.add(dir, 3);
			pos.add(Cardinal.UP, 2);
			pos.add(o, 2);
			IStair stair = theme.getPrimary().getStair();
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, pos);
			
			pos.add(Cardinal.UP);
			theme.getPrimary().getWall().set(editor, rand, pos);
			
			pos.add(Cardinal.reverse(o));
			stair.set(editor, pos);
		}
	}
}
