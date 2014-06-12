package greymerk.roguelike.catacomb.segment.part;

import java.util.Random;

import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentNetherLava extends SegmentBase {

	@Override
	protected void genWall(World world, Random rand, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock step = theme.getSecondaryStair();
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock lava = new MetaBlock(Blocks.lava);
		
		Coord start;
		Coord end;
		Coord cursor;
		
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		air.setBlock(world, cursor);
		cursor.add(Cardinal.UP, 1);
		air.setBlock(world, cursor);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 5);
		boolean isAir = world.isAirBlock(cursor.getX(), cursor.getY(), cursor.getZ());
		boolean isLava = true; //rand.nextInt(5) == 0;
		IBlockFactory wall = theme.getSecondaryWall();
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			start = new Coord(x, y, z);
			start.add(dir, 3);
			end = new Coord(start);
			start.add(orth, 1);
			start.add(Cardinal.UP, 2);
			end.add(Cardinal.DOWN, 1);
			if(isLava && !isAir){
				WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
				lava.setBlock(world, start);
				start.add(Cardinal.reverse(orth), 1);
				lava.setBlock(world, start);
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
