package com.greymerk.roguelike.dungeon.fragment.parts;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public class ArchWay implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		BoundingBox bb = BoundingBox.of(origin.copy());
		bb.add(dir, 3).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, bb, BlockType.get(BlockType.AIR));
		
		bb = BoundingBox.of(origin.copy());
		bb.add(dir, 3).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 2);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getFloor());
		
		bb = BoundingBox.of(origin.copy());
		bb.add(dir, 3).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 2);
		RectSolid.fill(editor, rand, bb, theme.getPrimary().getWall(), false, true);
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			IStair stair = theme.getPrimary().getStair();
			
			Coord pos = origin.copy().add(dir, 3).add(Cardinal.UP, 2).add(o, 2);
			stair.setOrientation(Cardinal.reverse(o), true).set(editor, pos);
			
			pos.add(Cardinal.UP);
			theme.getPrimary().getWall().set(editor, rand, pos);
			
			pos.add(Cardinal.reverse(o));
			stair.set(editor, pos);
		}
	}
}
