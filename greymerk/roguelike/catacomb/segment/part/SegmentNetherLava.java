package greymerk.roguelike.catacomb.segment.part;

import net.minecraft.src.Block;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

public class SegmentNetherLava extends SegmentBase {

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
		boolean lava = true; //rand.nextInt(5) == 0;
		IBlockFactory wall = theme.getSecondaryWall();
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			start = new Coord(x, y, z);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(orth, 1);
			start.add(Cardinal.UP, 2);
			end.add(Cardinal.DOWN, 1);
			if(lava && !air){
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(0), true, true);
				WorldGenPrimitive.setBlock(world, rand, start, new MetaBlock(Block.lavaMoving.blockID), true, true);
				start.add(Cardinal.reverse(orth), 1);
				WorldGenPrimitive.setBlock(world, rand, start, new MetaBlock(Block.lavaMoving.blockID), true, true);
			}
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			
			step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), false));
			cursor.add(orth, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
			
			step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
			
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, wall, true, true);
			cursor.add(Cardinal.reverse(orth), 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, wall, true, true);
		}
		
	}

}
