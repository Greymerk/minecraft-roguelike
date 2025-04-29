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
import com.greymerk.roguelike.util.WeightedRandomizer;

public class LevelSettingsSpruce extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsSpruce() {
		super();
		this.theme = Theme.getTheme(Theme.SPRUCE);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 1);
		rooms.addRoomOnce(Room.CROSS);
		rooms.addRoomSometimes(Room.PIT, 0.2);
		rooms.addRoomSometimes(Room.SMITH, 0.3);
		rooms.addRoomSometimes(Room.BTEAM, 0.1);
		rooms.setStairway(Room.IMPERIAL_STAIRWAY);
		
		this.walls = new WeightedRandomizer<Fragment>(5);
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 10));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CANDLES, 5));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_BANNER, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FOOD_BARREL, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_DECORATED_POT, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_MUSHROOMS, 1));
		
		this.alcoves.add(this.walls);
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_SAFETY, 1));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_FIRE, 5));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.BOOK_SHELF, 1));
		
		this.filters = List.of(Filter.get(Filter.LEAFLITTER));
	}
	
	@Override
	public String getName() {
		return LevelSettings.SPRUCE.name();
	}

	@Override
	public Difficulty getDifficulty() {
		return Difficulty.EASY;
	}
	
}
