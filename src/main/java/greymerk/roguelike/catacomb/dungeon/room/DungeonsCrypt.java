package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsCrypt implements IDungeon {

	public boolean generate(World world, Random rand, ITheme theme, int x, int y, int z) {

		IBlockFactory walls = theme.getPrimaryWall();
		MetaBlock stair = theme.getPrimaryStair();
		MetaBlock air = new MetaBlock(Blocks.air);
		IBlockFactory tombStone = new MetaBlock(Blocks.quartz_block);
		
		// clear box;
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y, z - 5, x + 5, y + 3, z + 5, air);
		
		// ceiling gaps
		WorldGenPrimitive.fillRectSolid(world, rand, x - 3, y + 4, z - 3, x + 3, y + 4, z + 3, air);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 5, z - 2, x + 2, y + 5, z + 2, air);

		// replace roof blocks
		WorldGenPrimitive.fillRectSolid(world, rand, x - 8, y + 3, z - 8, x + 8, y + 7, z + 8, walls, false, true);
		// replace floor
		WorldGenPrimitive.fillRectSolid(world, rand, x - 9, y - 1, z - 9, x + 9, y - 1, z + 9, walls, false, true);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		for(Cardinal dir : Cardinal.directions){
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				// crypt solids
				start = new Coord(x, y, z);
				start.add(dir, 6);
				start.add(orth, 2);
				end = new Coord(start);
				end.add(dir, 2);
				end.add(Cardinal.UP, 2);
				end.add(orth, 4);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, walls, true, true);
				
				// clear air near door;
				start = new Coord(x, y, z);
				start.add(dir, 6);
				end = new Coord(start);
				end.add(dir, 2);
				end.add(orth, 2);
				end.add(Cardinal.UP, 2);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
				
				// outer wall
				start = new Coord(x, y, z);
				start.add(dir, 9);
				end = new Coord(start);
				end.add(orth, 5);
				end.add(Cardinal.UP, 4);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, walls, false, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 6);
				cursor.add(orth, 2);
				cursor.add(Cardinal.UP, 2);
				WorldGenPrimitive.setBlock(world, rand, cursor, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true), true, true);
				cursor.add(dir, 2);
				WorldGenPrimitive.setBlock(world, rand, cursor, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true), true, true);
				
				//pillar
				start = new Coord(x, y, z);
				start.add(dir, 4);
				start.add(orth, 4);
				end = new Coord(start);
				end.add(Cardinal.UP, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryPillar(), true, true);
				end.add(Cardinal.reverse(dir), 1);
				start = new Coord(end);
				end.add(Cardinal.reverse(orth), 1);
				start.add(orth, 1);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true), true, true);
				cursor = new Coord(end);
				cursor.add(Cardinal.UP, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, walls, true, true);
				cursor.add(Cardinal.reverse(dir), 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true), true, true);
				cursor.add(Cardinal.reverse(orth), 1);
				cursor.add(Cardinal.UP, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, walls, true, true);				
				cursor.add(Cardinal.reverse(dir), 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true), true, true);
				
				// ceiling groove
				cursor = new Coord(x, y, z);
				cursor.add(dir, 7);
				cursor.add(Cardinal.UP, 3);
				WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true), true, true);
				
				// ceiling groove
				cursor = new Coord(x, y, z);
				cursor.add(dir, 5);
				cursor.add(Cardinal.UP, 4);
				WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(orth), true), true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 5);
				cursor.add(Cardinal.UP, 3);
				cursor.add(orth, 4);
				WorldGenPrimitive.setBlock(world, rand, cursor, walls, true, true);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, walls, true, true);
			}
		}
		
		for(Cardinal dir : Cardinal.directions){
			for(Cardinal orth : Cardinal.getOrthogonal(dir)){
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 7);
				cursor.add(orth, 3);
				cursor.add(Cardinal.UP, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, tombStone, true, true);
				cursor.add(orth, 1);
				start = new Coord(cursor);
				end = new Coord(cursor);
				end.add(Cardinal.UP, 1);
				end.add(orth, 1);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
				
				if(rand.nextInt(3) == 0){
					
					Spawner spawnerType = rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE;
					Spawner.generate(world, rand, cursor, spawnerType);
					
					cursor.add(orth, 1);
					TreasureChest[] types = {TreasureChest.ARMOUR, TreasureChest.WEAPONS};
					TreasureChest chestType = types[rand.nextInt(types.length)];
					TreasureChest.generate(world, rand, cursor, chestType);
				}
			}
		}
		return true;
	}
	
	public int getSize(){
		return 10;
	}
}
