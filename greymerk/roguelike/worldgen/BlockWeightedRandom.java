package greymerk.roguelike.worldgen;

import greymerk.roguelike.catacomb.dungeon.DungeonWeightedChoice;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import net.minecraft.src.World;

public class BlockWeightedRandom implements IBlockFactory{

	LinkedList<BlockChoice> blocks;
	
	public BlockWeightedRandom(IBlockFactory defaultBlock){
		this.blocks = new LinkedList<BlockChoice>();
		blocks.add(new BlockChoice(defaultBlock, 1));
	}
	
	public void addBlock(IBlockFactory block, int chance){
		blocks.add(new BlockChoice(block, chance));
		Collections.sort(blocks);
		Collections.reverse(blocks);
	}
	
	@Override
	public void setBlock(World world, Random rand, int x, int y, int z){
		setBlock(world, rand, x, y, z, true, true);
	}
	
	@Override
	public void setBlock(World world, Random rand, int x, int y, int z, boolean fillAir, boolean replaceSolid){
		IBlockFactory choice = getBlock(rand);
		choice.setBlock(world, rand, x, y, z, fillAir, replaceSolid);
	}
	
	private IBlockFactory getBlock(Random rand){
		
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
