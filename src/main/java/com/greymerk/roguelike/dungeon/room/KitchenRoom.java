package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.blocks.Furnace;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.treasure.ChestType;
import com.greymerk.roguelike.treasure.Treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class KitchenRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = worldPos.copy().add(direction, Cell.SIZE);
		Random rand = editor.getRandom(origin);
		IBlockFactory wall = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		IBlockFactory pillar = theme.getPrimary().getPillar();
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.directions, 8).grow(Cardinal.UP, 4);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.UP, 5);
		bb.grow(Cardinal.directions, 9);
		RectSolid.fill(editor, rand, bb, wall);
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.DOWN);
		bb.grow(Cardinal.directions, 9);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin);
		Cardinal.directions.forEach(dir -> {
			Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy().add(dir, Cell.SIZE));
			Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy().add(dir, Cell.SIZE).add(Cardinal.left(dir), Cell.SIZE));
		});
		
		for(Cardinal dir : Cardinal.directions) {
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				//outer wall
				bb = new BoundingBox(origin.copy());
				bb.add(dir, 9).add(o, 3);
				bb.grow(o, 6).grow(Cardinal.UP, 4);
				RectSolid.fill(editor, rand, bb, wall, false, true);
				
				//outer wall
				bb = new BoundingBox(origin.copy());
				bb.add(dir, 8).add(Cardinal.UP, 3);
				bb.grow(o, 8).grow(Cardinal.UP, 2);
				RectSolid.fill(editor, rand, bb, wall);
				
				//inner upper wall
				bb = new BoundingBox(origin.copy());
				bb.add(dir, 3).add(o, 2).add(Cardinal.UP, 3);
				bb.grow(Cardinal.UP, 1).grow(dir, 4);
				RectSolid.fill(editor, rand, bb, wall);
				
				bb = new BoundingBox(origin.copy());
				bb.add(dir, 7).add(o, 3).add(Cardinal.UP, 4).grow(o, 4);
				RectSolid.fill(editor, rand, bb, wall);
				
				//upper corner stairs
				bb = new BoundingBox(origin.copy());
				bb.add(dir, 7).add(o, 5).add(Cardinal.UP, 3);
				bb.grow(o, 2);
				stair.setOrientation(Cardinal.reverse(dir), true);
				bb.getShape(Shape.RECTSOLID).get().forEach(c -> stair.set(editor, c));
				
				//corner upper stair detailing
				Coord pos = origin.copy();
				pos.add(dir, 7).add(Cardinal.UP, 3).add(o, 3);
				stair.set(editor, pos);
				pos.add(o);
				wall.set(editor, rand, pos);
				
				//door pillars
				bb = new BoundingBox(origin.copy());
				bb.add(dir, 8).add(o, 2);
				bb.grow(Cardinal.UP, 2);
				RectSolid.fill(editor, rand, bb, pillar);
				
				//pillar crowns
				pos = origin.copy();
				pos.add(dir, 8).add(Cardinal.UP, 2).add(o);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
				pos.add(o).add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, pos);
				pos.add(dir).add(o);
				stair.set(editor, pos);
				
				pos = origin.copy();
				pos.add(dir, 2).add(o).add(Cardinal.UP, 4);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
			}
			
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 2).add(Cardinal.left(dir), 2);
			bb.grow(Cardinal.UP, 4);
			RectSolid.fill(editor, rand, bb, pillar);
			
			
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 6);
			pos.add(Cardinal.left(dir), 6);
			cornerPillars(editor, rand, pos, Cardinal.left(dir));
			
			bb = new BoundingBox(origin.copy());
			bb.add(dir, 3).add(Cardinal.left(dir), 3).add(Cardinal.DOWN);
			bb.grow(dir, 4).grow(Cardinal.left(dir), 4);
			RectSolid.fill(editor, rand, bb, theme.getSecondary().getFloor());
		}
		
		for(Cardinal dir : Cardinal.directions) {
			if(this.getEntrancesFromType(Entrance.DOOR).contains(dir)) {
				Coord pos = origin.copy().add(dir, 6);
				Fragment.generate(Fragment.ARCH, editor, rand, theme, pos, dir);
			}
		}
		
		boolean table = true;
		for(Cardinal dir : Cardinal.randDirs(rand)) {
			Coord pos = origin.copy();
			pos.add(dir, 6).add(Cardinal.left(dir), 6);
			if(table) {
				table(editor, rand, pos, Cardinal.left(dir));
			} else {
				chest(editor, rand, pos, Cardinal.left(dir));
			}
			table = !table;
		}
	}

	private void chest(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getSecondary().getStair();
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(dir, 2).grow(Cardinal.orthogonal(dir));
		stair.setOrientation(Cardinal.reverse(dir), false);
		bb.getShape(Shape.RECTSOLID).get().forEach(c -> stair.set(editor, c));
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.right(dir), 2).grow(Cardinal.orthogonal(Cardinal.right(dir)));
		stair.setOrientation(Cardinal.left(dir), false);
		bb.getShape(Shape.RECTSOLID).get().forEach(c -> stair.set(editor, c));
		
		Coord pos = origin.copy();
		stair.setOrientation(dir, true).set(editor, pos);
		pos.add(Cardinal.left(dir));
		stair.setOrientation(Cardinal.left(dir), true).set(editor, pos);
		pos.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).set(editor, pos);
		pos.add(Cardinal.right(dir));
		stair.setOrientation(Cardinal.right(dir), true).set(editor, pos);
		
		pos = origin.copy().add(Cardinal.reverse(dir)).add(Cardinal.left(dir)).add(Cardinal.UP);
		Candle.generate(editor, rand, pos, true);
	}

	private void table(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getSecondary().getStair();
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.add(dir, 2).grow(Cardinal.orthogonal(dir));
		stair.setOrientation(Cardinal.reverse(dir), true);
		bb.getShape(Shape.RECTSOLID).get().forEach(c -> stair.set(editor, c));
		
		bb = new BoundingBox(origin.copy());
		bb.add(Cardinal.right(dir), 2).grow(Cardinal.orthogonal(Cardinal.right(dir)));
		stair.setOrientation(Cardinal.left(dir), true);
		bb.getShape(Shape.RECTSOLID).get().forEach(c -> stair.set(editor, c));
		
		Coord pos = origin.copy();
		pos.add(Cardinal.UP).add(dir, 2);
		Treasure.generate(editor, rand, pos, Treasure.FOOD, ChestType.BARREL);
		
		pos = origin.copy().add(Cardinal.UP).add(Cardinal.right(dir), 2);
		Furnace.generate(editor, true, Cardinal.left(dir), pos, new ItemStack(Items.COAL, rand.nextBetween(1, 4)));
	}

	private void cornerPillars(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IBlockFactory pillar = theme.getSecondary().getPillar();
		IStair stair = theme.getSecondary().getStair();
		
		BoundingBox bb = new BoundingBox(origin.copy());
		bb.grow(Cardinal.UP, 2);
		bb.add(Cardinal.right(dir), 2).add(Cardinal.reverse(dir), 2);
		RectSolid.fill(editor, rand, bb, pillar);
		bb.add(dir, 4);
		RectSolid.fill(editor, rand, bb, pillar);
		bb.add(Cardinal.left(dir), 4);
		RectSolid.fill(editor, rand, bb, pillar);
		
		Coord pos = origin.copy();
		pos.add(Cardinal.UP, 2).add(Cardinal.right(dir), 2).add(Cardinal.reverse(dir), 2);
		Cardinal.directions.forEach(d -> stair.setOrientation(d, true).set(editor, pos.copy().add(d), true, false));
		pos.add(dir, 4);
		Cardinal.directions.forEach(d -> stair.setOrientation(d, true).set(editor, pos.copy().add(d), true, false));
		pos.add(Cardinal.left(dir), 4);
		Cardinal.directions.forEach(d -> stair.setOrientation(d, true).set(editor, pos.copy().add(d), true, false));
	}


	@Override
	public String getName() {
		return Room.KITCHEN.name();
	}

}
