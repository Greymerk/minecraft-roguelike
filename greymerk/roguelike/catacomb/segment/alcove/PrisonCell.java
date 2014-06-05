package greymerk.roguelike.catacomb.segment.alcove;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.segment.IAlcove;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Door;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

public class PrisonCell implements IAlcove{

	private static int RECESSED = 5;
	private ITheme theme;
	
	@Override
	public void generate(World world, Random rand, ITheme theme, int x, int y, int z, Cardinal dir) {
		
		this.theme = theme;
		
		Coord corridor = new Coord(x, y, z);
		Coord centre = new Coord(x, y, z);
		centre.add(dir, RECESSED);
		
		switch(dir){
		case NORTH: north(world, rand, corridor, centre); return;
		case SOUTH: south(world, rand, corridor, centre); return;
		case EAST: east(world, rand, corridor, centre); return;
		case WEST: west(world, rand, corridor, centre); return;
		}
		
		
		
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
			if (world.isAirBlock(c.getX(), c.getY(), c.getZ())) return false;
		}
		
		return true;
	}

	private void north(World world, Random rand, Coord corridor, Coord centre){

		int x = centre.getX();
		int y = centre.getY();
		int z = centre.getZ();
		
		IBlockFactory walls = theme.getPrimaryWall();
		WorldGenPrimitive.fillRectHollow(world, rand, x - 2, y - 1, z - 2, x + 2, y + 3, z + 2, walls, true, true);
		
		Coord end = new Coord(centre);
		end.add(Cardinal.UP, 1);
		
		WorldGenPrimitive.fillRectSolid(world, rand, corridor, end, new MetaBlock(0), true, true);

		WorldGenPrimitive.setBlock(world, x, y, z + 2, Block.pressurePlateStone.blockID);
		if(rand.nextBoolean()) Spawner.generate(world, rand, x, y - 1, z + 1, Spawner.ZOMBIE);
		Door.generate(world, x, y, z + 3, Cardinal.NORTH, Door.IRON);
	}

	
	private void south(World world, Random rand, Coord corridor, Coord centre){

		int x = centre.getX();
		int y = centre.getY();
		int z = centre.getZ();
		
		IBlockFactory walls = theme.getPrimaryWall();
		WorldGenPrimitive.fillRectHollow(world, rand, x - 2, y - 1, z - 2, x + 2, y + 3, z + 2, walls, true, true);
		
		Coord end = new Coord(centre);
		end.add(Cardinal.UP, 1);
		
		WorldGenPrimitive.fillRectSolid(world, rand, corridor, end, new MetaBlock(0), true, true);

		WorldGenPrimitive.setBlock(world, x, y, z - 2, Block.pressurePlateStone.blockID);
		if(rand.nextBoolean()) Spawner.generate(world, rand, x, y - 1, z - 1, Spawner.ZOMBIE);
		Door.generate(world, x, y, z - 3, Cardinal.SOUTH, Door.IRON);		
	}
	
	private void east(World world, Random rand, Coord corridor, Coord centre){

		int x = centre.getX();
		int y = centre.getY();
		int z = centre.getZ();
		
		IBlockFactory walls = theme.getPrimaryWall();
		WorldGenPrimitive.fillRectHollow(world, rand, x - 2, y - 1, z - 2, x + 2, y + 3, z + 2, walls, true, true);
		
		Coord end = new Coord(centre);
		end.add(Cardinal.UP, 1);
		
		WorldGenPrimitive.fillRectSolid(world, rand, corridor, end, new MetaBlock(0), true, true);

		WorldGenPrimitive.setBlock(world, x - 2, y, z, Block.pressurePlateStone.blockID);
		if(rand.nextBoolean()) Spawner.generate(world, rand, x - 1, y - 1, z, Spawner.ZOMBIE);
		Door.generate(world, x - 3, y, z, Cardinal.EAST, Door.IRON);
	}	

	private void west(World world, Random rand, Coord corridor, Coord centre){

		int x = centre.getX();
		int y = centre.getY();
		int z = centre.getZ();
		
		IBlockFactory walls = theme.getPrimaryWall();
		WorldGenPrimitive.fillRectHollow(world, rand, x - 2, y - 1, z - 2, x + 2, y + 3, z + 2, walls, true, true);
		
		Coord end = new Coord(centre);
		end.add(Cardinal.UP, 1);
		
		WorldGenPrimitive.fillRectSolid(world, rand, corridor, end, new MetaBlock(0), true, true);

		WorldGenPrimitive.setBlock(world, x + 2, y, z, Block.pressurePlateStone.blockID);
		if(rand.nextBoolean()) Spawner.generate(world, rand, x + 1, y - 1, z, Spawner.ZOMBIE);
		Door.generate(world, x + 3, y, z, Cardinal.WEST, Door.IRON);
	}	
	
	
}
