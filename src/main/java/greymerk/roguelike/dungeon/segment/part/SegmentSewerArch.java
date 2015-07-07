package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentSewerArch extends SegmentBase {

	@Override
	protected void genWall(World world, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
			
		MetaBlock stair = theme.getSecondaryStair(); 
		WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true);
		MetaBlock water = new MetaBlock(Blocks.flowing_water);
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock bars = new MetaBlock(Blocks.iron_bars);
		MetaBlock mossy = new MetaBlock(Blocks.mossy_cobblestone);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 3);
		WorldGenPrimitive.setBlock(world, rand, cursor, mossy, false, true);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, rand, cursor, water, false, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		bars.setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		bars.setBlock(world, cursor);
		
		start = new Coord(x, y, z);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, water, true, true);
		
		for(Cardinal o : orth){
			cursor = new Coord(x, y, z);
			cursor.add(o, 1);
			cursor.add(dir, 2);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getPrimaryWall(), true, true);
			cursor.add(Cardinal.reverse(dir), 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);			
		}
	}
}
