package com.greymerk.roguelike.dungeon.fragment.parts;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class CellSupportBeamFragment implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal d) {
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir, 2);
			pos.add(Cardinal.left(dir), 2);
			pos.add(Cardinal.DOWN, 2);
			while(!editor.isSolid(pos)) {
				theme.getPrimary().getWall().set(editor, rand, pos);
				if(Math.floorMod(pos.getY(), 10) == 9
					|| Math.floorMod(pos.getY(), 10) == 3) {
					Coord p = pos.copy();
					p.add(Cardinal.right(dir), 2);
					this.crossBar(editor, rand, theme, p, dir);
					
				}
				pos.add(Cardinal.DOWN);
			}
		}
	}
	
	private void crossBar(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		wall.set(editor, rand, origin);
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = origin.copy();
			pos.add(o);
			wall.set(editor, rand, pos);
			pos.add(Cardinal.DOWN);
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, pos);
		}
		
		if(rand.nextInt(10) == 0) {
			Coord pos = origin.copy();
			pos.add(Cardinal.DOWN);
			Lantern type = pos.getY() < 0 ? Lantern.SOUL : Lantern.FLAME;
			Lantern.set(editor, pos, type, true);
		}
	}
}
