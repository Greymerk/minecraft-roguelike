package greymerk.roguelike.catacomb.segment.part;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

public class SegmentFireArch extends SegmentBase {


	
	@Override
	protected void genWall(World world, Random rand, CatacombLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory walls = theme.getPrimaryWall();
		
		Coord start;
		Coord end;
		Coord cursor;
		
		Cardinal[] orths = Cardinal.getOrthogonal(dir);
		
		start = new Coord(x, y, z);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orths[0]);
		end.add(orths[0]);
		end.add(Cardinal.UP, 2);
		end.add(dir);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, walls, true, true);
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), false).setBlock(world, cursor);
		cursor.add(Cardinal.UP, 2);
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
		cursor.add(Cardinal.DOWN, 2);
		cursor.add(dir);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.netherrack);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.fire);
		cursor.add(Cardinal.reverse(dir));
		WorldGenPrimitive.setBlock(world, cursor, Blocks.iron_bars);
		
		for(Cardinal orth : orths){
			
			cursor = new Coord(x, y, z);
			cursor.add(dir);
			cursor.add(orth);
			cursor.add(Cardinal.UP, 2);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
			
		}
	}
}
