package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class DungeonsEnder implements IDungeon {
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	byte dungeonHeight;
	int dungeonLength;
	int dungeonWidth;
	
	int floorBlock;
	int wallBlock;
	
	public DungeonsEnder() {
		dungeonHeight = 10;
		dungeonLength = 4;
		dungeonWidth = 4;
		floorBlock = Block.blockNetherQuartz.blockID;
		wallBlock = Block.obsidian.blockID;
	}

	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;


		buildWalls();
		buildFloor();

		
		createChests(2);
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
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++) {
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){
				for (int blockY = originY - 1; blockY <= originY + dungeonHeight; blockY++) {

					int height = originY + rand.nextInt(8) + 2;
					
					// This prevents overlapping dungeons from overwriting
					// spawners
					if (world.getBlockId(blockX, blockY, blockZ) == Block.mobSpawner.blockID) {
						continue;
					}

					// this prevents overlapping dungeons from breaking chests
					if (world.getBlockId(blockX, blockY, blockZ) == Block.chest.blockID) {
						continue;
					}

					if(blockY > height){
						continue;
					}
					
					if (blockX == originX - dungeonLength - 1 || blockZ == originZ - dungeonWidth - 1 || blockX == originX + dungeonLength + 1 || blockZ == originZ + dungeonWidth + 1) {

						if (blockY >= 0 && !world.getBlockMaterial(blockX, blockY - 1, blockZ).isSolid()) {
							WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, 0);
							continue;
						}
						if (!world.getBlockMaterial(blockX, blockY, blockZ)
								.isSolid()) {
							continue;
						}

						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, wallBlock);
					} else {
						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, 0);
					}
				}
			}
		}
	}
	
	protected void buildFloor(){
		
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++){
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){
				if (blockX % 2 == 0) {
					if(blockZ % 2 == 0){
						WorldGenPrimitive.setBlock(world, blockX, originY - 1, blockZ, floorBlock);
					} else {
						WorldGenPrimitive.setBlock(world, blockX, originY - 1, blockZ, wallBlock);
					}
				} else {
					if(blockZ % 2 == 0){
						WorldGenPrimitive.setBlock(world, blockX, originY - 1, blockZ, wallBlock);
					} else {
						WorldGenPrimitive.setBlock(world, blockX, originY - 1, blockZ, floorBlock);
					}
				}
			}
		}
	}
	
	protected void buildRoof(){
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++){
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){
				WorldGenPrimitive.setBlock(world, blockX, originY + dungeonHeight + 1, blockZ, wallBlock);
			}
		}
	}



	protected void createChests(int numChests) {

		for (int chestCount = 0; chestCount < numChests; chestCount++) {

			for (int attempts = 0; attempts < 3; attempts++) {
				int x = (originX + rand.nextInt(dungeonLength * 2 + 1))
						- dungeonLength;
				int y = originY;
				int z = (originZ + rand.nextInt(dungeonWidth * 2 + 1))
						- dungeonWidth;

				if(    world.getBlockId(x - 1, y, z) == Block.obsidian.blockID
					|| world.getBlockId(x + 1, y, z) == Block.obsidian.blockID
					|| world.getBlockId(x, y, z - 1) == Block.obsidian.blockID
					|| world.getBlockId(x, y, z + 1) == Block.obsidian.blockID
					&& world.getBlockId(x, y, z) == 0){
					
					WorldGenPrimitive.setBlock(world, x, y, z, Block.enderChest.blockID);
					break;
				}
			}
		}
	}

	protected void placeMobSpawner() {
		Spawner.generate(world, rand, originX, originY, originZ, Spawner.ENDERMAN);
	}
}
