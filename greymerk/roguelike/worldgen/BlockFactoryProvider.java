package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.src.Block;

public class BlockFactoryProvider {

	public static final IBlockFactory getRandomizer(int level, Random rand){
		return getRandomizer(level, rand, false);
	}
	
	public static final IBlockFactory getRandomizer(int level, Random rand, boolean broken){
		
		BlockRandomizer randomizer;
		
		switch(level){
		case 0:
			randomizer = new BlockRandomizer(rand, new MetaBlock(Block.stoneBrick.blockID));
			if(broken) randomizer.addBlock(new MetaBlock(0), 3);
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 30);
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 30);
			return randomizer;
		case 1:
			randomizer = new BlockRandomizer(rand, new MetaBlock(Block.stoneBrick.blockID, 2));
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 3);
			if(broken) randomizer.addBlock(new MetaBlock(0), 3);
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID), 6);
			return randomizer;
		case 2:
			randomizer = new BlockRandomizer(rand, new MetaBlock(Block.stoneBrick.blockID, 2));
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 3);
			if(broken) randomizer.addBlock(new MetaBlock(0), 3);
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID), 6);
			randomizer.addBlock(new MetaBlock(Block.cobblestone.blockID), 10);
			randomizer.addBlock(new MetaBlock(Block.gravel.blockID), 20);
			return randomizer;
		case 3:
			randomizer = new BlockRandomizer(rand, new MetaBlock(Block.cobblestone.blockID));
			randomizer.addBlock(new MetaBlock(Block.cobblestoneMossy.blockID), 3);
			if(broken) randomizer.addBlock(new MetaBlock(0), 3);
			randomizer.addBlock(new MetaBlock(Block.silverfish.blockID, 1), 5);
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 10);
			randomizer.addBlock(new MetaBlock(Block.gravel.blockID), 60);
			return randomizer;
		case 4:
			randomizer = new BlockRandomizer(rand, new MetaBlock(Block.netherBrick.blockID));
			if(broken) randomizer.addBlock(new MetaBlock(0), 3);
			randomizer.addBlock(new MetaBlock(Block.netherrack.blockID), 10);
			randomizer.addBlock(new MetaBlock(Block.oreNetherQuartz.blockID), 20);
			randomizer.addBlock(new MetaBlock(Block.slowSand.blockID), 30);
			randomizer.addBlock(new MetaBlock(Block.coalBlock.blockID), 100);
			return randomizer;
		default:
			return new BlockFactoryBrick(rand);
		}
	}
}
