package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class DungeonsNetherBrick implements IDungeon {
	
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	byte dungeonHeight;
	int dungeonLength;
	int dungeonWidth;

	public DungeonsNetherBrick() {
	}

	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;
		
		dungeonHeight = 3;
		dungeonLength = 2 + rand.nextInt(3);
		dungeonWidth = 2 + rand.nextInt(3);
		
		buildWalls();
		buildFloor();
		buildRoof();
		TreasureChest.createChests(world, rand, 1, WorldGenPrimitive.getRectSolid(
				originX - dungeonLength, originY, originZ - dungeonWidth,
				originX + dungeonLength, originY, originZ + dungeonWidth));

		placeMobSpawner();

		return true;
	}
	
	public boolean isValidDungeonLocation(World world, int originX, int originY, int originZ) {

		int dungeonClearance = 0;
		for (int x = originX - dungeonLength - 1; x <= originX + dungeonLength + 1; x++) {
			for (int y = originY - 1; y <= originY + dungeonHeight + 1; y++) {
				for (int z = originZ - dungeonWidth - 1; z <= originZ + dungeonWidth + 1; z++) {
					Material material = world.getBlockMaterial(x, y, z);

					if (y == originY - 1 && !material.isSolid()) {
						return false;
					}

					if (y == originY + dungeonHeight + 1 && !material.isSolid()) {
						return false;
					}

					if ((      x == originX - dungeonLength - 1
							|| x == originX + dungeonLength + 1
							|| z == originZ - dungeonWidth - 1
							|| z == originZ + dungeonWidth + 1)
							&& y == originY
							&& world.isAirBlock(x, y, z)
							&& world.isAirBlock(x, y + 1, z)){
						dungeonClearance++;
					}
				}
			}
		}

		if (dungeonClearance < 1 || dungeonClearance > 5) {
			return false;
		}

		return true;
	}

	protected void buildWalls() {
		for (int x = originX - dungeonLength - 1; x <= originX + dungeonLength + 1; x++) {
			for (int y = originY + dungeonHeight; y >= originY - 1; y--) {
				for (int z = originZ - dungeonWidth - 1; z <= originZ + dungeonWidth + 1; z++) {

					// This prevents overlapping dungeons from overwriting
					// spawners
					if (world.getBlockId(x, y, z) == Block.mobSpawner.blockID) {
						continue;
					}

					// this prevents overlapping dungeons from breaking chests
					if (world.getBlockId(x, y, z) == Block.chest.blockID) {
						continue;
					}

					if (x == originX - dungeonLength - 1 || z == originZ - dungeonWidth - 1 || x == originX + dungeonLength + 1 || z == originZ + dungeonWidth + 1) {

						if (y >= 0 && !world.getBlockMaterial(x, y - 1, z).isSolid()) {
							WorldGenPrimitive.setBlock(world, x, y, z, 0);
							continue;
						}
						if (!world.getBlockMaterial(x, y, z)
								.isSolid()) {
							continue;
						}
						
                    	if(y > originY && y < originY + 4){
	                    	
	                    	if((z == originZ - dungeonWidth - 1 || z == originZ + dungeonWidth + 1) && x % 3 == 0){
	                    		WorldGenPrimitive.setBlock(world, x, y, z, Block.netherFence.blockID);
	                    		continue;
	                    	}
	                    	
	                    	if((x == originX - dungeonLength - 1 || x == originX + dungeonLength + 1) && z % 3 == 0){
	                    		WorldGenPrimitive.setBlock(world, x, y, z, Block.netherFence.blockID);
	                    		continue;
	                    	}
	                    }
						

                    	WorldGenPrimitive.setBlock(world, x, y, z, Block.netherBrick.blockID);
						
					} else {
						WorldGenPrimitive.setBlock(world, x, y, z, 0);
					}
				}
			}
		}
	}
	
	protected void buildFloor(){
		
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++){
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){

				if (rand.nextInt(10) == 0) {
					WorldGenPrimitive.setBlock(world, blockX, originY - 1, blockZ, Block.oreNetherQuartz.blockID);
					continue;
				}
				
				if (rand.nextInt(10) == 0) {
					WorldGenPrimitive.setBlock(world, blockX, originY - 1, blockZ, Block.blockRedstone.blockID);
					continue;
				}
				
				if (rand.nextInt(4) == 0) {
					WorldGenPrimitive.setBlock(world, blockX, originY - 1, blockZ, Block.netherrack.blockID);
					continue;
				}

				WorldGenPrimitive.setBlock(world, blockX, originY - 1, blockZ, Block.netherBrick.blockID);
			}
		}
	}
	
	protected void buildRoof(){
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++){
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){
				WorldGenPrimitive.setBlock(world, blockX, originY + dungeonHeight + 1, blockZ, Block.netherBrick.blockID);
			}
		}
		
		for (int blockX = originX - dungeonLength; blockX <= originX + dungeonLength; blockX++){
			for (int blockZ = originZ - dungeonWidth; blockZ <= originZ + dungeonWidth; blockZ++){
				if(rand.nextBoolean()){
					WorldGenPrimitive.setBlock(world, blockX, originY + dungeonHeight, blockZ, Block.netherFence.blockID);
				}
			}
		}
	}
	
	protected void placeMobSpawner(){
	
		int xOffset[] = {4, -4, 4, -4};
		int zOffset[] = {4, 4, -4, -4};
		
		for(int i = 0; i < 4; i++){
			
			int spawnX = originX + xOffset[i];
			int spawnY = originY + rand.nextInt(3);
			int spawnZ = originZ + zOffset[i];
			
			
	        world.setBlock(spawnX, spawnY, spawnZ, Block.mobSpawner.blockID, 0, 2);
	        Spawner.generate(world, rand, spawnX, spawnY, spawnZ);
		}
	}
}
