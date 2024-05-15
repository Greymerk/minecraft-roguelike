package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.blocks.BlackStone;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeBlack extends ThemeBase implements ITheme {

	public ThemeBlack() {
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(BlackStone.get(BlackStone.BRICK), 20);
		floor.addBlock(BlackStone.get(BlackStone.CRACKED_BRICK), 10);
		floor.addBlock(BlackStone.get(BlackStone.GILDED), 1);
		floor.addBlock(BlackStone.get(BlackStone.POLISHED), 2);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlackStone.get(BlackStone.BRICK), 10);
		walls.addBlock(BlackStone.get(BlackStone.CRACKED_BRICK), 5);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.addBlock(BlackStone.get(BlackStone.POLISHED), 10);
		pillar.addBlock(BlackStone.get(BlackStone.CHISELED_POLISHED), 1);
		
		MetaStair stair = new MetaStair(StairType.BLACKSTONE_BRICK);
		
		this.primary = new BlockSet()
				.setWall(walls)
				.setFloor(floor)
				.setStair(stair)
				.setPillar(pillar)
				.setLiquid(BlockType.get(BlockType.LAVA_FLOWING))
				.setSlab(Slab.of(Slab.BLACKSTONE_BRICK));
		this.secondary = this.primary;
	}
	
	@Override
	public String getName() {
		return Theme.BLACK.name();
	}
	
}
