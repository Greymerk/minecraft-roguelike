package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.blocks.Leaves;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentJungle extends SegmentBase {


	@Override
	protected void genWall(World world, Random rand, IDungeonLevel level, Cardinal wallDirection, ITheme theme, int x, int y, int z) {
		
		MetaBlock stair = theme.getSecondaryStair();
		
		MetaBlock leaves = Leaves.get(Leaves.JUNGLE, false);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(wallDirection);
		start = new Coord(x, y, z);
		start.add(wallDirection, 2);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 1);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.air), true, true);
		start.add(Cardinal.DOWN, 1);
		end.add(Cardinal.DOWN, 2);
		
		if(rand.nextInt(5) == 0){
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.flowing_water), true, true);
		} else {
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.grass), true, true);
			start.add(Cardinal.UP, 1);
			end.add(Cardinal.UP, 1);
			if(rand.nextBoolean()) WorldGenPrimitive.fillRectSolid(world, rand, start, end, leaves, true, true);
		}
		
		for(Cardinal d : orth){
			cursor = new Coord(x, y, z);
			cursor.add(wallDirection, 2);
			cursor.add(d, 1);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(d), true);
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
		}

	}
}
