package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.block.BlockStoneSlab;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentNetherStripes extends SegmentBase {
	

	@Override
	protected void genWall(World world, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock step = theme.getSecondaryStair();

		Coord start;
		Coord end;
		Coord cursor;
		MetaBlock air = new MetaBlock(Blocks.air);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 5);
		boolean isAir = world.isAirBlock(cursor.getBlockPos());
		boolean isLava = rand.nextInt(5) == 0;

		
		MetaBlock slab = new MetaBlock(Blocks.stone_slab);
		slab.withProperty(BlockStoneSlab.VARIANT_PROP, BlockStoneSlab.EnumType.NETHERBRICK);
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
			if(isLava && !isAir) WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.lava), false, true);
			
			WorldGenPrimitive.blockOrientation(step, Cardinal.reverse(orth), true);
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