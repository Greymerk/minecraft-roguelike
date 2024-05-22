package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeDefault extends ThemeBase implements ITheme {

	public ThemeDefault() {
		
		this.primary = new BlockSet()
				.setWall(BlockType.get(BlockType.STONE_BRICK))
				.setStair(Stair.of(Stair.STONEBRICK))
				.setPillar(BlockType.get(BlockType.ANDESITE_POLISHED));
		this.secondary = this.primary;
	}
	
	public String getName() {
		return Theme.DEFAULT.name();
	}
	
}
