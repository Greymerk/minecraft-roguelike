package greymerk.roguelike.dungeon.rooms;

import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Door;
import greymerk.roguelike.worldgen.blocks.StairType;


public class DungeonLibrary extends DungeonBase{

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		IBlockFactory walls = settings.getTheme().getPrimaryWall();
		
		IStair stair = settings.getTheme().getPrimaryStair();
		
		MetaBlock air = BlockType.get(BlockType.AIR);

		Coord cursor;
		Coord start;
		Coord end;
		
		
		editor.fillRectSolid(rand, x - 4, y, z - 4, x + 4, y + 3, z + 4, air, true, true);
		editor.fillRectSolid(rand, x - 3, y + 4, z - 3, x + 3, y + 6, z + 3, air, true, true);
		editor.fillRectSolid(rand, x - 2, y + 7, z - 2, x + 2, y + 7, z + 2, air, true, true);
		
		editor.fillRectHollow(rand, x - 5, y, z - 5, x + 5, y + 4, z + 5, walls, false, true);
		editor.fillRectHollow(rand, x - 4, y + 3, z - 4, x + 4, y + 7, z + 4, walls, false, true);
		editor.fillRectHollow(rand, x - 3, y + 6, z - 3, x + 3, y + 8, z + 3, walls, false, true);
		
		editor.fillRectSolid(rand, x - 5, y - 1, z - 5, x + 5, y - 1, z + 5, settings.getTheme().getPrimaryFloor(), true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 4);
		editor.setBlock(start, BlockType.get(BlockType.REDSTONE_LAMP_LIT));
		start.add(Cardinal.UP);
		editor.setBlock(start, BlockType.get(BlockType.REDSTONE_BLOCK));
		start.add(Cardinal.UP);
		end = new Coord(start);
		end.add(Cardinal.UP);
		editor.fillRectSolid(rand, start, end, settings.getTheme().getPrimaryPillar(), true, true);
		
		
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			if(Arrays.asList(entrances).contains(dir)){
				door(editor, rand, settings.getTheme(), dir, origin);
			} else {
				desk(editor, rand, settings.getTheme(), dir, origin);
			}
			
			start = new Coord(origin);
			start.add(dir, 4);
			start.add(orth[0], 4);
			end = new Coord(start);
			end.add(Cardinal.UP, 4);
			editor.fillRectSolid(rand, start, end, settings.getTheme().getPrimaryPillar(), true, true);
			
			start = new Coord(origin);
			start.add(dir, 3);
			start.add(orth[0], 3);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			editor.fillRectSolid(rand, start, end, settings.getTheme().getPrimaryPillar(), true, true);
			
			cursor = new Coord(end);
			cursor.add(Cardinal.reverse(dir));
			cursor.add(orth[1]);
			cursor.add(Cardinal.UP);
			editor.setBlock(rand, cursor, walls, true, true);
			
			for(Cardinal o : orth){
				cursor = new Coord(origin);
				cursor.add(dir, 4);
				cursor.add(o, 3);
				cursor.add(Cardinal.UP, 2);
				
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				cursor.add(Cardinal.UP);
				editor.setBlock(rand, cursor, walls, true, true);
				cursor.add(Cardinal.reverse(o));
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				cursor.add(Cardinal.UP, 3);
				cursor.add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				
			}
			
			// Light fixture related stuff
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 4);
			cursor.add(dir);
			stair.setOrientation(dir, true).setBlock(editor, cursor);
			cursor.add(dir, 2);
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			start = new Coord(cursor);
			end = new Coord(cursor);
			start.add(Cardinal.reverse(dir), 2);
			editor.fillRectSolid(rand, start, end, walls, true, true);
			cursor.add(Cardinal.UP);
			editor.setBlock(rand, cursor, walls, true, true);
			cursor.add(Cardinal.UP);
			cursor.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(dir));
			stair.setOrientation(dir, true).setBlock(editor, cursor);
		}
		
		
		
		
		return false;
	}

	private void door(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord pos){
		Coord start;
		Coord end;
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(pos);
		start.add(dir, 7);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		
		editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
		
		Coord cursor = new Coord(pos);
		cursor.add(dir, 7);
		Door.generate(editor, cursor, dir, Door.OAK);
		
		for(Cardinal o : orth){
			
			cursor = new Coord(pos);
			cursor.add(dir, 5);
			cursor.add(o);
			cursor.add(Cardinal.UP, 2);
			
			IStair stair = theme.getPrimaryStair();
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(dir);
			stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
		}
	}
	
	private void desk(WorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord pos){
		Coord start;
		Coord end;
		Coord cursor;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		MetaBlock shelf = BlockType.get(BlockType.SHELF);

		for(Cardinal o : orth){
			start = new Coord(pos);
			start.add(dir, 5);
			start.add(o, 2);
			end = new Coord(start);
			end.add(Cardinal.UP, 2);
			editor.fillRectSolid(rand, start, end, shelf, true, true);
		}
		
		start = new Coord(pos);
		start.add(dir, 6);
		end = new Coord(start);
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		editor.fillRectSolid(rand, start, end, theme.getSecondaryWall(), true, true);
		
		start.add(Cardinal.UP);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, shelf, true, true);
		
		start.add(Cardinal.reverse(dir));
		end.add(Cardinal.reverse(dir));
		editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.AIR), true, true);
		
		cursor = new Coord(pos);
		cursor.add(dir, 4);
		
		IStair stair = new MetaStair(StairType.OAK);
		stair.setOrientation(dir, false).setBlock(editor, cursor);
		
		cursor.add(dir);
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
		
		cursor.add(orth[0]);
		stair.setOrientation(orth[1], true).setBlock(editor, cursor);
		
		cursor.add(orth[1], 2);
		stair.setOrientation(orth[0], true).setBlock(editor, cursor);
		
		
	}
	
	@Override
	public int getSize() {
		return 8;
	}

}
