package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Warped;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeWarped extends ThemeBase implements ITheme {

	public ThemeWarped() {
		
		MetaBlock floor = Warped.get(Warped.NYLIUM);
		MetaBlock wall = Warped.get(Warped.PLANK);
		MetaStair stair = new MetaStair(StairType.WARPED);
		MetaBlock pillar = Warped.get(Warped.STEM);
		
		this.primary = new BlockSet(floor, wall, stair, pillar);
		this.primary.setSlab(Slab.get(Slab.WARPED));
		this.secondary = this.primary;
		
	}
	
	@Override
	public String getName() {
		return Theme.WARPED.name();
	}
}
