package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.filter.Filter;

import net.minecraft.util.math.random.Random;

public class CrossRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos().copy().add(direction, Cell.SIZE).freeze();
		Random rand = editor.getRandom(origin);
		
		clear(editor, rand, origin);
		corners(editor, rand, origin);
		sides(editor, rand, origin);
		center(editor, rand, origin);
		decorations(editor, rand, origin);
		supports(editor, rand, origin);
		pots(editor, rand, origin);
	}
	
	private void pots(IWorldEditor editor, Random rand, Coord origin) {
		Filter.get(Filter.POTS).apply(editor, rand, theme, BoundingBox.of(origin).grow(Cardinal.directions, 8));
	}

	private void supports(IWorldEditor editor, Random rand, Coord origin) {
		CellSupport.generate(editor, rand, theme, origin);
		Cardinal.directions.forEach(dir -> {
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, Cell.SIZE));
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, Cell.SIZE).add(Cardinal.left(dir), Cell.SIZE));
		});
	}

	private void decorations(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			Cardinal.orthogonal(dir).forEach(o -> {
				settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(dir, 6).add(o, 6), dir);
			});
			if(this.getEntrance(dir) == Entrance.WALL) {
				settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(dir, 6), dir);
			}
			if(this.getEntrance(dir) == Entrance.ALCOVE) {
				settings.getAlcove(rand).generate(editor, rand, theme, origin.copy().add(dir, 6), dir);
			}
		});
	}

	private void center(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		walls.set(editor, rand, origin.copy().add(Cardinal.UP, 6));
		BoundingBox.of(origin).add(Cardinal.UP, 7).grow(Cardinal.directions, 2).fill(editor, rand, walls, false, true);
		Cardinal.directions.forEach(dir -> {
			walls.set(editor, rand, origin.copy().add(Cardinal.UP, 6).add(dir));
			walls.set(editor, rand, origin.copy().add(Cardinal.UP, 5).add(dir, 3).add(Cardinal.left(dir), 3));
			stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, BoundingBox.of(origin).add(dir, 3).add(Cardinal.UP, 5).grow(Cardinal.orthogonal(dir)));
			
			BoundingBox.of(origin).add(Cardinal.UP, 6).add(dir, 2).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, walls);
			
			Cardinal.orthogonal(dir).forEach(o -> {
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(Cardinal.UP, 5).add(dir, 3).add(o, 2));
			});
		});
		Cardinal.directions.forEach(dir -> {
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 2).add(Cardinal.left(dir), 2).add(Cardinal.UP, 5));
		});
	}

	private void sides(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 5).add(Cardinal.UP, 6).grow(dir).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, walls, false, true);
			BoundingBox.of(origin).add(dir, 8).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 3).grow(Cardinal.UP, 2).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 7).add(Cardinal.UP, 5).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 4).add(Cardinal.UP, 5).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, walls);
			Cardinal.orthogonal(dir).forEach(o -> {
				BoundingBox.of(origin).add(dir, 5).add(o, 2).add(Cardinal.UP, 5).grow(dir).fill(editor, rand, walls);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 4).add(Cardinal.UP, 4).add(o, 3));
				Pillar.generate(editor, rand, origin.copy().add(dir, 8).add(o, 2), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 7).add(o, 3).add(Cardinal.UP, 4));
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 8).add(o, 3).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 6).add(o, 2).add(Cardinal.UP, 4));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 7).add(o).add(Cardinal.UP, 4));
				BoundingBox.of(origin).add(dir, 7).add(o, 2).add(Cardinal.UP, 3).grow(Cardinal.UP).fill(editor, rand, walls);
			});
		});
	}

	private void corners(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			corner(editor, rand, origin.copy().add(dir, 6).add(Cardinal.left(dir), 6));
		});
	}

	private void corner(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir)).grow(Cardinal.left(dir)).grow(Cardinal.UP, 2).fill(editor, rand, theme.getPrimary().getWall());
			Pillar.generate(editor, rand, origin.copy().add(dir, 2).add(Cardinal.left(dir), 2), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
		});
		BoundingBox.of(origin).add(Cardinal.UP, 5).grow(Cardinal.directions).fill(editor, rand, theme.getPrimary().getWall(), false, true);
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 8).grow(Cardinal.UP, 4).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.UP, 5).grow(Cardinal.directions, 4).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.UP, 6).grow(Cardinal.directions, 2).fill(editor, rand, Air.get());
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 4).add(Cardinal.UP, 5).grow(dir, 4).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, Air.get());
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 9).grow(Cardinal.orthogonal(dir), 9).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).grow(Cardinal.UP, 5).add(dir, 9).grow(Cardinal.orthogonal(dir), 9).fill(editor, rand, theme.getPrimary().getWall(), false, true);
			
		});
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 8).fill(editor, rand, theme.getPrimary().getFloor());
		this.getEntrancesFromType(Entrance.DOOR).forEach(dir -> {
			Fragment.generate(Fragment.ARCH, editor, rand, theme, origin.copy().add(dir, Cell.SIZE), dir);
		});
	}

	@Override
	public String getName() {
		return Room.CROSS.name();
	}

}
