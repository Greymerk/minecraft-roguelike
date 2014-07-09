package greymerk.roguelike.catacomb.segment.part;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

public class SegmentFirePlace extends SegmentBase {
	
	@Override
	protected void genWall(World world, Random rand, CatacombLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = theme.getSecondaryStair();
		
		Coord cursor = new Coord(x, y, z);
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		cursor.add(dir, 2);
		start = new Coord(cursor);
		start.add(orth[0], 1);
		end = new Coord(cursor);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		// front wall
		start.add(dir, 1);
		end.add(dir, 1);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), false, true);

		// stairs
		cursor.add(Cardinal.UP, 2);
		for(Cardinal d : orth){
			Coord c = new Coord(cursor);
			c.add(d, 1);
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(d), true));
			WorldGenPrimitive.setBlock(world, rand, c, stair, true, true);
		}
		
		stair = theme.getPrimaryStair();
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 3);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(dir), false));
		stair.setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.iron_bars);
		cursor.add(Cardinal.UP);
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(dir), true));
		stair.setBlock(world, cursor);
		
		start = new Coord(x, y, z);
		start.add(dir, 4);
		end = new Coord(start);
		start.add(Cardinal.DOWN);
		start.add(orth[0]);
		end.add(Cardinal.UP, 3);
		end.add(orth[1]);
		end.add(dir, 2);
		List<Coord> box = WorldGenPrimitive.getRectHollow(start, end);
		for(Coord c : box){
			if(!world.getBlock(c.getX(), c.getY(), c.getZ()).getMaterial().isSolid()) return;
		}
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getPrimaryWall(), true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 4);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.netherrack);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.fire);
	}	
}
