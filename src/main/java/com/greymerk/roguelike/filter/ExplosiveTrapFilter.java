package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class ExplosiveTrapFilter implements IFilter {

	@Override
	public void apply(IWorldEditor editor, Random rand, ILevelSettings settings, IBounded box) {
		box.forEach(pos -> {
			if(rand.nextInt(200) == 0) addTrap(editor, rand, pos);
		});
	}
	
	private void addTrap(IWorldEditor editor, Random rand, Coord pos) {
		if(!editor.isAir(pos)) return;
		if(!editor.isFaceFullSquare(pos.copy().add(Cardinal.DOWN), Cardinal.UP)) return;
		if(!isEncased(editor, pos.copy().add(Cardinal.DOWN, 2))) return;
		if(editor.hasBlockEntity(pos.copy().add(Cardinal.DOWN, 2))) return;
		MetaBlock.of(Blocks.STONE_PRESSURE_PLATE).set(editor, pos);
		if(rand.nextBoolean()) MetaBlock.of(Blocks.TNT).set(editor, pos.copy().add(Cardinal.DOWN, 2));
	}
	
	private boolean isEncased(IWorldEditor editor, Coord pos) {
		for (Cardinal dir : Cardinal.all){
			if(!editor.isSolid(pos.copy().add(dir))) return false;
		}
		return true;
	}

}
