package com.greymerk.roguelike.settings.level;

import java.util.List;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.filter.Filter;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.settings.LevelSettingsBase;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.util.WeightedChoice;

public class LevelSettingsStone extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsStone() {
		super();
		this.theme = Theme.getTheme(Theme.STONE);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 10);
		rooms.addRandomChoice(Room.PRISON, 2);
		rooms.addRandomChoice(Room.CRYPT, 3);
		rooms.addRoomOnce(Room.CROSS);
		rooms.addRoomOnce(Room.BALCONY);
		rooms.addRoomSometimes(Room.PIT, 0.2);
		rooms.addRoomSometimes(Room.BALCONY, 0.5);
		
		
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 40));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SPAWNER, 3));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 3));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FOOD_BARREL, 1));
		
		//this.alcoves.add(walls);
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_PRISON_CELL, 10));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_CRYPT, 3));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.BOOK_SHELF, 2));
		
		this.filters = List.of(Filter.get(Filter.CHAINS));
	}

	@Override
	public String getName() {
		return LevelSettings.STONE.name();
	}
	

	@Override
	public Difficulty getDifficulty() {
		return Difficulty.MEDIUM;
	}
}
