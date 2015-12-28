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
import greymerk.roguelike.worldgen.blocks.Door;
import greymerk.roguelike.worldgen.blocks.DyeColor;

public class WitchTower implements ITower {

	@Override
	public void generate(IWorldEditor editor, Random rand, ITheme theme, int x, int y, int z) {
		
		IBlockFactory blocks = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		IStair stair = theme.getPrimaryStair();
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock glass = ColorBlock.get(ColorBlock.GLASS, DyeColor.BLACK);
		
		Coord origin = new Coord(x, y, z);
		
		Coord main = Tower.getBaseCoord(editor, x, y, z);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		// main floor
		
		start = new Coord(main);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.WEST, 3);
		start.add(Cardinal.DOWN);
		end = new Coord(main);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.EAST, 3);
		end.add(Cardinal.UP, 3);
		
		editor.fillRectHollow(rand, start, end, blocks, true, true);
		
		for (Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			start = new Coord(main);
			start.add(dir, 3);
			start.add(orth[0], 3);
			start.add(Cardinal.DOWN);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			
			editor.fillRectSolid(rand, start, end, pillar, true, true);
			
			for (Cardinal o : orth){
				start = new Coord(main);
				start.add(dir, 4);
				start.add(o, 2);
				start.add(Cardinal.DOWN);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				
				editor.fillRectSolid(rand, start, end, pillar, true, true);
				cursor = new Coord(end);
				cursor.add(dir);
				stair.setOrientation(dir, true).setBlock(editor, cursor);
				for(Cardinal d : orth){
					cursor = new Coord(end);
					cursor.add(d);
					stair.setOrientation(d, true).setBlock(editor, cursor);
				}
			}
		}
		
		// second floor
		
		Coord secondFloor = new Coord(main);
		secondFloor.add(Cardinal.UP, 4);
		
		start = new Coord(secondFloor);
		start.add(Cardinal.NORTH, 4);
		start.add(Cardinal.WEST, 4);
		start.add(Cardinal.DOWN);
		end = new Coord(secondFloor);
		end.add(Cardinal.SOUTH, 4);
		end.add(Cardinal.EAST, 4);
		end.add(Cardinal.UP, 6);
		
		editor.fillRectHollow(rand, start, end, blocks, true, true);
		
		for (Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			start = new Coord(secondFloor);
			start.add(dir, 4);
			start.add(Cardinal.UP);
			end = new Coord(start);
			start.add(orth[0]);
			end.add(orth[1]);
			end.add(Cardinal.UP);
			editor.fillRectSolid(rand, start, end, glass, true, true);
			
			start = new Coord(secondFloor);
			start.add(dir, 4);
			start.add(Cardinal.DOWN);
			start.add(orth[0], 4);
			end = new Coord(start);
			end.add(Cardinal.UP, 4);
			editor.fillRectSolid(rand, start, end, air, true, true);
			
			for (Cardinal o : orth){
				
				start = new Coord(secondFloor);
				start.add(Cardinal.DOWN);
				start.add(dir, 4);
				start.add(o, 3);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				editor.fillRectSolid(rand, start, end, pillar, true, true);
				
				start = new Coord(secondFloor);
				start.add(Cardinal.DOWN);
				start.add(dir, 5);
				start.add(o, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 4);
				editor.fillRectSolid(rand, start, end, pillar, true, true);
				
				cursor = new Coord(end);
				cursor.add(dir);
				stair.setOrientation(dir, true).setBlock(editor, cursor);
				for(Cardinal d : orth){
					cursor = new Coord(end);
					cursor.add(d);
					stair.setOrientation(d, true).setBlock(editor, cursor);
				}
			}
		}
		
		// third floor
		
		Coord thirdFloor = new Coord(secondFloor);
		thirdFloor.add(Cardinal.UP, 7);
		
		start = new Coord(thirdFloor);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.WEST, 3);
		start.add(Cardinal.DOWN);
		end = new Coord(thirdFloor);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.EAST, 3);
		end.add(Cardinal.UP, 4);
		
		editor.fillRectHollow(rand, start, end, blocks, true, true);
		
		for (Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			cursor = new Coord(thirdFloor);
			cursor.add(dir, 3);
			cursor.add(Cardinal.UP);
			window(editor, rand, theme, dir, cursor);
			
			start = new Coord(thirdFloor);
			start.add(dir, 2);
			end = new Coord(start);
			end.add(dir, 4);
			end.add(Cardinal.DOWN);
			editor.fillRectSolid(rand, start, end, blocks, true, true);
			
			start = new Coord(thirdFloor);
			start.add(dir, 5);
			start.add(Cardinal.DOWN, 2);
			end = new Coord(start);
			start.add(orth[0]);
			end.add(orth[1]);
			end.add(Cardinal.DOWN);
			editor.fillRectSolid(rand, start, end, blocks, true, true);
			
			start = new Coord(thirdFloor);
			start.add(dir, 3);
			start.add(orth[0], 3);
			start.add(Cardinal.DOWN, 2);
			end = new Coord(start);
			end.add(Cardinal.UP, 5);
			editor.fillRectSolid(rand, start, end, pillar, true, true);
			
			cursor = new Coord(end);
			cursor.add(dir);
			stair.setOrientation(dir, true).setBlock(editor, cursor);

			cursor = new Coord(end);
			cursor.add(orth[0]);
			stair.setOrientation(orth[0], true).setBlock(editor, cursor);
			
			for (Cardinal o : orth){

				start = new Coord(thirdFloor);
				start.add(dir, 4);
				start.add(Cardinal.DOWN);
				start.add(o, 3);
				end = new Coord(start);
				end.add(o);
				end.add(Cardinal.DOWN);
				editor.fillRectSolid(rand, start, end, air, true, true);
				
				for(int i = 0; i < 4; ++i){
					start = new Coord(thirdFloor);
					start.add(dir, 4);
					start.add(o, i + 1);
					start.add(Cardinal.DOWN, i);
					end = new Coord(start);
					end.add(dir, 2);
					stair.setOrientation(o, false);
					editor.fillRectSolid(rand, start, end, stair, true, true);
					
					if (i < 3){
						start = new Coord(thirdFloor);
						start.add(dir, 4);
						start.add(o, i + 1);
						start.add(Cardinal.DOWN, i + 1);
						end = new Coord(start);
						end.add(dir, 2);
						editor.fillRectSolid(rand, start, end, blocks, true, true);
					}
					
					start = new Coord(thirdFloor);
					start.add(dir, 4);
					start.add(o, 2);
					start.add(Cardinal.DOWN, 3);
					end = new Coord(start);
					end.add(dir, 2);
					editor.fillRectSolid(rand, start, end, blocks, true, true);
					
					cursor = new Coord(thirdFloor);
					cursor.add(dir, 6);
					cursor.add(o);
					cursor.add(Cardinal.DOWN, 2);
					stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				}
			}
		}
		
		
		
		for(int i = thirdFloor.getY() - 1; i >= 50; --i){
			editor.spiralStairStep(rand, new Coord(origin.getX(), i, origin.getZ()), theme.getPrimaryStair(), theme.getPrimaryPillar());
		}
		
		// attic
		
		Coord attic = new Coord(thirdFloor);
		attic.add(Cardinal.UP, 5);
		
		start = new Coord(attic);
		start.add(Cardinal.NORTH, 2);
		start.add(Cardinal.WEST, 2);
		start.add(Cardinal.DOWN);
		end = new Coord(attic);
		end.add(Cardinal.SOUTH, 2);
		end.add(Cardinal.EAST, 2);
		end.add(Cardinal.UP, 3);
		
		editor.fillRectHollow(rand, start, end, blocks, true, true);
		
		start = new Coord(attic);
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		start.add(Cardinal.NORTH);
		start.add(Cardinal.WEST);
		end.add(Cardinal.SOUTH);
		end.add(Cardinal.EAST);
		editor.fillRectHollow(rand, start, end, blocks, true, true);
		
		start = new Coord(attic);
		start.add(Cardinal.UP, 5);
		end = new Coord(start);
		end.add(Cardinal.UP, 2);
		editor.fillRectHollow(rand, start, end, blocks, true, true);
		
		for (Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			cursor = new Coord(attic);
			cursor.add(dir, 2);
			cursor.add(Cardinal.UP);
			window(editor, rand, theme, dir, cursor);
			
			stair.setOrientation(dir, false);
			
			start = new Coord(attic);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			
			editor.fillRectSolid(rand, start, end, stair, true, true);
			
			start = new Coord(attic);
			start.add(dir, 4);
			start.add(Cardinal.DOWN);
			end = new Coord(start);
			start.add(orth[0], 4);
			end.add(orth[1], 4);
			
			editor.fillRectSolid(rand, start, end, stair, true, true);
			
			start = new Coord(attic);
			start.add(dir, 3);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			start.add(orth[0], 3);
			end.add(orth[1], 3);
			
			editor.fillRectSolid(rand, start, end, stair, true, true);
			
			start = new Coord(attic);
			start.add(dir, 2);
			start.add(Cardinal.UP, 4);
			end = new Coord(start);
			start.add(orth[0], 2);
			end.add(orth[1], 2);
			
			editor.fillRectSolid(rand, start, end, stair, true, true);
			
		}
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(main);
			cursor.add(dir, 4);
			if(editor.isAirBlock(cursor)){
				cursor = new Coord(main);
				cursor.add(dir, 3);
				Door.generate(editor, cursor, dir, Door.OAK);
				cursor.add(dir);
				start = new Coord(cursor);
				end = new Coord(start);
				end.add(Cardinal.UP);
				end.add(dir, 3);
				editor.fillRectSolid(rand, start, end, air, true, true);
				
				cursor = new Coord(main);
				cursor.add(dir, 4);
				cursor.add(Cardinal.DOWN);
				step(editor, rand, theme, dir, cursor);
				break;
			}
		}
		
		
	}
	
	private void window(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin){
		
		Coord cursor;
		
		IStair stair = theme.getPrimaryStair();
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		cursor = new Coord(origin);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		air.setBlock(editor, cursor);
		
		for (Cardinal o : Cardinal.getOrthogonal(dir)){
			cursor = new Coord(origin);
			cursor.add(o);
			stair.setOrientation(Cardinal.reverse(o), false).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
		}
	}
	
	private void step(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin){
		
		if(editor.getBlock(origin).getBlock().isOpaqueCube()) return;
		
		Coord start;
		Coord end;
		
		IStair stair = theme.getPrimaryStair();
		IBlockFactory blocks = theme.getPrimaryWall();
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
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
