package greymerk.roguelike.catacomb.dungeon;

import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Coord;
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

public class DungeonsBase implements IDungeon {
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	byte dungeonHeight;
	int dungeonLength;
	int dungeonWidth;
	int woolColor;
	int numChests;
	
	int floorBlock;
	int wallBlock;
	
	public DungeonsBase() {
		dungeonHeight = 3;
		dungeonLength = 2;
		dungeonWidth = 2;
		numChests = 2;
		floorBlock = Block.cobblestoneMossy.blockID;
		wallBlock = Block.cobblestone.blockID;
	}

	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		buildWalls();
		buildFloor();
		buildRoof();
		TreasureChest[] types = {TreasureChest.BLOCKS, TreasureChest.FOOD, TreasureChest.SUPPLIES, TreasureChest.TOOLS};
		TreasureChest.createChests(world, rand, 1, WorldGenPrimitive.getRectSolid(
										originX - dungeonLength, originY, originZ - dungeonWidth,
										originX + dungeonLength, originY, originZ + dungeonWidth), types);
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
			for (int blockY = originY + dungeonHeight; blockY >= originY - 1; blockY--) {
				for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {

					// This prevents overlapping dungeons from overwriting
					// spawners
					if (world.getBlockId(blockX, blockY, blockZ) == Block.mobSpawner.blockID) {
						continue;
					}

					// this prevents overlapping dungeons from breaking chests
					if (world.getBlockId(blockX, blockY, blockZ) == Block.chest.blockID) {
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
						
						int mossyChance = 2 * (1 + (blockY - originY));
						
						if(mossyChance < 2){
							mossyChance = 2;
						}
						
						if(rand.nextInt(mossyChance) == 0){
							WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, floorBlock);
						} else {
							WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, wallBlock);
						}
						
					} else {
						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, 0);
					}
				}
			}
		}
	}
	
	protected void buildFloor(){
		List<Coord> shape = WorldGenPrimitive.getRectSolid(	originX - dungeonLength - 1, originY - 1, originZ - dungeonWidth - 1, 
															originX + dungeonLength + 1, originY - 1, originZ + dungeonWidth + 1);
		
		for (Coord block : shape){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if (rand.nextInt(4) == 0) {
				WorldGenPrimitive.setBlock(world, x, y, z, floorBlock);
			} else {
				WorldGenPrimitive.setBlock(world, x, y, z, wallBlock);
			}
		}
	}
	
	protected void buildRoof(){
		WorldGenPrimitive.fillRectSolid(world, 	originX - dungeonLength - 1, originY + dungeonHeight + 1, originZ - dungeonWidth - 1, 
												originX + dungeonLength + 1, originY + dungeonHeight + 1, originZ + dungeonWidth + 1,
												wallBlock, 0, 2, false, true);
	}

	protected void placeMobSpawner() {
		Spawner.generate(world, rand, originX, originY, originZ);
	}

}
