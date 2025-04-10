package com.greymerk.roguelike.filter;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.SculkVein;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.block.Blocks;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.random.Random;

public class SculkFilter implements IFilter {
	
	@Override
	public void apply(IWorldEditor editor, Random rand, ILevelSettings settings, IBounded box) {
		box.forEach(pos -> {
			if(rand.nextInt(8) != 0) return;
			if(!editor.isAir(pos)) return;
			generate(editor, rand, pos.copy().add(Cardinal.DOWN).freeze(), 5);
		});
	}

	private void generate(IWorldEditor editor, Random rand, Coord origin, int counter) {
		if (counter <= 0) return;
		if(!editor.isAir(origin.copy().add(Cardinal.UP))) return;
		MetaBlock block = editor.getBlock(origin);
		if(!block.isIn(BlockTags.SCULK_REPLACEABLE_WORLD_GEN)) return;
		MetaBlock.of(Blocks.SCULK).set(editor, origin);
		MetaBlock shrieker = MetaBlock.of(Blocks.SCULK_SHRIEKER);
		shrieker.with(SculkShriekerBlock.CAN_SUMMON, true);
		
		BlockWeightedRandom blocks = new BlockWeightedRandom()
				.add(MetaBlock.of(Blocks.SCULK_SENSOR), 20)
				.add(MetaBlock.of(Blocks.SCULK_CATALYST), 10)
				.add(shrieker, 1);
		
		if(rand.nextInt(20) == 0) {
			blocks.set(editor, rand, origin.copy().add(Cardinal.UP));	
		} else {
			SculkVein.set(editor, origin.copy().add(Cardinal.UP));
		}
		
		Cardinal.directions.forEach(dir -> {
			if(rand.nextBoolean()) generate(editor, rand, origin.copy().add(dir).freeze(), counter - 1);
		});
	}

}
