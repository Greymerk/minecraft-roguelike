package com.greymerk.roguelike.settings.level;

import java.util.ArrayList;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.filter.Filter;
import com.greymerk.roguelike.filter.IFilter;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.settings.LevelSettingsBase;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

public class LevelSettingsTiledDeepslate extends LevelSettingsBase implements ILevelSettings {
	public LevelSettingsTiledDeepslate() {
		super();
		this.theme = Theme.getTheme(Theme.TILEDSLATE);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 5);
		rooms.addRandomChoice(Room.CROSS, 2);
		rooms.addRandomChoice(Room.CRYPT, 3);
		rooms.addRandomChoice(Room.CISTERN, 2);
		rooms.addRoomOnce(Room.OSSUARY);
		
		this.walls = new WeightedRandomizer<Fragment>(10);
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 10));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SPAWNER, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 1));
		
		this.alcoves.add(this.walls);
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_CRYPT, 5));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.BOOK_SHELF, 2));
		
		this.filters = new ArrayList<IFilter>();
		this.filters.add(Filter.get(Filter.POTS));
	}

	@Override
	public String getName() {
		return LevelSettings.TILED_SLATE.name();
	}
}
