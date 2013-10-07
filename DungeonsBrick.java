package greymerk.roguelike;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class DungeonsBrick implements IDungeon {

	
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	private static final int HEIGHT = 3;
	private static final int LENGTH = 2;
	private static final int WIDTH = 2;
		
	public DungeonsBrick(){
	}
	
	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;
		IBlockFactory blocks = BlockFactoryProvider.getRandomizer(Dungeon.getRank(inOriginY), rand);
		
		// fill air inside
		WorldGenPrimitive.fillRectSolid(world, 	originX - LENGTH, originY, originZ - WIDTH,
												originX + LENGTH, originY + HEIGHT, originZ + WIDTH, 0);
		
		// walls
		List<Coord> walls = WorldGenPrimitive.getRectHollow(	originX - LENGTH - 1, originY - 1, originZ - WIDTH - 1, 
																originX + LENGTH + 1, originY + HEIGHT, originZ + WIDTH + 1);
		
		
		for (Coord block : walls){
			
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			blocks.setBlock(inWorld, x, y, z, false, true);
		}

		//floor
		List<Coord> floor = WorldGenPrimitive.getRectSolid(	originX - LENGTH - 1, originY + HEIGHT + 1, originZ - WIDTH - 1, 
															originX + LENGTH + 1, originY + HEIGHT + 1, originZ + WIDTH + 1);

		for (Coord block : floor){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			blocks.setBlock(inWorld, x, y, z, false, true);
		}
				
		// roof
		WorldGenPrimitive.fillRectSolid(world, 	originX - LENGTH - 1, originY + HEIGHT + 1, originZ - WIDTH - 1, 
				originX + LENGTH + 1, originY + HEIGHT + 1, originZ + WIDTH + 1,
				blocks, false, true);
		
		// Chests
		List<Coord> space = new ArrayList<Coord>();
		space.add(new Coord(originX - 2, originY, originZ - 2));
		space.add(new Coord(originX - 2, originY, originZ + 2));
		space.add(new Coord(originX + 2, originY, originZ - 2));
		space.add(new Coord(originX + 2, originY, originZ + 2));

		TreasureChest[] types = {TreasureChest.ARMOUR, TreasureChest.WEAPONS, TreasureChest.TOOLS};
		TreasureChest.createChests(inWorld, inRandom, 1, space, types);
		
		placeMobSpawner();

		return true;
	}
	protected void placeMobSpawner() {
		Spawner.generate(world, rand, originX, originY, originZ);
	}

	
	public boolean isValidDungeonLocation(World world, int originX, int originY, int originZ) {

		int dungeonClearance = 0;
		for (int x = originX - LENGTH - 1; x <= originX + LENGTH + 1; x++) {
			for (int y = originY - 1; y <= originY + HEIGHT + 1; y++) {
				for (int z = originZ - WIDTH - 1; z <= originZ + WIDTH + 1; z++) {
					Material material = world.getBlockMaterial(x, y, z);

					if (y == originY - 1 && !material.isSolid()) {
						return false;
					}

					if (y == originY + HEIGHT + 1 && !material.isSolid()) {
						return false;
					}

					if ((      x == originX - LENGTH - 1
							|| x == originX + LENGTH + 1
							|| z == originZ - WIDTH - 1
							|| z == originZ + WIDTH + 1)
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
}
