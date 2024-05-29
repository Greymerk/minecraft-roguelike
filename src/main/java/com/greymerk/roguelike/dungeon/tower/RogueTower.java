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
import com.greymerk.roguelike.editor.shapes.Line;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;


public class RogueTower implements ITower{

	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon){
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		Coord origin = Tower.getBaseCoord(editor, dungeon).freeze();
		
		//floors and roof
		BoundingBox.of(origin).grow(Cardinal.directions, 3).grow(Cardinal.UP, 9).fill(editor, rand, Air.get());
		
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 3).fill(editor, rand, walls);
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 3).add(Cardinal.UP, 5).fill(editor, rand, walls);
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 4).add(Cardinal.UP, 10).fill(editor, rand, walls);
		
		for(Cardinal dir : Cardinal.directions) {
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 4).grow(Cardinal.UP, 9).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 3).add(Cardinal.left(dir), 3).grow(Cardinal.UP, 9).grow(Cardinal.DOWN).fill(editor, rand, theme.getPrimary().getPillar());
			BoundingBox.of(origin).add(dir, 3).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 3).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 3).add(Cardinal.UP, 5).fill(editor, rand, walls);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 3).add(Cardinal.UP, 2).add(o, 2));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 3).add(Cardinal.UP, 2).add(o, 2).add(Cardinal.UP, 5));
			}
			
			BoundingBox.of(origin).add(Cardinal.UP, 9).add(dir, 5).grow(Cardinal.orthogonal(dir), 2).fill(editor, rand, walls);
			walls.set(editor, rand, origin.copy().add(Cardinal.UP, 10).add(dir, 6));
			walls.set(editor, rand, origin.copy().add(Cardinal.UP, 10).add(dir, 4).add(Cardinal.left(dir), 4));
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				walls.set(editor, rand, origin.copy().add(Cardinal.UP, 9).add(dir, 5).add(o, 2).add(Cardinal.UP));
				walls.set(editor, rand, origin.copy().add(Cardinal.UP, 9).add(dir, 5).add(o, 2).add(Cardinal.UP).add(o));
				this.addCrenellation(editor, rand, origin.copy().add(Cardinal.UP, 9).add(dir, 5).add(o, 2).add(Cardinal.UP).add(o).add(Cardinal.UP), walls);
				walls.set(editor, rand, origin.copy().add(Cardinal.UP, 9).add(dir, 6).add(o).add(Cardinal.UP));
				this.addCrenellation(editor, rand, origin.copy().add(Cardinal.UP, 9).add(dir, 6).add(o).add(Cardinal.UP).add(Cardinal.UP), walls);
			}
			
			// ground level doorways
			walls.set(editor, rand, origin.copy().add(Cardinal.UP, 2).add(dir, 5));
			RectSolid.fill(editor, rand, BoundingBox.of(origin).add(dir, 5).grow(Cardinal.orthogonal(dir)).add(Cardinal.DOWN), walls);
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				walls.set(editor, rand, origin.copy().add(dir, 5).add(o));
				walls.set(editor, rand, origin.copy().add(dir, 5).add(o).add(Cardinal.UP));
				stair.setOrientation(o, false).set(editor, rand, origin.copy().add(dir, 5).add(o).add(Cardinal.UP).add(Cardinal.UP));
			}
			
			// upper level patios
			stair.setOrientation(dir, true).set(editor, rand, origin.copy().add(Cardinal.UP, 4).add(dir, 5));
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				stair.setOrientation(o, true).set(editor, rand, origin.copy().add(Cardinal.UP, 4).add(dir, 5).add(o));
			}
			
			// wallbrow
			stair.setOrientation(dir, true).set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 7));
			walls.set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 7).add(Cardinal.UP));
			stair.setOrientation(dir, true).set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 7).add(Cardinal.UP).add(dir));
			walls.set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 7).add(Cardinal.UP, 2).add(dir));
			walls.set(editor, rand, origin.copy().add(dir, 4).add(Cardinal.UP, 7).add(Cardinal.UP, 2).add(dir));
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				stair.setOrientation(o, true).set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 7).add(o));
				walls.set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 8).add(o));
				stair.setOrientation(o, true).set(editor, rand, origin.copy().add(dir, 6).add(Cardinal.UP, 8).add(o));
				walls.set(editor, rand, origin.copy().add(dir, 6).add(Cardinal.UP, 9).add(o));
				walls.set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 9).add(o));
				
				
				stair.setOrientation(o, true).set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 8).add(o, 2));
				walls.set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 9).add(o, 2));
				stair.setOrientation(o, true).set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 9).add(o, 3));
			}
			
			// doors
			BoundingBox.of(origin).add(dir, 4).grow(Cardinal.UP).fill(editor, rand, Air.get());
			BoundingBox.of(origin).add(dir, 4).grow(Cardinal.UP).add(Cardinal.UP, 5).fill(editor, rand, Air.get());
			
			//windows
			Cardinal.orthogonal(dir).forEach(o -> {
				IronBar.get().set(editor, origin.copy().add(Cardinal.UP, 6).add(dir, 4).add(o, 2));
			});
			
			//corner bevels
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				BoundingBox.of(origin).add(dir, 4).add(o, 3).grow(Cardinal.UP, 3).fill(editor, rand, Air.get());
				stair.setOrientation(o, true).set(editor, rand, origin.copy().add(dir, 4).add(o, 3).add(Cardinal.UP, 4));
			}
			
			//beard
			BoundingBox.of(origin.copy().add(Cardinal.DOWN).add(dir, 3).add(Cardinal.left(dir), 3),
				dungeon.copy().add(dir, 3).add(Cardinal.left(dir), 3))
				.fill(editor, rand, walls, true, false);
				
			BoundingBox.of(origin.copy().add(Cardinal.DOWN),
				dungeon.copy().add(Cardinal.UP, 4)).add(dir, 4).grow(Cardinal.orthogonal(dir), 3)
				.fill(editor, rand, walls, true, false);
		}
		
		//stairway
		BoundingBox.of(origin.copy().add(Cardinal.DOWN),
			dungeon.copy().add(Cardinal.UP, 4)).grow(Cardinal.directions, 2)
			.getShape(Shape.RECTHOLLOW).fill(editor, rand, walls, false, true);
		SpiralStairCase.generate(editor, rand, theme, Line.of(origin.copy().add(Cardinal.UP, 4), dungeon));
	}
	
	private void addCrenellation(IWorldEditor editor, Random rand, Coord origin, IBlockFactory blocks){
		blocks.set(editor, rand, origin.copy());
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, origin.copy().add(Cardinal.UP));
	}
}
