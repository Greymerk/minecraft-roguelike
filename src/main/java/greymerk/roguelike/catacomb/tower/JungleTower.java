package greymerk.roguelike.catacomb.tower;

import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class JungleTower implements ITower{

	@Override
	public void generate(World world, Random rand, ITheme theme, int x, int y, int z) {
		
		Coord origin = Tower.getBaseCoord(world, x, y, z);
		Coord cursor;
		
		base(world, rand, theme, origin);
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		top(world, rand, theme, cursor);
	}
	
	private void base(World world, Random rand, ITheme theme, Coord origin){
		
		Coord start;
		Coord end;
		MetaBlock air = new MetaBlock(Blocks.air);

		
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.NORTH, 10);
		start.add(Cardinal.WEST, 10);
		end.add(Cardinal.SOUTH, 10);
		end.add(Cardinal.EAST, 10);
		end.add(Cardinal.UP, 3);
		
		start = new Coord(start.getX(), 60, start.getZ());
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
		
		MetaBlock stair = theme.getPrimaryStair();
		
		BlockStripes nsStairs = new BlockStripes();
		nsStairs.addBlock(new MetaBlock(WorldGenPrimitive.blockOrientation(stair, Cardinal.NORTH, false)));
		nsStairs.addBlock(new MetaBlock(WorldGenPrimitive.blockOrientation(stair, Cardinal.SOUTH, false)));

		
		BlockStripes ewStairs = new BlockStripes();
		ewStairs.addBlock(new MetaBlock(WorldGenPrimitive.blockOrientation(stair, Cardinal.EAST, false)));
		ewStairs.addBlock(new MetaBlock(WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, false)));
		
		for (Cardinal dir : Cardinal.directions){
			
			IBlockFactory stairs;
			
			if(dir == Cardinal.NORTH || dir == Cardinal.SOUTH){
				stairs = ewStairs;
			} else {
				stairs = nsStairs;
			}
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			for (Cardinal o : orth){
				start = new Coord(origin);
				start.add(dir, 8);
				start.add(o, 5);
				end = new Coord(start);
				end.add(dir, 2);
				end.add(o, 3);
				end.add(Cardinal.UP, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
				start.add(dir);
				end.add(Cardinal.reverse(dir));
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, stairs, true, true);

			}
		}
	}
	
	private void top(World world, Random rand, ITheme theme, Coord origin){
		Coord start;
		Coord end;
		Coord cursor;
		
		IBlockFactory blocks = theme.getPrimaryWall();
		IBlockFactory pillar = theme.getPrimaryPillar();
		MetaBlock stair = theme.getPrimaryStair();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		start = new Coord(origin);
		end = new Coord(origin);
		
		start.add(Cardinal.NORTH, 6);
		start.add(Cardinal.WEST, 6);
		start.add(Cardinal.DOWN);
		
		end.add(Cardinal.SOUTH, 6);
		end.add(Cardinal.EAST, 6);
		end.add(Cardinal.UP, 3);
		
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, theme.getPrimaryWall(), true, true);
		
		for(int i = origin.getY() - 1; i > 55; --i){
			WorldGenPrimitive.spiralStairStep(world, rand, origin.getX(), i, origin.getZ(), theme.getPrimaryStair(), theme.getPrimaryPillar());
		}
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			start = new Coord(origin);
			start.add(dir, 6);
			end = new Coord(start);
			start.add(orth[0]);
			end.add(orth[1]);
			end.add(Cardinal.UP, 2);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
			
			for(Cardinal o : orth){
				cursor = new Coord(origin);
				cursor.add(dir, 5);
				cursor.add(o, 2);
				start = new Coord(cursor);
				end = new Coord(cursor);
				end.add(Cardinal.UP, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
				start.add(o, 2);
				end.add(o, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
				
				Coord temp = new Coord(cursor);
				
				cursor.add(o);
				WorldGenPrimitive.blockOrientation(stair, o, false).setBlock(world, cursor);
				cursor.add(o);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(o));
				WorldGenPrimitive.blockOrientation(stair, o, true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				blocks.setBlock(world, rand, cursor);
				cursor.add(o);
				blocks.setBlock(world, rand, cursor);
				
				cursor = new Coord(temp);
				cursor.add(Cardinal.reverse(dir));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(world, cursor);
				cursor.add(o);
				WorldGenPrimitive.blockOrientation(stair, o, false).setBlock(world, cursor);
				cursor.add(o);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				pillar.setBlock(world, rand, cursor);
				cursor.add(Cardinal.reverse(o));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(o));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
				cursor.add(Cardinal.reverse(o));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
				cursor.add(dir);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
				cursor.add(dir);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
				
				start = new Coord(origin);
				start.add(dir, 6);
				start.add(o, 3);
				end = new Coord(start);
				end.add(o);
				end.add(Cardinal.UP, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
				
				
			}
			
			cursor = new Coord(origin);
			cursor.add(Cardinal.DOWN);
			cursor.add(dir, 11);
			step(world, rand, theme, dir, cursor);
		}
	}
	
	private void step(World world, Random rand, ITheme theme, Cardinal dir, Coord origin){
		
		if(world.getBlock(origin.getX(), origin.getY(), origin.getZ()).isOpaqueCube()) return;
		
		Coord start;
		Coord end;
		
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory blocks = theme.getPrimaryWall();
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		end = new Coord(end.getX(), 60, end.getZ());
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(orth[0]);
		end.add(orth[1]);
		WorldGenPrimitive.blockOrientation(stair, dir, false);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, stair, true, true);
		
		origin.add(Cardinal.DOWN);
		origin.add(dir);
		step(world, rand, theme, dir, origin);
	}
}
