package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeDefault extends ThemeBase implements ITheme {

	public ThemeDefault() {
		MetaBlock walls = BlockType.get(BlockType.STONE_BRICK);
		MetaStair stair = Stair.get(StairType.STONEBRICK);
		MetaBlock pillar = BlockType.get(BlockType.ANDESITE_POLISHED);
		
		this.primary = new BlockSet(walls, stair, pillar);
		this.secondary = primary;
	}
	
	public String getName() {
		return Theme.DEFAULT.name();
	}
	
}
