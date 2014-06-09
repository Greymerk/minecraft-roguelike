package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.IAlcove;
import greymerk.roguelike.catacomb.segment.alcove.PrisonCell;
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
		
		MetaBlock step = theme.getSecondaryStair();

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

		
		MetaBlock slab = new MetaBlock(Block.stoneSingleSlab.blockID, 14);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		WorldGenPrimitive.setBlock(world, rand, cursor, slab, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, slab, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, slab, true, true);
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			start = new Coord(x, y, z);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(orth, 1);
			start.add(Cardinal.UP, 3);
			end.add(Cardinal.DOWN, 2);
			if(lava && !air) WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Block.lavaStill.blockID), false, true);
			
			step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(orth, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
			cursor.add(Cardinal.reverse(dir), 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
		}
	}
}