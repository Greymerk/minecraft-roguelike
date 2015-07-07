package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.segment.ISegment;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;

import java.util.Random;

import net.minecraft.world.World;

public abstract class SegmentBase implements ISegment {


	
	@Override
	public void generate(World world, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		if(level.hasNearbyNode(x, z)) return;
		
		if(isValidWall(world, dir, x, y, z)){
			genWall(world, rand, level, dir, theme, x, y, z);
		}
	}
	
	protected abstract void genWall(World world, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z);

	protected boolean isValidWall(World world, Cardinal wallDirection, int x, int y, int z) {
		
		switch(wallDirection){
		case NORTH:
			if(world.isAirBlock(new Coord(x - 1, y + 1, z - 2).getBlockPos())) return false;
			if(world.isAirBlock(new Coord(x + 1, y + 1, z - 2).getBlockPos())) return false;
			break;
		case SOUTH:
			if(world.isAirBlock(new Coord(x - 1, y + 1, z + 2).getBlockPos())) return false;
			if(world.isAirBlock(new Coord(x + 1, y + 1, z + 2).getBlockPos())) return false;
			break;
		case EAST:
			if(world.isAirBlock(new Coord(x + 2, y + 1, z - 1).getBlockPos())) return false;
			if(world.isAirBlock(new Coord(x + 2, y + 1, z + 1).getBlockPos())) return false;
			break;
		case WEST:
			if(world.isAirBlock(new Coord(x - 2, y + 1, z - 1).getBlockPos())) return false;
			if(world.isAirBlock(new Coord(x - 2, y + 1, z + 1).getBlockPos())) return false;
			break;
		}
		
		return true;
	}
	

}
