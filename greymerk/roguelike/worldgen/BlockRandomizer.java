package greymerk.roguelike.worldgen;

import greymerk.roguelike.catacomb.dungeon.DungeonWeightedChoice;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import net.minecraft.src.World;

public class BlockRandomizer implements IBlockFactory{

	Random rand;
	LinkedList<BlockChoice> blocks;

	public BlockRandomizer(Random rand, IBlockFactory defaultBlock){
		this.rand = rand;
		this.blocks = new LinkedList<BlockChoice>();
		blocks.add(new BlockChoice(defaultBlock, 1));
	}
	
	public void addBlock(MetaBlock block, int chance){
		blocks.add(new BlockChoice(block, chance));
		Collections.sort(blocks);
		Collections.reverse(blocks);
	}
	
	public void setBlock(World world, int x, int y, int z){
		setBlock(world, x, y, z, true, true);
	}
	
	public void setBlock(World world, int x, int y, int z, boolean fillAir, boolean replaceSolid){
		IBlockFactory choice = getBlock();
		choice.setBlock(world, x, y, z, fillAir, replaceSolid);
	}
	
	private IBlockFactory getBlock(){
		
		for(BlockChoice block : blocks){
			if(rand.nextInt(block.getChance()) == 0){
				return block.getBlock();
			}
		}
		
		// Shouldn't happen
		return new MetaBlock(0);
	}
	
	private class BlockChoice implements Comparable{
		
		private IBlockFactory block;
		private int chance;
		
		public BlockChoice(IBlockFactory block, int chance){
			this.block = block;
			this.chance = chance;
		}
		
		public IBlockFactory getBlock(){
			return block;
		}
		
		public int getChance(){
			return chance;
		}
		
		@Override
		public int compareTo(Object o) {
			BlockChoice other = (BlockChoice)o;
			if(chance < other.chance) return -1;
			if(chance > other.chance) return 1;
			return 0;
		}
	}
}
