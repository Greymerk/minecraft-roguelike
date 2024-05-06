package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.filter.DecoratedPotFilter;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.util.math.random.Random;

public class CrossRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos();
		Random rand = editor.getRandom(origin);
		IBlockFactory wall = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox bb = BoundingBox.of(origin);
		bb.grow(Cardinal.directions, 9).grow(Cardinal.DOWN).grow(Cardinal.UP, 4);
		RectHollow.fill(editor, rand, bb, wall, false, true);
		
		bb = BoundingBox.of(origin);
		bb.grow(Cardinal.directions, 9).add(Cardinal.DOWN);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 8).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 3).grow(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, bb, wall);
			
			for(Cardinal orth : Cardinal.orthogonal(dir)) {
				bb = BoundingBox.of(origin);
				bb.add(dir, 8).add(orth, 2).grow(Cardinal.UP, 3);
				RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
				
				Coord pos = origin.copy().add(dir, 8).add(orth, 2).add(Cardinal.UP, 2);
				for(Cardinal d : Cardinal.directions) {
					Coord p = pos.copy().add(d);
					stair.setOrientation(d, true).set(editor, p, true, false);
					p.add(Cardinal.UP);
					wall.set(editor, rand, p, true, false);
				}
				
				pos.add(Cardinal.UP).add(Cardinal.reverse(dir));
				for(Cardinal d : Cardinal.directions) {
					Coord p = pos.copy().add(d);
					stair.setOrientation(d, true).set(editor, p, true, false);
				}
			}
			
			Coord pos = origin.copy().add(dir, 6);
			if(this.getEntrance(dir) == Entrance.DOOR) {
				Fragment.generate(Fragment.ARCH, editor, rand, theme, pos, dir);
			}
			
			pos.add(Cardinal.left(dir), 6);
			cornerCell(editor, rand, pos, dir);
		}
		
		bb = BoundingBox.of(origin);
		bb.grow(Cardinal.directions, 8);
		new DecoratedPotFilter().apply(editor, rand, theme, bb);
	}

	private void cornerCell(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IBlockFactory pillar = theme.getPrimary().getPillar();
		IStair stair = theme.getPrimary().getStair();
		
		Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, origin.copy());
		
		for(Cardinal d : Cardinal.directions) {
			BoundingBox bb = BoundingBox.of(origin);
			bb.add(d, 2).add(Cardinal.left(d), 2).grow(Cardinal.UP);
			RectSolid.fill(editor, rand, bb, pillar);
			bb.add(Cardinal.UP, 2);
			RectSolid.fill(editor, rand, bb, wall);
			
			bb = BoundingBox.of(origin);
			bb.add(d, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(d));
			RectSolid.fill(editor, rand, bb, wall);
			
			for(Cardinal orth : Cardinal.orthogonal(d)) {
				Coord pos = origin.copy().add(d, 2).add(Cardinal.UP, 2).add(orth);
				stair.setOrientation(Cardinal.reverse(orth), true).set(editor, pos);
			}
		}
		
		this.settings.getWallFragment(rand).generate(editor, rand, theme, origin, dir);
		this.settings.getWallFragment(rand).generate(editor, rand, theme, origin, Cardinal.left(dir));
		
		if(rand.nextInt() == 0) Spawner.generate(editor, rand, origin.copy());		
	}
	
	@Override
	public String getName() {
		return Room.CROSS.name();
	}

}
