package greymerk.roguelike;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class DungeonsSpiderNest implements IDungeon {
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	byte dungeonHeight;
	int dungeonLength;
	int dungeonWidth;
	
	public DungeonsSpiderNest() {
		dungeonHeight = 2;
		dungeonLength = 3;
		dungeonWidth = 3;
	}

	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		buildWalls();
		Spawner.generate(world, rand, originX, originY, originZ, Spawner.CAVESPIDER);
		
		TreasureChest.createChests(world, rand, 1 + rand.nextInt(3), WorldGenPrimitive.getRectSolid(
				originX - dungeonLength, originY - 1, originZ - dungeonWidth,
				originX + dungeonLength, originY + 1, originZ + dungeonWidth));


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
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
				for (int blockY = originY + dungeonHeight; blockY >= originY - dungeonHeight; blockY--) {
					
					// This prevents overlapping dungeons from overwriting
					// spawners
					if (world.getBlockId(blockX, blockY, blockZ) == Block.mobSpawner.blockID) {
						continue;
					}

					// this prevents overlapping dungeons from breaking chests
					if (world.getBlockId(blockX, blockY, blockZ) == Block.chest.blockID) {
						continue;
					}

					int x = Math.abs(blockX - originX);
					int z = Math.abs(blockZ - originZ);
					
					int clearHeight = x > z ? x : z;
					
					if(blockY == originY){
						clear(blockX, blockY, blockZ);
					}
					
					if(clearHeight < 1){
						clearHeight = 1;
					}
					
					if(Math.abs(blockY - originY) > clearHeight){
						continue;
					}
						
					
					if(rand.nextInt(clearHeight)  == 0){
						clear(blockX, blockY, blockZ);
					} else if(rand.nextInt(5) == 0){
						world.setBlock(blockX, blockY, blockZ, Block.gravel.blockID);
					}
					
				}
			}
		}
	}

	private void clear(int x, int y, int z){
		if(rand.nextInt(3) == 0){
			world.setBlock(x, y, z, Block.web.blockID);
			return;
		}
		
		world.setBlock(x, y, z, 0);
	}
}
