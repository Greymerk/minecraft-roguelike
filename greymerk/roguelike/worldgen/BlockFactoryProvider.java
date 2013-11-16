package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.src.Block;

public class BlockFactoryProvider {

	public static final IBlockFactory getRandomizer(int rank, Random rand){
		
		BlockRandomizer randomizer;
		
		switch(rank){
		
		case 3:
			randomizer = new BlockRandomizer(rand, new MetaBlock(Block.netherBrick.blockID));
			randomizer.addBlock(new MetaBlock(Block.netherrack.blockID), 10);
			randomizer.addBlock(new MetaBlock(Block.oreNetherQuartz.blockID), 20);
			return randomizer;
		case 2:
			randomizer = new BlockRandomizer(rand, new MetaBlock(Block.cobblestone.blockID));
			randomizer.addBlock(new MetaBlock(Block.cobblestoneMossy.blockID), 3);
			randomizer.addBlock(new MetaBlock(Block.silverfish.blockID, 1), 5);
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 10);
			randomizer.addBlock(new MetaBlock(Block.gravel.blockID, 1), 15);
			return randomizer;
		case 1:
			randomizer = new BlockRandomizer(rand, new MetaBlock(Block.stoneBrick.blockID, 2));
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 3);
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID), 6);
			randomizer.addBlock(new MetaBlock(Block.cobblestone.blockID, 1), 10);
			randomizer.addBlock(new MetaBlock(Block.gravel.blockID, 1), 20);
			return randomizer;
		case 0:
			randomizer = new BlockRandomizer(rand, new MetaBlock(Block.stoneBrick.blockID));
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID, 1), 2);
			randomizer.addBlock(new MetaBlock(Block.stoneBrick.blockID, 2), 3);
			return randomizer;			
		default:
			return new BlockFactoryBrick(rand);
		}
	}
}
