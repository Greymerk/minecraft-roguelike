package com.greymerk.roguelike.settings;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public abstract class LevelSettingsBase implements ILevelSettings {

	protected ITheme theme;
	protected WeightedRandomizer<Fragment> walls;
	protected WeightedRandomizer<Fragment> alcoves;
	protected RoomProvider rooms;
	
	public LevelSettingsBase() {
		this.walls = new WeightedRandomizer<Fragment>();
		this.alcoves = new WeightedRandomizer<Fragment>();
		this.rooms = new RoomProvider();
	}
	
	@Override
	public IFragment getWallFragment(Random rand) {
		if(this.walls.isEmpty()) return Fragment.fromType(Fragment.WALL_EMPTY);
		Fragment type = this.walls.get(rand);
		return Fragment.fromType(type);
	}
	
	@Override
	public IFragment getAlcove(Random rand) {
		if(this.alcoves.isEmpty()) return Fragment.fromType(Fragment.WALL_EMPTY);
		Fragment type = this.alcoves.get(rand);
		return Fragment.fromType(type);
	}
	
	@Override
	public ITheme getTheme() {
		if(this.theme == null) return Theme.getTheme(Theme.STONE);
		return this.theme;
	}
	
	@Override
	public RoomProvider getRooms() {
		return this.rooms;
	}
	
}
