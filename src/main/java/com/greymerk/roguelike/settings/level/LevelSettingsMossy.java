package com.greymerk.roguelike.settings.level;

import java.util.ArrayList;

import com.greymerk.roguelike.dungeon.Difficulty;
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

public class LevelSettingsMossy extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsMossy() {
		super();
		this.theme = Theme.getTheme(Theme.MOSSY);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 10);
		rooms.addRandomChoice(Room.CISTERN, 3);
		rooms.addRandomChoice(Room.CRYPT, 3);
		rooms.addRoomSometimes(Room.CREEPER, 0.1);
		rooms.addRoomSometimes(Room.OSSUARY, 0.2);
		rooms.addRoomSometimes(Room.ABYSS, 0.3);
		rooms.addRoomOnce(Room.CROSS);
		
		this.walls = new WeightedRandomizer<Fragment>(10);
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 12));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SPAWNER, 3));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FOOD_BARREL, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SKULLS, 1));
		
		this.alcoves.add(walls);
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_SILVERFISH, 1));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_CRYPT, 2));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.BOOK_SHELF, 1));

		this.filters = new ArrayList<IFilter>();
		this.filters.add(Filter.get(Filter.VINE));
	}

	@Override
	public String getName() {
		return LevelSettings.MOSSY.name();
	}

	@Override
	public Difficulty getDifficulty() {
		return Difficulty.HARD;
	}
}
