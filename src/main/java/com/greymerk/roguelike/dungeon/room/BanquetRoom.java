package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class BanquetRoom extends AbstractLargeRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos().copy().add(direction, Cell.SIZE * 2).freeze();
		Random rand = editor.getRandom(origin);

		clear(editor, rand, origin);
		ceiling(editor, rand, origin);
		pillars(editor, rand, origin);
		decorations(editor, rand, origin);
		tables(editor, rand, origin);
		supports(editor, rand, origin);
		
	}
	
	private void supports(IWorldEditor editor, Random rand, Coord origin) {
		CellSupport.generate(editor, rand, theme, origin);
		Cardinal.directions.forEach(dir -> {
			List.of(6, 12).forEach(i -> {
				CellSupport.generate(editor, rand, theme, origin.copy().add(dir, i));
				List.of(6, 12).forEach(j -> {
					CellSupport.generate(editor, rand, theme, origin.copy().add(dir, i).add(Cardinal.left(dir), j));
				});
			});
		});
	}

	private void tables(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.orthogonal(direction).forEach(o -> {
			table(editor, rand, origin.copy().add(o, 3).freeze(), o);
		});
	}

	private void table(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, theme.getSecondary().getWall());
		Cardinal.parallel(dir).forEach(p -> {
			theme.getSecondary().getStair().setOrientation(p, true).fill(editor, rand, BoundingBox.of(origin).add(p).grow(Cardinal.orthogonal(dir), 4));
			Cardinal.orthogonal(dir).forEach(o -> {
				theme.getSecondary().getStair().setOrientation(Cardinal.reverse(p), false).set(editor, rand, origin.copy().add(p, 2).add(o));
				theme.getSecondary().getStair().setOrientation(Cardinal.reverse(p), false).set(editor, rand, origin.copy().add(p, 2).add(o, 3));
			});
			BoundingBox.of(origin).add(Cardinal.UP).add(p).grow(Cardinal.orthogonal(dir), 4).forEach(pos -> {
				if(rand.nextInt(3) == 0) MetaBlock.of(Blocks.FLOWER_POT).set(editor, pos);
			});
		});
		Cardinal.orthogonal(dir).forEach(o -> {
			theme.getSecondary().getStair().setOrientation(o, true).set(editor, rand, origin.copy().add(o, 4));
			theme.getSecondary().getStair().setOrientation(Cardinal.reverse(o), false).set(editor, rand, origin.copy().add(o, 5));
		});
		BoundingBox.of(origin).add(Cardinal.UP).grow(Cardinal.orthogonal(dir), 3).forEach(pos -> {
			if(rand.nextBoolean()) Candle.generate(editor, rand, pos);
		});
	}

	private void decorations(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			Cardinal.orthogonal(dir).forEach(o -> {
				List.of(6, 12).forEach(step -> {
					settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(dir, 12).add(o, step), dir);
				});
			});
		});
	}

	private void pillars(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			Cardinal.orthogonal(dir).forEach(o -> {
				List.of(3, 9).forEach(step -> {
					pillarSet(editor, rand, origin.copy().add(dir, 9).add(o, step).freeze(), dir);
				});
			});
			Pillar.generate(editor, rand, origin.copy().add(dir, 14).add(Cardinal.left(dir), 14), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
		});
		
	}

	private void pillarSet(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		Pillar.generate(editor, rand, origin, theme, 3);
		theme.getPrimary().getWall().set(editor, rand, origin.copy().add(Cardinal.UP, 5));
		theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 5).add(Cardinal.UP, 2));
		Cardinal.orthogonal(dir).forEach(o -> {
			Pillar.generate(editor, rand, origin.copy().add(o).add(dir, 5), theme, 2, List.of(o, dir, Cardinal.reverse(dir)));
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(o).add(dir, 3).add(Cardinal.UP, 3));
			theme.getPrimary().getWall().set(editor, rand, origin.copy().add(o).add(dir, 4).add(Cardinal.UP, 3));
		});
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			List.of(8, 10, 14).forEach(step -> {
				BoundingBox.of(origin).add(Cardinal.UP, 4).add(dir, step).grow(Cardinal.orthogonal(dir), 15).fill(editor, rand, theme.getPrimary().getWall());
			});
			List.of(2, 4, 8).forEach(step -> {
				BoundingBox.of(origin).add(Cardinal.UP, 5).add(dir, step).grow(Cardinal.orthogonal(dir), 15).fill(editor, rand, theme.getPrimary().getWall());
			});
			List.of(2, 4).forEach(step -> {
				Cardinal.orthogonal(dir).forEach(o -> {
					BoundingBox.of(origin).add(dir, 9).add(o, step).add(Cardinal.UP, 4).grow(dir, 4).fill(editor, rand, theme.getPrimary().getWall());
				});
			});
			BoundingBox.of(origin).add(Cardinal.UP, 5).add(dir, 9).grow(dir, 5).grow(Cardinal.left(dir), 14).grow(Cardinal.right(dir), 8).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
			BoundingBox.of(origin).add(Cardinal.UP, 3).add(dir, 14).grow(Cardinal.UP).grow(Cardinal.orthogonal(dir), 14).fill(editor, rand, theme.getPrimary().getWall());
		});
		BoundingBox.of(origin).add(Cardinal.UP, 6).grow(Cardinal.directions, 8).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 14).grow(Cardinal.UP, 4).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.UP, 5).grow(Cardinal.directions, 8).fill(editor, rand, Air.get());
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 15).grow(Cardinal.DOWN).grow(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 15).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		});
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 15).fill(editor, rand, theme.getPrimary().getFloor());
		
		this.getEntrancesFromType(Entrance.DOOR).forEach(dir -> {
			Fragment.generate(Fragment.ARCH, editor, rand, theme, origin.copy().add(dir, 12), dir); 
		});
	}

	@Override
	public String getName() {
		return Room.BANQUET.name();
	}

}
