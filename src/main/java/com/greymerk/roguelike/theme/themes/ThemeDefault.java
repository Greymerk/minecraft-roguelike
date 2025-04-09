package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

import net.minecraft.block.Blocks;

public class ThemeDefault extends ThemeBase implements ITheme {

	public ThemeDefault() {
		
		this.primary = BlockSet.builder()
				.walls(MetaBlock.of(Blocks.STONE_BRICKS))
				.stair(Stair.of(Stair.STONEBRICK))
				.pillar(MetaBlock.of(Blocks.POLISHED_ANDESITE))
				.build();
		this.secondary = this.primary;
	}
	
	public String getName() {
		return Theme.DEFAULT.name();
	}
	
}
