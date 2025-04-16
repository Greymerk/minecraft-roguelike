package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

import net.minecraft.util.math.random.Random;

public class Corridor extends AbstractRoom implements IRoom{


	@Override
	public void generate(IWorldEditor editor) {
		
		Random rand = editor.getRandom(worldPos);
		Coord origin = this.getWorldPos();
		clear(editor, rand, origin);
		cell(editor, rand, origin);
		CellSupport.generate(editor, rand, theme, origin);
		this.generateExits(editor, rand);
	}
	
	private void cell(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory blocks = theme.getPrimary().getWall();
		BoundingBox.of(origin).add(Cardinal.UP, 4).grow(Cardinal.directions).fill(editor, rand, blocks, Fill.SOLID);
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir), 2).fill(editor, rand, blocks);
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir), 2).fill(editor, rand, blocks);
			Pillar.generate(editor, rand, origin.copy().add(dir, 2).add(Cardinal.left(dir), 2), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
			blocks.set(editor, rand, origin.copy().add(dir, 2).add(Cardinal.left(dir), 2).add(Cardinal.UP, 4));
		});
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions).fill(editor, rand, theme.getPrimary().getFloor());
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 2).grow(Cardinal.UP, 3).fill(editor, rand, Air.get());
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 3).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.UP, 3)
				.fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		});
	}

	@Override
	public String getName() {
		return Room.CORRIDOR.name();
	}
}
