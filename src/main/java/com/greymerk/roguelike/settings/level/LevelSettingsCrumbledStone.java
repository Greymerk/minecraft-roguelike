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

public class LevelSettingsCrumbledStone extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsCrumbledStone() {
		super();
		this.theme = Theme.getTheme(Theme.CRUMBLEDSTONE);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 10);
		rooms.addRandomChoice(Room.CRYPT, 5);
		rooms.addRandomChoice(Room.PRISON, 3);
		rooms.addRoomOnce(Room.PANOPTICON);
		rooms.addRoomOnce(Room.ENDER);
		
		this.walls = new WeightedRandomizer<Fragment>(10);
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 15));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SPAWNER, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FOOD_BARREL, 1));
		

		//this.alcoves.add(this.walls);
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_PRISON_CELL, 10));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_CRYPT, 5));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.BOOK_SHELF, 3));
		
		this.filters = new ArrayList<IFilter>();
		this.filters.add(Filter.get(Filter.COBWEB));
	}

	@Override
	public String getName() {
		return LevelSettings.CRUMBLED_STONE.name();
	}

	@Override
	public Difficulty getDifficulty() {
		return Difficulty.MEDIUM;
	}
}
