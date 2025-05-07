package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.dungeon.layout.Exit;
import com.greymerk.roguelike.dungeon.layout.ExitType;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Piston;
import com.greymerk.roguelike.editor.blocks.RedstoneTorch;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class PitRoom extends AbstractMediumRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.worldPos.copy().add(direction, Cell.SIZE).freeze();
		Random rand = editor.getRandom(origin);
		this.centerRoom(editor, rand, origin);
		Cardinal.directions.forEach(dir -> {
			if(this.getExitType(dir) == ExitType.DOOR) {
				this.entryDoorWay(editor, rand, origin, dir);	
			} else {
				this.pistonTrap(editor, rand, origin, dir);
			}
		});
		
		this.tunnelDown(editor, rand, origin);
		this.lowerRoom(editor, rand, origin.add(Cardinal.DOWN, 20).freeze());
		this.supports(editor, rand, origin.add(Cardinal.DOWN, 20).freeze());
	}
	
	private void supports(IWorldEditor editor, Random rand, Coord origin) {
		CellSupport.generate(editor, rand, theme, origin.copy().add(Cardinal.DOWN));
		Cardinal.directions.forEach(dir -> {
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, 6));
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, 6).add(Cardinal.left(dir), 6));
		});
	}

	private void tunnelDown(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.DOWN, 16).grow(Cardinal.directions).fill(editor, rand, Air.get());
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 2)
				.grow(Cardinal.left(dir)).grow(Cardinal.right(dir), 2).grow(Cardinal.DOWN, 15)
				.fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		});
	}

	private void lowerRoom(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 8).grow(Cardinal.UP, 4).fill(editor, rand, Air.get());
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 9).grow(Cardinal.orthogonal(dir), 9).grow(Cardinal.UP, 3)
				.fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		});
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 8).fill(editor, rand, theme.getPrimary().getFloor());
		this.lowerRoomCeiling(editor, rand, origin);
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.UP, 3).add(dir, 8).grow(Cardinal.orthogonal(dir), 8).fill(editor, rand, theme.getPrimary().getWall());
			Cardinal.orthogonal(dir).forEach(o -> {
				Pillar.generate(editor, rand, origin.add(dir, 8).add(o, 2), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
				Pillar.generate(editor, rand, origin.add(dir, 8).add(o, 4), theme, 2, List.of(Cardinal.reverse(dir), o));
				List.of(2, 4).forEach(i -> {
					theme.getPrimary().getWall().set(editor, rand, origin.add(dir, 7).add(o, i).add(Cardinal.UP, 3));
					theme.getPrimary().getStair()
						.setOrientation(Cardinal.reverse(dir), true)
						.set(editor, rand, origin.add(dir, 6).add(Cardinal.UP, 3).add(o, i));
				});
			});
			Pillar.generate(editor, rand, origin.add(dir, 8).add(Cardinal.left(dir), 8), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
		});
		this.lowerRoomPond(editor, rand, origin);
	}

	private void lowerRoomPond(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).add(Cardinal.DOWN, 2).grow(Cardinal.directions, 3).fill(editor, rand, theme.getPrimary().getWall());
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 2).fill(editor, rand, theme.getPrimary().getLiquid());
		Cardinal.directions.forEach(dir -> {
			theme.getPrimary().getWall().set(editor, rand, origin.add(dir, 3).add(Cardinal.left(dir), 3).add(Cardinal.UP, 4));
			Pillar.generate(editor, rand, origin.add(dir, 3).add(Cardinal.left(dir), 3), theme, 3);
		});
	}

	private void lowerRoomCeiling(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).add(Cardinal.UP, 5).grow(Cardinal.directions, 8).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		Cardinal.directions.forEach(dir -> {
			List.of(2, 4, 8).forEach(i -> {
				BoundingBox.of(origin).add(Cardinal.UP, 4).add(dir, i).grow(Cardinal.orthogonal(dir), 8).fill(editor, rand, theme.getPrimary().getWall());
			});
		});
	}

	private void centerRoom(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 2).grow(Cardinal.UP, 3).fill(editor, rand, Air.get());
		BoundingBox.of(origin).grow(Cardinal.directions, 3).add(Cardinal.DOWN).fill(editor, rand, this.theme.getPrimary().getWall());
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 3).grow(Cardinal.orthogonal(dir), 3).grow(Cardinal.UP, 4).grow(Cardinal.DOWN).fill(editor, rand, this.theme.getPrimary().getWall());
		});
		BoundingBox.of(origin).add(Cardinal.UP, 4).grow(Cardinal.directions, 3).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
	}
	
	private void entryDoorWay(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		Corridor room = new Corridor();
		Coord wp = origin.add(dir, Cell.SIZE);
		room.setWorldPos(wp);
		room.setDirection(dir);
		room.setLevelSettings(settings);
		Cardinal.parallel(dir).forEach(d -> {
			room.addExit(Exit.of(ExitType.DOOR, wp, d));
		});
		Cardinal.orthogonal(dir).forEach(d -> {
			room.addExit(Exit.of(ExitType.WALL, wp, d));
		});
		room.generate(editor);
	}
	
	private void pistonTrap(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).add(dir, 3).grow(Cardinal.UP, 2).grow(Cardinal.DOWN, 2).grow(Cardinal.orthogonal(dir), 2).grow(dir, 3).fill(editor, rand, this.theme.getPrimary().getWall());
		MetaBlock.of(Blocks.STONE_PRESSURE_PLATE).set(editor, origin.add(dir, 2));
		RedstoneTorch.generate(editor, origin.add(dir, 3).add(Cardinal.DOWN), dir, true);
		RedstoneTorch.generate(editor, origin.add(dir, 5), false);
		MetaBlock.of(Blocks.REDSTONE_WIRE).set(editor, origin.add(dir, 4).add(Cardinal.DOWN));
		Piston.generate(editor, origin.add(dir, 4).add(Cardinal.UP), Cardinal.reverse(dir), true);
	}
	
	@Override
	public CellManager getCells(Cardinal roomDir) {
		CellManager cells = super.getCells(roomDir);
		
		cells.add(Cell.of(Coord.ZERO.add(roomDir).add(Cardinal.DOWN), CellState.OBSTRUCTED, this));
		Cardinal.directions.forEach(dir -> {
			cells.add(Cell.of(Coord.ZERO.add(roomDir).add(Cardinal.DOWN).add(dir), CellState.OBSTRUCTED, this).addWall(dir));
			cells.add(Cell.of(Coord.ZERO.add(roomDir).add(Cardinal.DOWN).add(dir).add(Cardinal.left(dir)), CellState.OBSTRUCTED, this, List.of(dir, Cardinal.left(dir))));
		});
		
		
		BoundingBox.of(Coord.ZERO.add(roomDir).add(Cardinal.DOWN, 2))
			.grow(Cardinal.directions).forEach(c -> {
			cells.add(Cell.of(c, CellState.OBSTRUCTED, this));
		});
		
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(Coord.ZERO.add(roomDir).add(Cardinal.DOWN, 2))
				.add(dir, 2).grow(Cardinal.orthogonal(dir)).forEach(c -> {
				cells.add(Cell.of(c, CellState.POTENTIAL, this));
			});
		});
		
		return cells;
	}
	
	@Override
	public BoundingBox getBoundingBox(Coord origin, Cardinal dir) {
		return BoundingBox.of(origin.copy().add(dir, Cell.SIZE))
			.grow(Cardinal.directions, 10)
			.grow(Cardinal.UP, 6)
			.grow(Cardinal.DOWN, 22);
	}
	
	@Override
	public String getName() {
		return Room.PIT.name();
	}

}
