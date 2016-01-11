package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.Bed;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Cake;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Door;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.Stair;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.redstone.Torch;

public class HouseTower implements ITower {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon) {
		
		Coord floor = Tower.getBaseCoord(editor, dungeon);
		
		IBlockFactory walls = theme.getPrimaryWall();
		IBlockFactory mainFloor = theme.getPrimaryFloor();
		IStair stair = theme.getPrimaryStair();
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		Cardinal dir = Cardinal.directions[(floor.getY() + 2) % 4];
		Cardinal[] orth = Cardinal.orthogonal(dir);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		int x = dungeon.getX();
		int y = dungeon.getY();
		int z = dungeon.getZ();
		
		floor.add(Cardinal.UP);
		
		start = new Coord(floor);
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		start.add(orth[1], 3);
		start.add(dir, 3);
		end.add(Cardinal.UP, 8);
		end.add(Cardinal.reverse(dir), 7);
		end.add(orth[0], 10);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(floor);
		start.add(orth[1], 2);
		start.add(Cardinal.DOWN);
		end = new Coord(floor);
		end.add(Cardinal.UP, 3);
		end.add(orth[0], 8);
		end.add(Cardinal.reverse(dir), 5);
		walls.fillRectSolid(editor, rand, new Coord(x - 2, floor.getY() + 3, z - 2), new Coord(x + 2, y + 10, z + 2), true, true);
		walls.fillRectHollow(editor, rand, start, end, true, true);
		
		cursor = new Coord(floor);
		cursor.add(orth[0], 6);
		cursor.add(Cardinal.reverse(dir), 6);
		door(editor, rand, theme, dir, cursor);
		
		start = new Coord(floor);
		start.add(Cardinal.DOWN);
		start.add(orth[1]);
		start.add(Cardinal.reverse(dir));
		end = new Coord(floor);
		end.add(Cardinal.DOWN);
		end.add(Cardinal.reverse(dir), 4);
		end.add(orth[0], 7);
		mainFloor.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(floor);
		start.add(Cardinal.DOWN, 2);
		start.add(orth[1], 2);
		start.add(Cardinal.reverse(dir), 2);
		end = new Coord(floor.getX(), y + 10, floor.getZ());
		end.add(Cardinal.reverse(dir), 5);
		end.add(orth[0], 8);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(floor);
		cursor.add(Cardinal.reverse(dir), 5);
		cursor.add(orth[1], 2);
		support(editor, rand, theme, new Cardinal[]{Cardinal.reverse(dir), orth[1]}, cursor);
		cursor.add(dir, 7);
		support(editor, rand, theme, new Cardinal[]{dir, orth[1]}, cursor);
		cursor.add(orth[0], 4);
		support(editor, rand, theme, new Cardinal[]{dir, orth[0]}, cursor);
		cursor.add(orth[0], 6);
		cursor.add(Cardinal.reverse(dir), 2);
		support(editor, rand, theme, new Cardinal[]{dir, orth[0]}, cursor);
		
		upperFloor(editor, rand, theme, dir, new Coord(x, floor.getY() + 3, z));
		roof(editor, rand, theme, dir, new Coord(x, floor.getY() + 4, z));
		upperWalls(editor, rand, theme, dir, new Coord(x, floor.getY() + 4, z));
		windows(editor, rand, theme, dir, floor);
		decor(editor, rand, theme, dir, floor);
		
		cursor = new Coord(floor);
		cursor.add(Cardinal.UP, 3);
		for(int i = floor.getY() + 3; i >= y; --i){
			editor.spiralStairStep(rand, new Coord(x, i, z), stair, theme.getSecondaryPillar());
		}
	}

	private void decor(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
		
		IStair stair = Stair.get(StairType.OAK);
		MetaBlock slab = Slab.get(Slab.OAK, true, false, false);
		Cardinal[] orth = Cardinal.orthogonal(dir);
		Coord cursor;
		Coord start;
		Coord end;
		
		// downstairs table
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 4);
		stair.setOrientation(orth[1], true).setBlock(editor, cursor);
		cursor.add(orth[0]);
		slab.setBlock(editor, cursor);
		cursor.add(orth[0]);
		stair.setOrientation(orth[0], true).setBlock(editor, cursor);
		
		cursor = new Coord(origin);
		cursor.add(orth[0], 4);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(orth[1], true).setBlock(editor, cursor);
		cursor.add(orth[0]);
		slab.setBlock(editor, cursor);
		cursor.add(orth[0]);
		stair.setOrientation(orth[0], true).setBlock(editor, cursor);
		cursor.add(orth[1]);
		cursor.add(Cardinal.UP);
		Cake.get().setBlock(editor, cursor);
		
		cursor = new Coord(origin);
		cursor.add(orth[0], 7);
		cursor.add(Cardinal.reverse(dir));
		slab.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
		cursor.add(Cardinal.DOWN);
		cursor.add(Cardinal.reverse(dir));
		BlockType.get(BlockType.CRAFTING_TABLE).setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		Furnace.generate(editor, true, orth[1], cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		cursor.add(orth[1], 2);
		cursor.add(Cardinal.reverse(dir), 3);
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(dir, true).setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		FlowerPot.generate(editor, rand, cursor);
		cursor.add(dir);
		FlowerPot.generate(editor, rand, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		cursor.add(orth[0]);
		cursor.add(Cardinal.reverse(dir), 5);
		stair.setOrientation(orth[1], true).setBlock(editor, cursor);
		cursor.add(orth[0]);
		slab.setBlock(editor, cursor);
		cursor.add(orth[0]);
		stair.setOrientation(orth[0], true).setBlock(editor, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		cursor.add(orth[0], 8);
		Treasure.generate(editor, rand, cursor, Treasure.STARTER, 0);
		cursor.add(Cardinal.reverse(dir));
		BlockType.get(BlockType.SHELF).setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		FlowerPot.generate(editor, rand, cursor);
		cursor.add(Cardinal.DOWN);
		cursor.add(Cardinal.reverse(dir));
		Bed.generate(editor, orth[1], cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(orth[0]);
		start = new Coord(cursor);
		end = new Coord(start);
		end.add(orth[0], 5);
		end.add(Cardinal.reverse(dir), 3);
		BlockStripes carpet = new BlockStripes();
		carpet.addBlock(ColorBlock.get(ColorBlock.CARPET, rand));
		carpet.addBlock(ColorBlock.get(ColorBlock.CARPET, rand));
		carpet.addBlock(ColorBlock.get(ColorBlock.CARPET, rand));
		carpet.fillRectSolid(editor, rand, start, end, true, true);
		
		
	}
	

	private void windows(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
		MetaBlock pane = ColorBlock.get(ColorBlock.PANE, DyeColor.LIGHT_GRAY);
		Cardinal[] orth = Cardinal.orthogonal(dir);
		Coord cursor;
		Coord start;
		Coord end;
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 5);
		cursor.add(Cardinal.UP);
		pane.setBlock(editor, cursor);
		cursor.add(orth[0], 2);
		pane.setBlock(editor, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		cursor.add(orth[0], 8);
		cursor.add(Cardinal.reverse(dir), 2);
		pane.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir), 2);
		pane.setBlock(editor, cursor);
		
		// upstairs
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 5);
		cursor.add(orth[0]);
		cursor.add(dir, 3);
		pane.setBlock(editor, cursor);
		cursor.add(orth[1], 2);
		pane.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir), 2);
		cursor.add(orth[1], 2);
		pane.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		pane.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir), 3);
		pane.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		pane.setBlock(editor, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 5);
		cursor.add(orth[0], 9);
		cursor.add(Cardinal.reverse(dir));
		pane.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		pane.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir), 2);
		pane.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		pane.setBlock(editor, cursor);
		cursor.add(Cardinal.UP, 2);
		cursor.add(dir);
		start = new Coord(cursor);
		end = new Coord(start);
		end.add(Cardinal.UP);
		end.add(dir, 2);
		pane.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		cursor.add(orth[0], 5);
		cursor.add(Cardinal.reverse(dir), 7);
		start = new Coord(cursor);
		end = new Coord(start);
		end.add(orth[0], 2);
		end.add(Cardinal.UP);
		pane.fillRectSolid(editor, rand, start, end, true, true);
	}

	private void roof(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
		IBlockFactory walls = theme.getSecondaryWall();
		IStair stair = theme.getSecondaryStair();
		Cardinal[] orth = Cardinal.orthogonal(dir);
		Coord cursor;
		Coord start;
		Coord end;
		
		cursor = new Coord(origin);
		cursor.add(orth[1], 4);
		cursor.add(dir, 4);
		cursor.add(Cardinal.UP, 2);
		start = new Coord(cursor);
		end = new Coord(cursor);
		end.add(Cardinal.reverse(dir), 10);
		stair.setOrientation(orth[1], false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(orth[0]);
		end.add(orth[0]);
		stair.setOrientation(orth[0], true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		stair.setOrientation(orth[1], false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(orth[0]);
		end.add(orth[0]);
		stair.setOrientation(orth[0], true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		stair.setOrientation(orth[1], false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(orth[0]);
		end.add(orth[0]);
		end.add(dir);
		stair.setOrientation(orth[0], true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		stair.setOrientation(orth[1], false).fillRectSolid(editor, rand, start, end, true, true);
		
		start.add(orth[0]);
		end.add(orth[0]);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 3);
		cursor.add(Cardinal.UP, 5);
		stair.setOrientation(orth[0], true).setBlock(editor, cursor);
		cursor.add(dir);
		stair.setOrientation(orth[0], true).setBlock(editor, cursor);
		cursor.add(Cardinal.DOWN);
		cursor.add(orth[0]);
		cursor.add(dir);
		stair.setOrientation(orth[1], true).setBlock(editor, cursor);
		cursor.add(Cardinal.DOWN);
		cursor.add(orth[0]);
		cursor.add(dir);
		stair.setOrientation(orth[1], true).setBlock(editor, cursor);
		
		start.add(orth[0]);
		end.add(orth[0]);
		end.add(dir, 5);
		stair.setOrientation(orth[0], false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		stair.setOrientation(orth[1], true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(orth[0]);
		end.add(orth[0]);
		end.add(dir);
		stair.setOrientation(orth[0], false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		stair.setOrientation(orth[1], true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(orth[0]);
		end.add(orth[0]);
		end.add(dir);
		stair.setOrientation(orth[0], false).fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 2);
		cursor.add(dir, 2);
		cursor.add(orth[0], 10);
		start = new Coord(cursor);
		end = new Coord(cursor);
		end.add(orth[1], 6);
		stair.setOrientation(dir, false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.reverse(dir));
		end.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		end.add(orth[1]);
		stair.setOrientation(dir, false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.reverse(dir));
		end.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		end.add(orth[1]);
		stair.setOrientation(dir, false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.reverse(dir));
		end.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		end.add(orth[1]);
		stair.setOrientation(dir, false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.reverse(dir));
		end.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		stair.setOrientation(dir, false).fillRectSolid(editor, rand, start, end, true, true);
		
		start.add(Cardinal.reverse(dir));
		end.add(Cardinal.reverse(dir));
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(end);
		end.add(Cardinal.reverse(dir), 2);
		start.add(orth[1]);
		end.add(orth[1]);
		start.add(dir);
		end.add(dir);
		stair.setOrientation(orth[1], false).fillRectSolid(editor, rand, start, end, true, true);
		
		cursor.add(Cardinal.reverse(dir), 10);
		start = new Coord(cursor);
		end = new Coord(cursor);
		end.add(orth[1], 7);
		stair.setOrientation(Cardinal.reverse(dir), false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(dir);
		end.add(dir);
		stair.setOrientation(dir, true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		end.add(orth[1], 5);
		stair.setOrientation(Cardinal.reverse(dir), false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(dir);
		end.add(dir);
		stair.setOrientation(dir, true).fillRectSolid(editor, rand, start, end, true, true);
		end.add(orth[0]);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		stair.setOrientation(Cardinal.reverse(dir), false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(dir);
		end.add(dir);
		stair.setOrientation(dir, true).fillRectSolid(editor, rand, start, end, true, true);
		end.add(orth[0]);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		stair.setOrientation(Cardinal.reverse(dir), false).fillRectSolid(editor, rand, start, end, true, true);
		start.add(dir);
		end.add(dir);
		stair.setOrientation(dir, true).fillRectSolid(editor, rand, start, end, true, true);
		end.add(orth[0]);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		stair.setOrientation(Cardinal.reverse(dir), false).fillRectSolid(editor, rand, start, end, true, true);
	}

	private void upperFloor(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
		IBlockFactory floor = theme.getPrimaryFloor();
		Cardinal[] orth = Cardinal.orthogonal(dir);
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(orth[1], 3);
		start.add(dir, 3);
		end = new Coord(origin);
		end.add(orth[0], 3);
		end.add(Cardinal.reverse(dir), 6);
		floor.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(orth[0], 3);
		start.add(dir);
		end = new Coord(origin);
		end.add(Cardinal.reverse(dir), 7);
		end.add(orth[0], 9);
		floor.fillRectSolid(editor, rand, start, end, true, true);
	}

	private void upperWalls(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
		IBlockFactory walls = theme.getPrimaryWall();
		Cardinal[] orth = Cardinal.orthogonal(dir);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(orth[1], 3);
		start.add(dir, 2);
		end = new Coord(start);
		end.add(Cardinal.reverse(dir), 7);
		end.add(Cardinal.UP, 2);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(orth[1], 2);
		start.add(dir, 3);
		end = new Coord(start);
		end.add(orth[0], 4);
		end.add(Cardinal.UP, 3);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		end.add(Cardinal.UP);
		end.add(orth[1]);
		start = new Coord(end);
		start.add(orth[1], 2);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(orth[0], 3);
		start.add(dir, 2);
		end = new Coord(start);
		end.add(Cardinal.UP, 2);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(orth[0], 4);
		start.add(dir);
		end = new Coord(start);
		end.add(orth[0], 4);
		end.add(Cardinal.UP, 2);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(orth[0], 9);
		end = new Coord(start);
		end.add(Cardinal.reverse(dir), 6);
		end.add(Cardinal.UP, 3);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		end.add(Cardinal.UP);
		end.add(dir);
		start = new Coord(end);
		start.add(dir, 4);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		end.add(Cardinal.UP);
		end.add(dir);
		start = new Coord(end);
		start.add(dir, 2);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		
		start = new Coord(origin);
		start.add(Cardinal.reverse(dir), 7);
		start.add(orth[0], 4);
		end = new Coord(start);
		end.add(orth[0], 4);
		end.add(Cardinal.UP, 2);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.reverse(dir), 6);
		start.add(orth[1], 2);
		end = new Coord(start);
		end.add(orth[0], 4);
		end.add(Cardinal.UP, 3);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(orth[1], 3);
		cursor.add(dir, 3);
		pillar(editor, rand, theme, 3, cursor);
		cursor.add(orth[0], 6);
		pillar(editor, rand, theme, 3, cursor);
		cursor.add(Cardinal.reverse(dir), 2);
		pillar(editor, rand, theme, 3, cursor);
		cursor.add(orth[0], 6);
		pillar(editor, rand, theme, 3, cursor);
		cursor.add(Cardinal.reverse(dir), 8);
		pillar(editor, rand, theme, 3, cursor);
		cursor.add(orth[1], 6);
		pillar(editor, rand, theme, 3, cursor);
		cursor.add(dir);
		pillar(editor, rand, theme, 3, cursor);
		cursor.add(orth[1], 6);
		pillar(editor, rand, theme, 3, cursor);
	}

	private void pillar(IWorldEditor editor, Random rand, ITheme theme, int height, Coord start) {
		IBlockFactory pillar = theme.getPrimaryPillar();
		Coord end;
		end = new Coord(start);
		end.add(Cardinal.UP, height - 1);
		pillar.fillRectSolid(editor, rand, start, end, true, true);
	}

	private void support(IWorldEditor editor, Random rand, ITheme theme, Cardinal[] dirs, Coord origin) {
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		end.add(Cardinal.UP, 2);
		pillar.fillRectSolid(editor, rand, start, end, true, true);
		cursor = new Coord(origin);
		cursor.add(Cardinal.DOWN);
		editor.fillDown(rand, cursor, pillar);
		
		for(Cardinal dir : dirs){
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 2);
			cursor.add(dir);
			stair.setOrientation(dir, true).setBlock(editor, cursor);
			for(Cardinal o : Cardinal.orthogonal(dir)){
				Coord c = new Coord(cursor);
				c.add(o);
				stair.setOrientation(o, true).setBlock(editor, rand, c, true, false);
			}
		}
		
	}

	private void door(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {
		
		IBlockFactory floor = theme.getPrimaryFloor();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getPrimaryStair();
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.orthogonal(dir);
		
		start = new Coord(origin);
		start.add(Cardinal.reverse(dir));
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.reverse(dir), 2);
		end.add(Cardinal.UP, 6);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(start);
		start.add(Cardinal.DOWN);
		start.add(orth[0]);
		end.add(Cardinal.UP, 2);
		end.add(orth[1]);
		floor.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir));
		end.add(dir);
		start.add(orth[0]);
		end.add(orth[1]);
		floor.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN, 2);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir));
		end.add(dir);
		start.add(orth[0]);
		end.add(orth[1]);
		end = new Coord(end.getX(), 60, end.getZ());
		floor.fillRectSolid(editor, rand, start, end, true, false);
		
		Door.generate(editor, origin, Cardinal.reverse(dir), Door.OAK);

		for(Cardinal o : orth){
			
			cursor = new Coord(origin);
			cursor.add(o, 2);
			cursor.add(Cardinal.UP, 2);
			editor.fillDown(rand, cursor, pillar);
			
			cursor = new Coord(end);
			cursor.add(o);
			stair.setOrientation(o, true).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(dir));
			stair.setOrientation(o, true).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(o));
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(o));
			stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 2);
		cursor.add(orth[0], 3);
		cursor.add(dir);
		stair.setOrientation(dir, true).setBlock(editor, cursor);
		
		start = new Coord(origin);
		start.add(dir);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.DOWN);
		cursor.add(Cardinal.reverse(dir), 2);
		step(editor, rand, theme, Cardinal.reverse(dir), cursor);
	}
	
	private void step(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin){
		
		Coord start;
		Coord end;
		Coord cursor;
		
		IStair stair = theme.getPrimaryStair();
		IBlockFactory blocks = theme.getPrimaryWall();
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.DOWN);
		cursor.add(dir);
		if(editor.validGroundBlock(cursor)) return;
		if(cursor.getY() <= 60) return;
		
		Cardinal[] orth = Cardinal.orthogonal(dir);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(orth[0]);
		end.add(orth[1]);
		end = new Coord(end.getX(), 60, end.getZ());
		editor.fillRectSolid(rand, start, end, blocks, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(orth[0]);
		end.add(orth[1]);
		stair.setOrientation(dir, false);
		editor.fillRectSolid(rand, start, end, stair, true, true);
		
		origin.add(Cardinal.DOWN);
		origin.add(dir);
		step(editor, rand, theme, dir, origin);
	}
}
