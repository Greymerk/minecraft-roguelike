package com.greymerk.roguelike.filter;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.shapes.Shape;
import com.greymerk.roguelike.theme.ITheme;

public class CobwebFilter implements IFilter{

	@Override
	public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box) {
		for(Coord pos : box.getShape(Shape.RECTSOLID)){
			if(rand.nextInt(100) != 0) continue;
			if(!editor.isAir(pos)) continue;
			if(!validLocation(editor, pos)) continue;
				
			generate(editor, rand, pos, rand.nextInt(2) + 2); 
		}
	}
	
	private boolean validLocation(IWorldEditor editor, Coord pos){
		return !editor.isAir(pos.copy().add(Cardinal.UP));
	}
	
	private void generate(IWorldEditor editor, Random rand, Coord pos, int count){
		if(!editor.isAir(pos)) return;
		if(count <= 0) return;
		
		BlockType.get(BlockType.WEB).set(editor, pos);
		
		for(int i = 0; i < 2; ++i){
			Cardinal dir = Cardinal.values()[rand.nextInt(Cardinal.values().length)];
			Coord cursor = pos.copy();
			cursor.add(dir);
			generate(editor, rand, cursor, count - 1);
		}
	}
}
