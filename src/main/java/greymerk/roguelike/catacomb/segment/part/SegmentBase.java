package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.segment.ISegment;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.world.World;

public abstract class SegmentBase implements ISegment {


	
	@Override
	public void generate(World world, Random rand, CatacombLevel level, Cardinal corridorDirection, ITheme theme, int x, int y, int z) {
		
		if(level.hasNearbyNode(x, z)){
			return;
		}
		
		Cardinal[] orth = Cardinal.getOrthogonal(corridorDirection);
		
		for (Cardinal dir : orth){
			if(isValidWall(world, dir, x, y, z)){
				genWall(world, rand, level, dir, theme, x, y, z);
			}
		}
		
		if(this instanceof SegmentArch || this instanceof SegmentMossyArch){
			addSupport(world, rand, theme, x, y, z);
		}
		
	}
	
	protected abstract void genWall(World world, Random rand, CatacombLevel level, Cardinal dir, ITheme theme, int x, int y, int z);

	protected boolean isValidWall(World world, Cardinal wallDirection, int x, int y, int z) {
		
		switch(wallDirection){
		case NORTH:
			if(world.isAirBlock(x - 1, y + 1, z - 2)) return false;
			if(world.isAirBlock(x + 1, y + 1, z - 2)) return false;
			break;
		case SOUTH:
			if(world.isAirBlock(x - 1, y + 1, z + 2)) return false;
			if(world.isAirBlock(x + 1, y + 1, z + 2)) return false;
			break;
		case EAST:
			if(world.isAirBlock(x + 2, y + 1, z - 1)) return false;
			if(world.isAirBlock(x + 2, y + 1, z + 1)) return false;
			break;
		case WEST:
			if(world.isAirBlock(x - 2, y + 1, z - 1)) return false;
			if(world.isAirBlock(x - 2, y + 1, z + 1)) return false;
			break;
		}
		
		return true;
	}
	
	private void addSupport(World world, Random rand, ITheme theme, int x, int y, int z){
		if(!world.isAirBlock(x, y - 2, z)) return;
		
		WorldGenPrimitive.fillDown(world, rand, x, y - 2, z, theme.getPrimaryPillar());
		
		MetaBlock stair = theme.getPrimaryStair();
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		WorldGenPrimitive.setBlock(world, x - 1, y - 2, z, stair);
		
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		WorldGenPrimitive.setBlock(world, x + 1, y - 2, z, stair);
		
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		WorldGenPrimitive.setBlock(world, x, y - 2, z + 1, stair);
		
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		WorldGenPrimitive.setBlock(world, x, y - 2, z - 1, stair);
		
	}
}
