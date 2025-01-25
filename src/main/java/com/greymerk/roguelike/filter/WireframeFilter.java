package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.shapes.IShape;
import com.greymerk.roguelike.editor.shapes.RectWireframe;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public class WireframeFilter implements IFilter{

	@Override
	public void apply(IWorldEditor editor, Random rand, ILevelSettings settings, IBounded box) {
		
		BoundingBox bb = box.getBoundingBox();
		bb.add(Cardinal.UP, 200);
		
		IShape shape = RectWireframe.of(bb);
		IBlockFactory block = BlockType.get(BlockType.SEA_LANTERN);
		
		shape.fill(editor, rand, block);
	}
}
