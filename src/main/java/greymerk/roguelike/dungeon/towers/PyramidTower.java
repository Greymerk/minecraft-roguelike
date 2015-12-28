package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class PyramidTower implements ITower{

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z) {

		Coord floor = Tower.getBaseCoord(editor, x, y, z);
		floor.add(Cardinal.UP);
		IBlockFactory blocks = theme.getPrimaryWall();
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(x - 8, floor.getY() - 1, z - 8);
		end = new Coord(x + 8, y + 10, z + 8);
		editor.fillRectSolid(rand, start, end, blocks, true, true);
		
		start = new Coord(x - 6, floor.getY() - 1, z - 6);
		end = new Coord(x + 6, floor.getY() + 3, z + 6);
		editor.fillRectHollow(rand, start, end, blocks, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(floor);
			cursor.add(dir, 6);
			wall(editor, rand, theme, dir, cursor);
			cursor.add(Cardinal.getOrthogonal(dir)[0], 6);
			corner(editor, rand, theme, dir, cursor);
		}
		
		cursor = new Coord(floor);
		cursor.add(Cardinal.EAST, 6);
		entrance(editor, rand, theme, Cardinal.EAST, cursor);
		
		cursor = new Coord(floor);
		cursor.add(Cardinal.UP, 4);
		spire(editor, rand, theme, cursor);
		
		for(int i = floor.getY() + 3; i >= y; --i){
			editor.spiralStairStep(rand, new Coord(x, i, z), theme.getPrimaryStair(), theme.getPrimaryPillar());
		}
		
	}
	
	private void entrance(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin) {

		IBlockFactory blocks = theme.getPrimaryWall();
		MetaBlock air = BlockType.get(BlockType.AIR);
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 3);
		end = new Coord(start);
		end.add(Cardinal.reverse(dir));
		start.add(orth[0]);
		end.add(orth[1]);
		blocks.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal o : orth){
			start = new Coord(origin);
			start.add(dir);
			start.add(o, 2);
			end = new Coord(start);
			end.add(Cardinal.reverse(dir));
			end.add(Cardinal.UP, 3);
			blocks.fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(o, 2);
			blocks.setBlock(editor, rand, cursor);
			cursor.add(Cardinal.UP);
			blocks.setBlock(editor, rand, cursor);
		}
		
		// door
		start = new Coord(origin);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir));
		end.add(dir);
		end.add(Cardinal.UP);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(dir);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		air.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 2);
		blocks.setBlock(editor, rand, cursor);
		
		// door cap
		start = new Coord(origin);
		start.add(Cardinal.UP, 3);
		start.add(dir);
		end = new Coord(start);
		end.add(Cardinal.UP, 2);
		start.add(orth[0]);
		end.add(orth[1]);
		blocks.fillRectSolid(editor, rand, start, end, true, true);		
		
		cursor = new Coord(origin);
		cursor.add(dir);
		cursor.add(Cardinal.UP, 4);
		BlockType.get(BlockType.LAPIS_BLOCK).setBlock(editor, cursor);
		
		cursor.add(Cardinal.UP, 2);
		blocks.setBlock(editor, rand, cursor);
		cursor.add(Cardinal.UP);
		blocks.setBlock(editor, rand, cursor);
	}

	private void spire(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {
		IBlockFactory blocks = theme.getPrimaryWall();
		MetaBlock air = BlockType.get(BlockType.AIR);
		Coord cursor;
		Coord start;
		Coord end;
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			// outer wall
			start = new Coord(origin);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			end.add(Cardinal.UP, 2);
			blocks.fillRectSolid(editor, rand, start, end, true, true);
			
			// doors
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			air.setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			air.setBlock(editor, cursor);
			
			// wall cap
			start = new Coord(origin);
			start.add(dir, 2);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			start.add(orth[0]);
			end.add(orth[1]);
			end.add(dir);
			blocks.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(dir);
			start.add(Cardinal.UP, 4);
			end = new Coord(start);
			end.add(dir, 2);
			blocks.fillRectSolid(editor, rand, start, end, true, true);
			
			// corner spikes
			start = new Coord(origin);
			start.add(dir, 3);
			start.add(orth[0], 3);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			end.add(Cardinal.UP);
			blocks.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(dir, 2);
			start.add(orth[0], 2);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			end.add(Cardinal.UP, 4);
			blocks.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(dir);
			start.add(orth[0]);
			start.add(Cardinal.UP, 4);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			blocks.fillRectSolid(editor, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(dir);
			start.add(Cardinal.UP, 7);
			end = new Coord(start);
			end.add(Cardinal.UP, 2);
			blocks.fillRectSolid(editor, rand, start, end, true, true);
			

		}
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 7);
		end = new Coord(start);
		end.add(Cardinal.UP, 6);
		blocks.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 7);
		BlockType.get(BlockType.GLOWSTONE).setBlock(editor, cursor);
		
	}

	private void wall(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord pos) {
		IBlockFactory blocks = theme.getPrimaryWall();
		MetaBlock air = BlockType.get(BlockType.AIR);
		Coord cursor;
		Coord start;
		Coord end;
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		// upper wall lip
		start = new Coord(pos);
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		start.add(orth[0], 5);
		end.add(orth[1], 5);
		blocks.fillRectSolid(editor, rand, start, end, true, true);
		
		// inner wall
		start = new Coord(pos);
		start.add(Cardinal.reverse(dir));
		end = new Coord(start);
		end.add(Cardinal.reverse(dir));
		end.add(Cardinal.UP, 2);
		start.add(orth[0], 4);
		end.add(orth[1], 4);
		blocks.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(pos);
		cursor.add(Cardinal.reverse(dir), 2);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		air.setBlock(editor, cursor);
		
		for(Cardinal o : orth){
			Coord c2 = new Coord(pos);
			for(int i = 0; i < 5; ++i){
				if(i % 2 == 0){
					cursor = new Coord(c2);
					cursor.add(Cardinal.UP, 5);
					blocks.setBlock(editor, rand, cursor);

					start = new Coord(c2);
					start.add(Cardinal.UP);
					end = new Coord(start);
					end.add(Cardinal.UP, 2);
					air.fillRectSolid(editor, rand, start, end, true, true);
				} else {
					cursor = new Coord(c2);
					cursor.add(dir);
					blocks.setBlock(editor, rand, cursor);
					cursor.add(Cardinal.UP);
					blocks.setBlock(editor, rand, cursor);
				}
				c2.add(o);
			}
			
			cursor = new Coord(pos);
			cursor.add(Cardinal.reverse(dir), 2);
			cursor.add(o, 2);
			air.setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			air.setBlock(editor, cursor);
		}
	}

	private void corner(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord pos){
		
		IBlockFactory blocks = theme.getPrimaryWall();
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] faces = {dir, Cardinal.getOrthogonal(dir)[0]};
		
		for(Cardinal face : faces){
			start = new Coord(pos);
			start.add(face);
			end = new Coord(start);
			end.add(Cardinal.getOrthogonal(face)[0]);
			start.add(Cardinal.getOrthogonal(face)[1]);
			end.add(Cardinal.UP);
			blocks.fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(pos);
			cursor.add(face, 2);
			blocks.setBlock(editor, rand, cursor);
			cursor.add(Cardinal.UP);
			blocks.setBlock(editor, rand, cursor);
			
			cursor = new Coord(pos);
			cursor.add(face);
			cursor.add(Cardinal.UP, 2);
			blocks.setBlock(editor, rand, cursor);
			cursor.add(Cardinal.UP);
			blocks.setBlock(editor, rand, cursor);
		}
		
		start = new Coord(pos);
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		end.add(Cardinal.UP, 2);
		blocks.fillRectHollow(editor, rand, start, end, true, true);
	}


}
