package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.chest.ChestType;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class CreeperRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(worldPos.copy());
		Coord origin = this.getWorldPos().copy().add(direction, Cell.SIZE).freeze();
		
		clear(editor, rand, origin);
		ceiling(editor, rand, origin);
		pillars(editor, rand, origin);
		entrances(editor, rand, origin);
		tnt(editor, rand, origin);
		chest(editor, rand, origin);
		Spawner.generate(editor, rand, origin.copy().add(Cardinal.UP, 5), Spawner.CREEPER);
		
	}
	
	private void entrances(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			if(this.getEntrance(dir) == Entrance.DOOR) {
				entry(editor, rand, origin.copy().add(dir, Cell.SIZE).freeze(), dir);
			} else {
				BoundingBox.of(origin).add(dir, 5).grow(Cardinal.orthogonal(dir), 3).grow(Cardinal.UP, 3).fill(editor, rand, theme.getPrimary().getWall(), false, true);
				settings.getWallFragment(rand).generate(editor, rand, theme, origin.copy().add(dir, 2), dir);	
			}
		});
	}

	private void chest(IWorldEditor editor, Random rand, Coord origin) {
		List<Coord> space = BoundingBox.of(origin).grow(Cardinal.directions, 2).get();
		RandHelper.shuffle(space, rand);
		Coord pos = space.getFirst();
		Treasure.generate(editor, rand, pos, Treasure.ORE, ChestType.TRAPPED_CHEST);
		MetaBlock.of(Blocks.TNT).set(editor, pos.copy().add(Cardinal.DOWN, 2));
	}

	private void tnt(IWorldEditor editor, Random rand, Coord origin) {
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(MetaBlock.of(Blocks.GRAVEL), 2);
		floor.addBlock(theme.getPrimary().getFloor(), 1);
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 3).fill(editor, rand, floor);
		
		BlockWeightedRandom blocks = new BlockWeightedRandom();
		blocks.addBlock(MetaBlock.of(Blocks.GRAVEL), 3);
		blocks.addBlock(MetaBlock.of(Blocks.TNT), 1);
		BoundingBox.of(origin).add(Cardinal.DOWN, 2).grow(Cardinal.DOWN).grow(Cardinal.directions, 3).fill(editor, rand, blocks);
		
		BoundingBox.of(origin).add(Cardinal.DOWN, 3).grow(Cardinal.directions, 4).fill(editor, rand, theme.getPrimary().getWall());
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 4).grow(Cardinal.orthogonal(dir), 4).grow(Cardinal.DOWN, 3).fill(editor, rand, theme.getPrimary().getWall());
		});
	}

	private void entry(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).grow(dir, 2).grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.reverse(dir)).grow(Cardinal.UP, 6).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions).grow(dir).fill(editor, rand, theme.getPrimary().getFloor());
		
		BoundingBox.of(origin).add(Cardinal.UP, 7).grow(Cardinal.directions, 2).fill(editor, rand, theme.getPrimary().getWall());
		BoundingBox.of(origin).add(Cardinal.UP, 3).grow(Cardinal.UP).fill(editor, rand, theme.getPrimary().getWall());
		BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir)).fill(editor, rand, theme.getPrimary().getWall());
		Cardinal.orthogonal(dir).forEach(o -> {
			Pillar.generate(editor, rand, origin.copy().add(dir, 2).add(o, 2), theme, 2, List.of(Cardinal.reverse(o), Cardinal.reverse(dir)));
			BoundingBox.of(origin).add(o, 2).add(Cardinal.UP, 3).grow(Cardinal.UP, 3).grow(Cardinal.reverse(dir)).grow(dir, 2).fill(editor, rand, theme.getPrimary().getWall());
			theme.getPrimary().getStair().setOrientation(dir, true).set(editor, rand, origin.copy().add(Cardinal.reverse(dir)).add(o, 2).add(Cardinal.UP, 2));
			BoundingBox.of(origin).add(o, 3).grow(Cardinal.orthogonal(o), 2).grow(Cardinal.UP, 3).fill(editor, rand, theme.getPrimary().getWall(), false, true);
		});
		Cardinal.directions.forEach(d -> {
			BoundingBox.of(origin).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(d)).grow(Cardinal.UP).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).add(Cardinal.UP, 3).add(d).grow(Cardinal.UP).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).add(d, 3).add(Cardinal.left(d), 3).grow(Cardinal.UP, 7).fill(editor, rand, theme.getPrimary().getWall());
		});
		
		Fragment.generate(Fragment.ARCH, editor, rand, theme, origin.copy(), dir);
	}

	private void pillars(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			Cardinal.orthogonal(dir).forEach(o -> {
				Pillar.generate(editor, rand, origin.copy().add(dir, 4).add(o, 2), theme, 2, List.of(Cardinal.reverse(dir), Cardinal.reverse(o)));
			});
		});
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory walls = theme.getPrimary().getWall();
		BoundingBox.of(origin).add(Cardinal.UP, 3).grow(Cardinal.UP).fill(editor, rand, walls);
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir).add(Cardinal.UP, 3).grow(dir, 3).grow(Cardinal.UP).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 4).add(Cardinal.UP, 5).grow(Cardinal.UP, 3).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 4).add(Cardinal.left(dir), 4).grow(Cardinal.UP, 8).fill(editor, rand, walls);
			List.of(2, 4).forEach(step -> {
				BoundingBox.of(origin).add(dir, step).add(Cardinal.UP, 3).grow(Cardinal.UP).grow(Cardinal.orthogonal(dir), 4).fill(editor, rand, walls);
			});
			Cardinal.orthogonal(dir).forEach(o -> {
				BoundingBox.of(origin).add(dir, 4).add(o, 2).add(Cardinal.UP, 5).grow(Cardinal.UP, 3).fill(editor, rand, walls);
			});
		});
		BoundingBox.of(origin).add(Cardinal.UP, 7).grow(Cardinal.directions, 4).fill(editor, rand, theme.getPrimary().getWall());
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 4).grow(Cardinal.UP, 6).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 4).fill(editor, rand, theme.getPrimary().getFloor());
	}

	@Override
	public String getName() {
		return Room.CREEPER.name();
	}

}
