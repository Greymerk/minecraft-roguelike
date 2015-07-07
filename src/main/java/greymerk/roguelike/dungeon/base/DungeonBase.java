package greymerk.roguelike.dungeon.base;

import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public abstract class DungeonBase implements IDungeonRoom{

	@Override
	public abstract boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin);
	
	@Override
	public abstract int getSize();
	
	@Override
	public boolean validLocation(World world, Cardinal dir, int x, int y, int z){
		
		int size = getSize();
		List<Coord> box = WorldGenPrimitive.getRectHollow(x - size, y - 2, z - size, x + size, y + 5, z + size);
		
		for(Coord pos : box){
			MetaBlock b = WorldGenPrimitive.getBlock(world, pos);
			if(!b.getBlock().getMaterial().isSolid()) return false;
		}
		
		return true;
	}
}
