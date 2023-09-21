package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.shapes.RectHollow;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class Corridor extends AbstractRoom implements IRoom{


	@Override
	public void generate(IWorldEditor editor) {
		
		Random rand = editor.getRandom(worldPos);
		IBlockFactory blocks = theme.getPrimary().getWall();
		IStair stairs = theme.getPrimary().getStair();
		
		Coord start = worldPos.copy();
		start.add(new Coord(-3, -1, -3));
		Coord end = worldPos.copy();
		end.add(new Coord(3, 4, 3));
		
		RectHollow.fill(editor, rand, start, end, blocks, false, true);
		
		for(Cardinal dir : Cardinal.directions) {
			start = worldPos.copy();
			start.add(dir, 2);
			start.add(Cardinal.left(dir), 2);
			end = start.copy();
			start.add(Cardinal.DOWN);
			end.add(Cardinal.UP, 3);
			RectSolid.fill(editor, rand, start, end, theme.getSecondary().getPillar());
			start = end.copy();
			end.add(Cardinal.right(dir), 3);
			RectSolid.fill(editor, rand, start, end, blocks);
			
			for(Cardinal orth : Cardinal.orthogonal(dir)) {
				Coord pos = worldPos.copy();
				pos.add(dir, 2);
				pos.add(orth);
				pos.add(Cardinal.UP, 2);
				stairs.setOrientation(Cardinal.reverse(orth), true).set(editor, pos);
			}
			
			Fragment.generate(Fragment.CELL_SUPPORT, editor, rand, theme, worldPos.copy());
			
			
		}
		
		start = new Coord(worldPos);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(Cardinal.NORTH);
		start.add(Cardinal.WEST);
		end.add(Cardinal.SOUTH);
		end.add(Cardinal.EAST);
		RectSolid.fill(editor, rand, start, end, theme.getPrimary().getFloor());
		
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = worldPos.copy();
			pos.add(Cardinal.DOWN);
			pos.add(dir, 2);
			this.crossBar(editor, rand, theme, pos, dir);
		}
		
		for(Cardinal dir : Cardinal.directions) {
			if(this.getEntrance(dir) == Entrance.DOOR) {
				Fragment.generate(Fragment.ARCH, editor, rand, theme, worldPos.copy(), dir);
			} else if(this.getEntrance(dir) == Entrance.ALCOVE){
				IFragment alcove = this.settings.getAlcove(rand);
				alcove.generate(editor, rand, theme, worldPos.copy(), dir);
			} else {
				IFragment wall = this.settings.getWallFragment(rand);
				wall.generate(editor, rand, theme, worldPos.copy(), dir);
			}
		}
	}
	
	private void crossBar(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		IBlockFactory wall = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		wall.set(editor, rand, origin, true, true);
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = origin.copy();
			pos.add(o);
			wall.set(editor, rand, pos, true, true);
			pos.add(Cardinal.DOWN);
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, pos, true, false);
		}
	}

	@Override
	public String getName() {
		return Room.CORRIDOR.name();
	}

}
