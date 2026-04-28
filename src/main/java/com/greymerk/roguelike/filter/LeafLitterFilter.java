package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.LeafLitter;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.settings.ILevelSettings;
import net.minecraft.util.RandomSource;

public class LeafLitterFilter implements IFilter {

	@Override
	public void apply(IWorldEditor editor, RandomSource rand, ILevelSettings settings, IBounded box) {
		box.forEach(c -> {
			if(rand.nextInt(30) == 0) {
				LeafLitter.set(editor, rand, c);
			}
		});
	}

}
