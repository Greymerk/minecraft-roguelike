package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonLinkerTop extends DungeonBase{

	@Override
	public boolean generate(World world, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory pillar = theme.getPrimaryPillar();
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory floor = theme.getPrimaryFloor();
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, 5, 4);
		wall.fillRectHollow(world, rand, start, end, false, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP, 5);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.glowstone);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, -1, 4);
		floor.fillRectSolid(world, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			start = new Coord(origin);
			end = new Coord(origin);
			start.add(dir, 3);
			start.add(orth[0], 3);
			end.add(dir, 4);
			end.add(orth[0], 4);
			end.add(Cardinal.UP, 4);
			pillar.fillRectSolid(world, rand, start, end, true, true);
			
			start = new Coord(origin);
			start.add(dir, 3);
			start.add(orth[0], 2);
			start.add(Cardinal.UP, 4);
			end = new Coord(start);
			end.add(orth[1], 4);
			wall.fillRectSolid(world, rand, start, end, true, true);
			start.add(Cardinal.reverse(dir));
			end.add(Cardinal.reverse(dir));
			WorldGenPrimitive.fillRectSolid(world, rand, start, end, WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(dir), true), true, true);
			
			for(Cardinal o : orth){
				cursor = new Coord(origin);
				cursor.add(dir, 3);
				cursor.add(Cardinal.UP, 2);
				cursor.add(o, 2);
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
				cursor.add(Cardinal.UP);
				wall.setBlock(world, rand, cursor);
				cursor.add(Cardinal.reverse(o));
				WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(o), true).setBlock(world, cursor);
			}
		}
		

		
		
		return true;
	}

	@Override
	public int getSize() {
		return 6;
	}

}
