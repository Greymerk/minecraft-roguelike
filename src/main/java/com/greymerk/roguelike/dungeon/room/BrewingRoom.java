package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.SpiralStairCase;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.BrewingStand;
import com.greymerk.roguelike.editor.blocks.Trapdoor;
import com.greymerk.roguelike.editor.blocks.Wood;
import com.greymerk.roguelike.editor.blocks.slab.ISlab;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.Line;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.loot.Loot;
import com.greymerk.roguelike.util.IWeighted;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class BrewingRoom extends AbstractRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos().copy();
		Random rand = editor.getRandom(origin);
		
		entry(editor, rand, origin);
		mainRoom(editor, rand, origin.copy().add(direction, 9));
		basement(editor, rand, origin.copy().add(direction, 9).add(Cardinal.DOWN, 6));
		
		theme.getPrimary().getDoor().generate(editor, origin.copy().add(direction, 3), Cardinal.reverse(direction));
		
		SpiralStairCase.generate(editor, rand, theme, Line.of(
				origin.copy().add(direction, 12).add(Cardinal.right(direction), 6).add(Cardinal.DOWN),
				origin.copy().add(direction, 12).add(Cardinal.right(direction), 6).add(Cardinal.DOWN, 6)));
	}

	private void basement(IWorldEditor editor, Random rand, Coord origin) {
		
		IBlockFactory walls = theme.getPrimary().getWall();
		
		BoundingBox.of(origin).grow(Cardinal.orthogonal(direction), 8).grow(Cardinal.parallel(direction), 5).grow(Cardinal.UP, 4)
			.getShape(Shape.RECTSOLID).fill(editor, rand, Air.get());
		
		Cardinal.parallel(direction).forEach(dir -> {
			BoundingBox.of(origin).add(dir, 6).grow(Cardinal.UP, 5).grow(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 8)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
		});
		
		Cardinal.orthogonal(direction).forEach(dir -> {
			BoundingBox.of(origin).add(dir, 9).grow(Cardinal.UP, 5).grow(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 5)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
		});
		
		BoundingBox.of(origin).add(Cardinal.left(direction), 3).grow(Cardinal.parallel(direction), 5).grow(Cardinal.DOWN).grow(Cardinal.UP, 4)
			.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
		
		basementEntry(editor, rand, origin.copy().add(Cardinal.right(direction), 3));
		labRoom(editor, rand, origin.copy().add(Cardinal.left(direction), 6));
	}

	private void labRoom(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory pillar = theme.getPrimary().getWall();
		IBlockFactory floor = theme.getPrimary().getFloor();
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.parallel(direction), 6).grow(Cardinal.orthogonal(direction), 3)
			.getShape(Shape.RECTSOLID).fill(editor, rand, floor);
		
		Cardinal.orthogonal(direction).forEach(o -> {
			BoundingBox.of(origin).add(o, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(o), 4).grow(Cardinal.UP)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(o, 2).add(Cardinal.UP, 2));
		});
		
		Cardinal.parallel(direction).forEach(dir -> {
			BoundingBox.of(origin).add(dir, 5).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			
			Cardinal.orthogonal(dir).forEach(o -> {
				BoundingBox.of(origin).add(dir).add(o, 2).grow(Cardinal.UP, 4).getShape(Shape.RECTSOLID).fill(editor, rand, pillar);
				BoundingBox.of(origin).add(dir).add(o).add(Cardinal.UP, 3).grow(Cardinal.UP).getShape(Shape.RECTSOLID).fill(editor, rand, pillar);
				BoundingBox.of(origin).add(dir, 5).add(o, 2).grow(Cardinal.UP, 4).getShape(Shape.RECTSOLID).fill(editor, rand, pillar);
				
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir).add(o).add(Cardinal.UP, 2));
				stair.setOrientation(dir, true).set(editor, rand, origin.copy().add(dir, 2).add(o, 2).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 4).add(o, 2).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 5).add(o).add(Cardinal.UP, 2));
			});
			
			this.shelf(editor, rand, origin.copy().add(dir, 3), dir, stair);
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, 3));
		});
		
		this.shelf(editor, rand, origin.copy().add(Cardinal.reverse(direction), 3), Cardinal.left(direction), stair);
		this.brewingStand(editor, rand, origin.copy().add(direction, 5).add(Cardinal.UP));
		Cardinal.orthogonal(direction).forEach(dir -> {
			this.wartFarm(editor, rand, origin.copy().add(direction, 3), dir);
		});
		Treasure.generate(editor, rand, origin.copy().add(Cardinal.reverse(direction), 5).add(Cardinal.UP), direction, Treasure.BREWING);
		Treasure.generate(editor, rand, origin.copy().add(Cardinal.reverse(direction), 3).add(Cardinal.left(direction), 2).add(Cardinal.UP), Cardinal.right(direction), Treasure.BREWING);
	}
	


	private void basementEntry(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory pillar = theme.getPrimary().getWall();
		IBlockFactory floor = theme.getPrimary().getFloor();
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 6)
			.getShape(Shape.RECTSOLID).fill(editor, rand, floor);
		
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 5).add(Cardinal.left(dir), 5).grow(Cardinal.UP, 4)
				.getShape(Shape.RECTSOLID).fill(editor, rand, pillar);
			
			BoundingBox.of(origin).add(dir).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 2)
				.getShape(Shape.RECTSOLID).fill(editor, rand, theme.getPrimary().getSlab().upsideDown(true).get());
			
			BoundingBox.of(origin).add(dir, 5).add(Cardinal.UP, 3).grow(Cardinal.UP).grow(Cardinal.orthogonal(dir), 5)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 2));
			stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, BoundingBox.of(origin).add(dir, 4).add(Cardinal.UP, 4)
					.getShape(Shape.RECTSOLID));
			
			Cardinal.orthogonal(dir).forEach(o -> {
				BoundingBox.of(origin).add(dir, 5).add(o).grow(Cardinal.UP, 4)
					.getShape(Shape.RECTSOLID).fill(editor, rand, pillar);
				BoundingBox.of(origin).add(dir, 4).add(o).add(Cardinal.UP, 3).grow(Cardinal.UP)
					.getShape(Shape.RECTSOLID).fill(editor, rand, pillar);
				
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 4).add(o).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 3).add(o).add(Cardinal.UP, 4));
				
				stair.setOrientation(o, true).set(editor, rand, origin.copy().add(dir, 5).add(o, 2).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 5).add(o, 4).add(Cardinal.UP, 2));
			});
			
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, 3).add(Cardinal.left(dir), 3));
		});
		
		settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(direction, 3).add(Cardinal.left(direction), 3), direction);
		settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(direction, 3).add(Cardinal.left(direction), 3), Cardinal.left(direction));
		settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(Cardinal.reverse(direction), 3).add(Cardinal.left(direction), 3), Cardinal.reverse(direction));
		
		settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(Cardinal.reverse(direction), 3).add(Cardinal.right(direction), 3), Cardinal.reverse(direction));
		settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(Cardinal.reverse(direction), 3).add(Cardinal.right(direction), 3), Cardinal.right(direction));
		
		theme.getPrimary().getDoor().generate(editor, origin.copy().add(Cardinal.left(direction), 6).add(Cardinal.reverse(direction), 3), Cardinal.right(direction));
	}

	private void mainRoom(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory pillar = theme.getSecondary().getPillar();
		IBlockFactory floor = theme.getSecondary().getFloor();
		IBlockFactory walls = theme.getPrimary().getWall();
		ISlab ceiling = theme.getSecondary().getSlab().upsideDown(true);
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox.of(origin).grow(Cardinal.orthogonal(direction), 8).grow(List.of(direction, Cardinal.reverse(direction)), 5).grow(Cardinal.UP, 4)
			.getShape(Shape.RECTSOLID).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.UP, 5).grow(Cardinal.parallel(direction), 3).grow(Cardinal.orthogonal(direction), 6)
			.getShape(Shape.RECTSOLID).fill(editor, rand, ceiling.get(), Fill.SOLID);
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(List.of(direction, Cardinal.reverse(direction)), 4).grow(Cardinal.orthogonal(direction), 7)
			.getShape(Shape.RECTSOLID).fill(editor, rand, floor);
		
		Cardinal.parallel(direction).forEach(dir -> {
			BoundingBox.of(origin).add(dir, 6).grow(Cardinal.UP, 6).grow(Cardinal.orthogonal(dir), 8)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 5).add(Cardinal.UP, 3).grow(Cardinal.UP, 2).grow(Cardinal.orthogonal(dir), 8)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 4).add(Cardinal.UP, 5).grow(Cardinal.orthogonal(dir), 8)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir).add(Cardinal.UP, 5).grow(Cardinal.orthogonal(dir), 8)
			.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 5).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 8)
			.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
		});
		
		Cardinal.orthogonal(direction).forEach(dir -> {
			BoundingBox.of(origin).add(dir, 9).grow(Cardinal.UP, 6).grow(Cardinal.orthogonal(dir), 5).grow(Cardinal.DOWN)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 8).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 4)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 8).add(Cardinal.UP, 3).grow(Cardinal.UP, 2).grow(Cardinal.orthogonal(dir), 5)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 7).add(Cardinal.UP, 5).grow(Cardinal.orthogonal(dir), 4)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP, 5).grow(Cardinal.orthogonal(dir), 4)
			.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 4).add(Cardinal.UP, 5).grow(Cardinal.orthogonal(dir), 4)
			.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
		});
		
		for(Cardinal orth : Cardinal.orthogonal(direction)) {
			BoundingBox.of(origin).add(Cardinal.DOWN).add(orth, 4).grow(Cardinal.orthogonal(orth), 5)
				.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			
			stair.setOrientation(Cardinal.reverse(orth), true).set(editor, rand, origin.copy().add(orth, 8).add(Cardinal.UP, 2));
			stair.setOrientation(Cardinal.reverse(orth), true).set(editor, rand, origin.copy().add(orth, 7).add(Cardinal.UP, 4));
			
			for(Cardinal o : Cardinal.orthogonal(orth)) {
				BoundingBox.of(origin).add(orth, 2).add(o, 5).grow(Cardinal.UP, 5)
					.getShape(Shape.RECTSOLID).fill(editor, rand, pillar);
				BoundingBox.of(origin).add(orth, 2).add(o, 4).add(Cardinal.UP, 3).grow(Cardinal.UP, 3)
					.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
				BoundingBox.of(origin).add(orth, 4).add(o, 5).grow(Cardinal.UP, 5)
					.getShape(Shape.RECTSOLID).fill(editor, rand, pillar);
				BoundingBox.of(origin).add(orth, 4).add(o, 4).add(Cardinal.UP, 3).grow(Cardinal.UP, 3)
					.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
				BoundingBox.of(origin).add(orth, 8).add(o).grow(Cardinal.UP, 5)
					.getShape(Shape.RECTSOLID).fill(editor, rand, pillar);
				BoundingBox.of(origin).add(orth, 7).add(o).add(Cardinal.UP, 3).grow(Cardinal.UP, 3)
					.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
				BoundingBox.of(origin).add(orth, 8).add(o, 5).grow(Cardinal.UP, 5)
					.getShape(Shape.RECTSOLID).fill(editor, rand, pillar);
				
				
				//lower
				stair.setOrientation(Cardinal.reverse(orth), true).set(editor, rand, origin.copy().add(orth).add(o, 5).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(orth, 2).add(o, 4).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(orth, 3).add(o, 5).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(orth, 4).add(o, 4).add(Cardinal.UP, 2));
				stair.setOrientation(orth, true).set(editor, rand, origin.copy().add(orth, 5).add(o, 5).add(Cardinal.UP, 2));
				
				stair.setOrientation(Cardinal.reverse(orth), true).set(editor, rand, origin.copy().add(orth, 7).add(o, 5).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(orth, 8).add(o, 4).add(Cardinal.UP, 2));
				stair.setOrientation(o, true).set(editor, rand, origin.copy().add(orth, 8).add(o, 2).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(orth), true).set(editor, rand, origin.copy().add(orth, 7).add(o).add(Cardinal.UP, 2));
				
				//upper
				stair.setOrientation(Cardinal.reverse(orth), true).set(editor, rand, origin.copy().add(orth).add(o, 4).add(Cardinal.UP, 4));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(orth, 2).add(o, 3).add(Cardinal.UP, 4));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(orth, 3).add(o, 4).add(Cardinal.UP, 4));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(orth, 4).add(o, 3).add(Cardinal.UP, 4));
				stair.setOrientation(orth, true).set(editor, rand, origin.copy().add(orth, 5).add(o, 4).add(Cardinal.UP, 4));
				
				stair.setOrientation(o, true).set(editor, rand, origin.copy().add(orth, 7).add(o, 2).add(Cardinal.UP, 4));
				stair.setOrientation(Cardinal.reverse(orth), true).set(editor, rand, origin.copy().add(orth, 6).add(o).add(Cardinal.UP, 4));
				
				BoundingBox.of(origin).add(Cardinal.DOWN).add(orth, 4).add(o).grow(orth, 4)
					.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
				
				settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(orth, 6).add(o, 3), orth);
				settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(orth, 6).add(o, 3), o);
			}
		}
		
		Fragment.generate(Fragment.WALL_CANDLES, editor, rand, theme, origin.copy().add(direction, 3), direction);
	}

	private void entry(IWorldEditor editor, Random rand, Coord origin) {
		Corridor cor = new Corridor();
		
		Cardinal.directions.forEach(dir -> {
			Entrance type = this.getEntrance(dir);
			if(type == Entrance.DOOR) {
				cor.addEntrance(dir, Entrance.DOOR);	
			} else {
				cor.addEntrance(dir, Entrance.WALL);
			}
		});
		
		cor.setLevelSettings(settings);
		cor.worldPos = this.worldPos.copy();
		cor.addEntrance(direction, Entrance.DOOR);
		cor.generate(editor);
	}
	
	private void shelf(IWorldEditor editor, Random rand, Coord origin, Cardinal dir, IStair stair) {
		stair.setOrientation(Cardinal.reverse(dir), true)
			.fill(editor, rand, BoundingBox.of(origin).add(dir, 2).grow(Cardinal.orthogonal(dir)).getShape(Shape.RECTSOLID));
	}
	
	private void wartFarm(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox bb = BoundingBox.of(origin).add(dir).grow(Cardinal.orthogonal(dir));
		bb.getShape(Shape.RECTSOLID).fill(editor, rand, Trapdoor.getWooden(Wood.SPRUCE, dir, false, true));
		bb.add(dir);
		bb.getShape(Shape.RECTSOLID).fill(editor, rand, BlockType.get(BlockType.SOUL_SAND));
		bb.add(Cardinal.UP);
		bb.getShape(Shape.RECTSOLID).fill(editor, rand, MetaBlock.of(Blocks.NETHER_WART));
	}
	
	private void brewingStand(IWorldEditor editor, Random rand, Coord origin) {
		BrewingStand.generate(editor, origin);
		IWeighted<ItemStack> provider = Loot.getProvider(Loot.POTION, Difficulty.fromY(origin.getY()), editor);
		BrewingStand.slots.forEach(slot -> {
			BrewingStand.add(editor, origin, slot, provider.get(rand));
		});
		BrewingStand.add(editor, origin, BrewingStand.FUEL, new ItemStack(Items.BLAZE_POWDER, rand.nextBetween(1, 4)));
	}
	
	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = Coord.ZERO;
		CellManager cells = new CellManager();
		
		cells.add(Cell.of(origin.copy(), CellState.OBSTRUCTED));
		cells.add(Cell.of(origin.copy().add(dir), CellState.OBSTRUCTED));
		cells.add(Cell.of(origin.copy().add(Cardinal.DOWN).add(dir), CellState.OBSTRUCTED).addWall(Cardinal.reverse(dir)));
		cells.add(Cell.of(origin.copy().add(dir, 2), CellState.OBSTRUCTED).addWall(dir));
		cells.add(Cell.of(origin.copy().add(Cardinal.DOWN).add(dir, 2), CellState.OBSTRUCTED).addWall(dir));
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			cells.add(Cell.of(origin.copy().add(dir).add(o), CellState.OBSTRUCTED).addWall(Cardinal.reverse(dir)).addWall(o));
			cells.add(Cell.of(origin.copy().add(Cardinal.DOWN).add(dir).add(o), CellState.OBSTRUCTED).addWall(Cardinal.reverse(dir)).addWall(o));
			cells.add(Cell.of(origin.copy().add(dir, 2).add(o), CellState.OBSTRUCTED).addWall(dir).addWall(o));
			cells.add(Cell.of(origin.copy().add(Cardinal.DOWN).add(dir, 2).add(o), CellState.OBSTRUCTED).addWall(dir).addWall(o));
		}
		
		for(Cardinal d : Cardinal.directions) {
			if(d == dir) continue;
			cells.add(Cell.of(origin.copy().add(d), CellState.POTENTIAL));
		}
		
		return cells;
	}

	@Override
	public BoundingBox getBoundingBox(Coord origin, Cardinal dir) {
		return BoundingBox.of(origin)
				.grow(dir, 12)
				.grow(Cardinal.orthogonal(dir), 9)
				.grow(Cardinal.DOWN, 10)
				.grow(Cardinal.UP, 6);
	}
	
	@Override
	public void determineEntrances(Floor f, Coord floorPos) {
		for(Cardinal dir : Cardinal.directions) {
			if(dir == this.direction) continue;
			Cell c = f.getCell(floorPos.copy().add(dir));
			if(!c.isRoom()) continue;
			if(!c.getWalls().contains(Cardinal.reverse(dir))){
				this.addEntrance(dir, Entrance.DOOR);
			} else {
				this.addEntrance(dir, Entrance.WALL);
			}
		}
	}
	
	@Override
	public String getName() {
		return Room.BREWING.name();
	}

}
