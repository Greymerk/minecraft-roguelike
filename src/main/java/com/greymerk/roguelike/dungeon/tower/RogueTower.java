package com.greymerk.roguelike.dungeon.tower;

import com.greymerk.roguelike.dungeon.fragment.parts.SpiralStairCase;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.IronBar;
import com.greymerk.roguelike.editor.blocks.Torch;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.util.math.random.Random;


public class RogueTower implements ITower{

	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon){
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		Coord origin = Tower.getBaseCoord(editor, dungeon);
		
		//floors and roof
		BoundingBox bb = BoundingBox.of(origin);
		bb.grow(Cardinal.directions, 3).grow(Cardinal.UP, 9);
		RectSolid.fill(editor, rand, bb, Air.get());
		
		bb = BoundingBox.of(origin);
		bb.add(Cardinal.DOWN).grow(Cardinal.directions, 3);
		RectSolid.fill(editor, rand, bb, walls);
		bb.add(Cardinal.UP, 5);
		RectSolid.fill(editor, rand, bb, walls);
		bb.add(Cardinal.UP, 5).grow(Cardinal.directions);
		RectSolid.fill(editor, rand, bb, walls);
		
		for(Cardinal dir : Cardinal.directions) {
			bb = BoundingBox.of(origin).add(Cardinal.DOWN);
			bb.add(dir, 4).grow(Cardinal.UP, 9).grow(Cardinal.orthogonal(dir), 3);
			RectSolid.fill(editor, rand, bb, walls);
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 3).add(Cardinal.left(dir), 3).grow(Cardinal.UP, 9).grow(Cardinal.DOWN);
			RectSolid.fill(editor, rand, bb, theme.getPrimary().getPillar());
			
			bb = BoundingBox.of(origin);
			bb.add(dir, 3).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 3);
			RectSolid.fill(editor, rand, bb, walls);
			bb.add(Cardinal.UP, 5);
			RectSolid.fill(editor, rand, bb, walls);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				Coord pos = origin.copy();
				pos.add(dir, 3).add(Cardinal.UP, 2).add(o, 2);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
				pos.add(Cardinal.UP, 5);
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
			}
			
			bb = BoundingBox.of(origin);
			bb.add(Cardinal.UP, 9).add(dir, 5).grow(Cardinal.orthogonal(dir), 2);
			RectSolid.fill(editor, rand, bb, walls);
			
			Coord roof = origin.copy().add(Cardinal.UP, 9);
			Coord pos = roof.copy().add(dir, 6).add(Cardinal.UP);
			walls.set(editor, rand, pos);
			
			pos = roof.copy().add(dir, 4).add(Cardinal.left(dir), 4).add(Cardinal.UP);
			walls.set(editor, rand, pos);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				pos = roof.copy().add(dir, 5).add(o, 2).add(Cardinal.UP);
				walls.set(editor, rand, pos);
				pos.add(o);
				walls.set(editor, rand, pos);
				pos.add(Cardinal.UP);
				this.addCrenellation(editor, rand, pos, walls);
				
				pos = roof.copy().add(dir, 6).add(o).add(Cardinal.UP);
				walls.set(editor, rand, pos);
				pos.add(Cardinal.UP);
				this.addCrenellation(editor, rand, pos, walls);
			}
			
			// ground level doorways
			pos = origin.copy().add(Cardinal.UP, 2).add(dir, 5);
			walls.set(editor, rand, pos);
			bb = BoundingBox.of(origin);
			bb.add(dir, 5).grow(Cardinal.orthogonal(dir)).add(Cardinal.DOWN);
			RectSolid.fill(editor, rand, bb, walls);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				pos = origin.copy().add(dir, 5).add(o);
				walls.set(editor, rand, pos);
				pos.add(Cardinal.UP);
				walls.set(editor, rand, pos);
				pos.add(Cardinal.UP);
				stair.setOrientation(o, false).set(editor, pos);
			}
			
			// upper level patios
			pos = origin.copy().add(Cardinal.UP, 4).add(dir, 5);
			stair.setOrientation(dir, true).set(editor, pos);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				pos = origin.copy().add(Cardinal.UP, 4).add(dir, 5).add(o);
				stair.setOrientation(o, true).set(editor, pos);
			}
			
			// wallbrow
			pos = origin.copy().add(dir, 5).add(Cardinal.UP, 7);
			stair.setOrientation(dir, true).set(editor, pos);
			pos.add(Cardinal.UP);
			walls.set(editor, rand, pos);
			pos.add(dir);
			stair.setOrientation(dir, true).set(editor, pos);
			pos.add(Cardinal.UP);
			walls.set(editor, rand, pos);
			pos.add(Cardinal.reverse(dir));
			walls.set(editor, rand, pos);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				pos = origin.copy().add(dir, 5).add(Cardinal.UP, 7).add(o);
				stair.setOrientation(o, true).set(editor, pos);
				pos.add(Cardinal.UP);
				walls.set(editor, rand, pos);
				pos.add(dir);
				stair.setOrientation(o, true).set(editor, pos);
				pos.add(Cardinal.UP);
				walls.set(editor, rand, pos);
				pos.add(Cardinal.reverse(dir));
				walls.set(editor, rand, pos);
				
				pos = origin.copy().add(dir, 5).add(Cardinal.UP, 8).add(o, 2);
				stair.setOrientation(o, true).set(editor, pos);
				pos.add(Cardinal.UP);
				walls.set(editor, rand, pos);
				pos.add(o);
				stair.setOrientation(o, true).set(editor, pos);
			}
			
			// doors
			bb = BoundingBox.of(origin);
			bb.add(dir, 4).grow(Cardinal.UP);
			RectSolid.fill(editor, rand, bb, Air.get());
			bb.add(Cardinal.UP, 5);
			RectSolid.fill(editor, rand, bb, Air.get());
			
			//windows
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				pos = origin.copy().add(Cardinal.UP, 6).add(dir, 4).add(o, 2);
				IronBar.get().set(editor, pos);
			}
			
			//corner bevels
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				bb = BoundingBox.of(origin);
				bb.add(dir, 4).add(o, 3).grow(Cardinal.UP, 3);
				RectSolid.fill(editor, rand, bb, Air.get());
				pos = origin.copy().add(dir, 4).add(o, 3).add(Cardinal.UP, 4);
				stair.setOrientation(o, true).set(editor, pos);
			}
			
			//beard
			Coord top = origin.copy().add(Cardinal.DOWN).add(dir, 3).add(Cardinal.left(dir), 3);
			Coord bottom = dungeon.copy().add(dir, 3).add(Cardinal.left(dir), 3);
			RectSolid.fill(editor, rand, new BoundingBox(top, bottom), walls, true, false);
			bb = new BoundingBox(origin.copy().add(Cardinal.DOWN), dungeon.copy());
			bb.add(dir, 4).grow(Cardinal.orthogonal(dir), 3);
			RectSolid.fill(editor, rand, bb, walls, true, false);
		}
		
		//stairway
		bb = new BoundingBox(origin.copy().add(Cardinal.DOWN), dungeon.copy().add(Cardinal.UP, 4));
		bb.grow(Cardinal.directions, 2);
		RectHollow.fill(editor, rand, bb, walls, false, true);
		SpiralStairCase staircase = new SpiralStairCase(origin.copy().add(Cardinal.UP, 4), dungeon);
		staircase.generate(editor, rand, theme);
		
		
	}
	
	
	private void addCrenellation(IWorldEditor editor, Random rand, Coord origin, IBlockFactory blocks){
		blocks.set(editor, rand, origin.copy());
		
		if(editor.isAir(origin)) return;

		Coord pos = origin.copy().add(Cardinal.UP);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, pos);
	}
}
