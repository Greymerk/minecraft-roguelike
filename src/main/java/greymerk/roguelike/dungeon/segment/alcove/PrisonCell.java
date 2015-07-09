package greymerk.roguelike.dungeon.segment.alcove;

import greymerk.roguelike.dungeon.segment.IAlcove;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.blocks.Door;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class PrisonCell implements IAlcove{

	private static int RECESSED = 5;
	private ITheme theme;
	
	@Override
	public void generate(World world, Random rand, LevelSettings settings, int x, int y, int z, Cardinal dir) {
		
		this.theme = settings.getTheme();
		IBlockFactory walls = theme.getPrimaryWall();
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock plate = new MetaBlock(Blocks.stone_pressure_plate);
		
		Coord origin = new Coord(x, y, z);
		
		Coord start = new Coord(origin);
		start.add(dir, RECESSED);
		Coord end = new Coord(start);
		start.add(-2, -1, -2);
		end.add(2, 3, 2);
		walls.fillRectHollow(world, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		end.add(dir, RECESSED);
		end.add(Cardinal.UP);
		air.fillRectSolid(world, rand, start, end, true, true);
		
		Coord cursor = new Coord(origin);
		cursor.add(dir, RECESSED - 1);
		plate.setBlock(world, cursor);
		cursor.add(Cardinal.DOWN);
		if(rand.nextBoolean()) Spawner.generate(world, rand, settings, cursor, Spawner.ZOMBIE);
		
		cursor = new Coord(origin);
		cursor.add(dir, 3);
		Door.generate(world, cursor, Cardinal.reverse(dir), Door.IRON);
		
	}

	@Override
	public boolean isValidLocation(World world, int x, int y, int z, Cardinal dir) {

		Coord centre = new Coord(x, y, z);
		centre.add(dir, RECESSED);
		x = centre.getX();
		y = centre.getY();
		z = centre.getZ();
		
		List<Coord> toCheck = WorldGenPrimitive.getRectSolid(x - 2, y, z - 2, x + 2, y, z + 2);

		for(Coord c : toCheck){
			if (world.isAirBlock(c.getBlockPos())) return false;
		}
		
		return true;
	}
}
