package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.blocks.Warped;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeWarped extends ThemeBase implements ITheme {

	public ThemeWarped() {
		this.primary = new BlockSet()
				.setWall(Warped.get(Warped.PLANK))
				.setFloor(Warped.get(Warped.NYLIUM))
				.setStair(Stair.of(Stair.WARPED))
				.setPillar(Warped.get(Warped.STEM))
				.setDoor(Door.of(DoorType.WARPED))
				.setSlab(Slab.get(Slab.WARPED));
		this.secondary = primary;
	}
	
	@Override
	public String getName() {
		return Theme.WARPED.name();
	}
}
