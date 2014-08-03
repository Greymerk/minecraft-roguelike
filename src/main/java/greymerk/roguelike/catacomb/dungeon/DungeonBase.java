package greymerk.roguelike.catacomb.dungeon;

import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public abstract class DungeonBase implements IDungeon{

	@Override
	public abstract boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, int x, int y, int z);
	
	@Override
	public abstract int getSize();
	
	@Override
	public boolean validLocation(World world, Cardinal dir, int x, int y, int z){
		
		int size = getSize();
		List<Coord> box = WorldGenPrimitive.getRectHollow(x - size, y - 2, z - size, x + size, y + 5, z + size);
		
		for(Coord pos : box){
			Block b = world.getBlock(pos.getX(), pos.getY(), pos.getZ());
			if(!b.getMaterial().isSolid()) return false;
		}
		
		return true;
	}
}
