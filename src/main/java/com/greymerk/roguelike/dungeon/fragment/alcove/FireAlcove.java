package com.greymerk.roguelike.dungeon.fragment.alcove;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.IronBar;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class FireAlcove implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		ITheme theme = settings.getTheme();
		BoundingBox.of(origin).add(dir, 3)
			.grow(dir, 3).grow(Cardinal.orthogonal(dir)).grow(Cardinal.UP, 3).grow(Cardinal.DOWN)
			.fill(editor, rand, theme.getPrimary().getWall());
		theme.getPrimary().getSlab().upsideDown(false).set(editor, origin.copy().add(dir, 3));
		IronBar.get().set(editor, rand, origin.copy().add(dir, 3).add(Cardinal.UP));
		theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 4).add(Cardinal.UP, 3));
		BoundingBox.of(origin).add(dir, 4).grow(Cardinal.UP, 2)
			.fill(editor, rand, Air.get());
		if(origin.getY() > 0) {
			MetaBlock.of(Blocks.NETHERRACK).set(editor, origin.copy().add(dir, 4).add(Cardinal.DOWN));
			MetaBlock.of(Blocks.FIRE).set(editor, origin.copy().add(dir, 4));	
		} else {
			MetaBlock.of(Blocks.SOUL_SOIL).set(editor, origin.copy().add(dir, 4).add(Cardinal.DOWN));
			MetaBlock.of(Blocks.SOUL_FIRE).set(editor, origin.copy().add(dir, 4));
		}
		
	}

}
