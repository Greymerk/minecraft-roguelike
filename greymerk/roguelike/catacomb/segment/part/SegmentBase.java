package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.segment.ISegment;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

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
	ITheme theme;
	
	@Override
	public void generate(World world, Random rand, CatacombLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		if(level.hasNearbyNode(x, z)){
			return;
		}
		
		this.world = world;
		this.rand = rand;
		this.dir = dir;
		this.theme = theme;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		
		orth = Cardinal.getOrthogonal(dir);
		
		for (Cardinal wall : orth){
			if(isValidWall(wall)){
				genWall(wall);
			}
		}
		
		if(this instanceof SegmentArch || this instanceof SegmentMossyArch){
			addSupport();
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
		case 0: return Block.stairsWoodOak.blockID;
		case 1: return Block.stairsWoodSpruce.blockID;
		case 2: return Block.stairsStoneBrick.blockID;
		case 3: return Block.stairsCobblestone.blockID;
		case 4: return Block.stairsNetherBrick.blockID;
		default: return Block.stairsStoneBrick.blockID;
		}
	}
	
	private void addSupport(){
		if(!world.isAirBlock(x, y - 2, z)) return;
		
		int level = Catacomb.getLevel(y);
		
		WorldGenPrimitive.fillDown(world, x, y - 2, z, theme.getPrimaryWall());
		
		int stairId;
		switch(level){
		case 0: 
		case 1: 
		case 2: stairId = Block.stairsStoneBrick.blockID; break;
		case 3: stairId = Block.stairsCobblestone.blockID; break;
		case 4: stairId = Block.stairsNetherBrick.blockID; break;
		default: stairId = Block.stairsStoneBrick.blockID;
		}
		
		MetaBlock stair = new MetaBlock(stairId);
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
