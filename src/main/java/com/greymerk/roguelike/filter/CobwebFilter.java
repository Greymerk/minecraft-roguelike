package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.dungeon.fragment.parts.SpiderNest;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.util.math.random.Random;

public class CobwebFilter implements IFilter{

	@Override
	public void apply(IWorldEditor editor, Random rand, ILevelSettings settings, IBounded box) {
		box.forEach(pos -> {
			if(!editor.isAir(pos)) return;
			if(rand.nextInt(100) != 0) return;
			if(editor.isSolid(pos.copy().add(Cardinal.UP))) {
				placeWeb(editor, rand, pos, rand.nextInt(2) + 2);
			} else {
				if(rand.nextInt(100) == 0) SpiderNest.generate(editor, rand, settings, pos);
			}
		});
	}
	
	private void placeWeb(IWorldEditor editor, Random rand, Coord pos, int count){
		if(!editor.isAir(pos)) return;
		if(count <= 0) return;
		
		BlockType.get(BlockType.WEB).set(editor, pos);
		
		for(int i = 0; i < 2; ++i){
			Cardinal dir = Cardinal.values()[rand.nextInt(Cardinal.values().length)];
			Coord cursor = pos.copy();
			cursor.add(dir);
			placeWeb(editor, rand, cursor, count - 1);
		}
	}
}
