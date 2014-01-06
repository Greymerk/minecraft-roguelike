package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.BlockFactoryProvider;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class DungeonsNetherBrick implements IDungeon {
	

	public DungeonsNetherBrick() {
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		
		int height = 3;
		int length = 2 + rand.nextInt(3);
		int width = 2 + rand.nextInt(3);
		
		IBlockFactory walls = BlockFactoryProvider.getRandomizer(Catacomb.getLevel(y), rand);
		WorldGenPrimitive.fillRectHollow(world, x - length - 1, y - 1, z - width - 1, x + length + 1, y + height + 1, z + width + 1, walls, false, true);
		
		BlockRandomizer floor = new BlockRandomizer(rand, new MetaBlock(Block.netherBrick.blockID));
		floor.addBlock(new MetaBlock(Block.netherrack.blockID), 3);
		floor.addBlock(new MetaBlock(Block.oreNetherQuartz.blockID), 5);
		floor.addBlock(new MetaBlock(Block.blockRedstone.blockID), 10);
		floor.addBlock(new MetaBlock(Block.blockGold.blockID), 50);
		floor.addBlock(new MetaBlock(Block.blockDiamond.blockID), 100);
		WorldGenPrimitive.fillRectSolid(world, x - length - 1, y - 1, z - width - 1, x + length + 1, y - 1, z + width + 1, floor);

		// lava crap under the floor
		BlockRandomizer subFloor = new BlockRandomizer(rand, new MetaBlock(Block.lavaStill.blockID));
		subFloor.addBlock(new MetaBlock(Block.obsidian.blockID), 3);
		WorldGenPrimitive.fillRectSolid(world, x - length, y - 5, z - width, x + length, y - 2, z + width, subFloor);
		
		BlockRandomizer ceiling = new BlockRandomizer(rand, new MetaBlock(Block.netherFence.blockID));
		ceiling.addBlock(new MetaBlock(0), 2);
		WorldGenPrimitive.fillRectSolid(world, x - length, y + height, z - width, x + length, y + height, z + width, ceiling);
		
		TreasureChest.createChests(world, rand, 1, WorldGenPrimitive.getRectSolid(x - length, y, z - width, x + length, y, z + width));

		Spawner.generate(world, rand, x - length - 1, y + rand.nextInt(2), z - width - 1);
		Spawner.generate(world, rand, x - length - 1, y + rand.nextInt(2), z + width + 1);
		Spawner.generate(world, rand, x + length + 1, y + rand.nextInt(2), z - width - 1);
		Spawner.generate(world, rand, x + length + 1, y + rand.nextInt(2), z + width + 1);

		return true;
	}
	
	public int getSize(){
		return 6;
	}
}
