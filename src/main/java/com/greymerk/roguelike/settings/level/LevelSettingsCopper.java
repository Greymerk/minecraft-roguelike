package com.greymerk.roguelike.settings.level;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.settings.LevelSettingsBase;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

public class LevelSettingsCopper extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsCopper() {
		super();
		this.theme = Theme.getTheme(Theme.COPPER);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 10);
		rooms.addRoomOnce(Room.CROSS);
		rooms.addRoomOnce(Room.PRISON);
		
		this.walls = new WeightedRandomizer<Fragment>(10);
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 12));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SPAWNER, 3));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FOOD_BARREL, 1));

		this.alcoves.add(walls);
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.BOOK_SHELF, 1));
	}

	@Override
	public String getName() {
		return LevelSettings.COPPER.name();
	}

}
