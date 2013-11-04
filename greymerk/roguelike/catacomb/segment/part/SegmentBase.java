package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.segment.ISegment;
import greymerk.roguelike.worldgen.Cardinal;

import java.util.Random;

import net.minecraft.src.World;

public abstract class SegmentBase implements ISegment {

	protected World world;
	protected Random rand;
	protected Cardinal dir;
	
	protected int originX;
	protected int originY;
	protected int originZ;
	
	protected Cardinal[] orth;
	
	@Override
	public void generate(World world, Random rand, Cardinal dir, int x, int y, int z) {
		
		this.world = world;
		this.rand = rand;
		this.dir = dir;
		
		originX = x;
		originY = y;
		originZ = z;
		
		
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
			if(world.isAirBlock(originX - 1, originY + 1, originZ - 2)) return false;
			if(world.isAirBlock(originX + 1, originY + 1, originZ - 2)) return false;
			break;
		case SOUTH:
			if(world.isAirBlock(originX - 1, originY + 1, originZ + 2)) return false;
			if(world.isAirBlock(originX + 1, originY + 1, originZ + 2)) return false;
			break;
		case EAST:
			if(world.isAirBlock(originX + 2, originY + 1, originZ - 1)) return false;
			if(world.isAirBlock(originX + 2, originY + 1, originZ + 1)) return false;
			break;
		case WEST:
			if(world.isAirBlock(originX - 2, originY + 1, originZ - 1)) return false;
			if(world.isAirBlock(originX - 2, originY + 1, originZ + 1)) return false;
			break;
		}
		
		return true;
	}
}
