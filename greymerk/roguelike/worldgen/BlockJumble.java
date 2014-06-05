package greymerk.roguelike.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.World;

public class BlockJumble implements IBlockFactory {

	private List<IBlockFactory> blocks;
	
	public BlockJumble(IBlockFactory defaultBlock){
		blocks = new ArrayList<IBlockFactory>();
		blocks.add(defaultBlock);
	}
	
	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}
	
	@Override
	public void setBlock(World world, Random rand, int x, int y, int z) {
		setBlock(world, rand, x, y, z, true, true);
	}

	@Override
	public void setBlock(World world, Random rand, int x, int y, int z, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = blocks.get(rand.nextInt(blocks.size()));
		block.setBlock(world, rand, x, y, z, fillAir, replaceSolid);
	}

}
