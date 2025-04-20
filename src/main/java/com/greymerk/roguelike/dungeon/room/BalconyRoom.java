package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.IronBar;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

import net.minecraft.util.math.random.Random;

public class BalconyRoom  extends AbstractRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {

		Coord origin = this.getWorldPos().add(this.direction, Cell.SIZE).freeze();
		Random rand = editor.getRandom(origin);
		
		this.clear(editor, rand, origin);
		this.mainWalls(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.upperRoom(editor, rand, origin);
		this.lowerRoom(editor, rand, origin.add(Cardinal.DOWN, 10).freeze());
		this.generateExits(editor, rand);
	}

	private void lowerRoom(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			
			BoundingBox.of(origin).add(dir, 8).add(Cardinal.UP, 3)
				.grow(Cardinal.orthogonal(dir), 8).grow(Cardinal.UP, 5)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			BoundingBox.of(origin).add(dir, 7).add(Cardinal.UP, 8)
				.grow(Cardinal.orthogonal(dir), 8)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true)
				.fill(editor, rand, BoundingBox.of(origin)
					.add(dir, 6).add(Cardinal.UP, 8)
					.grow(Cardinal.orthogonal(dir), 6));
			
			Pillar.generate(editor, rand, origin.add(dir, 8).add(Cardinal.left(dir), 8), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));

			Cardinal.orthogonal(dir).forEach(o -> {
				Pillar.generate(editor, rand, origin.add(dir, 8).add(o, 2), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
				Pillar.generate(editor, rand, origin.add(dir, 7).add(o, 2).add(Cardinal.UP, 3), theme, 4, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
				Pillar.generate(editor, rand, origin.add(dir, 8).add(o, 4), theme, 2, List.of(Cardinal.reverse(dir), o));
				Pillar.generate(editor, rand, origin.add(dir, 7).add(o, 4).add(Cardinal.UP, 3), theme, 4, List.of(Cardinal.reverse(dir), o));
				theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.add(dir, 8).add(o, 3).add(Cardinal.UP, 2));
				theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.add(dir, 7).add(o, 3).add(Cardinal.UP, 7));
				
				List.of(2, 4).forEach(i -> {
					theme.getPrimary().getWall().set(editor, rand, origin.add(dir, 6).add(Cardinal.UP, 8).add(o, i));
					theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.add(dir, 5).add(Cardinal.UP, 8).add(o, i));	
				});
				
			});
		});
	}

	private void upperRoom(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.UP, 3).add(dir, 8)
				.grow(Cardinal.orthogonal(dir), 8)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			Pillar.generate(editor, rand, origin.add(dir, 8).add(Cardinal.left(dir), 8), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
			Pillar.generate(editor, rand, origin.add(dir, 4).add(Cardinal.left(dir), 4), theme, 2, List.of(dir, Cardinal.left(dir)));

			Cardinal.orthogonal(dir).forEach(o -> {
				Pillar.generate(editor, rand, origin.add(dir, 8).add(o, 4), theme, 2, List.of(Cardinal.reverse(dir), o));
				Pillar.generate(editor, rand, origin.add(dir, 8).add(o, 2), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
				
				Pillar.generate(editor, rand, origin.add(dir, 4).add(o, 2), theme, 2, List.of(dir));
				Pillar.generate(editor, rand, origin.add(dir, 4).add(o, 2).add(Cardinal.UP, 3), theme, 0, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
				
				BoundingBox.of(origin).add(Cardinal.UP, 3).add(dir, 3).add(o, 4)
					.grow(dir, 4).fill(editor, rand, theme.getPrimary().getWall());
				
				BoundingBox.of(origin).add(Cardinal.UP, 3).add(dir, 5).add(o, 2)
					.grow(dir, 2).fill(editor, rand, theme.getPrimary().getWall());
			});
			
			BoundingBox.of(origin).add(dir, 4).grow(Cardinal.orthogonal(dir), 3)
				.fill(editor, rand, IronBar.get(), Fill.AIR.and(Fill.SUPPORTED));
		});
		
		
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			List.of(2, 4, 8).forEach(i -> {
				BoundingBox.of(origin).add(Cardinal.UP, 4).add(dir, i)
					.grow(Cardinal.orthogonal(dir), 9)
					.fill(editor, rand, theme.getPrimary().getWall());
				List.of(2, 4, 8).forEach(j -> {
					Cardinal.orthogonal(dir).forEach(o -> {
						theme.getPrimary().getWall().set(editor, rand, 
								origin.add(Cardinal.UP, 5).add(dir, i).add(o, j),
								Fill.AIR.or(Fill.LIQUID));
					});
				});
			});
		});
	}

	private void mainWalls(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).add(Cardinal.DOWN, 11).grow(Cardinal.directions, 8)
			.fill(editor, rand, this.settings.getTheme().getPrimary().getFloor());
		
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 8)
			.fill(editor, rand, this.settings.getTheme().getPrimary().getFloor());
	
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 3)
			.fill(editor, rand, Air.get());
		
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 9)
				.grow(Cardinal.orthogonal(dir), 9)
				.grow(Cardinal.UP, 5)
				.grow(Cardinal.DOWN, 11)
				.fill(editor, rand, theme.getPrimary().getWall());
		});
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 8)
			.grow(Cardinal.UP, 4)
			.grow(Cardinal.DOWN, 10)
			.fill(editor, rand, Air.get());
	}

	@Override
	public BoundingBox getBoundingBox(Coord origin, Cardinal dir) {
		return BoundingBox.of(origin.copy().add(dir, Cell.SIZE))
			.grow(Cardinal.directions, 10)
			.grow(Cardinal.UP, 6)
			.grow(Cardinal.DOWN, 11);
	}
	
	@Override
	public CellManager getCells(Cardinal dir) {
		
		Coord origin = Coord.ZERO.add(dir).freeze();
		
		CellManager cells = new CellManager();
		
		BoundingBox.of(origin).grow(Cardinal.directions).grow(Cardinal.DOWN)
			.forEach(pos -> {
				cells.add(Cell.of(pos, CellState.OBSTRUCTED, this));
			});
		
		Cardinal.directions.forEach(d -> {
			BoundingBox.of(origin).add(d, 2)
				.grow(Cardinal.orthogonal(d))
				.grow(Cardinal.DOWN)
				.forEach(pos -> {
					cells.add(Cell.of(pos, CellState.POTENTIAL, this));
				});
		});
		
		return cells;
	}
	
	
	@Override
	public String getName() {
		return Room.BALCONY.name();
	}
}
