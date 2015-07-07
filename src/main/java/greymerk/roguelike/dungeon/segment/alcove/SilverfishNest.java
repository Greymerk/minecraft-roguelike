package greymerk.roguelike.dungeon.segment.alcove;

import greymerk.roguelike.dungeon.segment.IAlcove;
import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SilverfishNest implements IAlcove{

	private static int RECESSED = 6;
	
	@Override
	public void generate(World world, Random rand, CatacombLevelSettings settings, int x, int y, int z, Cardinal dir) {
		
		Coord corridor = new Coord(x, y, z);
		Coord centre = new Coord(x, y, z);
		centre.add(dir, RECESSED);
		
		nest(world, rand, centre.getX(), centre.getY(), centre.getZ());
		
		Coord start = new Coord(corridor);
		start.add(Cardinal.UP);
		
		Coord end = new Coord(centre);
		end.add(Cardinal.UP);
		end.add(Cardinal.reverse(dir), 1);
		
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, new MetaBlock(Blocks.air), true, true);
		Spawner.generate(world, rand, settings, centre, Spawner.SILVERFISH);
		
	}

	@Override
	public boolean isValidLocation(World world, int x, int y, int z, Cardinal dir) {

		Coord centre = new Coord(x, y, z);
		centre.add(dir, RECESSED);
		x = centre.getX();
		y = centre.getY();
		z = centre.getZ();
		
		List<Coord> toCheck = WorldGenPrimitive.getRectSolid(x - 2, y + 1, z - 2, x + 2, y + 1, z + 2);

		for(Coord c : toCheck){
			if (world.isAirBlock(c.getBlockPos())) return false;
		}
		
		return true;
	}
	
	private void nest(World world, Random rand, int x, int y, int z){
		BlockWeightedRandom fish = new BlockWeightedRandom();
		MetaBlock egg = new MetaBlock(Blocks.monster_egg);
		egg.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.COBBLESTONE);
		fish.addBlock(egg, 20);
		fish.addBlock(new MetaBlock(Blocks.soul_sand), 5);
		WorldGenPrimitive.fillRectHollow(world, rand, new Coord(x - 2, y, z - 2), new Coord(x + 2, y + 3, z + 2), fish, true, true);
		
		fish.setBlock(world, rand, new Coord(x - 1, y + 2, z));
		fish.setBlock(world, rand, new Coord(x + 1, y + 2, z));
		fish.setBlock(world, rand, new Coord(x, y + 2, z - 1));
		fish.setBlock(world, rand, new Coord(x, y + 2, z + 1));
		fish.setBlock(world, rand, new Coord(x, y + 1, z));
		
		WorldGenPrimitive.setBlock(world, x, y + 2, z, Blocks.flowing_water);
	}
}
