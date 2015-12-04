package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.redstone.Hopper;

public class DungeonsSmithy extends DungeonBase {

	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();

		Coord cursor;
		
		Cardinal dir = entrances[0];
		
		clearBoxes(editor, rand, theme, dir, origin);
		
		cursor = new Coord(origin);
		cursor.add(dir, 6);
		sideRoom(editor, rand, settings, dir, cursor);
		anvilRoom(editor, rand, settings, dir, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 6);
		sideRoom(editor, rand, settings, dir, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 9);
		MetaBlock air = BlockType.get(BlockType.AIR);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		air.setBlock(editor, cursor);
		
		mainRoom(editor, rand, settings, dir, origin);
		
		return true;
	}
	
	private void sideRoom(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin){
		
		ITheme theme = settings.getTheme();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		
		for(Cardinal side : orth){
			
			start = new Coord(origin);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			start.add(side, 2);
			start.add(Cardinal.reverse(dir), 2);
			end.add(side, 3);
			end.add(dir, 2);
			editor.fillRectSolid(rand, start, end, wall, true, true);
			
			start.add(dir);
			end = new Coord(start);
			end.add(dir, 2);
			editor.fillRectSolid(rand, start, end, stair.setOrientation(Cardinal.reverse(side), true), true, true);
			
			for(Cardinal o : Cardinal.getOrthogonal(side)){
				start = new Coord(origin);
				start.add(side, 3);
				start.add(o, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 2);
				editor.fillRectSolid(rand, start, end, pillar, true, true);
				
				cursor = new Coord(end);
				cursor.add(Cardinal.reverse(side));
				stair.setOrientation(Cardinal.reverse(side), true).setBlock(editor, cursor);
				cursor.add(Cardinal.UP);
				cursor.add(Cardinal.reverse(side));
				stair.setOrientation(Cardinal.reverse(side), true).setBlock(editor, cursor);
				
				cursor = new Coord(end);
				cursor.add(Cardinal.reverse(o));
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
			}
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		overheadLight(editor, rand, settings, cursor);
	}
	
	private void clearBoxes(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin){
		
		IBlockFactory wall = theme.getPrimaryWall();
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		// end room
		cursor = new Coord(origin);
		cursor.add(dir, 6);
		
		start = new Coord(cursor);
		start.add(Cardinal.DOWN);
		start.add(dir, 3);
		start.add(orth[0], 4);
		
		end = new Coord(cursor);
		end.add(Cardinal.UP, 4);
		end.add(Cardinal.reverse(dir), 3);
		end.add(orth[1], 4);
		
		editor.fillRectHollow(rand, start, end, wall, true, true);
		
		// entrance
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 6);
		
		start = new Coord(cursor);
		start.add(Cardinal.DOWN);
		start.add(dir, 3);
		start.add(orth[0], 4);
		
		end = new Coord(cursor);
		end.add(Cardinal.UP, 4);
		end.add(Cardinal.reverse(dir), 3);
		end.add(orth[1], 4);
		
		editor.fillRectHollow(rand, start, end, wall, true, true);
		
		// middle
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		start.add(orth[0], 6);
		start.add(Cardinal.reverse(dir), 4);
		
		end = new Coord(origin);
		end.add(Cardinal.UP, 6);
		end.add(orth[1], 6);
		end.add(dir, 4);
		
		editor.fillRectHollow(rand, start, end, wall, false, true);
		
	}
	
	private void mainRoom(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin){
		
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(dir, 3);
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		start.add(orth[0], 5);
		end.add(orth[1], 5);
		end.add(Cardinal.UP);
		editor.fillRectSolid(rand, start, end, wall, true, true);
		start.add(Cardinal.reverse(dir), 6);
		end.add(Cardinal.reverse(dir), 6);
		editor.fillRectSolid(rand, start, end, wall, true, true);
		
		for(Cardinal side : orth){
			for(Cardinal o : Cardinal.getOrthogonal(side)){
				cursor = new Coord(origin);
				cursor.add(side, 2);
				cursor.add(o, 3);
				mainPillar(editor, rand, theme, o, cursor);
				cursor.add(side, 3);
				mainPillar(editor, rand, theme, o, cursor);
			}
		}
		
		cursor = new Coord(origin);
		smelterSide(editor, rand, settings, orth[0], origin);
		fireplace(editor, rand, settings, orth[1], origin);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 6);
		overheadLight(editor, rand, settings, cursor);
		
	}
	
	private void mainPillar(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin){
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		end.add(Cardinal.UP, 3);
		editor.fillRectSolid(rand, start, end, pillar, true, true);
		cursor = new Coord(end);
		cursor.add(orth[0]);
		stair.setOrientation(orth[0], true).setBlock(editor, cursor);
		cursor = new Coord(end);
		cursor.add(orth[1]);
		stair.setOrientation(orth[1], true).setBlock(editor, cursor);
		cursor = new Coord(end);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		editor.setBlock(rand, cursor, wall, true, true);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.UP);
		start = new Coord(cursor);
		end = new Coord(cursor);
		end.add(dir, 2);
		editor.fillRectSolid(rand, start, end, wall, true, true);
		cursor = new Coord(end);
		cursor.add(orth[0]);
		stair.setOrientation(orth[0], true).setBlock(editor, cursor);
		cursor = new Coord(end);
		cursor.add(orth[1]);
		stair.setOrientation(orth[1], true).setBlock(editor, cursor);
	}
	
	
	private void smelterSide(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin){
		
		ITheme theme = settings.getTheme();
		IBlockFactory wall = theme.getPrimaryWall();
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		Coord cursor;
		Coord start;
		Coord end;

		start = new Coord(origin);
		start.add(dir, 5);
		end = new Coord(start);
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		editor.fillRectSolid(rand, start, end, wall, true, true);
		start.add(Cardinal.reverse(dir));
		end.add(Cardinal.reverse(dir));
		IStair stair = theme.getPrimaryStair();
		stair = stair.setOrientation(Cardinal.reverse(dir), false);
		editor.fillRectSolid(rand, start, end, stair, true, true);
		
		
		for(Cardinal o : orth){
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(o);
			smelter(editor, rand, settings, dir, cursor);
			
			cursor.add(o, 2);
			editor.setBlock(rand, cursor, wall, true, true);
			cursor.add(dir);
			editor.setBlock(rand, cursor, wall, true, true);
		}
	}
	
	private void smelter(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin){
		Coord cursor;
		Treasure.generate(editor, rand, origin, Treasure.EMPTY, 1, false);
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		cursor.add(Cardinal.UP, 2);
		Treasure.generate(editor, rand, cursor, Treasure.EMPTY, 1, false);
		cursor.add(Cardinal.UP);
		cursor.add(Cardinal.reverse(dir));
		Treasure.generate(editor, rand, cursor, Treasure.EMPTY, 1, false);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		cursor.add(dir);
		Furnace.generate(editor, Cardinal.reverse(dir), cursor);
		
		cursor = new Coord(origin);
		cursor.add(dir);
		Hopper.generate(editor, Cardinal.reverse(dir), cursor);
		
		cursor.add(dir);
		cursor.add(Cardinal.UP);
		Hopper.generate(editor, Cardinal.reverse(dir), cursor);
		
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.UP);
		Hopper.generate(editor, Cardinal.DOWN, cursor);
	}
	
	private void fireplace(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin){
		
		IStair stair = new MetaStair(StairType.BRICK);
		MetaBlock brick = BlockType.get(BlockType.BRICK);
		MetaBlock brickSlab = Slab.get(Slab.BRICK);
		MetaBlock bars = BlockType.get(BlockType.IRON_BAR);
		MetaBlock air = BlockType.get(BlockType.AIR);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(dir, 4);
		end = new Coord(start);
		start.add(Cardinal.DOWN);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(dir, 2);
		end.add(Cardinal.UP, 5);
		
		editor.fillRectSolid(rand, start, end, brick, true, true);
		
		start = new Coord(origin);
		start.add(dir, 5);
		end = new Coord(start);
		end.add(Cardinal.UP, 5);
		editor.fillRectSolid(rand, start, end, air, true, true);
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 4);
		air.setBlock(editor, cursor);
		
		for(Cardinal side : orth){
			
			cursor = new Coord(origin);
			cursor.add(dir, 4);
			cursor.add(side);
			
			stair.setOrientation(Cardinal.reverse(side), false).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(side), true).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(side, false).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			bars.setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			bars.setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(side, true).setBlock(editor, cursor);
					
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(side);
			stair.setOrientation(Cardinal.reverse(dir), false).setBlock(editor, cursor);
			cursor.add(side);
			stair.setOrientation(Cardinal.reverse(dir), false).setBlock(editor, cursor);
			cursor.add(side);
			brick.setBlock(editor, cursor);
			cursor.add(dir);
			brick.setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(side), false).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(side), false).setBlock(editor, cursor);
			
			cursor = new Coord(origin);
			cursor.add(dir, 4);
			cursor.add(side, 2);
			brick.setBlock(editor, cursor);
			cursor.add(dir);
			brick.setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			brick.setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(dir), false).setBlock(editor, cursor);
			cursor.add(Cardinal.DOWN);
			cursor.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), false).setBlock(editor, cursor);
			
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			cursor.add(Cardinal.UP, 5);
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			
		}
		
		MetaBlock netherrack = BlockType.get(BlockType.NETHERRACK);
		MetaBlock fire = BlockType.get(BlockType.FIRE);
		
		start = new Coord(origin);
		start.add(dir, 5);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		editor.fillRectSolid(rand, start, end, netherrack, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP);
		editor.fillRectSolid(rand, start, end, fire, true, true);
		
		cursor = new Coord(origin);
		cursor.add(dir, 3);
		brickSlab.setBlock(editor, cursor);
		cursor.add(dir);
		brickSlab.setBlock(editor, cursor);
		
	}
	
	private void anvilRoom(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin){
		
		ITheme theme = settings.getTheme();
		IStair stair = theme.getPrimaryStair();
		IBlockFactory wall = theme.getPrimaryWall();
		
		
		Coord cursor;
		Coord start;
		Coord end;
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		
		MetaBlock anvil = BlockType.get(BlockType.AIR);
		cursor = new Coord(origin);
		cursor.add(dir);
		anvil.setBlock(editor, cursor);
		
		start = new Coord(origin);
		start.add(orth[1], 2);
		end = new Coord(start);
		start.add(dir, 2);
		end.add(Cardinal.reverse(dir), 2);
		stair.setOrientation(orth[0], false);
		editor.fillRectSolid(rand, start, end, stair, true, true);
		
		cursor = new Coord(origin);
		cursor.add(orth[1], 3);
		editor.setBlock(rand, cursor, wall, true, true);
		cursor.add(dir);
		editor.setBlock(cursor, BlockType.get(BlockType.WATER_FLOWING));
		cursor.add(Cardinal.reverse(dir), 2);
		editor.setBlock(cursor, BlockType.get(BlockType.LAVA_FLOWING));
		
		cursor = new Coord(origin);
		cursor.add(orth[0], 3);
		start = new Coord(cursor);
		end = new Coord(start);
		start.add(dir);
		end.add(Cardinal.reverse(dir));
		stair.setOrientation(orth[1], true);
		editor.fillRectSolid(rand, start, end, stair, true, true);
		cursor.add(Cardinal.UP);
		Treasure.generate(editor, rand, cursor, Treasure.SMITH, Dungeon.getLevel(cursor.getY()));
		
		cursor = new Coord(origin);
	}
	
	
	private void overheadLight(WorldEditor editor, Random rand, LevelSettings settings, Coord origin){
		
		ITheme theme = settings.getTheme();
		IStair stair = theme.getPrimaryStair();
		
		Coord cursor;
		
		editor.setBlock(origin, BlockType.get(BlockType.AIR));
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir);
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.getOrthogonal(dir)[0]);
			stair.setBlock(editor, cursor);
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		editor.setBlock(cursor, BlockType.get(BlockType.REDSTONE_LAMP_LIT));
		cursor.add(Cardinal.UP);
		editor.setBlock(cursor, BlockType.get(BlockType.REDSTONE_BLOCK));
	}
	
	public int getSize(){
		return 9;
	}
	
}
