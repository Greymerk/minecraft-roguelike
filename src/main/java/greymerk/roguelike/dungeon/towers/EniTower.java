package greymerk.roguelike.dungeon.towers;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Door;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EniTower implements ITower {

	public EniTower(){}
	
	@Override
	public void generate(World world, Random rand, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		IBlockFactory blocks = theme.getPrimaryWall();
		
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord floor = Tower.getBaseCoord(world, x, y, z);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, floor.getY(), z - 4, x + 4, floor.getY() + 3, z + 4, air);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, floor.getY() + 4, z - 3, x + 3, floor.getY() + 12, z + 3, air);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, floor.getY() + 13, z - 2, x + 2, floor.getY() + 21, z + 2, air);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, floor.getY() + 22, z - 3, x + 3, floor.getY() + 28, z + 3, air);

		Coord start;
		Coord end;
		Coord cursor;
		
		for(Cardinal dir : Cardinal.directions){
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				
				start = new Coord(floor);
				end = new Coord(start);
				end.add(dir, 4);
				end.add(orth, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				start = new Coord(floor);
				start.add(dir, 5);
				end = new Coord(start);
				start.add(orth);
				end.add(Cardinal.reverse(orth));
				end.add(Cardinal.UP, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				start = new Coord(floor);
				start.add(dir, 4);
				start.add(orth, 2);
				end = new Coord(start);
				end.add(orth);
				end.add(Cardinal.UP, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				start = new Coord(floor);
				start.add(dir, 3);
				start.add(orth, 3);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				cursor = new Coord(floor);
				cursor.add(dir, 5);
				cursor.add(Cardinal.UP, 3);
				WorldGenPrimitive.setBlock(world, rand, cursor, blocks, true, true);
				cursor.add(orth);
				WorldGenPrimitive.blockOrientation(stair, orth, false).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(orth);
				WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
				cursor.add(orth);
				WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(Cardinal.reverse(orth));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true).setBlock(world, cursor);
				cursor.add(dir);
				cursor.add(Cardinal.reverse(orth));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true).setBlock(world, cursor);
				
				// second section
				
				start = new Coord(floor);
				start.add(Cardinal.UP, 4);
				start.add(dir, 4);
				end = new Coord(start);
				start.add(orth);
				end.add(Cardinal.reverse(orth));
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				start = new Coord(floor);
				start.add(Cardinal.UP, 4);
				start.add(dir, 3);
				start.add(orth, 2);
				end = new Coord(start);
				end.add(orth);
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				cursor = new Coord(floor);
				cursor.add(Cardinal.UP, 13);
				cursor.add(dir, 4);
				WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
				cursor.add(orth);
				WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(orth);
				WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
				
				// section 3
				
				start = new Coord(floor);
				start.add(Cardinal.UP, 13);
				start.add(dir, 3);
				end = new Coord(start);
				start.add(orth);
				end.add(Cardinal.reverse(orth));
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				start = new Coord(floor);
				start.add(Cardinal.UP, 13);
				start.add(dir, 2);
				start.add(orth, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 8);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				// section 4
				
				start = new Coord(floor);
				start.add(Cardinal.UP, 22);
				start.add(dir, 4);
				end = new Coord(start);
				start.add(orth, 2);
				end.add(Cardinal.reverse(orth), 2);
				end.add(Cardinal.UP, 6);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, false);
				
				start = new Coord(floor);
				start.add(Cardinal.UP, 22);
				start.add(dir, 3);
				start.add(orth, 2);
				end = new Coord(start);
				end.add(Cardinal.UP, 6);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				start = new Coord(floor);
				start.add(Cardinal.UP, 22);
				end = new Coord(start);
				end.add(dir, 3);
				end.add(orth, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				cursor = new Coord(floor);
				cursor.add(Cardinal.UP, 20);
				cursor.add(dir, 3);
				cursor.add(orth, 2);
				WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.setBlock(world, rand, cursor, blocks, true, true);
				cursor.add(dir);
				WorldGenPrimitive.blockOrientation(stair, dir, true).setBlock(world, cursor);
				
				// section 4 roof
				
				MetaBlock roof = theme.getSecondaryStair();
				start = new Coord(floor);
				start.add(Cardinal.UP, 29);
				start.add(dir, 3);
				end = new Coord(start);
				end.add(dir, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryWall(), true, true);
				start.add(orth);
				end.add(orth);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(roof, orth, false), true, true);
				start.add(orth);
				end.add(orth);
				start.add(Cardinal.DOWN);
				end.add(Cardinal.DOWN);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(roof, orth, false), true, true);
				start.add(orth);
				end.add(orth);
				start.add(Cardinal.DOWN);
				end.add(Cardinal.DOWN);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(roof, orth, false), true, true);
				cursor = new Coord(end);
				cursor.add(Cardinal.reverse(orth));
				WorldGenPrimitive.blockOrientation(roof, Cardinal.reverse(orth), true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(orth));
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(roof, Cardinal.reverse(orth), true).setBlock(world, cursor);
				
				cursor.add(Cardinal.reverse(dir), 3);
				cursor.add(orth);
				WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
				
				// tower top
				start = new Coord(floor);
				start.add(Cardinal.UP, 29);
				end = new Coord(start);
				start.add(dir, 2);
				start.add(orth);
				end.add(dir, 2);
				end.add(Cardinal.reverse(orth));
				end.add(Cardinal.UP, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);

				
				cursor = new Coord(floor);
				cursor.add(Cardinal.UP, 33);
				cursor.add(dir, 3);
				WorldGenPrimitive.blockOrientation(roof, dir, false).setBlock(world, cursor);
				cursor.add(orth);
				WorldGenPrimitive.blockOrientation(roof, dir, false).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				cursor.add(orth);
				WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
				cursor.add(Cardinal.reverse(orth));
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(roof, orth, false).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(orth));
				WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(roof, dir, false).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
				cursor.add(Cardinal.DOWN);
				WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
				cursor.add(orth);
				WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
				cursor.add(Cardinal.UP);
				cursor.add(Cardinal.reverse(orth));
				WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(roof, dir, false).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(dir));
				WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
			}
		}

		// mid floors
		start = new Coord(floor);
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		start.add(Cardinal.NORTH, 3);
		start.add(Cardinal.EAST, 3);
		end.add(Cardinal.SOUTH, 3);
		end.add(Cardinal.WEST, 3);
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
		start.add(Cardinal.UP, 3);
		end.add(Cardinal.UP, 3);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
		start.add(Cardinal.UP, 3);
		end.add(Cardinal.UP, 3);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
		

		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			// lower windows
			cursor = new Coord(floor);
			cursor.add(dir, 4);
			cursor.add(Cardinal.UP, 4);
			MetaBlock window = ColorBlock.get(Blocks.stained_glass_pane, rand);
			for(int i = 0; i < 3; i++){
				WorldGenPrimitive.blockOrientation(stair, dir, false).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				window.setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				window.setBlock(world, cursor);
				cursor.add(Cardinal.UP);
			}
			
			// floor before slit windows
			cursor.add(Cardinal.reverse(dir), 2);
			start = new Coord(cursor);
			start.add(orth[0]);
			end = new Coord(cursor);
			end.add(orth[1]);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
			
			// slit windows
			cursor = new Coord(floor);
			cursor.add(Cardinal.UP, 14);
			cursor.add(dir, 3);
			cursor.add(orth[0]);
			WorldGenPrimitive.blockOrientation(stair, orth[1], false).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, orth[1], true).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			cursor.add(orth[1]);
			WorldGenPrimitive.blockOrientation(stair, orth[0], false).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, orth[0], true).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, orth[1], false).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, orth[1], true).setBlock(world, cursor);
			cursor.add(orth[1]);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, orth[0], false).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, orth[0], true).setBlock(world, cursor);
			
			// top windows
			
			cursor = new Coord(floor);
			cursor.add(Cardinal.UP, 23);
			cursor.add(dir, 4);
			window.setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			window.setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			window.setBlock(world, cursor);
			cursor.add(Cardinal.DOWN);
			cursor.add(orth[0]);
			window.setBlock(world, cursor);
			cursor.add(orth[1], 2);
			window.setBlock(world, cursor);
			
			// top ceiling
			cursor = new Coord(floor);
			cursor.add(Cardinal.UP, 26);
			cursor.add(dir, 3);
			start = new Coord(cursor);
			start.add(orth[0]);
			end = new Coord(cursor);
			end.add(orth[1]);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
			start.add(Cardinal.reverse(dir));
			end.add(Cardinal.reverse(dir));
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
			
			cursor.add(Cardinal.reverse(dir));
			cursor.add(orth[0], 2);
			WorldGenPrimitive.setBlock(world, rand, cursor, blocks, true, true);
		}
		
		start = new Coord(x - 4, 60, z - 4);
		end = new Coord(x + 4, floor.getY(), z + 4);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
		
		for(int i = (floor.getY() + 22); i >= 50; --i){
			WorldGenPrimitive.spiralStairStep(world, rand, new Coord(x, i, z), stair, theme.getPrimaryPillar());
		}
		
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(floor);
			cursor.add(Cardinal.UP);
			cursor.add(dir, 6);
			if(world.isAirBlock(cursor.getBlockPos())){
				cursor = new Coord(floor);
				cursor.add(Cardinal.UP);
				cursor.add(dir, 5);
				Door.generate(world, cursor, dir, Door.OAK);
				cursor.add(dir);
				start = new Coord(cursor);
				end = new Coord(start);
				end.add(Cardinal.UP);
				end.add(dir, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
				break;
			}
		}



		
	}
}
