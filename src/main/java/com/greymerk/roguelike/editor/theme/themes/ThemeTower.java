package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;
import com.greymerk.roguelike.editor.factories.BlockJumble;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeTower extends ThemeBase implements ITheme {

	public ThemeTower(){
	
		BlockJumble stone = new BlockJumble();
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK));
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED));
		stone.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.AIR), 5);
		walls.addBlock(stone, 30);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 10);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 5);
		
		MetaStair stair = new MetaStair(StairType.STONEBRICK);
		
		IBlockFactory pillar = BlockType.get(BlockType.ANDESITE_POLISHED);
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = this.primary;
	}
	
	public String getName() {
		return Theme.TOWER.name();
	}
}
