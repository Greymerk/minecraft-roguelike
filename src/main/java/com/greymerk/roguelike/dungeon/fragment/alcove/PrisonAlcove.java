package com.greymerk.roguelike.dungeon.fragment.alcove;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.IronBar;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class PrisonAlcove implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		cell(editor, rand, theme, origin.copy().add(dir, Cell.SIZE).freeze());

		Cardinal.directions.forEach(d -> {
			if(d != Cardinal.reverse(dir) && rand.nextInt(5) == 0) {
				if(rand.nextBoolean()) {
					Fragment.generate(Fragment.WALL_SPAWNER, editor, rand, theme, origin.copy().add(dir, Cell.SIZE), d);	
				} else {
					Fragment.generate(Fragment.WALL_CHEST, editor, rand, theme, origin.copy().add(dir, Cell.SIZE), d);
				}
			}
		});
		
		BoundingBox.of(origin).add(dir, 3).grow(Cardinal.orthogonal(dir)).grow(Cardinal.UP, 2).fill(editor, rand, IronBar.getBroken());
	}

	private void cell(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin);
		BoundingBox.of(origin).grow(Cardinal.directions, 2).grow(Cardinal.UP, 3).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.UP, 4).grow(Cardinal.directions, 3).fill(editor, rand, walls, false, true);
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 2).fill(editor, rand, theme.getPrimary().getFloor());
		
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 3).grow(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, walls, false, true);
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.UP).fill(editor, rand, theme.getPrimary().getPillar());
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.UP, 2).grow(Cardinal.UP).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir)).fill(editor, rand, walls);
			stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, BoundingBox.of(origin).add(dir).add(Cardinal.UP, 3).grow(Cardinal.left(dir)));
			Cardinal.orthogonal(dir).forEach(o -> {
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.add(dir, 2).add(o).add(Cardinal.UP, 2));
			});
		});
	}

}
