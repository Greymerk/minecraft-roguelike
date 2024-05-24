package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.Deepslate;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

public class ThemeTiledSlate extends ThemeBase implements ITheme {

	public ThemeTiledSlate() {
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(Deepslate.get(Deepslate.COBBLED), 50);
		floor.addBlock(Deepslate.get(Deepslate.GOLD), 3);
		floor.addBlock(Deepslate.get(Deepslate.DIAMOND), 1);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(Deepslate.get(Deepslate.TILE), 30);
		walls.addBlock(Deepslate.get(Deepslate.CRACKED_TILE), 5);
		walls.addBlock(Deepslate.get(Deepslate.COBBLED), 1);
		
		
		IStair stair = Stair.of(Stair.TILED_DEEPSLATE);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.addBlock(Deepslate.get(Deepslate.TILE), 20);
		pillar.addBlock(Deepslate.get(Deepslate.CRACKED_TILE), 4);
		pillar.addBlock(Deepslate.get(Deepslate.CHISELED), 1);
		
		this.primary = new BlockSet()
				.setWall(walls)
				.setFloor(floor)
				.setStair(stair)
				.setPillar(pillar)
				.setSlab(Slab.get(Slab.TILED_SLATE))
				.setLiquid(BlockType.get(BlockType.LAVA_FLOWING));
		this.secondary = primary;
	}
	
	@Override
	public String getName() {
		return Theme.TILEDSLATE.name();
	}
	
}
