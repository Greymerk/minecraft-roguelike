package greymerk.roguelike.catacomb.dungeon.room;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

public class DungeonObsidian implements IDungeon {

	private static MetaBlock obs = new MetaBlock(Block.obsidian.blockID);
	private static MetaBlock brick = new MetaBlock(Block.netherBrick.blockID);
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		HashSet<Coord> spawners = new HashSet<Coord>();
		
		// space
		WorldGenPrimitive.fillRectSolid(world, x - 10, y - 3, z - 10, x + 10, y + 3, z + 10, 0);
		
		
		// roof
		WorldGenPrimitive.fillRectSolid(world, x - 7, y + 6, z - 7, x + 7, y + 6, z + 7, Block.obsidian.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 8, y + 5, z - 8, x + 8, y + 5, z + 8, Block.obsidian.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 9, y + 4, z - 9, x + 9, y + 4, z + 9, Block.obsidian.blockID);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 3, z - 1, x + 1, y + 5, z + 1, 0);
		WorldGenPrimitive.setBlock(world, x, y + 5, z, obs);
		spawners.add(new Coord(x, y + 4, z));
		
		
		// foundation
		WorldGenPrimitive.fillRectSolid(world, x - 10, y - 4, z - 10, x + 10, y - 4, z + 10, Block.obsidian.blockID);
		
		// ceiling holes
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				Coord start = new Coord(x, y, z);
				start.add(Cardinal.UP, 3);
				start.add(dir, 3);
				start.add(orth, 3);
				Coord end = new Coord(start);
				end.add(Cardinal.UP, 2);
				end.add(dir, 2);
				end.add(orth, 2);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(0), true, true);

				start = new Coord(x, y, z);
				start.add(dir, 3);
				start.add(Cardinal.UP, 3);
				end = new Coord(start);
				end.add(dir, 2);
				start.add(orth, 1);
				end.add(Cardinal.UP, 2);
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(0), true, true);
				
				Coord cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 4);
				cursor.add(dir, 4);
				spawners.add(new Coord(cursor));
				cursor.add(orth, 4);
				spawners.add(new Coord(cursor));
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 5);
				cursor.add(dir, 4);
				WorldGenPrimitive.setBlock(world, cursor, obs, true, true);
				cursor.add(orth, 4);
				WorldGenPrimitive.setBlock(world, cursor, obs, true, true);
			}
		}

		
		// ceiling trims and outer walls
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			// outer wall trim
			Coord start = new Coord(x, y, z);
			start.add(dir, 10);
			Coord end = new Coord(start);
			start.add(orth[0], 9);
			end.add(orth[1], 9);
			
			start.add(Cardinal.DOWN, 4);
			end.add(Cardinal.DOWN, 1);
			WorldGenPrimitive.fillRectSolid(world, start, end, obs, true, true);
			
			start.add(Cardinal.UP, 4 + 3);
			end.add(Cardinal.UP, 1 + 3);
			WorldGenPrimitive.fillRectSolid(world, start, end, obs, true, true);
			
			// mid
			start = new Coord(x, y, z);
			start.add(dir, 6);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			start.add(orth[0], 9);
			end.add(orth[1], 9);
			WorldGenPrimitive.fillRectSolid(world, start, end, obs, true, true);
			
			// inner
			start = new Coord(x, y, z);
			start.add(dir, 2);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			start.add(orth[0], 9);
			end.add(orth[1], 9);
			WorldGenPrimitive.fillRectSolid(world, start, end, obs, true, true);
			
			// outer shell
			start = new Coord(x, y, z);
			start.add(dir, 11);
			end = new Coord(start);
			start.add(Cardinal.DOWN, 3);
			end.add(Cardinal.UP, 3);
			start.add(orth[0], 11);
			end.add(orth[1], 11);
			WorldGenPrimitive.fillRectSolid(world, start, end, obs, false, true);
		}
				
		outerPillars(world, x, y, z);
		
		// upper mid floor
		for(Cardinal dir : Cardinal.directions){
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			Coord start = new Coord(x, y, z);
			start.add(Cardinal.DOWN, 1);
			Coord end = new Coord(start);
			end.add(Cardinal.DOWN, 3);
			start.add(dir, 9);
			start.add(orth[0], 1);
			end.add(orth[1], 1);
			WorldGenPrimitive.fillRectSolid(world, start, end, brick, true, true);
		}
		
		// mid outer floors
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				Coord start = new Coord(x, y, z);
				Coord end = new Coord(start);
				start.add(dir, 9);
				start.add(orth, 2);
				start.add(Cardinal.DOWN, 3);
				end.add(dir, 8);
				end.add(orth, 9);
				end.add(Cardinal.DOWN, 2);
				
				WorldGenPrimitive.fillRectSolid(world, start, end, brick, true, true);
				MetaBlock step = new MetaBlock(Block.stairsNetherBrick.blockID);
				Coord stepSpot = new Coord(x, y, z);
				stepSpot.add(dir, 8);
				stepSpot.add(Cardinal.DOWN, 1);
				stepSpot.add(orth, 2);
				step.setMeta(WorldGenPrimitive.blockOrientation(orth, false));
				WorldGenPrimitive.setBlock(world, stepSpot, step, true, true);
				stepSpot.add(dir, 1);
				WorldGenPrimitive.setBlock(world, stepSpot, step, true, true);
				
				step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(dir), false));
				stepSpot = new Coord(x, y, z);
				stepSpot.add(Cardinal.DOWN, 2);
				stepSpot.add(dir, 7);
				stepSpot.add(orth, 3);
				WorldGenPrimitive.setBlock(world, stepSpot, step, true, true);
				stepSpot.add(orth, 1);
				WorldGenPrimitive.setBlock(world, stepSpot, step, true, true);
				stepSpot.add(Cardinal.DOWN, 1);
				stepSpot.add(Cardinal.reverse(dir), 1);
				WorldGenPrimitive.setBlock(world, stepSpot, step, true, true);
				stepSpot.add(Cardinal.reverse(orth), 1);
				WorldGenPrimitive.setBlock(world, stepSpot, step, true, true);
				stepSpot.add(dir, 1);
				WorldGenPrimitive.setBlock(world, stepSpot, brick, true, true);
				stepSpot.add(orth, 1);
				WorldGenPrimitive.setBlock(world, stepSpot, brick, true, true);
				
				Coord corner = new Coord(x, y, z);
				corner.add(dir, 7);
				corner.add(orth, 7);
				corner.add(Cardinal.DOWN, 2);
				WorldGenPrimitive.setBlock(world, corner, brick, true, true);
				corner.add(Cardinal.DOWN, 1);
				WorldGenPrimitive.setBlock(world, corner, brick, true, true);

				corner = new Coord(x, y, z);
				corner.add(dir, 6);
				corner.add(orth, 6);
				corner.add(Cardinal.DOWN, 1);
				spawners.add(new Coord(corner));
				
			}
		}
		
		// chests areas
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				Coord cursor = new Coord(x, y, z);
				cursor.add(Cardinal.DOWN, 2);
				cursor.add(dir, 3);
				lavaWindow(world, new Coord(cursor), orth);
				cursor.add(dir, 2);
				lavaWindow(world, new Coord(cursor), orth);
				
				Coord chestPos = new Coord(x, y, z);
				chestPos.add(dir, 4);
				chestPos.add(orth, 2);
				chestPos.add(Cardinal.DOWN, 3);
				TreasureChest.generate(world, rand, chestPos.getX(), chestPos.getY(), chestPos.getZ(), TreasureChest.ORE);
			}
		}
		
		innerPillars(world, x, y, z);
		
		for(Coord space : spawners){
			Spawner.generate(world, rand, space.getX(), space.getY(), space.getZ());
		}
		
		return true;
	}

	@Override
	public int getSize() {
		return 10;
	}
	
	private static void outerPillars(World world, int x, int y, int z){
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				Coord pillarLocation = new Coord(x, y, z);
				pillarLocation.add(dir, 10);

				pillarLocation.add(orth, 2);
				outerPillar(world, pillarLocation, dir);

				pillarLocation.add(orth, 3);
				outerPillar(world, pillarLocation, dir);
				
				pillarLocation.add(orth, 3);
				outerPillar(world, pillarLocation, dir);
			}
		}		
	}
	
	private static void outerPillar(World world, Coord pillarLocation, Cardinal dir){
		
		int x = pillarLocation.getX();
		int y = pillarLocation.getY();
		int z = pillarLocation.getZ();
		
		WorldGenPrimitive.fillRectSolid(world, x, y - 2, z, x, y + 3, z, Block.obsidian.blockID);
		Coord blockLocation = new Coord(x, y + 3, z);
		
		blockLocation.add(dir, 1);
		WorldGenPrimitive.setBlock(world, blockLocation, Block.obsidian.blockID);
		
		for(int i = 0; i < 3; ++i){
			blockLocation.add(Cardinal.reverse(dir), 1);
			blockLocation.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, blockLocation, Block.obsidian.blockID);
		}
	}
	
	private static void innerPillars(World world, int x, int y, int z){
		for(Cardinal dir : Cardinal.directions){			
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				Coord pillar = new Coord(x, y, z);
				pillar.add(dir, 2);
				pillar.add(orth, 2);
				WorldGenPrimitive.fillRectSolid(world, pillar.getX(), y - 4, pillar.getZ(), pillar.getX(), y + 4, pillar.getZ(), obs, true, true);
				pillar.add(dir, 4);
				WorldGenPrimitive.fillRectSolid(world, pillar.getX(), y - 4, pillar.getZ(), pillar.getX(), y + 4, pillar.getZ(), obs, true, true);
				pillar.add(orth, 3);
				WorldGenPrimitive.fillRectSolid(world, pillar.getX(), y - 4, pillar.getZ(), pillar.getX(), y + 4, pillar.getZ(), obs, true, true);
				
				Coord start = new Coord(x, y, z);
				start.add(Cardinal.DOWN, 1);
				start.add(orth, 2);
				start.add(dir, 2);
				Coord end = new Coord(start);
				end.add(dir, 5);
				WorldGenPrimitive.fillRectSolid(world, start, end, obs, true, true);

				start = new Coord(x, y, z);
				start.add(Cardinal.DOWN, 1);
				start.add(dir, 7);
				start.add(orth, 5);
				WorldGenPrimitive.setBlock(world, start, Block.obsidian.blockID);
				start.add(Cardinal.DOWN, 1);
				end = new Coord(start);
				end.add(Cardinal.reverse(dir), 1);
				end.add(orth, 1);
				end.add(Cardinal.DOWN, 1);
				WorldGenPrimitive.fillRectSolid(world, start, end, obs, true, true);
			}
		}
	}
	
	private static void lavaWindow(World world, Coord cursor, Cardinal orth){
		WorldGenPrimitive.setBlock(world, cursor, Block.lavaMoving.blockID);
		cursor.add(Cardinal.DOWN, 1);
		WorldGenPrimitive.setBlock(world, cursor, Block.lavaMoving.blockID);
		cursor.add(orth, 1);
		WorldGenPrimitive.setBlock(world, cursor, Block.netherFence.blockID);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, cursor, Block.netherFence.blockID);
	}

}
