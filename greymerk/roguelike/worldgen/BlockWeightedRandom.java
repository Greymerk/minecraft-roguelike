package greymerk.roguelike.worldgen;

import java.util.Random;

import net.minecraft.src.World;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class BlockWeightedRandom implements IBlockFactory{

	private WeightedRandomizer<IBlockFactory> blocks;
	
	public BlockWeightedRandom(){
		blocks = new WeightedRandomizer<IBlockFactory>();
	}
	
	public void addBlock(IBlockFactory toAdd, int weight){
		blocks.add(new WeightedChoice<IBlockFactory>(toAdd, weight));
	}
	
	public IBlockFactory get(Random rand){
		return blocks.get(rand);
	}

	@Override
	public void setBlock(World world, Random rand, int x, int y, int z) {
		setBlock(world, rand, x, y, z, true, true);
	}

	@Override
	public void setBlock(World world, Random rand, int x, int y, int z, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = blocks.get(rand);
		block.setBlock(world, rand, x, y, z, fillAir, replaceSolid);
	}
	
	
}
