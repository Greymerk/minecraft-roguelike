package com.greymerk.roguelike.dungeon.fragment.parts;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.Column;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class CellSupport implements IFragment {

	public static void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {
		CellSupport.generate(editor, rand, theme, origin, Cardinal.NORTH);
	}
	
	public static void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal d) {
		IBlockFactory wall = theme.getPrimary().getWall();
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 2);
			pos.add(Cardinal.left(dir), 2);
			pos.add(Cardinal.DOWN, 2);
			Column.fillDown(editor, rand, wall, pos.copy());
		}
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 2);
			pos.add(Cardinal.DOWN, 2);
			for(Coord p : new Column(pos).getUntilSolid(editor)) {
				if(Math.floorMod(p.getY(), 10) == 9	|| Math.floorMod(p.getY(), 10) == 3) {
					CellSupport.crossBar(editor, rand, theme, p, dir);
				}
			}
		}
	}
	
	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal d) {
		CellSupport.generate(editor, rand, settings.getTheme(), origin, d);
	}
	
	public static void crossBar(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		// to avoid having ends hanging in mid air
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = origin.copy().add(o, 2);
			if(!editor.isSolid(pos)) {
				return;
			}
		}
		
		wall.set(editor, rand, origin, Fill.AIR);
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = origin.copy().add(o);
			wall.set(editor, rand, pos, Fill.AIR);
			pos.add(Cardinal.DOWN);
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, rand, pos, Fill.AIR);
		}
		
		if(rand.nextInt(10) == 0) {
			Coord pos = origin.copy().add(Cardinal.DOWN);
			Lantern.set(editor, pos, theme, true);
		}
	}
}
