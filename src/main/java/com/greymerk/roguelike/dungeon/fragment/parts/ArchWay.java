package com.greymerk.roguelike.dungeon.fragment.parts;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class ArchWay implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		arch(editor, rand, theme, origin.copy().add(dir, 3).freeze(), dir);
	}

	private void arch(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 3).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 2).fill(editor, rand, theme.getPrimary().getFloor());
		BoundingBox.of(origin).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 2).fill(editor, rand, theme.getPrimary().getWall(), Fill.ONLY_SOLID);
		
		Cardinal.orthogonal(dir).forEach(o -> {
			IStair stair = theme.getPrimary().getStair();
			
			theme.getPrimary().getWall().set(editor, rand, origin.copy().add(Cardinal.UP, 3).add(o, 2));
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(Cardinal.UP, 2).add(o, 2));
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(Cardinal.UP, 3).add(o));
			
			BoundingBox.of(origin).add(o, 3).grow(Cardinal.DOWN).grow(Cardinal.UP, 3).fill(editor, rand, theme.getPrimary().getWall(), Fill.ONLY_SOLID);
		});
	}
}
