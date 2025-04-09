package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlackStone;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

import net.minecraft.block.Blocks;

public class ThemeBlack extends ThemeBase implements ITheme {

	public ThemeBlack() {
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.add(BlackStone.get(BlackStone.BRICK), 20);
		floor.add(BlackStone.get(BlackStone.CRACKED_BRICK), 10);
		floor.add(BlackStone.get(BlackStone.GILDED), 1);
		floor.add(BlackStone.get(BlackStone.POLISHED), 2);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.add(BlackStone.get(BlackStone.BRICK), 10);
		walls.add(BlackStone.get(BlackStone.CRACKED_BRICK), 5);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.add(BlackStone.get(BlackStone.POLISHED), 10);
		pillar.add(BlackStone.get(BlackStone.CHISELED_POLISHED), 1);
		
		MetaStair stair = Stair.of(Stair.BLACKSTONE_BRICK);
		
		this.primary = BlockSet.builder()
				.walls(walls)
				.floor(floor)
				.stair(stair)
				.pillar(pillar)
				.liquid(MetaBlock.of(Blocks.LAVA))
				.slab(Slab.of(Slab.BLACKSTONE_BRICK))
				.build();
		this.secondary = this.primary;
	}
	
	@Override
	public String getName() {
		return Theme.BLACK.name();
	}
	
}
