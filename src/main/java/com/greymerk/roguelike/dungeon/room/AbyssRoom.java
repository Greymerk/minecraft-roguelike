package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.CryptFragment;
import com.greymerk.roguelike.dungeon.layout.ExitType;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Campfire;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.blocks.Skull;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.util.Color;

import net.minecraft.util.math.random.Random;

public class AbyssRoom extends AbstractLargeRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(this.worldPos);
		Coord origin = this.worldPos.copy().add(direction, Cell.SIZE * 2).freeze();
		this.clear(editor, rand, origin);
		this.pillars(editor, rand, origin);
		this.level(editor, rand, origin.copy());
		this.level(editor, rand, origin.copy().add(Cardinal.DOWN, 10));
		this.level(editor, rand, origin.copy().add(Cardinal.DOWN, 20));
		this.doors(editor, rand, origin);
		this.floor(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.fires(editor, rand, origin);
		this.bridge(editor, rand, origin);
		this.features(editor, rand, origin);
		this.supports(editor, rand, origin.copy().add(Cardinal.DOWN, 20).freeze());
		this.generateExits(editor, rand);
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

	private void features(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy().add(dir, 8).add(Cardinal.DOWN, 6);
			this.feature(editor, rand, pos.copy(), dir);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				this.feature(editor, rand, pos.copy().add(o, 6), dir);
			}
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy().add(dir, 8).add(Cardinal.DOWN, 16);
			this.feature(editor, rand, pos.copy(), dir);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				this.feature(editor, rand, pos.copy().add(o, 6), dir);
			}
		}
	}

	private void feature(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(dir);
		bb.grow(dir, 5).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 4);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		this.decorations(editor, rand, origin, dir);
	}

	private void decorations(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = origin.copy();
			pos.add(Cardinal.UP).add(o).add(dir);
			this.deco(editor, rand, pos, dir);
			pos.add(Cardinal.UP, 2);
			this.deco(editor, rand, pos, dir);
		}		
	}

	private void deco(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		
		if(rand.nextBoolean()) {
			if(rand.nextBoolean()) {
				if(rand.nextBoolean()) {
					Candle.generate(editor, rand, origin, Color.BLACK); return;	
				} else {
					Skull.set(editor, rand, origin, Cardinal.reverse(dir)); return;	
				}
			} else {
				Air.get().set(editor, origin);
			}
		}

		CryptFragment crypt = new CryptFragment();
		crypt.setEmpty(rand.nextInt(5) != 0);
		crypt.generate(editor, rand, settings, origin.copy(), dir);
	}
	
	private void bridge(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(Cardinal.DOWN).add(dir, 3);
			bb.grow(dir, 4).grow(Cardinal.orthogonal(dir));
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		}
		
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.DOWN).grow(Cardinal.directions, 2);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		this.cell(editor, rand, origin);
	}

	private void fires(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy().add(dir, 9);
			this.fire(editor, rand, pos.copy().add(Cardinal.right(dir), 3));
			this.fire(editor, rand, pos.copy().add(Cardinal.left(dir), 3));
			this.fire(editor, rand, pos.copy().add(Cardinal.left(dir), 9));
		}
	}

	private void fire(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		
		Campfire.generate(editor, origin, Campfire.SOUL);
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy().add(dir);
			stair.setOrientation(dir, false).set(editor, rand, pos);
			pos.add(Cardinal.UP, 2);
			stair.setOrientation(dir, true).set(editor, rand, pos);
			pos.add(Cardinal.UP);
			theme.getPrimary().getWall().set(editor, rand, pos);
		}
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.UP, 4);
		bb.grow(Cardinal.directions, 7);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		for(Cardinal dir : Cardinal.directions) {
			bb = BoundingBox.of(origin.copy());
			bb.add(Cardinal.UP, 4);
			bb.grow(Cardinal.orthogonal(dir), 7);
			bb.add(dir, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			bb.add(dir, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			bb.add(dir, 4);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
		}
		
		
	}

	private void floor(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.DOWN, 21);
		bb.grow(Cardinal.directions, 7);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
	}

	private void level(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox.of(origin).add(dir, 9).add(Cardinal.DOWN)
				.grow(dir, 6).grow(Cardinal.left(dir), 15).grow(Cardinal.right(dir), 8)
				.fill(editor, rand, theme.getPrimary().getFloor());
			
			BoundingBox.of(origin).add(dir, 8).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 7)
				.fill(editor, rand, theme.getPrimary().getWall());

			BoundingBox.of(origin).add(dir, 8).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 7)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			List.of(-6, 0, 6, 12).forEach(offset -> {
				this.cell(editor, rand, origin.copy().add(dir, 12).add(Cardinal.left(dir), offset));
			});
		}
	}

	private void cell(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		IBlockFactory walls = theme.getPrimary().getWall();
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(dir, 2).add(Cardinal.left(dir), 2);
			bb.grow(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, bb, walls);
			bb = BoundingBox.of(origin.copy());
			bb.add(Cardinal.UP, 3).add(dir, 2);
			bb.grow(Cardinal.orthogonal(dir));
			RectSolid.fill(editor, rand, bb, walls);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy();
				pos.add(Cardinal.UP, 2).add(dir, 2).add(o);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);	
			}
			
			BoundingBox.of(origin).add(dir, 3).grow(Cardinal.orthogonal(dir)).grow(Cardinal.UP, 2)
				.forEach(pos -> {
					if(!editor.isSolid(pos.copy().add(dir))) {
						Air.get().set(editor, pos);
					}
				});
		}
	}

	private void pillars(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			this.innerPillarSet(editor, rand, origin.copy().add(dir, 8).add(Cardinal.UP, 3), dir);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy();
				pos.add(dir, 8).add(Cardinal.UP, 3).add(o, 6);
				this.innerPillarSet(editor, rand, pos, dir);
			}
		}
	}

	private void innerPillarSet(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.grow(Cardinal.DOWN, 23);
			bb.add(o, 2);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall());
			
			Coord pos = origin.copy();
			pos.add(o, 2);
			pos.add(Cardinal.reverse(dir));
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
		}
		Coord pos = origin.copy();
		this.crossBar(editor, rand, pos, dir);
		pos.add(Cardinal.DOWN, 4);
		this.crossBar(editor, rand, pos, dir);
		
		pos.add(Cardinal.DOWN, 6);
		this.crossBar(editor, rand, pos, dir);
		pos.add(Cardinal.DOWN, 4);
		this.crossBar(editor, rand, pos, dir);
		
		pos.add(Cardinal.DOWN, 6);
		this.crossBar(editor, rand, pos, dir);
	}

	private void crossBar(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = BoundingBox.of(origin);
		bb.grow(Cardinal.orthogonal(dir));
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = origin.copy();
			pos.add(o).add(Cardinal.DOWN);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, pos);
		}
	}

	private void doors(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			if(this.getExitType(dir) != ExitType.DOOR) continue;
			Coord pos = origin.copy();
			pos.add(dir, 12);
			Fragment.generate(Fragment.ARCH, editor, rand, settings, pos, dir);
		}
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.grow(Cardinal.UP, 3).grow(Cardinal.directions, 14);
		RectSolid.fill(editor, rand, bb, Air.get());
		bb.add(Cardinal.DOWN, 10);
		RectSolid.fill(editor, rand, bb, Air.get());
		bb.add(Cardinal.DOWN, 10);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.DOWN, 5).grow(Cardinal.directions, 8);
		RectSolid.fill(editor, rand, bb, Air.get());
		bb.add(Cardinal.DOWN, 10);
		RectSolid.fill(editor, rand, bb, Air.get());
	}

	@Override
	public CellManager getCells(Cardinal dir) {
		CellManager cells = super.getCells(dir);
		
		BoundingBox.of(Coord.ZERO).add(dir, 2).add(Cardinal.DOWN).grow(Cardinal.directions, 2).grow(Cardinal.DOWN)
			.forEach(pos -> cells.add(Cell.of(pos, CellState.OBSTRUCTED, this)));
		
		Cardinal.directions.forEach(d -> {
			BoundingBox.of(Coord.ZERO).add(dir, 2).add(d, 3).add(Cardinal.DOWN)
				.grow(Cardinal.orthogonal(d), 2).grow(Cardinal.DOWN)
				.forEach(pos -> cells.add(Cell.of(pos, CellState.POTENTIAL, this)));
		});
		
		return cells;
	}
	
	@Override
	public BoundingBox getBoundingBox(Coord origin, Cardinal dir) {
		return BoundingBox.of(origin.copy().add(dir, Cell.SIZE * 2))
			.grow(Cardinal.directions, 15)
			.grow(Cardinal.UP, 6)
			.grow(Cardinal.DOWN, 23);
	}
	
	@Override
	public String getName() {
		return Room.ABYSS.name();
	}
}
