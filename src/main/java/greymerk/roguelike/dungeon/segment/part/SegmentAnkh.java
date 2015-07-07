package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.CatacombLevel;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.blocks.ColorBlock;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentAnkh extends SegmentBase{

	@Override
	protected void genWall(World world, Random rand, CatacombLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		Coord start;
		Coord end;
		Coord cursor;
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = theme.getSecondaryStair();
		MetaBlock glass = ColorBlock.get(Blocks.stained_glass, rand);
		MetaBlock white = new MetaBlock(Blocks.stained_hardened_clay);
		MetaBlock glowstone = new MetaBlock(Blocks.glowstone);
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(x, y, z);
		start.add(dir, 2);
		end = new Coord(start);
		end.add(Cardinal.UP, 2);
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		
		for(Cardinal o : orth){
			
			cursor = new Coord(x, y, z);
			cursor.add(dir, 2);
			cursor.add(o);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), false).setBlock(world, cursor);
			cursor.add(Cardinal.UP);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
		}
		
		start = new Coord(x, y, z);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, glass, true, true);
		start.add(dir);
		end.add(dir);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, white, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 3);
		cursor.add(Cardinal.DOWN);
		glowstone.setBlock(world, cursor);
		cursor.add(Cardinal.UP, 4);
		glowstone.setBlock(world, cursor);
	}

}
