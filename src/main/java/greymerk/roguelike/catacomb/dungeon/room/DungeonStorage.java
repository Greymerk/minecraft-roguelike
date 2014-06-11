package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonStorage implements IDungeon {

	@Override
	public boolean generate(World world, Random rand, ITheme theme, int x, int y, int z) {

		HashSet<Coord> chestSpaces = new HashSet<Coord>();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// space
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y, z - 6, x + 6, y + 3, z + 6, air);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		IBlockFactory blocks = theme.getPrimaryWall();
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y - 1, z - 6, x + 6, y - 1, z + 6, blocks, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y + 4, z - 5, x + 5, y + 4, z + 5, blocks, true, true);
		
		for(Cardinal dir : Cardinal.directions){			
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 3);
				cursor.add(dir, 2);
				cursor.add(orth, 2);
				pillarTop(world, rand, theme, cursor);
				cursor.add(dir, 3);
				cursor.add(orth, 3);
				pillarTop(world, rand, theme, cursor);
				start = new Coord(cursor);
				
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 1);
				pillarTop(world, rand, theme, cursor);
				
				end = new Coord(cursor);
				end.add(Cardinal.DOWN, 3);
				end.add(dir, 1);
				end.add(orth, 1);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 2);
				cursor.add(orth, 2);
				pillar(world, rand, cursor, theme, 4);
				cursor.add(dir, 4);
				pillar(world, rand, cursor, theme, 3);

				
				cursor.add(Cardinal.UP, 2);
				pillarTop(world, rand, theme, cursor);
				
				cursor.add(Cardinal.UP, 1);
				cursor.add(Cardinal.reverse(dir), 1);
				pillarTop(world, rand, theme, cursor);
				
				cursor.add(Cardinal.reverse(dir), 3);
				pillarTop(world, rand, theme, cursor);
				
				start = new Coord(x, y, z);
				start.add(dir, 6);
				start.add(Cardinal.UP, 3);
				end = new Coord(start);
				end.add(orth, 5);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, true, true);
				start.add(dir, 1);
				end.add(dir, 1);
				end.add(Cardinal.DOWN, 3);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, blocks, false, true);				
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 6);
				cursor.add(orth, 3);
				MetaBlock step = theme.getSecondaryStair();
				step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(dir), true));
				WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
				cursor.add(Cardinal.UP, 1);
				chestSpaces.add(new Coord(cursor));
				cursor.add(orth, 1);
				chestSpaces.add(new Coord(cursor));				
				
				start = new Coord(x, y, z);
				start.add(Cardinal.DOWN, 1);
				start.add(dir, 3);
				start.add(orth, 3);
				end = new Coord(start);
				end.add(dir, 3);
				end.add(orth, 1);
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.hardened_clay), true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 5);
				cursor.add(orth, 5);
				pillar(world, rand, cursor, theme, 4);
				
			}
		}
		

		List<TreasureChest> types = new ArrayList<TreasureChest>(Arrays.asList(TreasureChest.BLOCKS, TreasureChest.SUPPLIES));
		
		List<Coord> spaces = new ArrayList<Coord>(chestSpaces);
		
		TreasureChest.createChests(world, rand, 6, spaces, types);
		
		return true;
	}

	@Override
	public int getSize() {
		return 8;
	}

	private static void pillarTop(World world, Random rand, ITheme theme, Coord cursor){
		MetaBlock step = theme.getSecondaryStair();
		for(Cardinal dir : Cardinal.directions){
			step.setMeta(WorldGenPrimitive.blockOrientation(dir, true));
			cursor.add(dir, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, step, true, false);
			cursor.add(Cardinal.reverse(dir), 1);
		}
	}
	
	private static void pillar(World world, Random rand, Coord base, ITheme theme, int height){
		Coord top = new Coord(base);
		top.add(Cardinal.UP, height);
		WorldGenPrimitive.fillRectSolid(world, rand, base, top, theme.getSecondaryPillar(), true, true);
	}	
}
