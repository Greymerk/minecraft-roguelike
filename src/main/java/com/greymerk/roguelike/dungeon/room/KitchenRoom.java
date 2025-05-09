package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.blocks.Furnace;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.chest.ChestType;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class KitchenRoom extends AbstractMediumRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = worldPos.copy().add(direction, Cell.SIZE).freeze();
		Random rand = editor.getRandom(origin);
		IBlockFactory wall = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		IBlockFactory pillar = theme.getPrimary().getPillar();
		
		BoundingBox.of(origin).grow(Cardinal.directions, 8).grow(Cardinal.UP, 4).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.UP, 5).grow(Cardinal.directions, 9).fill(editor, rand, wall);
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions, 9).fill(editor, rand, theme.getPrimary().getFloor());
		
		CellSupport.generate(editor, rand, theme, origin);
		Cardinal.directions.forEach(dir -> {
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, Cell.SIZE));
			CellSupport.generate(editor, rand, theme, origin.copy().add(dir, Cell.SIZE).add(Cardinal.left(dir), Cell.SIZE));
		});
		
		for(Cardinal dir : Cardinal.directions) {
			//outer wall
			BoundingBox.of(origin).add(dir, 9).grow(Cardinal.orthogonal(dir), 9).grow(Cardinal.UP, 4).fill(editor, rand, wall, Fill.SOLID);
			
			for(Cardinal o : Cardinal.orthogonal(dir)) {
				
				//outer wall
				BoundingBox.of(origin).add(dir, 8).add(Cardinal.UP, 3).grow(o, 8).grow(Cardinal.UP, 2).fill(editor, rand, wall);
				
				//inner upper wall
				BoundingBox.of(origin).add(dir, 3).add(o, 2).add(Cardinal.UP, 3).grow(Cardinal.UP, 1).grow(dir, 4).fill(editor, rand, wall);
				BoundingBox.of(origin).add(dir, 7).add(o, 3).add(Cardinal.UP, 4).grow(o, 4).fill(editor, rand, wall);
				
				//upper corner stairs
				stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, BoundingBox.of(origin).add(dir, 7).add(o, 5).add(Cardinal.UP, 3).grow(o, 2));;
				
				//corner upper stair detailing
				stair.set(editor, rand, origin.copy().add(dir, 7).add(Cardinal.UP, 3).add(o, 3));
				wall.set(editor, rand, origin.copy().add(dir, 7).add(Cardinal.UP, 3).add(o, 4));
				
				//door pillars
				BoundingBox.of(origin).add(dir, 8).add(o, 2).grow(Cardinal.UP, 2).fill(editor, rand, pillar);
				
				//pillar crowns
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 8).add(Cardinal.UP, 2).add(o));
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 7).add(Cardinal.UP, 2).add(o, 2));
				stair.set(editor, rand, origin.copy().add(dir, 8).add(Cardinal.UP, 2).add(o, 3));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 2).add(o).add(Cardinal.UP, 4));
			}
			
			BoundingBox.of(origin.copy()).add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.UP, 4).fill(editor, rand, pillar);
		}
		
		for(Cardinal dir : Cardinal.directions) {
			cornerPillars(editor, rand, origin.copy().add(dir, 6).add(Cardinal.left(dir), 6), Cardinal.left(dir));
			BoundingBox.of(origin.copy()).add(dir, 3).add(Cardinal.left(dir), 3).add(Cardinal.DOWN).grow(dir, 4).grow(Cardinal.left(dir), 4).fill(editor, rand, theme.getSecondary().getFloor());
		}
		
		boolean table = true;
		for(Cardinal dir : Cardinal.randDirs(rand)) {
			if(table) {
				table(editor, rand, origin.copy().add(dir, 6).add(Cardinal.left(dir), 6), Cardinal.left(dir));
			} else {
				chest(editor, rand, origin.copy().add(dir, 6).add(Cardinal.left(dir), 6), Cardinal.left(dir));
			}
			table = !table;
		}
		
		this.generateExits(editor, rand);
	}

	private void chest(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getSecondary().getStair();
		
		stair.setOrientation(Cardinal.reverse(dir), false).fill(editor, rand, BoundingBox.of(origin).add(dir, 2).grow(Cardinal.orthogonal(dir)));
		stair.setOrientation(Cardinal.left(dir), false).fill(editor, rand, BoundingBox.of(origin).add(Cardinal.right(dir), 2).grow(Cardinal.orthogonal(Cardinal.right(dir))));;
		
		stair.setOrientation(dir, true).set(editor, rand, origin);
		stair.setOrientation(Cardinal.left(dir), true).set(editor, rand, origin.copy().add(Cardinal.left(dir)));
		stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(Cardinal.left(dir)).add(Cardinal.reverse(dir)));
		stair.setOrientation(Cardinal.right(dir), true).set(editor, rand, origin.copy().add(Cardinal.reverse(dir)));
		
		Candle.generate(editor, rand, origin.copy().add(Cardinal.reverse(dir)).add(Cardinal.left(dir)).add(Cardinal.UP));
	}

	private void table(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		IStair stair = theme.getSecondary().getStair();
		
		stair.setOrientation(Cardinal.reverse(dir), true).fill(editor, rand, BoundingBox.of(origin).add(dir, 2).grow(Cardinal.orthogonal(dir)));
		stair.setOrientation(Cardinal.left(dir), true).fill(editor, rand, BoundingBox.of(origin).add(Cardinal.right(dir), 2).grow(Cardinal.orthogonal(Cardinal.right(dir))));
		Treasure.generate(editor, rand, settings.getDifficulty(), origin.copy().add(Cardinal.UP).add(dir, 2), Treasure.FOOD, ChestType.BARREL);
		Furnace.generate(editor, Cardinal.left(dir), origin.copy().add(Cardinal.UP).add(Cardinal.right(dir), 2), true, new ItemStack(Items.COAL, rand.nextBetween(1, 4)));
	}

	private void cornerPillars(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		Pillar.generate(editor, rand, origin.copy().add(Cardinal.right(dir), 2).add(Cardinal.reverse(dir), 2),  theme.getSecondary(), 2, Cardinal.directions);
		Pillar.generate(editor, rand, origin.copy().add(Cardinal.right(dir), 2).add(Cardinal.reverse(dir), 2).add(dir, 4),  theme.getSecondary(), 2, Cardinal.directions);
		Pillar.generate(editor, rand, origin.copy().add(Cardinal.left(dir), 2).add(Cardinal.reverse(dir), 2).add(dir, 4),  theme.getSecondary(), 2, Cardinal.directions);
	}

	@Override
	public String getName() {
		return Room.KITCHEN.name();
	}

}
