package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.DyeColor;

public class BunkerTower implements ITower{

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord dungeon) {
		
		Coord origin = Tower.getBaseCoord(editor, dungeon);
		origin.add(Cardinal.UP);
		Coord cursor;
		Coord start;
		Coord end;
		
		IBlockFactory walls = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		MetaBlock window = ColorBlock.get(ColorBlock.PANE, DyeColor.GRAY);
		
		start = new Coord(origin);
		end = new Coord(start);
		start.add(Cardinal.DOWN);
		start.add(Cardinal.NORTH, 5);
		start.add(Cardinal.EAST, 5);
		end.add(Cardinal.SOUTH, 5);
		end.add(Cardinal.WEST, 5);
		end.add(Cardinal.UP, 4);
		walls.fillRectHollow(editor, rand, start, end, true, true);
		
		start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
		end = new Coord(origin);
		end.add(Cardinal.DOWN);
		start.add(Cardinal.NORTH, 5);
		start.add(Cardinal.EAST, 5);
		end.add(Cardinal.SOUTH, 5);
		end.add(Cardinal.WEST, 5);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(dir, 5);
			end = new Coord(start);
			start.add(orth[0]);
			end.add(orth[1]);
			start = new Coord(start.getX(), dungeon.getY() + 10, start.getZ());
			end.add(Cardinal.UP, 3);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			end.add(Cardinal.DOWN);
			end.add(dir);
			start.add(dir);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			end.add(Cardinal.DOWN);
			end.add(dir);
			start.add(dir);
			walls.fillRectSolid(editor, rand, start, end, true, true);
		}
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(dir, 5);
			cursor.add(orth[0], 5);
			start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
			start.add(dir, 6);
			start.add(orth[0], 6);
			end = new Coord(origin);
			end.add(dir, 6);
			end.add(orth[0], 6);
			end.add(Cardinal.UP, 2);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			start.add(dir);
			start.add(orth[0]);
			end.add(Cardinal.DOWN);
			end.add(dir);
			end.add(orth[0]);
			walls.fillRectSolid(editor, rand, start, end, true, true);
			
			
			
			for(Cardinal o : orth){
				start = new Coord(origin.getX(), dungeon.getY() + 10, origin.getZ());
				start.add(dir, 5);
				start.add(o, 5);
				end = new Coord(origin);
				end.add(dir, 5);
				end.add(o, 5);
				end.add(Cardinal.UP, 2);
				end.add(o, 2);
				walls.fillRectSolid(editor, rand, start, end, true, true);
			}
		}	
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			stair.setOrientation(dir, false);
			for(Cardinal o : orth){
				start = new Coord(origin);
				start.add(dir, 6);
				start.add(o, 6);
				start.add(Cardinal.UP, 3);
				end = new Coord(start);
				end.add(Cardinal.reverse(o));
				stair.fillRectSolid(editor, rand, start, end, true, true);
				start.add(Cardinal.DOWN);
				start.add(dir);
				start.add(o);
				end = new Coord(start);
				end.add(Cardinal.reverse(o), 2);
				stair.fillRectSolid(editor, rand, start, end, true, true);
			}
		}
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 3);
			cursor.add(dir, 6);
			stair.setOrientation(dir, false).setBlock(editor, cursor);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				stair.setOrientation(dir, false).setBlock(editor, c);
			}
			cursor.add(Cardinal.DOWN);
			cursor.add(dir);
			stair.setOrientation(dir, false).setBlock(editor, cursor);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				stair.setOrientation(dir, false).setBlock(editor, c);
			}	
		}
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 4);
			cursor.add(dir, 5);
			start = new Coord(cursor);
			end = new Coord(start);
			start.add(orth[0], 5);
			end.add(orth[1], 5);
			stair.setOrientation(dir, false).fillRectSolid(editor, rand, start, end, true, true);
		}
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 5);
			cursor.add(dir, 4);
			stair.setOrientation(dir, false).setBlock(editor, cursor);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				pillar.setBlock(editor, rand, c);
				c.add(o);
				stair.setOrientation(dir, false).setBlock(editor, c);
				c.add(o);
				pillar.setBlock(editor, rand, c);
			}
			cursor.add(Cardinal.UP);
			window.setBlock(editor, cursor);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				pillar.setBlock(editor, rand, c);
				c.add(o);
				window.setBlock(editor, c);
				c.add(o);
				pillar.setBlock(editor, rand, c);
			}
			cursor.add(Cardinal.UP);
			start = new Coord(cursor);
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			stair.setOrientation(dir, false).fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.reverse(dir));
			end.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true).fillRectSolid(editor, rand, start, end, true, true);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			start.add(orth[1]);
			end.add(orth[0]);
			stair.setOrientation(dir, false).fillRectSolid(editor, rand, start, end, true, true);
			stair.setOrientation(orth[0], false).setBlock(editor, start);
			stair.setOrientation(orth[1], false).setBlock(editor, end);
			start.add(Cardinal.reverse(dir));
			end.add(Cardinal.reverse(dir));
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			start.add(orth[1]);
			end.add(orth[0]);
			stair.setOrientation(dir, false).fillRectSolid(editor, rand, start, end, true, true);
			stair.setOrientation(orth[0], false).setBlock(editor, start);
			stair.setOrientation(orth[1], false).setBlock(editor, end);
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 8);
		start = new Coord(cursor);
		end = new Coord(cursor);
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.EAST, 2);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.WEST, 2);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		cursor.add(Cardinal.UP);
		start = new Coord(cursor);
		end = new Coord(cursor);
		start.add(Cardinal.NORTH);
		start.add(Cardinal.EAST);
		end.add(Cardinal.SOUTH);
		end.add(Cardinal.WEST);
		walls.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(Cardinal.UP, 3);
			start.add(dir, 4);
			end = new Coord(start);
			start.add(orth[0], 4);
			end.add(orth[1], 4);
			stair.setOrientation(Cardinal.reverse(dir), true).fillRectSolid(editor, rand, start, end, true, true);
		}

		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(dir, 4);
			start.add(orth[0], 4);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
		}
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(Cardinal.UP, 5);
			start.add(dir, 3);
			start.add(orth[0], 3);
			end = new Coord(start);
			end.add(Cardinal.UP, 2);
			pillar.fillRectSolid(editor, rand, start, end, true, true);
		}
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 2);
			cursor.add(dir, 5);
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			BlockType.get(BlockType.REDSTONE_BLOCK).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(dir));
			BlockType.get(BlockType.REDSTONE_LAMP_LIT).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, c);
				c.add(dir);
				stair.setOrientation(o, true).setBlock(editor, c);
			}
		}	
		
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(dir, 5);
			end = new Coord(start);
			end.add(Cardinal.UP);
			end.add(dir, 3);
			BlockType.get(BlockType.AIR).fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(start);
			for(Cardinal o : orth){
				start = new Coord(cursor);
				start.add(o, 2);
				start.add(Cardinal.UP);
				end = new Coord(start);
				end.add(o);
				stair.setOrientation(dir, false).fillRectSolid(editor, rand, start, end, true, true);
				start.add(Cardinal.UP);
				end.add(Cardinal.UP);
				window.fillRectSolid(editor, rand, start, end, true, true);
				start.add(Cardinal.DOWN, 2);
				end.add(Cardinal.DOWN, 2);
				start.add(Cardinal.reverse(dir));
				end.add(Cardinal.reverse(dir));
				walls.fillRectSolid(editor, rand, start, end, true, true);
				start.add(Cardinal.reverse(dir));
				end.add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(dir), false).fillRectSolid(editor, rand, start, end, true, true);
			}
			
			cursor = new Coord(origin);
			cursor.add(dir, 3);
			for(Cardinal o : orth){
				Coord c = new Coord(cursor);
				c.add(o);
				stair.setOrientation(Cardinal.reverse(o), false).setBlock(editor, c);
				c.add(dir);
				stair.setOrientation(Cardinal.reverse(o), false).setBlock(editor, c);
			}
			
			
		}		
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		start = new Coord(cursor.getX(), dungeon.getY(), cursor.getZ());
		end = new Coord(cursor);
		for(Coord c : editor.getRectSolid(start, end)){
			editor.spiralStairStep(rand, c, stair, pillar);
		}
	}
}
