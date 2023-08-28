package com.greymerk.roguelike.editor.filter;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.editor.shapes.RectWireframe;
import com.greymerk.roguelike.editor.theme.ITheme;

public class WireframeFilter implements IFilter{

	@Override
	public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box) {
		Coord start = box.getStart();
		Coord end = box.getEnd();
		
		start.add(Cardinal.UP, 200);
		end.add(Cardinal.UP, 200);
		
		IShape shape = new RectWireframe(start, end);
		IBlockFactory block = BlockType.get(BlockType.SEA_LANTERN);
		
		shape.fill(editor, rand, block);
	}
}
