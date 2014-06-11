package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class SegmentNetherWart extends SegmentBase{

	@Override
	protected void genWall(Cardinal dir) {
		
		MetaBlock step = theme.getSecondaryStair();
		IBlockFactory wall = theme.getSecondaryWall();
		MetaBlock air = new MetaBlock(Blocks.air);

		Coord start;
		Coord end;
		Coord cursor;
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 5);
		boolean isAir = world.isAirBlock(cursor.getX(), cursor.getY(), cursor.getZ());
		boolean lava = rand.nextInt(5) == 0;
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 3);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.nether_brick_fence);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.nether_brick_fence);
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(orth, 1);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, wall, true, true);
			cursor.add(Cardinal.reverse(orth), 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, wall, true, true);
			cursor.add(Cardinal.DOWN, 2);
			WorldGenPrimitive.setBlock(world, cursor, Blocks.nether_wart);
			cursor.add(orth, 1);
			WorldGenPrimitive.setBlock(world, cursor, Blocks.nether_wart);
			cursor.add(Cardinal.DOWN, 1);
			WorldGenPrimitive.setBlock(world, cursor, Blocks.soul_sand);
			cursor.add(Cardinal.reverse(orth), 1);
			WorldGenPrimitive.setBlock(world, cursor, Blocks.soul_sand);
		}
		
	}

}
