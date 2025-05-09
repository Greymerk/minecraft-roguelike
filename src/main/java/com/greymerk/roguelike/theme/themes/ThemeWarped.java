package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.blocks.Warped;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

public class ThemeWarped extends ThemeBase implements ITheme {

	public ThemeWarped() {
		this.primary = BlockSet.builder()
				.walls(Warped.get(Warped.PLANK))
				.floor(Warped.get(Warped.NYLIUM))
				.stair(Stair.of(Stair.WARPED))
				.pillar(Warped.get(Warped.STEM))
				.door(Door.of(DoorType.WARPED))
				.slab(Slab.get(Slab.WARPED))
				.build();
		this.secondary = primary;
	}
	
	@Override
	public String getName() {
		return Theme.WARPED.name();
	}
}
