package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.IAlcove;
import greymerk.roguelike.catacomb.segment.alcove.PrisonCell;
import greymerk.roguelike.worldgen.BlockFactoryProvider;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentNetherStripes extends SegmentBase {
	

	@Override
	protected void genWall(Cardinal dir) {
		
		MetaBlock step = new MetaBlock(Block.stairsNetherBrick.blockID);

		Coord start;
		Coord end;
		Coord cursor;
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		WorldGenPrimitive.setBlock(world, cursor, 0);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, cursor, 0);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 5);
		boolean air = world.isAirBlock(cursor.getX(), cursor.getY(), cursor.getZ());
		boolean lava = rand.nextInt(5) == 0;
		int choice = rand.nextInt(3);
		
		switch(choice){
			case 0:
				
				MetaBlock slab = new MetaBlock(Block.stoneSingleSlab.blockID, 14);
				cursor = new Coord(x, y, z);
				cursor.add(dir, 2);
				WorldGenPrimitive.setBlock(world, cursor, slab, true, true);
				cursor.add(Cardinal.UP, 1);
				WorldGenPrimitive.setBlock(world, cursor, slab, true, true);
				cursor.add(Cardinal.UP, 1);
				WorldGenPrimitive.setBlock(world, cursor, slab, true, true);
				
				for(Cardinal orth : Cardinal.getOrthogonal(dir)){
					start = new Coord(x, y, z);
					start.add(dir, 3);
					end = new Coord(start);
					start.add(orth, 1);
					start.add(Cardinal.UP, 3);
					end.add(Cardinal.DOWN, 2);
					if(lava && !air) WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.lavaStill.blockID), false, true);
					
					step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
					cursor = new Coord(x, y, z);
					cursor.add(dir, 2);
					cursor.add(orth, 1);
					WorldGenPrimitive.setBlock(world, cursor, step, true, true);
					cursor.add(Cardinal.UP, 1);
					WorldGenPrimitive.setBlock(world, cursor, step, true, true);
					cursor.add(Cardinal.UP, 1);
					WorldGenPrimitive.setBlock(world, cursor, step, true, true);
					cursor.add(Cardinal.reverse(dir), 1);
					WorldGenPrimitive.setBlock(world, cursor, step, true, true);
				}
				
				return;
			case 1:
				for(Cardinal orth : Cardinal.getOrthogonal(dir)){
					start = new Coord(x, y, z);
					start.add(dir, 3);
					end = new Coord(start);
					start.add(orth, 1);
					start.add(Cardinal.UP, 1);
					end.add(Cardinal.DOWN, 2);
					if(lava && !air) WorldGenPrimitive.fillRectSolid(world, start, end, new MetaBlock(Block.lavaStill.blockID), false, true);
					
					cursor = new Coord(x, y, z);
					cursor.add(dir, 2);
					
					step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), false));
					cursor.add(orth, 1);
					WorldGenPrimitive.setBlock(world, cursor, step, true, true);
					
					step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
					cursor.add(Cardinal.UP, 1);
					WorldGenPrimitive.setBlock(world, cursor, step, true, true);
					
					cursor.add(Cardinal.UP, 1);
					WorldGenPrimitive.setBlock(world, cursor, Block.netherBrick.blockID);
					cursor.add(Cardinal.reverse(orth), 1);
					WorldGenPrimitive.setBlock(world, cursor, Block.netherBrick.blockID);
				}
				
				return;		
			case 2:
				
				cursor = new Coord(x, y, z);
				cursor.add(dir, 3);
				WorldGenPrimitive.setBlock(world, cursor, Block.netherFence.blockID);
				cursor.add(Cardinal.UP, 1);
				WorldGenPrimitive.setBlock(world, cursor, Block.netherFence.blockID);
				
				for(Cardinal orth : Cardinal.getOrthogonal(dir)){
					step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
					cursor = new Coord(x, y, z);
					cursor.add(dir, 2);
					cursor.add(orth, 1);
					cursor.add(Cardinal.UP, 1);
					WorldGenPrimitive.setBlock(world, cursor, step, true, true);
					cursor.add(Cardinal.UP, 1);
					WorldGenPrimitive.setBlock(world, cursor, Block.netherBrick.blockID);
					cursor.add(Cardinal.reverse(orth), 1);
					WorldGenPrimitive.setBlock(world, cursor, Block.netherBrick.blockID);
					cursor.add(Cardinal.DOWN, 2);
					WorldGenPrimitive.setBlock(world, cursor, Block.netherStalk.blockID);
					cursor.add(orth, 1);
					WorldGenPrimitive.setBlock(world, cursor, Block.netherStalk.blockID);
					cursor.add(Cardinal.DOWN, 1);
					WorldGenPrimitive.setBlock(world, cursor, Block.slowSand.blockID);
					cursor.add(Cardinal.reverse(orth), 1);
					WorldGenPrimitive.setBlock(world, cursor, Block.slowSand.blockID);
				}
				
				return;		
		
		
		}
		

		
	}
}