package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.wall.WallFlowers;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Bed;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.blocks.Furnace;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.treasure.Treasure;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class BedRoom extends AbstractRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos();
		Random rand = editor.getRandom(origin);
		
		Corridor cor = new Corridor();
		this.exits.forEach(e -> {
			cor.addExit(e);
		});
		
		cor.setDirection(this.direction);
		cor.setLevelSettings(settings);
		cor.worldPos = this.worldPos.copy();
		cor.generate(editor);
		
		
		BoundingBox bb = BoundingBox.of(origin);
		bb.add(direction, 3).grow(Cardinal.DOWN).grow(Cardinal.UP, 3);
		bb.grow(direction, 12).grow(Cardinal.orthogonal(direction), 4);
		RectHollow.fill(editor, rand, bb, this.theme.getPrimary().getWall());

		bb = BoundingBox.of(origin);
		bb.add(direction, 5).add(Cardinal.DOWN);
		bb.grow(Cardinal.orthogonal(direction), 2).grow(direction, 8);
		RectSolid.fill(editor, rand, bb, this.theme.getSecondary().getFloor());

		for(Cardinal o : Cardinal.orthogonal(this.direction)) {
			bb = BoundingBox.of(origin);
			bb.add(this.direction, 4).add(o, 3).grow(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, bb, this.theme.getPrimary().getWall());
			bb.add(this.direction, 10);
			RectSolid.fill(editor, rand, bb, this.theme.getPrimary().getWall());
		}
		
		Coord pos = origin.copy().add(direction, 3);
		this.theme.getPrimary().getDoor().generate(editor, pos, Cardinal.reverse(direction));
		
		this.setPillars(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.decorations(editor, rand, origin);
		
		CellSupport.generate(editor, rand, theme, origin.copy().add(direction, 6));
		CellSupport.generate(editor, rand, theme, origin.copy().add(direction, 12));
	}
	
	private void setPillars(IWorldEditor editor, Random rand, Coord origin) {
		List<Coord> stops = new ArrayList<Coord>();
		Coord pos = origin.copy();
		pos.add(this.direction, 5);
		stops.add(pos.copy());
		pos.add(this.direction, 4);
		stops.add(pos.copy());
		pos.add(this.direction, 4);
		stops.add(pos.copy());
		
		for(Cardinal o : Cardinal.orthogonal(this.direction)) {
			for(Coord p : stops) {
				Coord c = p.copy().add(o, 3);
				this.pillar(editor, rand, c);
			}
		}
		
		pos = origin.copy();
		pos.add(direction, 4);
		for(Cardinal o : Cardinal.orthogonal(this.direction)) {
			Coord p = pos.copy();
			p.add(o, 2);
			this.pillar(editor, rand, p);
			p.add(direction, 10);
			this.pillar(editor, rand, p);
		}
		
	}

	private void pillar(IWorldEditor editor, Random rand, Coord origin) {
		
		IBlockFactory pillar = this.theme.getSecondary().getPillar();
		IBlockFactory wall = this.theme.getSecondary().getWall();
		IStair stair = this.theme.getSecondary().getStair();
		
		Coord pos = origin.copy();
		pillar.set(editor, rand, pos);
		pos.add(Cardinal.UP);
		pillar.set(editor, rand, pos);
		pos.add(Cardinal.UP);
		wall.set(editor, rand, pos);
		for(Cardinal dir : Cardinal.directions) {
			Coord p = pos.copy();
			p.add(dir);
			stair.setOrientation(dir, true);
			stair.set(editor, rand, p, Fill.AIR);
		}
	}
	
	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox bb = BoundingBox.of(origin);
		bb.add(direction, 5).add(Cardinal.UP, 4);
		bb.grow(Cardinal.orthogonal(direction), 2).grow(direction, 8);
		RectSolid.fill(editor, rand, bb, this.theme.getPrimary().getWall());
		bb.add(Cardinal.UP);
		RectSolid.fill(editor, rand, bb, this.theme.getSecondary().getWall());
		
		bb = BoundingBox.of(origin);
		bb.add(direction, 7).add(Cardinal.UP, 3);
		bb.grow(Cardinal.directions).grow(Cardinal.UP);
		RectSolid.fill(editor, rand, bb, Air.get());
		bb.add(direction, 4);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		for(Cardinal dir : Cardinal.directions) {
			if(dir == direction) continue;
			bb = BoundingBox.of(origin);
			bb.add(direction, 7).add(Cardinal.UP, 3).add(dir, 2);
			bb.grow(Cardinal.orthogonal(dir));
			IStair stair = this.theme.getPrimary().getStair();
			stair.setOrientation(Cardinal.reverse(dir), true);
			for(Coord c : bb.getShape(Shape.RECTSOLID)) {
				stair.set(editor, rand, c);
			}
		}
		
		Coord pos = origin.copy().add(direction, 7).add(Cardinal.UP, 3);
		pos.add(Cardinal.UP);
		Lantern.set(editor, pos);
		pos.add(direction, 4);
		Lantern.set(editor, pos);
		pos.add(Cardinal.DOWN);
		
		for(Cardinal dir : Cardinal.directions) {
			if(dir == Cardinal.reverse(direction)) continue;
			
			bb = BoundingBox.of(origin);
			bb.add(direction, 7).add(Cardinal.UP, 3).add(dir, 2);
			bb.grow(Cardinal.orthogonal(dir));
			IStair stair = this.theme.getPrimary().getStair();
			stair.setOrientation(Cardinal.reverse(dir), true);
			for(Coord c : bb.getShape(Shape.RECTSOLID)) {
				stair.set(editor, rand, c);
			}
		}
		
		pos = origin.copy().add(direction, 9).add(Cardinal.UP, 3);
		Air.get().set(editor, pos);
		IStair stair = this.theme.getPrimary().getStair();
		for(Cardinal o : Cardinal.orthogonal(direction)) {
			Coord p = pos.copy();
			p.add(o);
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, rand, p);
		}
		
		
		

	}
	
	private void decorations(IWorldEditor editor, Random rand, Coord origin) {
		for(Cardinal o : Cardinal.orthogonal(direction)) {
			Coord pos = origin.copy();
			pos.add(direction, 7);
			pos.add(o);
			new WallFlowers().generate(editor, rand, settings, pos, o);
		}
		
		Coord pos = origin.copy();
		pos.add(direction, 11);
		pos.add(Cardinal.left(direction), 3);
		Bed.generate(editor, rand, Cardinal.right(direction), pos);
		pos.add(direction);
		MetaBlock.of(Blocks.BOOKSHELF).set(editor, pos);
		pos.add(Cardinal.UP);
		Candle.generate(editor, rand, pos);

		pos = origin.copy();
		pos.add(direction, 12);
		new WallFlowers().generate(editor, rand, settings, pos, direction);
		
		pos = origin.copy();
		pos.add(direction, 10);
		pos.add(Cardinal.right(direction), 3);
		Treasure.generate(editor, rand, settings.getDifficulty(), pos, Cardinal.left(direction), Treasure.STARTER);
		pos.add(direction);
		MetaBlock.of(Blocks.CRAFTING_TABLE).set(editor, pos);
		pos.add(direction);
		Furnace.generate(editor, Cardinal.left(direction), pos, false, new ItemStack(Items.COAL, rand.nextBetween(1, 4)));
	}
	
	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = Coord.ZERO;
		CellManager cells = new CellManager();
		
		cells.add(Cell.of(origin.copy(), CellState.OBSTRUCTED, this));
		cells.add(Cell.of(origin.copy().add(dir), CellState.OBSTRUCTED, this));
		cells.add(Cell.of(origin.copy().add(dir, 2), CellState.OBSTRUCTED, this).addWall(dir));
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			cells.add(Cell.of(origin.copy().add(dir).add(o), CellState.OBSTRUCTED, this).addWall(Cardinal.reverse(dir)).addWall(o));
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
				.grow(dir, 12)
				.grow(Cardinal.orthogonal(dir), 6)
				.grow(Cardinal.UP, 6)
				.grow(Cardinal.DOWN, 3);
	}
	
	@Override
	public String getName() {
		return Room.BEDROOM.name();
	}
}
