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

public class SegmentNetherArch extends SegmentBase {

	@Override
	protected void genWall(World world, Random rand, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock step = theme.getSecondaryStair();
		step.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(dir), true));
		IBlockFactory pillar = theme.getSecondaryPillar();
		

		Coord cursor;
		
		boolean hasLava = rand.nextInt(5) == 0;
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			cursor = new Coord(x, y, z);
			cursor.add(dir, 1);
			cursor.add(orth, 1);
			cursor.add(Cardinal.UP, 2);
			WorldGenPrimitive.setBlock(world, rand, cursor, step, true, true);
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(orth, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, pillar, true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, pillar, true, true);
		}
			
		MetaBlock fence = new MetaBlock(Blocks.nether_brick_fence);
		MetaBlock lava = new MetaBlock(Blocks.flowing_lava);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);		
		WorldGenPrimitive.setBlock(world, rand, cursor, fence, true, true);
		cursor.add(Cardinal.UP, 1);		
		WorldGenPrimitive.setBlock(world, rand, cursor, fence, true, true);
		
		if(hasLava){
			cursor.add(dir, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, lava, true, true);
			cursor.add(Cardinal.DOWN, 1);		
			WorldGenPrimitive.setBlock(world, rand, cursor, lava, true, true);
		}
	}
}
