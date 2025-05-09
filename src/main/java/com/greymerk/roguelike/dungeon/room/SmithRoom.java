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
import com.greymerk.roguelike.editor.blocks.Anvil;
import com.greymerk.roguelike.editor.blocks.Hopper;
import com.greymerk.roguelike.editor.blocks.IronBar;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.treasure.Treasure;

import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.random.Random;

public class SmithRoom extends AbstractRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos().freeze();
		Random rand = editor.getRandom(origin);
		
		this.clear(editor, rand, origin);
		this.entryRoom(editor, rand, origin);
		this.anteroom(editor, rand, origin);
		this.mainRoom(editor, rand, origin.add(direction, 12).freeze());
		this.smelters(editor, rand, origin.add(direction, 17).freeze());
		this.supports(editor, rand, origin);
	}

	private void supports(IWorldEditor editor, Random rand, Coord origin) {
		CellSupport.generate(editor, rand, theme, origin.add(direction, 6));
		CellSupport.generate(editor, rand, theme, origin.add(direction, 12));
		CellSupport.generate(editor, rand, theme, origin.add(direction, 18));
		Cardinal.orthogonal(direction).forEach(o -> {
			CellSupport.generate(editor, rand, theme, origin.add(direction, 12).add(o, 6));
		});
	}

	private void smelters(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		IBlockFactory wall = theme.getPrimary().getWall();
		BoundingBox.of(origin).add(direction, 3).grow(Cardinal.DOWN).grow(Cardinal.UP, 5).grow(Cardinal.orthogonal(direction), 3).fill(editor, rand, wall);
		stair.setOrientation(Cardinal.reverse(direction), true).fill(editor, rand, BoundingBox.of(origin).add(direction, 2).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(direction)));
		BoundingBox.of(origin).add(direction, 2).grow(Cardinal.orthogonal(direction), 2).fill(editor, rand, wall);
		this.smelter(editor, rand, origin);
		Cardinal.orthogonal(direction).forEach(o -> {
			stair.setOrientation(Cardinal.reverse(o), true).fill(editor, rand, BoundingBox.of(origin).add(o, 2).add(Cardinal.UP, 4).grow(direction, 2));
			BoundingBox.of(origin).add(o, 3).grow(Cardinal.UP, 4).grow(direction, 3).fill(editor, rand, wall);
			wall.set(editor, rand, origin.add(o).add(direction));
			this.smelter(editor, rand, origin.add(o, 2).freeze());
		});
	}

	private void smelter(IWorldEditor editor, Random rand, Coord origin) {
		MetaBlock chest = MetaBlock.of(Blocks.CHEST).with(HorizontalFacingBlock.FACING, Cardinal.facing(direction));
		chest.set(editor, origin);
		Hopper.generate(editor, rand, origin.add(direction), Cardinal.reverse(direction));
		Hopper.generate(editor, rand, origin.add(direction, 2).add(Cardinal.UP), Cardinal.reverse(direction));
		Hopper.generate(editor, rand, origin.add(direction).add(Cardinal.UP, 2), Cardinal.DOWN);
		chest.set(editor, origin.add(direction, 2).add(Cardinal.UP, 2));
		chest.set(editor, origin.add(direction).add(Cardinal.UP, 3));
		MetaBlock furnace = MetaBlock.of(Blocks.BLAST_FURNACE).with(HorizontalFacingBlock.FACING, Cardinal.facing(direction));
		furnace.set(editor, origin.add(direction).add(Cardinal.UP));
		
	}

	private void mainRoom(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		IBlockFactory wall = theme.getPrimary().getWall();
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.reverse(direction), 4).grow(direction, 8).grow(Cardinal.orthogonal(direction), 3).fill(editor, rand, theme.getPrimary().getFloor());
		BoundingBox.of(origin).add(Cardinal.UP, 5).grow(Cardinal.reverse(direction), 4).grow(direction, 8).grow(Cardinal.orthogonal(direction), 3).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		this.anvilRoom(editor, rand, origin.add(Cardinal.left(direction), 6).freeze(), Cardinal.left(direction));
		this.smithingRoom(editor, rand, origin.add(Cardinal.right(direction), 6).freeze(), Cardinal.right(direction));
		Cardinal.parallel(direction).forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.UP, 4).add(dir, 2).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).add(Cardinal.UP, 4).add(dir, 4).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, theme.getPrimary().getWall());
		});
		Cardinal.orthogonal(direction).forEach(orth -> {
			BoundingBox.of(origin).add(orth, 3).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(orth), 4).grow(orth).fill(editor, rand, theme.getPrimary().getWall());
			stair.setOrientation(Cardinal.reverse(orth), true).fill(editor, rand, BoundingBox.of(origin).add(orth, 3).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(orth)));
			stair.setOrientation(Cardinal.reverse(orth), true).fill(editor, rand, BoundingBox.of(origin).add(orth, 2).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(orth)));
			Cardinal.orthogonal(orth).forEach(o -> {
				BoundingBox.of(origin).add(orth, 4).add(o, 3).grow(o).grow(Cardinal.UP, 3).fill(editor, rand, theme.getPrimary().getWall());
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.add(orth, 3).add(o, 4).add(Cardinal.UP, 2));
				BoundingBox.of(origin).add(orth, 3).add(o, 2).add(Cardinal.UP, 3).grow(o, 2).fill(editor, rand, wall);
			});
		});
	}


	private void smithingRoom(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		sideRoom(editor, rand, origin, dir);
		MetaBlock grindstone = MetaBlock.of(Blocks.GRINDSTONE).with(HorizontalFacingBlock.FACING, Cardinal.facing(Cardinal.left(dir)));
		grindstone.set(editor, origin.add(Cardinal.left(dir), 2));
		MetaBlock table = MetaBlock.of(Blocks.SMITHING_TABLE);
		table.set(editor, origin.add(Cardinal.right(dir), 2));
		theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, BoundingBox.of(origin).add(dir, 2).grow(Cardinal.orthogonal(dir)));
		Treasure.generate(editor, rand, settings.getDifficulty(), origin.add(dir, 2).add(Cardinal.left(dir)).add(Cardinal.UP), Cardinal.reverse(dir), Treasure.WEAPON);
		Treasure.generate(editor, rand, settings.getDifficulty(), origin.add(dir, 2).add(Cardinal.right(dir)).add(Cardinal.UP), Cardinal.reverse(dir), Treasure.ARMOR);
	}


	private void anvilRoom(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		sideRoom(editor, rand, origin, dir);
		theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, BoundingBox.of(origin).add(dir, 2).grow(Cardinal.orthogonal(dir)));
		Treasure.generate(editor, rand, settings.getDifficulty(), origin.add(dir, 2).add(Cardinal.UP), Cardinal.reverse(dir), Treasure.ORE);
		this.lavaTank(editor, rand, origin, Cardinal.right(dir));
		this.waterTank(editor, rand, origin, Cardinal.left(dir));
		Anvil.set(editor, origin, Anvil.CHIPPED, Cardinal.left(dir));
	}
	
	private void waterTank(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		BoundingBox.of(origin).add(dir, 3).grow(Cardinal.orthogonal(dir), 2).grow(dir, 2).grow(Cardinal.UP, 3).grow(Cardinal.DOWN).fill(editor, rand, theme.getPrimary().getWall());
		BoundingBox.of(origin).add(dir, 3).add(Cardinal.UP).grow(Cardinal.orthogonal(dir)).grow(dir).fill(editor, rand, Air.get());
		stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, BoundingBox.of(origin).add(dir, 4).add(Cardinal.UP).grow(Cardinal.orthogonal(dir)));
		stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.add(dir, 3).add(Cardinal.UP, 2));
		Cardinal.orthogonal(dir).forEach(o -> {
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.add(dir, 3).add(o).add(Cardinal.UP));
		});
		
		MetaBlock.of(Blocks.WATER).set(editor, origin.add(dir, 3));
		Cardinal.directions.forEach(d -> {
			stair.setOrientation(Cardinal.reverse(d), false).waterlog().fill(editor, rand, BoundingBox.of(origin).add(dir, 3).add(d).grow(Cardinal.left(d)));
		});
	}

	private void lavaTank(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getPrimary().getStair();
		BoundingBox.of(origin).add(dir, 3).grow(Cardinal.orthogonal(dir), 2).grow(dir, 2).grow(Cardinal.UP, 3).grow(Cardinal.DOWN).fill(editor, rand, theme.getPrimary().getWall());
		BoundingBox.of(origin).add(dir, 3).add(Cardinal.UP).grow(Cardinal.orthogonal(dir)).grow(dir).fill(editor, rand, Air.get());
		stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, BoundingBox.of(origin).add(dir, 3).add(Cardinal.UP, 2).grow(Cardinal.orthogonal(dir)));
		stair.setOrientation(Cardinal.reverse(dir), false).set(editor, rand, origin.add(dir, 2));
		Cardinal.orthogonal(dir).forEach(o -> {
			stair.setOrientation(Cardinal.reverse(o), false).set(editor, rand, origin.add(dir, 2).add(o));
			IronBar.get().set(editor, rand, origin.add(dir, 2).add(o).add(Cardinal.UP));
		});
		BoundingBox.of(origin).add(dir, 3).grow(dir).grow(Cardinal.orthogonal(dir)).fill(editor, rand, MetaBlock.of(Blocks.LAVA));
	}

	private void sideRoom(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 3).grow(Cardinal.reverse(dir), 2).grow(dir, 3).fill(editor, rand, theme.getPrimary().getFloor());
		BoundingBox.of(origin).add(Cardinal.UP, 4).grow(Cardinal.directions, 2).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		BoundingBox.of(origin).add(dir, 3).grow(Cardinal.DOWN).grow(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 2).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		Cardinal.directions.forEach(d -> {
			BoundingBox.of(origin).add(Cardinal.UP, 3).add(d, 2).grow(Cardinal.orthogonal(d), 2).fill(editor, rand, theme.getPrimary().getWall());
		});
		Cardinal.orthogonal(dir).forEach(o -> {
			BoundingBox.of(origin).add(o, 3).grow(Cardinal.DOWN).grow(Cardinal.UP, 3).grow(Cardinal.orthogonal(o), 2).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
			Pillar.generate(editor, rand, origin.add(Cardinal.reverse(dir), 2).add(o, 2), theme, 2, List.of(Cardinal.reverse(dir), dir, Cardinal.reverse(o)));
			Pillar.generate(editor, rand, origin.add(dir, 2).add(o, 2), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
		});
	}


	private void anteroom(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox.of(origin).add(direction, 3).add(Cardinal.DOWN).grow(direction, 4).grow(Cardinal.orthogonal(direction), 2).fill(editor, rand, theme.getPrimary().getFloor());
		BoundingBox.of(origin).add(direction, 4).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(direction)).fill(editor, rand, theme.getPrimary().getWall());
		BoundingBox.of(origin).add(direction, 4).add(Cardinal.UP, 4).grow(direction, 3).grow(Cardinal.orthogonal(direction), 2).fill(editor, rand, theme.getPrimary().getWall());
		
		Cardinal.orthogonal(direction).forEach(o -> {
			BoundingBox.of(origin).add(direction, 4).add(o, 3).grow(direction, 3).grow(Cardinal.UP, 4).fill(editor, rand, theme.getPrimary().getWall());
			Pillar.generate(editor, rand, origin.add(direction, 4).add(o, 2), theme, 2, List.of(direction));
			Pillar.generate(editor, rand, origin.add(direction, 7).add(o, 2), theme, 2, List.of(Cardinal.reverse(direction)));
			stair.setOrientation(Cardinal.reverse(o), true).fill(editor, rand, BoundingBox.of(origin).add(direction, 5).add(o, 2).grow(direction));
			stair.setOrientation(Cardinal.reverse(o), true).fill(editor, rand, BoundingBox.of(origin).add(direction, 5).add(o).add(Cardinal.UP, 3).grow(direction, 2));
			BoundingBox.of(origin).add(direction, 4).add(o, 2).add(Cardinal.UP, 3).grow(direction, 3).fill(editor, rand, theme.getPrimary().getWall());
		});
		
	}


	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).add(direction, 4).grow(Cardinal.orthogonal(direction), 2).grow(Cardinal.UP, 3).grow(direction, 3).fill(editor, rand, Air.get());;
		BoundingBox.of(origin).add(direction, 8).grow(direction, 8).grow(Cardinal.orthogonal(direction), 4).grow(Cardinal.UP, 4).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(direction, 17).grow(direction, 2).grow(Cardinal.orthogonal(direction), 2).grow(Cardinal.UP, 4).fill(editor, rand, Air.get());
		Cardinal.orthogonal(direction).forEach(o -> {
			BoundingBox.of(origin).add(direction, 12).add(o, 6).grow(Cardinal.directions, 2).grow(Cardinal.UP, 3).fill(editor, rand, Air.get());
		});
	}


	private void entryRoom(IWorldEditor editor, Random rand, Coord origin) {
		Corridor cor = new Corridor();
		this.exits.forEach(e -> {
			cor.addExit(e);
		});
		
		cor.setDirection(direction);
		cor.setLevelSettings(settings);
		cor.worldPos = this.worldPos.copy();
		cor.generate(editor);
		
		BoundingBox.of(origin).add(direction, 3).grow(Cardinal.DOWN).grow(Cardinal.UP, 3).grow(Cardinal.orthogonal(direction), 2).fill(editor, rand, theme.getPrimary().getWall());
		this.theme.getPrimary().getDoor().generate(editor, origin.copy().add(direction, 3), Cardinal.reverse(direction));
	}


	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = Coord.ZERO;
		CellManager cells = new CellManager();
		
		cells.add(Cell.of(origin.copy(), CellState.OBSTRUCTED, this));
		cells.add(Cell.of(origin.copy().add(dir), CellState.OBSTRUCTED, this));
		cells.add(Cell.of(origin.copy().add(dir, 2), CellState.OBSTRUCTED, this));
		cells.add(Cell.of(origin.copy().add(dir, 3), CellState.OBSTRUCTED, this).addWall(dir).addWalls(Cardinal.orthogonal(dir)));
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			cells.add(Cell.of(origin.copy().add(dir).add(o), CellState.OBSTRUCTED, this).addWall(Cardinal.reverse(dir)).addWall(o));
			cells.add(Cell.of(origin.copy().add(dir).add(o), CellState.OBSTRUCTED, this).addWall(o));
			cells.add(Cell.of(origin.copy().add(dir, 2).add(o), CellState.OBSTRUCTED, this).addWall(dir).addWall(o));
		}
		
		for(Cardinal d : Cardinal.directions) {
			if(d == dir) continue;
			cells.add(Cell.of(origin.copy().add(d), CellState.POTENTIAL, this));
		}
		
		return cells;
	}

	@Override
	public BoundingBox getBoundingBox(Coord origin, Cardinal dir) {
		return BoundingBox.of(origin)
				.grow(dir, 20)
				.grow(Cardinal.reverse(dir), 2)
				.grow(Cardinal.orthogonal(dir), 8)
				.grow(Cardinal.UP, 6)
				.grow(Cardinal.DOWN);
	}
	
	@Override
	public String getName() {
		return Room.SMITH.name();	
	}
}
