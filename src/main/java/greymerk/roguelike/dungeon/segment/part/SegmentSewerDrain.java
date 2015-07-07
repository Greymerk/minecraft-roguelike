package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.CatacombLevel;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentSewerDrain extends SegmentBase {

	
	@Override
	protected void genWall(World world, Random rand, CatacombLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock water = new MetaBlock(Blocks.flowing_water);
		MetaBlock stair = theme.getSecondaryStair();
		MetaBlock bars = new MetaBlock(Blocks.iron_bars);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(x, y, z);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, water, false, true);
				
		start = new Coord(x, y, z);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		start.add(dir);
		end.add(dir);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.mossy_cobblestone), true, true);
		
		for(Cardinal o : orth){
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(o);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			bars.setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
		}
		
		start = new Coord(x, y, z);
		start.add(Cardinal.UP);
		end = new Coord(start);
		end.add(dir, 5);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		water.setBlock(world, end);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.DOWN);
		cursor.add(dir);
		air.setBlock(world, cursor);
		
	}
}
