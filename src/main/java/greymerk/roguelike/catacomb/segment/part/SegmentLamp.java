package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.redstone.Lever;
import greymerk.roguelike.worldgen.redstone.Torch;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentLamp extends SegmentBase{

	@Override
	protected void genWall(World world, Random rand, CatacombLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		
		Coord origin = new Coord(x, y, z);
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory wall = theme.getPrimaryWall();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 3);
		end = new Coord(start);
		start.add(dir);
		start.add(orth[0]);
		end.add(Cardinal.reverse(dir));
		end.add(orth[1]);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		start = new Coord(origin);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(dir, 2);
		end.add(Cardinal.UP, 6);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
		start = new Coord(end);
		start.add(Cardinal.DOWN, 2);
		start.add(Cardinal.reverse(dir), 6);
		start.add(orth[0], 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, wall, true, true);
		
		for (Cardinal side : orth){
			
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(side);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(side), false).setBlock(world, cursor);
			cursor.add(Cardinal.UP, 2);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(side), true).setBlock(world, cursor);
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 4);
		overheadLight(world, rand, theme, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 2);
		Lever.generate(world, Cardinal.reverse(dir), cursor, false);
		
		cursor.add(dir);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.hardened_clay);
		cursor.add(dir);
		Torch.generate(world, Torch.REDSTONE, dir, cursor);
		cursor.add(Cardinal.UP, 2);
		Torch.generate(world, Torch.REDSTONE, Cardinal.UP, cursor);
		cursor.add(Cardinal.UP, 2);
		start = new Coord(cursor);
		end = new Coord(start);
		end.add(Cardinal.reverse(dir), 3);
		MetaBlock wire = new MetaBlock(Blocks.redstone_wire);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, wire, true, true);
	}
	
	private void overheadLight(World world, Random rand, ITheme theme, Coord origin){
		
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord cursor;
		
		WorldGenPrimitive.setBlock(world, origin, Blocks.air);
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true).setBlock(world, cursor);
			cursor.add(Cardinal.getOrthogonal(dir)[0]);
			stair.setBlock(world, cursor);
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.redstone_lamp);
	}
	

}
