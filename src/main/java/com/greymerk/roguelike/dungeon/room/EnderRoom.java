package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.EnderChest;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.theme.IBlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.ThemeBase;
import com.greymerk.roguelike.theme.blocksets.EnderBlocks;

import net.minecraft.util.math.random.Random;

public class EnderRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = worldPos.copy().add(direction, Cell.SIZE);
		Random rand = editor.getRandom(origin);
		IBlockSet blocks = new EnderBlocks();
		this.clear(editor, rand, origin, blocks);
		this.walls(editor, rand, origin, blocks);
		this.pillars(editor, rand, origin, blocks);
		this.floor(editor, rand, origin, blocks);
		this.ceiling(editor, rand, origin, blocks);
		this.doors(editor, rand, origin, blocks);
		this.enderchest(editor, rand, origin, blocks);
		this.supports(editor, rand, origin, blocks);
		Spawner.generate(editor, rand, origin, Spawner.ENDERMAN);
	}

	private void supports(IWorldEditor editor, Random rand, Coord origin, IBlockSet blocks) {
		ITheme ender = new EnderTheme();
		
		CellSupport.generate(editor, rand, ender, origin);
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 6);
			CellSupport.generate(editor, rand, ender, pos);
			pos.add(Cardinal.left(dir), 6);
			CellSupport.generate(editor, rand, ender, pos);
		}
	}

	private void doors(IWorldEditor editor, Random rand, Coord origin, IBlockSet blocks) {
		for(Cardinal dir : this.getEntrancesFromType(Entrance.DOOR)) {
			Coord pos = origin.copy();
			pos.add(dir, 6);
			Fragment.generate(Fragment.ARCH, editor, rand, theme, pos, dir);
		}
	}

	private void enderchest(IWorldEditor editor, Random rand, Coord origin, IBlockSet blocks) {
		Coord pos = origin.copy();
		Cardinal dir = Cardinal.randDirs(rand).get(0);
		pos.add(dir, 6);
		Cardinal o = rand.nextBoolean() ? Cardinal.left(dir) : Cardinal.right(dir);
		pos.add(o, 8);
		EnderChest.set(editor, Cardinal.reverse(o), pos);	
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin, IBlockSet blocks) {
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(Cardinal.UP, 6).add(dir, 2);
			bb.grow(Cardinal.orthogonal(dir), 4);
			RectSolid.fill(editor, rand, bb, blocks.getWall());
			bb.add(dir, 2);
			RectSolid.fill(editor, rand, bb, blocks.getWall());
			
			bb = BoundingBox.of(origin.copy());
			bb.add(dir, 4).add(Cardinal.UP, 5);
			bb.grow(Cardinal.orthogonal(dir), 7);
			RectSolid.fill(editor, rand, bb, blocks.getWall());
			bb.add(dir, 4);
			RectSolid.fill(editor, rand, bb, blocks.getWall());
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				bb = BoundingBox.of(origin.copy());
				bb.add(dir, 3).add(o, 2).add(Cardinal.UP, 5);
				bb.grow(dir, 4);
				RectSolid.fill(editor, rand, bb, blocks.getWall());
			}
			
			bb = BoundingBox.of(origin.copy());
			bb.add(Cardinal.UP, 6).add(dir, 5).grow(dir, 3).grow(Cardinal.left(dir), 4).grow(Cardinal.right(dir), 8);
			RectSolid.fill(editor, rand, bb, blocks.getWall(), Fill.ONLY_SOLID);
		}

		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(Cardinal.UP, 7).grow(Cardinal.directions, 4);
		RectSolid.fill(editor, rand, bb, blocks.getWall(), Fill.ONLY_SOLID);
	}

	private void floor(IWorldEditor editor, Random rand, Coord origin, IBlockSet blocks) {
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.grow(Cardinal.directions, 7);
		bb.add(Cardinal.DOWN);
		RectSolid.fill(editor, rand, bb, blocks.getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			bb = BoundingBox.of(origin.copy());
			bb.add(dir, 8).add(Cardinal.DOWN);
			bb.grow(Cardinal.orthogonal(dir), 8).grow(dir);
			RectSolid.fill(editor, rand, bb, blocks.getWall());
			
			bb = BoundingBox.of(origin.copy());
			bb.add(dir, 2).add(Cardinal.left(dir), 2).add(Cardinal.DOWN);
			bb.grow(dir, 2).grow(Cardinal.left(dir), 2);
			RectSolid.fill(editor, rand, bb, blocks.getWall());
		}
	}

	private void pillars(IWorldEditor editor, Random rand, Coord origin, IBlockSet blocks) {
		IStair stair = blocks.getStair();
		
		// central pillars
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(dir, 3).add(Cardinal.left(dir), 3);
			bb.grow(Cardinal.UP, 7);
			RectSolid.fill(editor, rand, bb, blocks.getPillar());
			
			Coord pos = origin.copy();
			pos.add(dir, 3).add(Cardinal.left(dir), 3).add(Cardinal.UP, 3);
			for(Cardinal d : Cardinal.directions) {
				Coord p = pos.copy();
				p.add(d);
				stair.setOrientation(d, true).set(editor, rand, p);
				p.add(Cardinal.UP);
				blocks.getWall().set(editor, rand, p);
			}
		}
		
		// outer wall pillars
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(dir, 8).add(Cardinal.left(dir), 8);
			bb.grow(Cardinal.UP, 6);
			RectSolid.fill(editor, rand, bb, blocks.getPillar());
			
			Coord pos = origin.copy();
			pos.add(dir, 8).add(Cardinal.left(dir), 8).add(Cardinal.UP, 4);
			for(Cardinal d : Cardinal.directions) {
				Coord p = pos.copy().add(d);
				stair.setOrientation(d, true).set(editor, rand, p, true, false);
			}
			
			for(Cardinal orth : Cardinal.orthogonal(dir)) {
				for(Cardinal o : Cardinal.orthogonal(dir)) {
					bb = BoundingBox.of(origin.copy());
					bb.add(dir, 8).add(orth, 3);
					bb.add(o);
					bb.grow(Cardinal.UP, 6);
					RectSolid.fill(editor, rand, bb, blocks.getPillar());
					
					pos = origin.copy();
					pos.add(dir, 8).add(orth, 3).add(o).add(Cardinal.UP, 4);
					stair.setOrientation(o, true).set(editor, rand, pos.copy().add(o));
					stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos.copy().add(Cardinal.reverse(dir)));
				}
			}
		}
		
		
	}

	private void walls(IWorldEditor editor, Random rand, Coord origin, IBlockSet blocks) {
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin.copy());
			bb.add(dir, 9).grow(Cardinal.orthogonal(dir), 9).grow(Cardinal.UP, 5).grow(Cardinal.DOWN);
			RectSolid.fill(editor, rand, bb, blocks.getWall(), Fill.ONLY_SOLID);
		}
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin, IBlockSet blocks) {

		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.grow(Cardinal.directions, 8);
		bb.grow(Cardinal.UP, 5);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin.copy());
		bb.grow(Cardinal.directions, 4);
		bb.add(Cardinal.UP, 6);
		RectSolid.fill(editor, rand, bb, Air.get());
	}

	private class EnderTheme extends ThemeBase implements ITheme{

		public EnderTheme() {
			this.primary = new EnderBlocks();
			this.secondary = this.primary;
		}
		
		@Override
		public String getName() {
			return "Ender";
		}
	}
	
	@Override
	public String getName() {
		return Room.ENDER.name();
	}

}
