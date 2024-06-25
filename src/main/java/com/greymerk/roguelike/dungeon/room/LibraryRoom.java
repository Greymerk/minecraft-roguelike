package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.ColorBlock;
import com.greymerk.roguelike.editor.blocks.Terracotta;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.factories.BlockCheckers;
import com.greymerk.roguelike.editor.factories.BlockFloor;
import com.greymerk.roguelike.util.Color;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.util.math.random.Random;

public class LibraryRoom extends AbstractLargeRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos().add(this.direction, Cell.SIZE * 2).freeze();
		Random rand = editor.getRandom(origin);
		this.clear(editor, rand, origin);
		this.entries(editor, rand, origin);
		this.floor(editor, rand, origin);
	}

	private void entries(IWorldEditor editor, Random rand, Coord origin) {
		this.getEntrancesFromType(Entrance.DOOR).forEach(dir -> {
			Fragment.generate(Fragment.ARCH, editor, rand, theme, origin.add(dir, 12),  dir);
		});
	}

	private void floor(IWorldEditor editor, Random rand, Coord origin) {
		List<Color> colors = RandHelper.pickCountFrom(List.of(Color.values()), rand, 4);
		BlockCheckers tiles = new BlockCheckers(
				ColorBlock.get(ColorBlock.CLAY, colors.get(0)),
				ColorBlock.get(ColorBlock.CLAY, colors.get(1)));
		BlockFloor tileFloor = new BlockFloor(tiles);
		
		BlockFloor trim = new BlockFloor(ColorBlock.get(ColorBlock.CLAY, colors.get(2)));
		
		BlockFloor glazed = new BlockFloor(Terracotta.getGlazed(colors.get(3), Cardinal.NORTH));
		
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 14).grow(dir).grow(Cardinal.orthogonal(dir), 15).fill(editor, rand, theme.getPrimary().getFloor());
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 11).grow(dir, 2).grow(Cardinal.right(dir), 10).grow(Cardinal.left(dir), 13).fill(editor, rand, tileFloor);
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 2).grow(dir, 8).grow(Cardinal.orthogonal(dir)).fill(editor, rand, tileFloor);
			BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions).fill(editor, rand, tileFloor);
			
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 8).add(Cardinal.left(dir), 2).grow(dir, 2).grow(Cardinal.left(dir), 8).fill(editor, rand, theme.getPrimary().getFloor());
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 8).add(Cardinal.right(dir), 2).grow(dir, 2).grow(Cardinal.right(dir), 5).fill(editor, rand, theme.getPrimary().getFloor());
			
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.left(dir), 5).fill(editor, rand, trim);
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 7).add(Cardinal.left(dir), 2).grow(Cardinal.left(dir), 5).fill(editor, rand, trim);
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 3).add(Cardinal.left(dir), 2).grow(dir, 3).fill(editor, rand, trim);
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 3).add(Cardinal.left(dir), 7).grow(dir, 3).fill(editor, rand, trim);
			
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 3).add(Cardinal.left(dir), 3).grow(dir, 3).grow(Cardinal.left(dir), 3).fill(editor, rand, glazed);
		});
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 14).grow(Cardinal.UP, 6).fill(editor, rand, Air.get());;
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 15).grow(Cardinal.orthogonal(dir), 15).grow(Cardinal.UP, 5).fill(editor, rand, theme.getPrimary().getWall(), false, true);
		});
	}

	@Override
	public String getName() {
		return Room.LIBRARY.name();
	}

}
