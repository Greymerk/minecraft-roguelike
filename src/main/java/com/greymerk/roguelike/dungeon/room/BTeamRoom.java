package com.greymerk.roguelike.dungeon.room;

import java.util.Optional;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.BrewingStand;
import com.greymerk.roguelike.editor.blocks.Crops;
import com.greymerk.roguelike.editor.blocks.Log;
import com.greymerk.roguelike.editor.blocks.Trapdoor;
import com.greymerk.roguelike.editor.blocks.Wood;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.factories.BlockCheckers;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.chest.ChestType;
import com.greymerk.roguelike.treasure.chest.ITreasureChest;
import com.greymerk.roguelike.treasure.loot.potions.PotionMixture;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class BTeamRoom extends AbstractRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord outside = this.getWorldPos().freeze();
		Coord origin = outside.add(direction, Cell.SIZE).freeze();
		Random rand = editor.getRandom(outside);
		entry(editor, rand, this.getWorldPos());
		clear(editor, rand, origin);
		ceiling(editor, rand, origin);
		lamps(editor, rand, origin);
		walls(editor, rand, origin);
		table(editor, rand, origin);
		emeraldB(editor, rand, origin);
		decor(editor, rand, origin);
		passage(editor, rand, this.getWorldPos());
		
	}

	private void decor(IWorldEditor editor, Random rand, Coord origin) {
		MetaBlock.of(Blocks.JUKEBOX).set(editor, origin.add(direction, 7).add(Cardinal.left(direction), 4));
		Optional<ITreasureChest> maybeChest = Treasure.generate(editor, rand, settings.getDifficulty(), origin.add(direction, 7).add(Cardinal.left(direction), 5), Cardinal.reverse(direction), Treasure.EMPTY, ChestType.CHEST); 
		if(maybeChest.isPresent()) {
			maybeChest.get().setRandomEmptySlot(new ItemStack(Items.MUSIC_DISC_STAL));	
		}
		
		MetaBlock.of(Blocks.BOOKSHELF).set(editor, origin.add(Cardinal.right(direction), 5).add(direction, 7));
		Coord stand = origin.add(Cardinal.right(direction), 5).add(direction, 7).add(Cardinal.UP);
		BrewingStand.generate(editor, stand);
		BrewingStand.add(editor, stand, BrewingStand.LEFT, PotionMixture.getBooze(rand));
		BrewingStand.add(editor, stand, BrewingStand.MIDDLE, PotionMixture.getBooze(rand));
		BrewingStand.add(editor, stand, BrewingStand.RIGHT, PotionMixture.getBooze(rand));
	}

	private void emeraldB(IWorldEditor editor, Random rand, Coord origin) {
		MetaBlock emerald = MetaBlock.of(Blocks.EMERALD_BLOCK);
		BoundingBox.of(origin).add(Cardinal.right(direction), 5).grow(Cardinal.UP, 4).forEach(pos -> {
			if(pos.getY() % 2 == 0) {
				BoundingBox.of(pos).add(direction, 3).grow(direction, 2).fill(editor, rand, emerald);
			} else {
				emerald.set(editor, pos.copy().add(direction, 2));
				emerald.set(editor, pos.copy().add(direction, 5));
			}
		});
	}

	private void table(IWorldEditor editor, Random rand, Coord origin) {
		MetaBlock carpet = MetaBlock.of(Blocks.GRAY_CARPET);
		IStair chair = Stair.of(Stair.NETHERBRICK);
		BoundingBox.of(origin).add(direction).grow(Cardinal.orthogonal(direction), 4).fill(editor, rand, carpet);
		BoundingBox.of(origin).add(direction, 6).grow(Cardinal.orthogonal(direction), 4).fill(editor, rand, carpet);
		Cardinal.orthogonal(direction).forEach(o -> {
			BoundingBox.of(origin).add(direction, 2).add(o, 4).grow(direction, 3).fill(editor, rand, carpet);
			chair.setOrientation(direction, false).set(editor, rand, origin.add(direction).add(o));
			chair.setOrientation(Cardinal.reverse(direction), false).set(editor, rand, origin.add(direction, 6).add(o));
			Stair.of(Stair.SPRUCE).setOrientation(o, true).fill(editor, rand, BoundingBox.of(origin).add(direction, 3).add(o, 2).grow(direction));
		});
		Slab.of(Slab.SPRUCE).upsideDown(true).get().fill(editor, rand, BoundingBox.of(origin).add(direction, 3).grow(direction).grow(Cardinal.orthogonal(direction)));
	}

	private void walls(IWorldEditor editor, Random rand, Coord origin) {
		BlockCheckers panels = new BlockCheckers(Log.get(Wood.SPRUCE, Cardinal.left(direction)), Log.get(Wood.SPRUCE, Cardinal.UP));
		BoundingBox.of(origin).add(Cardinal.reverse(direction)).add(Cardinal.UP).grow(Cardinal.orthogonal(direction), 5).grow(Cardinal.UP, 3).fill(editor, rand, panels);
		BoundingBox.of(origin).add(Cardinal.reverse(direction)).grow(Cardinal.orthogonal(direction), 5).fill(editor, rand, MetaBlock.of(Blocks.SPRUCE_PLANKS));
		BoundingBox.of(origin).add(direction, 8).add(Cardinal.UP).grow(Cardinal.orthogonal(direction), 5).grow(Cardinal.UP, 3).fill(editor, rand, panels);
		BoundingBox.of(origin).add(direction, 8).grow(Cardinal.orthogonal(direction), 5).fill(editor, rand, MetaBlock.of(Blocks.SPRUCE_PLANKS));
		BoundingBox.of(origin).add(Cardinal.right(direction), 6).grow(direction, 7).fill(editor, rand, MetaBlock.of(Blocks.SPRUCE_PLANKS));
		BoundingBox.of(origin).add(Cardinal.right(direction), 6).add(Cardinal.UP).grow(direction, 7).grow(Cardinal.UP, 4).fill(editor, rand, theme.getPrimary().getWall());
		BoundingBox.of(origin).add(Cardinal.left(direction), 6).grow(direction, 7).fill(editor, rand, MetaBlock.of(Blocks.SPRUCE_PLANKS));
		BoundingBox.of(origin).add(Cardinal.left(direction), 6).add(Cardinal.UP, 4).grow(direction, 7).fill(editor, rand, theme.getPrimary().getWall());
		BoundingBox.of(origin).add(Cardinal.left(direction), 6).add(Cardinal.UP).grow(Cardinal.UP, 2).grow(direction, 7).fill(editor, rand, MetaBlock.of(Blocks.BOOKSHELF));
		BoundingBox.of(origin).add(Cardinal.left(direction), 6).add(direction, 2).grow(direction, 3).fill(editor, rand, MetaBlock.of(Blocks.NOTE_BLOCK));
		BoundingBox.of(origin).add(Cardinal.left(direction), 6).add(direction, 2).add(Cardinal.UP).grow(Cardinal.UP, 2).grow(direction, 3).fill(editor, rand, MetaBlock.of(Blocks.BLACK_WOOL));
		Log.get(Wood.JUNGLE, Cardinal.left(direction)).set(editor, origin.add(Cardinal.left(direction), 6).add(direction).add(Cardinal.UP, 2));
		Log.get(Wood.JUNGLE, Cardinal.left(direction)).set(editor, origin.add(Cardinal.left(direction), 6).add(direction, 6).add(Cardinal.UP, 2));
		MetaBlock cocao = Crops.getCocao(Cardinal.left(direction));
		cocao.set(editor, origin.add(Cardinal.left(direction), 5).add(direction).add(Cardinal.UP, 2));
		cocao.set(editor, origin.add(Cardinal.left(direction), 5).add(direction, 6).add(Cardinal.UP, 2));
		
	}

	private void lamps(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.orthogonal(direction).forEach(o -> {
			lamp(editor, rand, origin.add(o, 3).freeze(), direction);
			lamp(editor, rand, origin.add(o, 3).add(direction, 7).freeze(), Cardinal.reverse(direction));
		});
	}

	private void lamp(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		MetaBlock spruce = MetaBlock.of(Blocks.SPRUCE_PLANKS);
		MetaBlock fence = MetaBlock.of(Blocks.SPRUCE_FENCE);
		spruce.set(editor, origin);
		fence.set(editor, origin.add(Cardinal.UP));
		MetaBlock.of(Blocks.GLOWSTONE).set(editor, origin.add(Cardinal.UP, 2));
		Cardinal.directions.forEach(d -> {
			if(d != Cardinal.reverse(dir)) {
				Trapdoor.getWooden(Wood.OAK, Cardinal.reverse(d), true, true).set(editor, origin.add(Cardinal.UP, 2).add(d));	
			}
		});
		fence.set(editor, origin.add(Cardinal.UP, 3));
		spruce.set(editor, origin.add(Cardinal.UP, 4));
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = Stair.of(Stair.SPRUCE);
		BoundingBox.of(origin).add(Cardinal.UP, 5).grow(direction, 7).grow(Cardinal.orthogonal(direction), 5).fill(editor, rand, MetaBlock.of(Blocks.STONE_BRICKS));
		stair.setOrientation(direction, true).fill(editor, rand, BoundingBox.of(origin).add(Cardinal.UP, 5).add(direction, 2).grow(Cardinal.orthogonal(direction), 2));
		stair.setOrientation(Cardinal.reverse(direction), true).fill(editor, rand, BoundingBox.of(origin).add(Cardinal.UP, 5).add(direction, 5).grow(Cardinal.orthogonal(direction), 2));
		Cardinal.orthogonal(direction).forEach(o -> {
			stair.setOrientation(Cardinal.reverse(o), true).fill(editor, rand, BoundingBox.of(origin).add(Cardinal.UP, 5).add(o, 3).add(direction, 2).grow(direction, 3));
		});
		BoundingBox.of(origin).add(Cardinal.UP, 5).add(direction, 3).grow(direction).grow(Cardinal.orthogonal(direction), 2).fill(editor, rand, MetaBlock.of(Blocks.REDSTONE_LAMP));
		
		stair.setOrientation(direction, true).fill(editor, rand, BoundingBox.of(origin).add(Cardinal.UP, 4).grow(Cardinal.orthogonal(direction), 5));
		stair.setOrientation(Cardinal.reverse(direction), true).fill(editor, rand, BoundingBox.of(origin).add(Cardinal.UP, 4).add(direction, 7).grow(Cardinal.orthogonal(direction), 5));
		BoundingBox.of(origin).add(Cardinal.UP, 4).add(Cardinal.left(direction), 5).add(direction).grow(direction, 5).fill(editor, rand, Slab.of(Slab.SPRUCE).upsideDown(true).get());
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(direction, 7).grow(Cardinal.orthogonal(direction), 5).grow(Cardinal.UP, 4).fill(editor, rand, Air.get());
		BoundingBox.of(origin).grow(direction, 7).grow(Cardinal.orthogonal(direction), 5).add(Cardinal.DOWN).fill(editor, rand, MetaBlock.of(Blocks.COBBLESTONE));
	}

	private void passage(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).add(direction, 3).grow(Cardinal.orthogonal(direction)).grow(Cardinal.UP, 3).fill(editor, rand, theme.getPrimary().getWall());
		BoundingBox.of(origin).add(direction, 4).grow(Cardinal.orthogonal(direction)).grow(Cardinal.UP, 2).grow(direction).grow(Cardinal.DOWN).fill(editor, rand, MetaBlock.of(Blocks.COBBLESTONE));
		BoundingBox.of(origin).add(direction, 4).grow(Cardinal.UP).grow(direction).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(direction, 3).grow(Cardinal.UP).grow(direction, 2).fill(editor, rand, MetaBlock.of(Blocks.GRAVEL));
	}

	private void entry(IWorldEditor editor, Random rand, Coord origin) {
		Corridor cor = new Corridor();
		
		Cardinal.directions.forEach(dir -> {
			Entrance type = this.getEntrance(dir);
			if(type == Entrance.DOOR) {
				cor.addEntrance(dir, Entrance.DOOR);	
			} else {
				cor.addEntrance(dir, Entrance.WALL);
			}
		});
		
		cor.setLevelSettings(settings);
		cor.worldPos = this.worldPos.copy();
		cor.addEntrance(direction, Entrance.DOOR);
		cor.generate(editor);
	}
	
	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = Coord.ZERO;
		CellManager cells = new CellManager();
		
		cells.add(Cell.of(origin.copy(), CellState.OBSTRUCTED));
		cells.add(Cell.of(origin.copy().add(dir), CellState.OBSTRUCTED));
		cells.add(Cell.of(origin.copy().add(dir, 2), CellState.OBSTRUCTED).addWall(dir));
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			cells.add(Cell.of(origin.copy().add(dir).add(o), CellState.OBSTRUCTED).addWall(Cardinal.reverse(dir)).addWall(o));
			cells.add(Cell.of(origin.copy().add(dir, 2).add(o), CellState.OBSTRUCTED).addWall(dir).addWall(o));
		}
		
		for(Cardinal d : Cardinal.directions) {
			if(d == dir) continue;
			cells.add(Cell.of(origin.copy().add(d), CellState.POTENTIAL));
		}
		
		return cells;
	}
	
	@Override
	public String getName() {
		return Room.BTEAM.name();
	}

}
