package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsCreeperDen implements IDungeon {
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
	
	MetaBlock floorBlock;
	MetaBlock wallBlock;
	
	public DungeonsCreeperDen() {
		dungeonHeight = 3;
		dungeonLength = 2;
		dungeonWidth = 2;
		numChests = 2;
	}

	public boolean generate(World inWorld, Random inRandom, ITheme theme, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		floorBlock = new MetaBlock(Blocks.gravel);
		wallBlock = new MetaBlock(Blocks.mossy_cobblestone);
		
		buildWalls();
		buildFloor();
		buildRoof();
		placeMobSpawner();
		createChests(numChests);


		return true;
	}
	
	public boolean isValidDungeonLocation(World world, int originX, int originY, int originZ) {

		int dungeonClearance = 0;
		for (int x = originX - dungeonLength - 1; x <= originX + dungeonLength + 1; x++) {
			for (int y = originY - 1; y <= originY + dungeonHeight + 1; y++) {
				for (int z = originZ - dungeonWidth - 1; z <= originZ + dungeonWidth + 1; z++) {
					Material material = world.getBlock(x, y, z).getMaterial();

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
			for (int blockY = originY + dungeonHeight; blockY >= originY - 3; blockY--) {
				for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
					
					if (blockX == originX - dungeonLength - 1 || blockZ == originZ - dungeonWidth - 1 || blockX == originX + dungeonLength + 1 || blockZ == originZ + dungeonWidth + 1) {

						if (blockY >= 0 && !world.getBlock(blockX, blockY - 1, blockZ).getMaterial().isSolid()) {
							WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, Blocks.air);
							continue;
						}
						if (!world.getBlock(blockX, blockY, blockZ).getMaterial().isSolid()) {
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
						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, Blocks.air);
					}
				}
			}
		}
	}
	
	protected void buildFloor(){
		
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++){
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){
				for(int blockY = originY - 1; blockY > originY - 4; blockY--){
					if (blockY < originY - 1 && rand.nextInt(12) == 0) {
						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, Blocks.tnt);
					}
					else if(rand.nextBoolean()){
						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, floorBlock);
					} else {
						WorldGenPrimitive.setBlock(world, blockX, blockY, blockZ, wallBlock);
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
				int chestPosX = (originX + rand.nextInt(dungeonLength * 2 + 1)) - dungeonLength;
				int chestPosY = originY;
				int chestPosZ = (originZ + rand.nextInt(dungeonWidth * 2 + 1)) - dungeonWidth;

				if (TreasureChest.isValidChestSpace(world, chestPosX, chestPosY, chestPosZ)) {
					TreasureChest.generate(world, rand, chestPosX, chestPosY, chestPosZ, Catacomb.getLevel(chestPosY), true);
					
					if(rand.nextBoolean()){
						WorldGenPrimitive.setBlock(world, chestPosX, chestPosY - 2, chestPosZ, Blocks.tnt);
					}
					
					break;
				}
				

			}
		}
	}

	protected void placeMobSpawner() {
		Spawner.generate(world, rand, originX, originY, originZ, Spawner.CREEPER);
	}
	
	public int getSize(){
		return 6;
	}
}
