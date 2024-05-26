package com.greymerk.roguelike.settings;

import java.util.List;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.filter.IFilter;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.util.math.random.Random;

public abstract class LevelSettingsBase implements ILevelSettings {

	protected ITheme theme;
	protected WeightedRandomizer<Fragment> walls;
	protected WeightedRandomizer<Fragment> alcoves;
	protected RoomProvider rooms;
	protected List<IFilter> filters;
	
	public LevelSettingsBase() {
		this.walls = new WeightedRandomizer<Fragment>(10);
		this.alcoves = new WeightedRandomizer<Fragment>(10);
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
		if(this.alcoves.isEmpty()) return getWallFragment(rand);
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
	
	@Override
	public void applyFilters(IWorldEditor editor, Random rand, IBounded box) {
		if(this.filters == null) return;
		for(IFilter filter : this.filters) {
			filter.apply(editor, rand, this.getTheme(), box);
		}
	}
	
}
