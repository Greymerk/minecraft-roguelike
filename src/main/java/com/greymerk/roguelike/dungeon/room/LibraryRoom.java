package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.BookShelf;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.blocks.ColorBlock;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.Terracotta;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.factories.BlockCheckers;
import com.greymerk.roguelike.editor.factories.BlockFloor;
import com.greymerk.roguelike.util.Color;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class LibraryRoom extends AbstractLargeRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos().add(this.direction, Cell.SIZE * 2).freeze();
		Random rand = editor.getRandom(origin);
		this.clear(editor, rand, origin);
		this.floor(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.walls(editor, rand, origin);
		//this.decorations(editor, rand, origin);
		this.shelves(editor, rand, origin);
		this.supports(editor, rand, origin);
		this.generateExits(editor, rand);
	}

	/*
	private void decorations(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			Cardinal.orthogonal(dir).forEach(o -> {
				BookShelf.set(editor, rand, settings.getDifficulty(), origin.add(dir, 5).add(o, 8).add(Cardinal.UP), Cardinal.reverse(o));
				settings.getWallFragment(rand).generate(editor, rand, settings, origin.add(dir, 12).add(o, 6), dir);
				settings.getWallFragment(rand).generate(editor, rand, settings, origin.add(dir, 12).add(o, 6), Cardinal.reverse(dir));
				settings.getWallFragment(rand).generate(editor, rand, settings, origin.add(dir, 12).add(o, 12), dir);
			});
		});
	}
	*/

	private void shelves(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			Cardinal.orthogonal(dir).forEach(o -> {
				this.shelf(editor, rand, origin.copy().add(dir, 8).add(o, 5).freeze(), dir);
			});
		});
	}

	private void shelf(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).add(Cardinal.UP)
			.grow(Cardinal.orthogonal(dir)).grow(Cardinal.UP, 2)
			.forEach(pos -> {
				if(rand.nextInt(4) == 0) {
					BookShelf.set(editor, rand, this.settings.getDifficulty(), pos, Cardinal.reverse(dir), rand.nextInt(3) + 1);
				} else {
					MetaBlock.of(Blocks.BOOKSHELF).set(editor, pos);
				}
			});
		this.theme.getSecondary().getStair().setOrientation(Cardinal.reverse(dir), true)
			.fill(editor, rand, BoundingBox.of(origin).add(Cardinal.reverse(dir)).grow(Cardinal.orthogonal(dir)));
		
		BoundingBox.of(origin).add(Cardinal.reverse(dir)).add(Cardinal.UP).grow(Cardinal.orthogonal(dir))
			.forEach(pos -> {
				if(rand.nextBoolean()) return;
				Candle.generate(editor, rand, pos);
			});
	}

	private void walls(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		Cardinal.directions.forEach(dir -> {
			Pillar.generate(editor, rand, origin.add(dir, 7).add(Cardinal.left(dir), 7),  theme, 5);
			Cardinal.orthogonal(dir).forEach(o -> {
				BoundingBox.of(origin).add(dir, 8).add(o, 3).grow(Cardinal.UP, 6).grow(dir).grow(o, 6).fill(editor, rand, wall);
				Pillar.generate(editor, rand, origin.add(dir, 7).add(o, 3), theme, 5);
				BoundingBox.of(origin).add(Cardinal.UP, 6).add(dir, 4).add(o, 4).grow(dir, 6).fill(editor, rand, wall);
				Pillar.generate(editor, rand, origin.add(dir, 10).add(o, 4), theme, 4, List.of(dir, o));
				Pillar.generate(editor, rand, origin.add(dir, 10).add(o, 8), theme, 4, List.of(dir, Cardinal.reverse(o)));
				
				BoundingBox.of(origin).add(dir, 10).add(o, 2).add(Cardinal.UP, 5).grow(o, 12).fill(editor, rand, wall);
				List.of(2, 4, 8).forEach(step -> {
					BoundingBox.of(origin).add(Cardinal.UP, 5).add(dir, 11).add(o, step).grow(dir, 2).fill(editor, rand, wall);
				});
				Pillar.generate(editor, rand, origin.add(dir, 14).add(o, 2), theme, 4, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
				Pillar.generate(editor, rand, origin.add(dir, 14).add(o, 4), theme, 4, List.of(Cardinal.reverse(dir), o));
				Pillar.generate(editor, rand, origin.add(dir, 14).add(o, 8), theme, 4, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
				Pillar.generate(editor, rand, origin.add(dir, 14).add(o, 10), theme, 4, List.of(Cardinal.reverse(dir), o));
				
				BoundingBox.of(origin).add(dir, 6).add(o, 3).add(Cardinal.UP, 6).grow(o, 4).grow(dir).fill(editor, rand, wall);
				
				wall.set(editor, rand, origin.add(dir, 9).add(o, 2).add(Cardinal.UP, 4));
				wall.set(editor, rand, origin.add(dir, 8).add(o, 2).add(Cardinal.UP, 5));
				wall.set(editor, rand, origin.add(dir, 10).add(o, 2).add(Cardinal.UP, 5));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.add(dir, 8).add(o, 2).add(Cardinal.UP, 4));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.add(dir, 8).add(o).add(Cardinal.UP, 5));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.add(dir, 9).add(o, 2).add(Cardinal.UP, 3));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.add(dir, 9).add(o).add(Cardinal.UP, 4));
				stair.setOrientation(dir, true).set(editor, rand, origin.add(dir, 10).add(o, 2).add(Cardinal.UP, 4));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.add(dir, 10).add(o).add(Cardinal.UP, 5));
				
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.add(Cardinal.UP, 4).add(dir, 14).add(o, 3));
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.add(Cardinal.UP, 4).add(dir, 14).add(o, 9));
				stair.setOrientation(dir, true).set(editor, rand, origin.add(Cardinal.UP, 4).add(dir, 10).add(o, 9));
			});
			MetaBlock.of(Blocks.CHAIN).set(editor, origin.add(dir, 4).add(Cardinal.left(dir), 4).add(Cardinal.UP, 5));
			Lantern.set(editor, origin.add(dir, 4).add(Cardinal.left(dir), 4).add(Cardinal.UP, 4));
			
			BoundingBox.of(origin).add(Cardinal.UP, 5).add(dir, 14).grow(Cardinal.orthogonal(dir), 14).fill(editor, rand, wall);
			Pillar.generate(editor, rand, origin.add(dir, 10).add(Cardinal.left(dir), 10), theme, 4, List.of(dir, Cardinal.left(dir)));
			Pillar.generate(editor, rand, origin.add(dir, 14).add(Cardinal.left(dir), 14), theme, 4, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
			
			BoundingBox.of(origin).add(dir, 9).add(Cardinal.UP, 5).grow(Cardinal.orthogonal(dir), 2).fill(editor, rand, wall);
			
		});
	}

	private void supports(IWorldEditor editor, Random rand, Coord origin) {
		CellSupport.generate(editor, rand, theme, origin);
		Cardinal.directions.forEach(dir -> {
			List.of(6, 12).forEach(i -> {
				CellSupport.generate(editor, rand, theme, origin.copy().add(dir, i));
				List.of(6, 12).forEach(j -> {
					CellSupport.generate(editor, rand, theme, origin.copy().add(dir, i).add(Cardinal.left(dir), j));
				});
			});
		});
	}
	
	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			List.of(2, 4, 8, 10, 14).forEach(step -> {
				BoundingBox.of(origin).add(Cardinal.UP, 6).add(dir, step).grow(Cardinal.orthogonal(dir), 14).fill(editor, rand, theme.getPrimary().getWall());	
			});
		});
		
		BoundingBox.of(origin).add(Cardinal.UP, 7).grow(Cardinal.directions, 14).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
	}

	private void floor(IWorldEditor editor, Random rand, Coord origin) {
		List<Color> colors = RandHelper.pickCountFrom(List.of(Color.values()), rand, 4);
		BlockCheckers tiles = new BlockCheckers(
				ColorBlock.get(ColorBlock.CLAY, colors.get(0)),
				ColorBlock.get(ColorBlock.CLAY, colors.get(1)));
		BlockFloor tileFloor = new BlockFloor(tiles);
		
		BlockFloor trim = new BlockFloor(ColorBlock.get(ColorBlock.CLAY, colors.get(2)));
		
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 14).grow(dir).grow(Cardinal.orthogonal(dir), 15).fill(editor, rand, theme.getPrimary().getFloor());
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 11).grow(dir, 2).grow(Cardinal.right(dir), 10).grow(Cardinal.left(dir), 13).fill(editor, rand, tileFloor);
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 2).grow(dir, 8).grow(Cardinal.orthogonal(dir)).fill(editor, rand, tileFloor);
			BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions).fill(editor, rand, tileFloor);
			
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 8).add(Cardinal.left(dir), 2).grow(dir, 2).grow(Cardinal.left(dir), 8).fill(editor, rand, theme.getPrimary().getFloor());
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 8).add(Cardinal.right(dir), 2).grow(dir, 2).grow(Cardinal.right(dir), 5).fill(editor, rand, theme.getPrimary().getFloor());
			
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.left(dir), 5).fill(editor, rand, trim);
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 7).add(Cardinal.left(dir), 2).grow(Cardinal.left(dir), 5).fill(editor, rand, trim);
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 3).add(Cardinal.left(dir), 2).grow(dir, 3).fill(editor, rand, trim);
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 3).add(Cardinal.left(dir), 7).grow(dir, 3).fill(editor, rand, trim);
			
			Terracotta.fillSquare(editor, rand, origin.copy().add(Cardinal.DOWN).add(dir, 3).add(Cardinal.left(dir), 3), dir, 4, colors.get(3));
		});
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 14).grow(Cardinal.UP, 6).fill(editor, rand, Air.get());;
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 15).grow(Cardinal.orthogonal(dir), 15).grow(Cardinal.UP, 5).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		});
	}
	
	@Override
	public CellManager getCells(Cardinal dir) {
		CellManager cells = new CellManager();
		BoundingBox.of(Coord.ZERO).add(dir, 2)
			.grow(Cardinal.directions, 2)
			.forEach(pos -> {
				cells.add(Cell.of(pos, CellState.OBSTRUCTED, this));
			});
		
		Cardinal.directions.forEach(d -> {
			BoundingBox.of(Coord.ZERO.add(dir, 2)).add(d, 3)
				.grow(Cardinal.orthogonal(d), 2)
				.forEach(pos -> {
					if(pos.equals(Coord.ZERO.add(Cardinal.reverse(dir), 3))) return;
					cells.add(Cell.of(pos, CellState.POTENTIAL, this));
				});
		});
		
		return cells;
	}

	@Override
	public String getName() {
		return Room.LIBRARY.name();
	}

}
