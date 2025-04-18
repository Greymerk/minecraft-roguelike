package com.greymerk.roguelike.dungeon.fragment.alcove;

import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.factories.BlockJumble;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class SilverfishNest implements IFragment {

	@Override
	public void generate(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).add(dir, 3).grow(Cardinal.orthogonal(dir)).grow(Cardinal.DOWN).grow(Cardinal.UP, 2).fill(editor, rand, settings.getTheme().getPrimary().getWall());
		nest(editor, rand, settings, origin.copy().add(dir, 6).freeze());
		BoundingBox.of(origin).add(Cardinal.UP).add(dir, 2).grow(dir, 2).fill(editor, rand, Air.get());
	}

	private void nest(IWorldEditor editor, Random rand, ILevelSettings settings, Coord origin) {
		BlockJumble nest = new BlockJumble()
		  .add(MetaBlock.of(Blocks.MOSSY_COBBLESTONE))
		  .add(MetaBlock.of(Blocks.COBBLESTONE))
		  .add(MetaBlock.of(Blocks.MOSSY_STONE_BRICKS));
		
		BoundingBox.of(origin).grow(Cardinal.directions, 2).grow(Cardinal.DOWN).grow(Cardinal.UP, 3).fill(editor, rand, nest);
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.UP).add(dir).grow(Cardinal.left(dir)).fill(editor, rand, Air.get());
		});
		
		MetaBlock.of(Blocks.WATER).set(editor, origin.copy().add(Cardinal.UP, 2));
		Spawner.generate(editor, rand, settings.getDifficulty(), origin, Spawner.SILVERFISH);
		
		CellSupport.generate(editor, rand, settings.getTheme(), origin);
	}
}
