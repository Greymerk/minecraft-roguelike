package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.segment.ISegment;
import greymerk.roguelike.worldgen.Cardinal;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public abstract class SegmentBase implements ISegment {

	protected World world;
	protected Random rand;
	protected Cardinal dir;
	
	protected int x;
	protected int y;
	protected int z;
	
	protected Cardinal[] orth;
	
	@Override
	public void generate(World world, Random rand, CatacombLevel level, Cardinal dir, int x, int y, int z) {
		
		if(level.hasNearbyNode(x, z)){
			return;
		}
		
		this.world = world;
		this.rand = rand;
		this.dir = dir;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		
		orth = Cardinal.getOrthogonal(dir);
		
		for (Cardinal wall : orth){
			if(isValidWall(wall)){
				genWall(wall);
			}
		}
	}
	
	protected abstract void genWall(Cardinal wallDirection);

	protected boolean isValidWall(Cardinal wallDirection) {
		
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
	
	protected int getStairType(int level){
		switch(Catacomb.getLevel(y)){
		case 0: return Block.stairsWoodSpruce.blockID;
		case 1: return Block.stairsWoodSpruce.blockID;
		case 2: return Block.stairsStoneBrick.blockID;
		case 3: return Block.stairsCobblestone.blockID;
		case 4: return Block.stairsNetherBrick.blockID;
		default: return Block.stairsStoneBrick.blockID;
		}
	}
}
