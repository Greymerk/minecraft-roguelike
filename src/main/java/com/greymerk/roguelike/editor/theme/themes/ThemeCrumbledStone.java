package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.SilverfishBlock;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeCrumbledStone extends ThemeBase implements ITheme {

	public ThemeCrumbledStone() {
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 20);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 10);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 10);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 10);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 3);
		walls.addBlock(SilverfishBlock.getJumble(), 2);
		
		MetaStair stair = Stair.get(StairType.STONEBRICK);
		MetaBlock pillar = BlockType.get(BlockType.ANDESITE_POLISHED);
		
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = primary;
	}
	
	@Override
	public String getName() {
		return Theme.CRUMBLEDSTONE.name();
	}
}
