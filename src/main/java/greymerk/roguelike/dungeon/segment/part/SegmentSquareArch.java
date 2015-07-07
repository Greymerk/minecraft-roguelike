package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentSquareArch extends SegmentBase {

	@Override
	protected void genWall(World world, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {

		Coord origin = new Coord(x, y, z);
		Coord start;
		Coord end;
		
		MetaBlock air = new MetaBlock(Blocks.air);
		IBlockFactory pillar = level.getSettings().getTheme().getPrimaryPillar();
		
		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		start = new Coord(origin);
		start.add(dir, 3);
		end = new Coord(start);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			start = new Coord(origin);
			start.add(orth, 1);
			start.add(dir, 2);
			end = new Coord(start);
			end.add(Cardinal.UP, 2);
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, pillar, true, true);
		}
	}
}
