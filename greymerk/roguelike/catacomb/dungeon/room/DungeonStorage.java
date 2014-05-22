package greymerk.roguelike.catacomb.dungeon.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.BlockFactoryProvider;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

public class DungeonStorage implements IDungeon {

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		HashSet<Coord> chestSpaces = new HashSet<Coord>();
		
		// space
		WorldGenPrimitive.fillRectSolid(world, x - 6, y, z - 6, x + 6, y + 3, z + 6, 0);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		IBlockFactory blocks = BlockFactoryProvider.getRandomizer(Catacomb.getLevel(y), rand);
		
		WorldGenPrimitive.fillRectSolid(world, x - 6, y - 1, z - 6, x + 6, y - 1, z + 6, blocks, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 4, z - 5, x + 5, y + 4, z + 5, blocks, true, true);
		
		for(Cardinal dir : Cardinal.directions){			
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				
				cursor = new Coord(x, y, z);
				cursor.add(Cardinal.UP, 3);
				cursor.add(dir, 2);
				cursor.add(orth, 2);
				pillarTop(world, cursor);
				cursor.add(dir, 3);
				cursor.add(orth, 3);
				pillarTop(world, cursor);
				start = new Coord(cursor);
				
				cursor.add(Cardinal.DOWN, 1);
				cursor.add(dir, 1);
				pillarTop(world, cursor);
				
				end = new Coord(cursor);
				end.add(Cardinal.DOWN, 3);
				end.add(dir, 1);
				end.add(orth, 1);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 2);
				cursor.add(orth, 2);
				pillar(world, rand, cursor, 4);
				cursor.add(dir, 4);
				pillar(world, rand, cursor, 3);

				
				cursor.add(Cardinal.UP, 2);
				pillarTop(world, cursor);
				
				cursor.add(Cardinal.UP, 1);
				cursor.add(Cardinal.reverse(dir), 1);
				pillarTop(world, cursor);
				
				cursor.add(Cardinal.reverse(dir), 3);
				pillarTop(world, cursor);
				
				start = new Coord(x, y, z);
				start.add(dir, 6);
				start.add(Cardinal.UP, 3);
				end = new Coord(start);
				end.add(orth, 5);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, true, true);
				start.add(dir, 1);
				end.add(dir, 1);
				end.add(Cardinal.DOWN, 3);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, false, true);				
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 6);
				cursor.add(orth, 3);
				MetaBlock step = getStair(Catacomb.getLevel(y));
				step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(dir), true));
				WorldGenPrimitive.setBlock(world, cursor, step, true, true);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, cursor, step, true, true);
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
				WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.hardenedClay.blockID), true, true);
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 5);
				cursor.add(orth, 5);
				pillar(world, rand, cursor, 4);
				
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

	private static void pillarTop(World world, Coord cursor){
		MetaBlock step = getStair(Catacomb.getLevel(cursor.getY()));
		for(Cardinal dir : Cardinal.directions){
			step.setMeta(WorldGenPrimitive.blockOrientation(dir, true));
			cursor.add(dir, 1);
			WorldGenPrimitive.setBlock(world, cursor, step, true, false);
			cursor.add(Cardinal.reverse(dir), 1);
		}
	}
	
	private static void pillar(World world, Random rand, Coord base, int height){

		Coord top = new Coord(base);
		top.add(Cardinal.UP, height);
		
		switch(Catacomb.getLevel(base.getY())){
		case 0:
			WorldGenPrimitive.fillRectSolid(world, base, top, Log.getLog(Log.OAK, Cardinal.UP, false), true, true);
			return;
		case 1:
			WorldGenPrimitive.fillRectSolid(world, base, top, Log.getLog(Log.SPRUCE, Cardinal.UP, false), true, true);
			return;
		default:
			WorldGenPrimitive.fillRectSolid(world, base, top, BlockFactoryProvider.getRandomizer(Catacomb.getLevel(base.getY()), rand), true, true);
			return;
		}
		


	
	}
	
	private static MetaBlock getStair(int level){
		switch(level){
		case 0:
			return new MetaBlock(Block.stairsWoodOak.blockID);
		case 1:
			return new MetaBlock(Block.stairsWoodSpruce.blockID);
		case 2:
			return new MetaBlock(Block.stairsStoneBrick.blockID);
		case 3:
			return new MetaBlock(Block.stairsCobblestone.blockID);
		case 4:
			return new MetaBlock(Block.stairsNetherBrick.blockID);
		default:
			return new MetaBlock(Block.stairsStoneBrick.blockID);
		}
		
		
	}
	
}
