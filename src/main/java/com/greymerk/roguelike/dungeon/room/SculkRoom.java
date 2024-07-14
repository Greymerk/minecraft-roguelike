package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Campfire;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.slab.ISlab;
import com.greymerk.roguelike.editor.blocks.spawners.Spawner;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.filter.Filter;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public class SculkRoom extends AbstractLargeRoom implements IRoom {

	List<Coord> spawners;
	
	public SculkRoom() {
		super();
		this.spawners = new ArrayList<Coord>();
	}
	
	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos().copy().add(direction, Cell.SIZE * 2).freeze();
		Random rand = editor.getRandom(origin);
		
		clear(editor, rand, origin);
		ceiling(editor, rand, origin);
		floor(editor, rand, origin);
		braziers(editor, rand, origin);
		pillars(editor, rand, origin);
		deco(editor, rand, origin);
		tower(editor, rand, origin);
		bridges(editor, rand, origin);
		placeSpawners(editor, rand, origin);
		placeChests(editor, rand, origin);
		Filter.get(Filter.SCULK).apply(editor, rand, theme, getBoundingBox().get());

	}

	private void placeChests(IWorldEditor editor, Random rand, Coord origin) {
		WeightedRandomizer<Treasure> types = new WeightedRandomizer<Treasure>()
			.add(new WeightedChoice<Treasure>(Treasure.ARMOR, 1))
			.add(new WeightedChoice<Treasure>(Treasure.TOOL, 1))
			.add(new WeightedChoice<Treasure>(Treasure.WEAPON, 1));
		
		List<Coord> empty = BoundingBox.of(origin).add(Cardinal.DOWN, 2).grow(Cardinal.directions, 9).get().stream()
				.filter(pos -> editor.isAir(pos))
				.filter(pos -> checkerBoard(pos))
				.collect(Collectors.toList());
		RandHelper.shuffle(empty, rand);
		int count = rand.nextBetween(5, 9);
		if(empty.size() <= count) {
			empty.forEach(pos -> {
				Treasure.generate(editor, rand, pos, types.get(rand));	
			});
		} else {
			empty.subList(0, count).forEach(pos -> {
				Treasure.generate(editor, rand, pos, types.get(rand));
			});	
		}
	}
	
	private boolean checkerBoard(Coord pos) {
		int x = pos.getX();
		int z = pos.getZ();
		if(Math.floorMod(x, 2) == 0 && Math.floorMod(z, 2) == 0) return true;
		if(Math.floorMod(x, 2) == 1 && Math.floorMod(z, 2) == 1) return true;
		
		return false;
	}

	private void bridges(IWorldEditor editor, Random rand, Coord origin) {
		this.getEntrancesFromType(Entrance.DOOR).forEach(dir -> {
			bridge(editor, rand, origin, dir);
		});
	}

	private void bridge(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).add(dir, 3).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir)).grow(dir, 7).fill(editor, rand, theme.getPrimary().getFloor());
		BoundingBox.of(origin).add(dir, 4).add(Cardinal.DOWN, 2).grow(Cardinal.orthogonal(dir), 2).grow(dir, 5).fill(editor, rand, theme.getPrimary().getWall());
	}

	private void placeSpawners(IWorldEditor editor, Random rand, Coord origin) {
		WeightedRandomizer<Spawner> types = new WeightedRandomizer<Spawner>();
		types.add(new WeightedChoice<Spawner>(Spawner.CAVESPIDER, 1));
		types.add(new WeightedChoice<Spawner>(Spawner.CREEPER, 1));
		types.add(new WeightedChoice<Spawner>(Spawner.ZOMBIE, 3));
		types.add(new WeightedChoice<Spawner>(Spawner.SKELETON, 3));
		types.add(new WeightedChoice<Spawner>(Spawner.SPIDER, 2));
		
		spawners.forEach(pos -> {
			if(rand.nextBoolean()) Spawner.generate(editor, rand, pos, types.get(rand));
		});
	}

	private void tower(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory walls = theme.getPrimary().getWall();
		BoundingBox.of(origin).add(Cardinal.DOWN, 2).grow(Cardinal.directions, 3).fill(editor, rand, walls);
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 2).fill(editor, rand, walls);
		Cardinal.directions.forEach(dir -> {
			Pillar.generate(editor, rand, origin.copy().add(dir, 2).add(Cardinal.left(dir), 2), theme, 3);
			Cardinal.orthogonal(dir).forEach(o -> {
				Pillar.generate(editor, rand, origin.copy().add(dir, 3).add(o, 2).add(Cardinal.UP, 4), theme, 1, List.of(dir, Cardinal.reverse(o)));
			});
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(dir), 3).grow(Cardinal.UP, 3).fill(editor, rand, walls);
			this.spawners.add(origin.copy().add(dir, 2).add(Cardinal.UP, 5));
			this.spawners.add(origin.copy().add(dir).add(Cardinal.left(dir)).add(Cardinal.DOWN, 2));
		});
		Lantern.set(editor, origin.copy().add(Cardinal.UP, 6), Lantern.SOUL, true);
		BoundingBox.of(origin).add(Cardinal.UP, 7).grow(Cardinal.directions, 2).fill(editor, rand, walls);
		MetaBlock.of(Blocks.SCULK_CATALYST).set(editor, origin);
	}

	private void deco(IWorldEditor editor, Random rand, Coord origin) {
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
					pillarSet(editor, rand, origin.copy().add(dir, 12).add(o, step).freeze(), dir);
				});
			});
			Pillar.generate(editor, rand, origin.copy().add(dir, 14).add(Cardinal.left(dir), 14), theme, 4, List.of(Cardinal.right(dir), Cardinal.reverse(dir)));
		});
	}

	private void pillarSet(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 2).add(Cardinal.UP, 4));
		Cardinal.orthogonal(dir).forEach(o -> {
			Pillar.generate(editor, rand, origin.copy().add(dir, 2).add(o), theme, 4, List.of(o, Cardinal.reverse(dir)));
			theme.getPrimary().getWall().set(editor, rand, origin.copy().add(dir).add(o).add(Cardinal.UP, 5));
		});		
	}

	private void braziers(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			brazier(editor, rand, origin.add(dir, 9).add(Cardinal.left(dir), 9).freeze());
		});
	}

	private void brazier(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		ISlab slab = theme.getPrimary().getSlab();
		this.spawners.add(origin);
		walls.set(editor, rand, origin);
		Campfire.generate(editor, origin.copy().add(Cardinal.UP), Campfire.SOUL);
		Cardinal.directions.forEach(dir -> {
			stair.setOrientation(dir, true).set(editor, rand, origin.copy().add(dir));
			stair.setOrientation(Cardinal.reverse(dir), false).set(editor, rand, origin.copy().add(dir).add(Cardinal.UP));
			stair.setOrientation(dir, false).set(editor, rand, origin.copy().add(dir).add(Cardinal.DOWN));
			slab.upsideDown(false).set(editor, origin.copy().add(Cardinal.DOWN).add(dir).add(Cardinal.left(dir)));
			this.spawners.add(origin.copy().add(Cardinal.DOWN, 2).add(dir).add(Cardinal.left(dir)));
		});
		BoundingBox.of(origin).add(Cardinal.DOWN, 2).grow(Cardinal.directions, 2).fill(editor, rand, walls);
	}

	private void floor(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 12).grow(dir, 3).grow(Cardinal.left(dir), 15).grow(Cardinal.right(dir), 11).fill(editor, rand, theme.getPrimary().getFloor());
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 11).grow(Cardinal.orthogonal(dir), 11).fill(editor, rand, theme.getPrimary().getWall());
			
			BoundingBox.of(origin).add(Cardinal.DOWN, 2).add(dir, 11).grow(dir, 4).grow(Cardinal.left(dir), 15).grow(Cardinal.right(dir), 11).fill(editor, rand, theme.getPrimary().getFloor());
			BoundingBox.of(origin).add(Cardinal.DOWN, 2).add(dir, 10).grow(Cardinal.orthogonal(dir), 10).fill(editor, rand, theme.getPrimary().getWall());
		});
		
		
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			List.of(2, 4, 8, 10).forEach(step -> {
				BoundingBox.of(origin).add(Cardinal.UP, 6).add(dir, step).grow(Cardinal.orthogonal(dir), 15).fill(editor, rand, theme.getPrimary().getWall());
			});
			BoundingBox.of(origin).add(dir, 14).add(Cardinal.UP, 5).grow(Cardinal.UP, 2).grow(Cardinal.orthogonal(dir), 14).fill(editor, rand, theme.getPrimary().getWall());
		});
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 14).grow(Cardinal.DOWN, 2).grow(Cardinal.UP, 6).fill(editor, rand, Air.get());
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 15).grow(Cardinal.orthogonal(dir), 15).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
			if(this.getEntrance(dir) == Entrance.DOOR) {
				Fragment.generate(Fragment.ARCH, editor, rand, theme, origin.copy().add(dir, 12), dir);	
			}
		});
		BoundingBox.of(origin).add(Cardinal.UP, 7).grow(Cardinal.directions, 15).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		BoundingBox.of(origin).add(Cardinal.DOWN, 3).grow(Cardinal.directions, 15).fill(editor, rand, theme.getPrimary().getWall());
	}

	@Override
	public String getName() {
		return Room.SCULK.name();
	}
}
