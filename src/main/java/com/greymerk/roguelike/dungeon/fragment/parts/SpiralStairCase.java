package com.greymerk.roguelike.dungeon.fragment.parts;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.Line;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class SpiralStairCase implements IFragment {

	Line stairWell;
	
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
		MetaBlock air = BlockType.get(BlockType.AIR);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(new Coord(-1, 0, -1));
		end = new Coord(origin);
		end.add(new Coord(1, 0, 1));
		
		RectSolid.fill(editor, rand, start, end, air);
		fill.set(editor, rand, origin);
		
		cursor = new Coord(origin);
		cursor.add(dir);
		stair.setOrientation(Cardinal.left(dir), false).set(editor, cursor);
		cursor.add(Cardinal.right(dir));
		stair.setOrientation(Cardinal.right(dir), true).set(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).set(editor, cursor);
	}

}
