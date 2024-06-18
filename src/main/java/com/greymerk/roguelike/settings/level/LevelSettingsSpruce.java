package com.greymerk.roguelike.settings.level;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.settings.LevelSettingsBase;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.util.WeightedChoice;

public class LevelSettingsSpruce extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsSpruce() {
		super();
		this.theme = Theme.getTheme(Theme.SPRUCE);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 1);
		rooms.addRoomOnce(Room.CROSS);
		rooms.addRoomOnce(Room.BANQUET);
		rooms.addRoomSometimes(Room.BTEAM, 0.1);
		
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 5));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CANDLES, 5));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_BANNER, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FOOD_BARREL, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_DECORATED_POT, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_MUSHROOMS, 1));
		
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.WALL_MUSHROOMS, 5));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_SAFETY, 1));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_FIRE, 5));
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.BOOK_SHELF, 1));
	}
	
	@Override
	public String getName() {
		return LevelSettings.SPRUCE.name();
	}

}
