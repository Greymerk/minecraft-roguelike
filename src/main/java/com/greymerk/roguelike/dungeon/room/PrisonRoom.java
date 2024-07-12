package com.greymerk.roguelike.dungeon.room;


import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.IronBar;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

import net.minecraft.util.math.random.Random;

public class PrisonRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.worldPos.copy().add(direction, Cell.SIZE).freeze();
		Random rand = editor.getRandom(origin);
		clear(editor, rand, origin);
		ceiling(editor, rand, origin);
		cornerCells(editor, rand, origin);
		entrances(editor, rand, origin);
		center(editor, rand, origin);
		supports(editor, rand, origin);
	}

	private void supports(IWorldEditor editor, Random rand, Coord origin) {
		CellSupport.generate(editor, rand, theme, origin);
		Cardinal.directions.forEach(dir -> {
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, 6));
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, 6).add(Cardinal.left(dir), 6));
		});
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 8).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 8).grow(Cardinal.UP).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).add(Cardinal.UP, 4).add(dir, 2).grow(Cardinal.orthogonal(dir), 8).grow(Cardinal.UP).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).add(Cardinal.UP, 4).add(dir, 4).grow(Cardinal.orthogonal(dir), 8).grow(Cardinal.UP).fill(editor, rand, theme.getPrimary().getWall());
			Cardinal.orthogonal(dir).forEach(o -> {
				List.of(2, 4, 8).forEach(step -> {
					theme.getPrimary().getWall().set(editor, rand, origin.add(Cardinal.UP, 6).add(dir, 2).add(o, step), Fill.ONLY_AIR);
					theme.getPrimary().getWall().set(editor, rand, origin.add(Cardinal.UP, 6).add(dir, 4).add(o, step), Fill.ONLY_AIR);
				});
			});
			theme.getPrimary().getWall().set(editor, rand, origin.add(Cardinal.UP, 5).add(dir, 8).add(Cardinal.left(dir), 8), Fill.ONLY_AIR);
		});
		BoundingBox.of(origin).add(Cardinal.UP, 5).grow(Cardinal.directions, 8).fill(editor, rand, theme.getPrimary().getWall(), Fill.ONLY_SOLID);
	}



	private void center(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			if(this.getEntrance(dir) == Entrance.WALL && this.getEntrance(Cardinal.left(dir)) != Entrance.WALL){
				Pillar.generate(editor, rand, origin.add(dir, 3).add(Cardinal.left(dir), 3), theme, 3, List.of(Cardinal.left(dir), Cardinal.reverse(dir), Cardinal.right(dir)));
			}
			if(this.getEntrance(dir) == Entrance.WALL && this.getEntrance(Cardinal.left(dir)) == Entrance.WALL){
				Pillar.generate(editor, rand, origin.add(dir, 3).add(Cardinal.left(dir), 3), theme, 3, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
			}
			if(this.getEntrance(dir) != Entrance.WALL && this.getEntrance(Cardinal.left(dir)) != Entrance.WALL){
				Pillar.generate(editor, rand, origin.add(dir, 4).add(Cardinal.left(dir), 4), theme, 3, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
			}
			if(this.getEntrance(dir) != Entrance.WALL && this.getEntrance(Cardinal.left(dir)) == Entrance.WALL){
				Pillar.generate(editor, rand, origin.add(dir, 3).add(Cardinal.left(dir), 3), theme, 3, List.of(dir, Cardinal.reverse(dir), Cardinal.right(dir)));
			}
		});
	}



	private void entrances(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			if(this.getEntrance(dir) == Entrance.WALL) {
				cellWall(editor, rand, origin, dir);
			} else {
				entry(editor, rand, origin, dir);
			}
		});
	}

	private void entry(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		Cardinal.orthogonal(dir).forEach(o -> {
			Pillar.generate(editor, rand, origin.add(dir, 8).add(o, 2), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
			theme.getPrimary().getWall().set(editor, rand, origin.add(dir, 7).add(o, 2).add(Cardinal.UP, 3));
		});
		
		if(this.getEntrance(dir) == Entrance.DOOR) {
			Fragment.generate(Fragment.ARCH, editor, rand, theme, origin.add(dir, 6), dir);
		} else if(this.getEntrance(dir) == Entrance.ALCOVE) {
			BoundingBox.of(origin).add(dir, 9).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 2).fill(editor, rand, theme.getPrimary().getWall(), Fill.ONLY_SOLID);
			settings.getAlcove(rand).generate(editor, rand, theme, origin.add(dir, 6), dir);
		}
	}



	private void cellWall(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		Cardinal.orthogonal(dir).forEach(o -> {
			BoundingBox.of(origin).add(dir, 4).add(o, 2).grow(o).grow(Cardinal.UP, 4).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).add(dir, 5).add(o, 2).add(Cardinal.UP, 3).grow(dir, 2).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).add(dir, 8).add(o, 2).grow(Cardinal.UP, 2).fill(editor, rand, theme.getPrimary().getWall());
			bars(editor, rand, origin.add(dir, 6).add(o, 2), o);
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.add(dir, 8).add(Cardinal.UP, 2).add(o));
			BoundingBox.of(origin).add(dir, 4).add(o, 4).grow(Cardinal.UP, 4).fill(editor, rand, theme.getPrimary().getWall());
		});
		BoundingBox.of(origin).add(dir, 4).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir)).grow(Cardinal.UP).fill(editor, rand, theme.getPrimary().getWall());
		bars(editor, rand, origin.add(dir, 4), dir);
		BoundingBox.of(origin).add(dir, 9).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 2).fill(editor, rand, theme.getPrimary().getWall(), Fill.ONLY_SOLID);
		settings.getWallFragment(rand).generate(editor, rand, theme, origin.add(dir, 6), dir);
	}

	private void cornerCells(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			Pillar.generate(editor, rand, origin.add(dir, 8).add(Cardinal.left(dir), 8), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
			Cardinal.orthogonal(dir).forEach(o -> {
				Pillar.generate(editor, rand, origin.add(dir, 8).add(o, 4), theme, 2, List.of(o));
				BoundingBox.of(origin).add(dir, 5).add(o, 4).add(Cardinal.UP, 3).grow(dir, 2).fill(editor, rand, theme.getPrimary().getWall());
				bars(editor, rand, origin.add(dir, 6).add(o, 4), o);
				settings.getWallFragment(rand).generate(editor, rand, theme, origin.add(dir, 6).add(o, 6), o);
			});
		});
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 8).grow(Cardinal.UP, 4).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 9).fill(editor, rand, theme.getPrimary().getFloor());
		Cardinal.directions.forEach(dir -> {
			Cardinal.orthogonal(dir).forEach(o -> {
				BoundingBox.of(origin).add(dir, 9).add(o, 3).grow(o, 6).grow(Cardinal.UP, 3).fill(editor, rand, theme.getPrimary().getWall(), Fill.ONLY_SOLID);
			});
		});
	}

	private void bars(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).grow(Cardinal.orthogonal(dir)).grow(Cardinal.UP, 2).fill(editor, rand, IronBar.getBroken());
	}


	@Override
	public String getName() {
		return Room.PRISON.name();
	}

}
