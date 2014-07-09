package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.FlowerPot;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentFlowers extends SegmentBase {
	
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
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryWall(), false, true);
		start.add(dir, 1);
		end.add(dir, 1);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryWall(), false, true);
		start.add(Cardinal.reverse(dir), 1);
		start.add(Cardinal.UP, 1);
		end.add(Cardinal.reverse(dir), 1);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, false, true);
		cursor.add(Cardinal.UP, 2);
		for(Cardinal d : orth){
			Coord c = new Coord(cursor);
			c.add(d, 1);
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(d), true));
			WorldGenPrimitive.setBlock(world, rand, c, stair, true, true);
		}
		
		start = new Coord(x, y, z);
		start.add(dir, 2);
		start.add(Cardinal.UP);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		List<Coord> pots = WorldGenPrimitive.getRectSolid(start, end);
		for(Coord c : pots){
			if(rand.nextInt(3) == 0 && world.getBlock(c.getX(), c.getY() - 1, c.getZ()).getMaterial().isSolid()){
				FlowerPot.generate(world, rand, c);
			}
		}
	}
}
