package com.greymerk.roguelike.settings.level;

import java.util.ArrayList;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.editor.filter.Filter;
import com.greymerk.roguelike.editor.filter.IFilter;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.settings.LevelSettingsBase;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

public class LevelSettingsMossy extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsMossy() {
		super();
		this.theme = Theme.getTheme(Theme.MOSSY);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 10);
		rooms.addRandomChoice(Room.CISTERN, 3);
		rooms.addRandomChoice(Room.CRYPT, 2);
		rooms.addRoomOnce(Room.OSSUARY);
		rooms.addRoomOnce(Room.CROSS);
		rooms.addRoomOnce(Room.RESERVOIR);
		rooms.addRoomOnce(Room.CREEPER);
		
		this.walls = new WeightedRandomizer<Fragment>(10);
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 10));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SPAWNER, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_BOOK_SHELF, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FOOD_BARREL, 1));
		
		this.alcoves.add(walls);
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_SILVERFISH, 2));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_CRYPT, 1));

		this.filters = new ArrayList<IFilter>();
		this.filters.add(Filter.get(Filter.VINE));
	}

	@Override
	public String getName() {
		return LevelSettings.MOSSY.name();
	}
}
