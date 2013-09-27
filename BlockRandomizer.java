package greymerk.roguelike;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import net.minecraft.src.World;

public class BlockRandomizer implements IBlockFactory{

	Random rand;
	LinkedList<BlockChoice> blocks;

	public BlockRandomizer(Random rand, MetaBlock defaultBlock){
		this.rand = rand;
		this.blocks = new LinkedList<BlockChoice>();
		blocks.addFirst(new BlockChoice(defaultBlock, 1));
	}
	
	public void addBlock(MetaBlock block, int chance){
		blocks.addFirst(new BlockChoice(block, chance));
	}
	
	public void setBlock(World world, int x, int y, int z){
		setBlock(world, x, y, z, true, true);
	}
	
	public void setBlock(World world, int x, int y, int z, boolean fillAir, boolean replaceSolid){
		
		MetaBlock choice = getMetaBlock();
		int blockID = choice.getBlockID();
		int meta = choice.getMeta();
		int flags = choice.getFlag();
		
		WorldGenPrimitive.setBlock(world, x, y, z, blockID, meta, flags, fillAir, replaceSolid);
	}
	
	public MetaBlock getMetaBlock(){
		
		for(BlockChoice block : blocks){
			if(rand.nextInt(block.getChance()) == 0){
				return block.getBlock();
			}
		}
		
		// Shouldn't happen
		return new MetaBlock(0);
	}
	
	private class BlockChoice{
		
		private MetaBlock block;
		private int chance;
		
		public BlockChoice(MetaBlock block, int chance){
			this.block = block;
			this.chance = chance;
		}
		
		public MetaBlock getBlock(){
			return block;
		}
		
		public int getChance(){
			return chance;
		}
		
	}
}
