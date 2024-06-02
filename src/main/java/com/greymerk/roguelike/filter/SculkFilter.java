package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.SculkVein;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.random.Random;

public class SculkFilter implements IFilter {

	@Override
	public void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box) {
		box.getBoundingBox().forEach(pos -> {
			if(rand.nextInt(8) != 0) return;
			if(!editor.isAir(pos)) return;
			generate(editor, rand, pos.copy().add(Cardinal.DOWN).freeze(), 5);
		});
	}

	private void generate(IWorldEditor editor, Random rand, Coord origin, int counter) {
		if (counter <= 0) return;
		if(!editor.isAir(origin.copy().add(Cardinal.UP))) return;
		MetaBlock block = editor.getBlock(origin);
		if(!block.getState().isIn(BlockTags.SCULK_REPLACEABLE_WORLD_GEN)) return;
		MetaBlock.of(Blocks.SCULK).set(editor, origin);
		WeightedRandomizer<Block> sculks = new WeightedRandomizer<Block>()
			.add(new WeightedChoice<Block>(Blocks.SCULK_CATALYST, 1))
			.add(new WeightedChoice<Block>(Blocks.SCULK_SENSOR, 3))
			.add(new WeightedChoice<Block>(Blocks.SCULK_SHRIEKER, 2));
		if(rand.nextInt(20) == 0) {
			MetaBlock.of(sculks.get(rand)).set(editor, origin.copy().add(Cardinal.UP));	
		} else {
			SculkVein.set(editor, origin.copy().add(Cardinal.UP));
		}
		
		Cardinal.directions.forEach(dir -> {
			if(rand.nextBoolean()) generate(editor, rand, origin.copy().add(dir).freeze(), counter - 1);
		});
	}

}
