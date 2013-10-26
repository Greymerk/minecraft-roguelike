package greymerk.roguelike;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class DungeonCorner implements IDungeon {

	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	
	@Override
	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;
		
		int stair;
		switch(Dungeon.getRank(originY)){
		case 2:
			stair = Block.stairsCobblestone.blockID;
			break;
		case 3:
			stair = Block.stairsNetherBrick.blockID;
			break;
		default:
			stair = Block.stairsStoneBrick.blockID;
		}
		
		MetaBlock southStair = new MetaBlock(stair, WorldGenPrimitive.blockOrientation(Cardinal.SOUTH, true));
		MetaBlock northStair = new MetaBlock(stair, WorldGenPrimitive.blockOrientation(Cardinal.NORTH, true));
		MetaBlock eastStair = new MetaBlock(stair, WorldGenPrimitive.blockOrientation(Cardinal.EAST, true));
		MetaBlock westStair = new MetaBlock(stair, WorldGenPrimitive.blockOrientation(Cardinal.WEST, true));
		
		
		IBlockFactory blocks = BlockFactoryProvider.getRandomizer(Dungeon.getRank(inOriginY), rand);
		
		// fill air inside
		WorldGenPrimitive.fillRectSolid(world, 	originX - 2, originY, originZ - 2, originX + 2, originY + 3, originZ + 2, 0);
		
		// shell
		WorldGenPrimitive.fillRectHollow(world, originX - 3, originY - 1, originZ - 3, originX + 3, originY + 4, originZ + 3, blocks, false, true);
		
		// pillars
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY, originZ - 2, originX - 2, originY + 3, originZ - 2, blocks, true, true);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 3, originZ - 1, southStair, true, true);
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 3, originZ - 2, eastStair, true, true);
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY, originZ + 2, originX - 2, originY + 3, originZ + 2, blocks, true, true);
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 3, originZ + 2, eastStair, true, true);
		WorldGenPrimitive.setBlock(world, originX - 2, originY + 3, originZ + 1, northStair, true, true);
		WorldGenPrimitive.fillRectSolid(world, originX + 2, originY, originZ - 2, originX + 2, originY + 3, originZ - 2, blocks, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 3, originZ - 2, westStair, true, true);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 3, originZ - 1, southStair, true, true);
		WorldGenPrimitive.fillRectSolid(world, originX + 2, originY, originZ + 2, originX + 2, originY + 3, originZ + 2, blocks, true, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 3, originZ + 2, westStair, true, true);
		WorldGenPrimitive.setBlock(world, originX + 2, originY + 3, originZ + 1, northStair, true, true);
		
		WorldGenPrimitive.setBlock(world, originX - 1, originY + 4, originZ, eastStair, false, true);
		WorldGenPrimitive.setBlock(world, originX + 1, originY + 4, originZ, westStair, false, true);
		WorldGenPrimitive.setBlock(world, originX, originY + 4, originZ - 1, southStair, false, true);
		WorldGenPrimitive.setBlock(world, originX, originY + 4, originZ + 1, northStair, false, true);
		
		WorldGenPrimitive.setBlock(world, originX, originY + 4, originZ, 0);
		blocks.setBlock(world, originX, originY + 6, originZ, false, true);
		
		return true;
	}

	@Override
	public boolean isValidDungeonLocation(World world, int x, int y, int z) {
		return false;
	}

}
