package greymerk.roguelike;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class DungeonsCrypt implements IDungeon {
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	List<Coord> airFill;
	IBlockFactory blocks;
	
	
	public DungeonsCrypt() {
		airFill = new ArrayList<Coord>();
	}

	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		rand = inRandom;
		world = inWorld;
		blocks = BlockFactoryProvider.getRandomizer(Dungeon.getRank(inOriginY), rand);
		
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		// clear box;
		WorldGenPrimitive.fillRectSolid(world, originX - 8, originY, originZ - 5, originX + 8, originY + 2, originZ + 5, 0);
		WorldGenPrimitive.fillRectSolid(world, originX - 5, originY, originZ - 8, originX + 5, originY + 2, originZ + 8, 0);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 8, originY - 1, originZ - 8, originX + 8, originY - 1, originZ + 8, blocks);
		WorldGenPrimitive.fillRectSolid(world, originX - 8, originY + 3, originZ - 8, originX + 8, originY + 5, originZ + 8, blocks);
		buildTombs();

		// columns
		int x;
		int y;
		int z;
		x = originX - 3;
		z = originZ - 3;
		WorldGenPrimitive.fillRectSolid(world, x, originY, z, x, originY + 2, z, blocks);
		x = originX - 3;
		z = originZ + 3;
		WorldGenPrimitive.fillRectSolid(world, x, originY, z, x, originY + 2, z, blocks);
		x = originX + 3;
		z = originZ - 3;
		WorldGenPrimitive.fillRectSolid(world, x, originY, z, x, originY + 2, z, blocks);
		x = originX + 3;
		z = originZ + 3;
		WorldGenPrimitive.fillRectSolid(world, x, originY, z, x, originY + 2, z, blocks);

		// ceiling gaps
		WorldGenPrimitive.fillRectSolid(world, originX - 4, originY + 3, originZ - 1, originX + 4, originY + 3, originZ + 1, 0);
		WorldGenPrimitive.fillRectSolid(world, originX - 1, originY + 3, originZ - 4, originX + 1, originY + 3, originZ + 4, 0);
		
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY + 4, originZ - 2, originX + 2, originY + 4, originZ + 2, 0);
		
		y = originY + 4;
		blocks.setBlock(world, originX - 2, y, originZ - 2);
		blocks.setBlock(world, originX - 2, y, originZ + 2);
		blocks.setBlock(world, originX + 2, y, originZ - 2);
		blocks.setBlock(world, originX + 2, y, originZ + 2);
		
		y = originY + 3;
		WorldGenPrimitive.setBlock(world, originX - 2, y, originZ - 2, 0);
		WorldGenPrimitive.setBlock(world, originX - 2, y, originZ + 2, 0);
		WorldGenPrimitive.setBlock(world, originX + 2, y, originZ - 2, 0);
		WorldGenPrimitive.setBlock(world, originX + 2, y, originZ + 2, 0);
		
		
		// entrance walls
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY, originZ - 9, originX + 2, originY + 2, originZ - 9, blocks, false, true);
		WorldGenPrimitive.fillRectSolid(world, originX - 2, originY, originZ + 9, originX + 2, originY + 2, originZ + 9, blocks, false, true);
		WorldGenPrimitive.fillRectSolid(world, originX - 9, originY, originZ - 2, originX - 9, originY + 2, originZ + 2, blocks, false, true);
		WorldGenPrimitive.fillRectSolid(world, originX + 9, originY, originZ - 2, originX + 9, originY + 2, originZ + 2, blocks, false, true);
		
		return true;
	}

	private void buildTombs(){
		
		//north
		tombWest(originX - 3, originY + 1, originZ - 7);
		tombEast(originX + 3, originY + 1, originZ - 7);
		
		//south
		tombWest(originX - 3, originY + 1, originZ + 7);
		tombEast(originX + 3, originY + 1, originZ + 7);
		
		//west
		tombNorth(originX - 7, originY + 1, originZ - 3);
		tombSouth(originX - 7, originY + 1, originZ + 3);
		
		//east
		tombNorth(originX + 7, originY + 1, originZ - 3);
		tombSouth(originX + 7, originY + 1, originZ + 3);
		
		for(Coord block : airFill){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			WorldGenPrimitive.setBlock(world, x, y, z, 0);
		}
	}
	
	private void tombNorth(int inX, int inY, int inZ){

		WorldGenPrimitive.fillRectHollow(world, inX - 1, inY - 1, inZ - 3, inX + 1, inY + 2, inZ, blocks, true, true);
		if(rand.nextInt(3) == 0){
			WorldGenPrimitive.setBlock(world, inX, inY, inZ - 2, 0);
			TreasureChest.generate(world, rand, inX, inY, inZ - 2, getType());
			airFill.add(new Coord(inX, inY + 1, inZ - 2));
			
			Spawner type = rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE;
			Spawner.generate(world, rand, inX, inY, inZ - 1, type);
		}
		
		WorldGenPrimitive.setBlock(world, inX, inY, inZ, Block.blockNetherQuartz.blockID);
	}
	
	private void tombWest(int inX, int inY, int inZ){
		WorldGenPrimitive.fillRectHollow(world, inX - 3, inY - 1, inZ - 1, inX, inY + 2, inZ + 1, blocks, true, true);
		
		if(rand.nextInt(3) == 0){
			WorldGenPrimitive.setBlock(world, inX - 2, inY, inZ, 0);
			TreasureChest.generate(world, rand, inX - 2, inY, inZ, getType());
			airFill.add(new Coord(inX - 2, inY + 1, inZ));
			
			Spawner type = rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE;
			Spawner.generate(world, rand, inX - 1, inY, inZ, type);
		}
		
		WorldGenPrimitive.setBlock(world, inX, inY, inZ, Block.blockNetherQuartz.blockID);		
	}
	

		
	private void tombSouth(int inX, int inY, int inZ){
		
		WorldGenPrimitive.fillRectHollow(world, inX - 1, inY - 1, inZ, inX + 1, inY + 2, inZ + 3, blocks, true, true);
		if(rand.nextInt(3) == 0){
			WorldGenPrimitive.setBlock(world, inX, inY, inZ + 2, 0);
			TreasureChest.generate(world, rand, inX, inY, inZ + 2, getType());
			airFill.add(new Coord(inX, inY + 1, inZ + 2));
			
			Spawner type = rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE;
			Spawner.generate(world, rand, inX, inY, inZ + 1, type);
		}
		WorldGenPrimitive.setBlock(world, inX, inY, inZ, Block.blockNetherQuartz.blockID);
	}
	
	private void tombEast(int inX, int inY, int inZ){
		WorldGenPrimitive.fillRectHollow(world, inX, inY - 1, inZ - 1, inX + 3, inY + 2, inZ + 1, blocks, true, true);
		
		if(rand.nextInt(3) == 0){
			WorldGenPrimitive.setBlock(world, inX + 2, inY, inZ, 0);
			TreasureChest.generate(world, rand, inX + 2, inY, inZ, getType());
			airFill.add(new Coord(inX + 2, inY + 1, inZ));
			
			Spawner type = rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE;
			Spawner.generate(world, rand, inX + 1, inY, inZ, type);
		}
		WorldGenPrimitive.setBlock(world, inX, inY, inZ, Block.blockNetherQuartz.blockID);		
	}
	
	private TreasureChest getType(){
		
		
		if(rand.nextInt(20) == 0){
			return TreasureChest.NOVELTY;
		}
		
		TreasureChest[] types = {TreasureChest.ARMOUR, TreasureChest.WEAPONS};
		return types[rand.nextInt(types.length)];		
	}
	
	public boolean isValidDungeonLocation(World world, int originX, int originY, int originZ) {
		return false;
	}
}
