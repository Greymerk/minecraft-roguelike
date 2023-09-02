package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.blocks.Deepslate;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeSlate extends ThemeBase implements ITheme {

	public ThemeSlate() {
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(Deepslate.get(Deepslate.COBBLED), 80);
		floor.addBlock(Deepslate.get(Deepslate.GOLD), 2);
		floor.addBlock(Deepslate.get(Deepslate.DIAMOND), 1);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(Deepslate.get(Deepslate.BRICK), 30);
		walls.addBlock(Deepslate.get(Deepslate.CRACKED_BRICK), 5);
		walls.addBlock(Deepslate.get(Deepslate.COBBLED), 1);
		
		MetaStair stair = new MetaStair(StairType.DEEPSLATE_BRICK);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.addBlock(Deepslate.get(Deepslate.POLISHED), 20);
		pillar.addBlock(Deepslate.get(Deepslate.CHISELED), 1);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		this.secondary = primary;
		
	}
	
	@Override
	public String getName() {
		return Theme.SLATE.name();
	}
}
