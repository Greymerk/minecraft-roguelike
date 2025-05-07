package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.blocks.Deepslate;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

public class ThemeSlate extends ThemeBase implements ITheme {

	public ThemeSlate() {
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.add(Deepslate.get(Deepslate.COBBLED), 80);
		floor.add(Deepslate.get(Deepslate.GOLD), 2);
		floor.add(Deepslate.get(Deepslate.DIAMOND), 1);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.add(Deepslate.get(Deepslate.BRICK), 30);
		walls.add(Deepslate.get(Deepslate.CRACKED_BRICK), 5);
		walls.add(Deepslate.get(Deepslate.COBBLED), 1);
		
		MetaStair stair = Stair.of(Stair.DEEPSLATE_BRICK);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.add(Deepslate.get(Deepslate.POLISHED), 20);
		pillar.add(Deepslate.get(Deepslate.CHISELED), 1);
		
		this.primary = BlockSet.builder()
				.walls(walls)
				.floor(floor)
				.stair(stair)
				.pillar(pillar)
				.slab(Slab.of(Slab.SLATE_BRICK))
				.build();
		this.secondary = this.primary;
		
	}
	
	@Override
	public String getName() {
		return Theme.SLATE.name();
	}
}
