package com.greymerk.roguelike.dungeon.fragment.parts;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.Line;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class SpiralStairCase implements IFragment {

	Line stairWell;
	
	public static void generate(IWorldEditor editor, Random rand, ITheme theme, Line line) {
		line.forEach(pos -> {
			spiralStairStep(editor, rand, pos, Cardinal.directions.get(pos.getY() % 4), theme);
		});
	}
	
	public SpiralStairCase(Coord start, Coord end) {
		this.stairWell = new Line(start, end);
	}
	
	public void generate(IWorldEditor editor, Random rand, ITheme theme) {
		for(Coord pos : stairWell) {
			Cardinal dir = Cardinal.directions.get(pos.getY() % 4);
			this.generate(editor, rand, theme, pos, dir);
		}		
	}
	
	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		spiralStairStep(editor, rand, origin, dir, theme);
	}
	
	public static void spiralStairStep(IWorldEditor editor, Random rand, Coord origin, Cardinal dir, ITheme theme){
		
		IBlockFactory fill = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		MetaBlock air = Air.get();
		
		BoundingBox bb = BoundingBox.of(origin);
		bb.grow(Cardinal.directions);
		
		RectSolid.fill(editor, rand, bb, air);
		fill.set(editor, rand, origin);
		
		Coord pos = origin.copy().add(dir);
		stair.setOrientation(Cardinal.left(dir), false).set(editor, rand, pos);
		pos.add(Cardinal.right(dir));
		stair.setOrientation(Cardinal.right(dir), true).set(editor, rand, pos);
		pos.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, pos);
	}

}
