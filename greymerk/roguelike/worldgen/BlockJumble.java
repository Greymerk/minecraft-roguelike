package greymerk.roguelike.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.World;

public class BlockJumble implements IBlockFactory {

	private List<IBlockFactory> blocks;
	Random rand;
	
	public BlockJumble(Random rand, IBlockFactory defaultBlock){
		this.rand = rand;
		blocks = new ArrayList<IBlockFactory>();
		blocks.add(defaultBlock);
	}
	
	public void addBlock(IBlockFactory toAdd){
		blocks.add(toAdd);
	}
	
	@Override
	public void setBlock(World world, int x, int y, int z) {
		setBlock(world, x, y, z, true, true);
	}

	@Override
	public void setBlock(World world, int x, int y, int z, boolean fillAir, boolean replaceSolid) {
		IBlockFactory block = blocks.get(rand.nextInt(blocks.size()));
		block.setBlock(world, x, y, z, fillAir, replaceSolid);
	}

}
