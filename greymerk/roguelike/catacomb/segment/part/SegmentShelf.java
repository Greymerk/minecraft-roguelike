package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentShelf extends SegmentBase {
	
	@Override
	protected void genWall(Cardinal dir) {
		
		MetaBlock air = new MetaBlock(0);
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
		WorldGenPrimitive.fillRectSolid(world, start, end, theme.getSecondaryWall(), false, true);
		start.add(dir, 1);
		end.add(dir, 1);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, start, end, theme.getSecondaryWall(), false, true);
		start.add(Cardinal.reverse(dir), 1);
		start.add(Cardinal.UP, 1);
		end.add(Cardinal.reverse(dir), 1);
		WorldGenPrimitive.fillRectSolid(world, start, end, air, false, true);
		cursor.add(Cardinal.UP, 2);
		for(Cardinal d : orth){
			Coord c = new Coord(cursor);
			c.add(d, 1);
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(d), true));
			WorldGenPrimitive.setBlock(world, c, stair, true, true);
		}
	}	
}
