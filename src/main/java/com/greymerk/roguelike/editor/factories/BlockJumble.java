package com.greymerk.roguelike.editor.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.util.math.random.Random;

public class BlockJumble extends BlockBase {

	private List<IBlockFactory> blocks;
	
	public static BlockJumble of(List<IBlockFactory> blockList) {
		return new BlockJumble().addAll(blockList);
	}
	
	public static BlockJumble ofBlocks(List<Block> blockList) {
		BlockJumble jumble = new BlockJumble();
		blockList.forEach(b -> {
			jumble.add(MetaBlock.of(b));
		});
		return jumble;
	}
	
	public BlockJumble(){
		blocks = new ArrayList<IBlockFactory>();
	}

	public BlockJumble addAll(List<IBlockFactory> toAdd) {
		toAdd.forEach(b -> {
			this.blocks.add(b);
		});
		return this;
	}
	
	public BlockJumble add(IBlockFactory toAdd){
		blocks.add(toAdd);
		return this;
	}

	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord origin, Predicate<BlockContext> p) {
		IBlockFactory block = blocks.get(rand.nextInt(blocks.size()));
		return block.set(editor, rand, origin, p);
	}

}
