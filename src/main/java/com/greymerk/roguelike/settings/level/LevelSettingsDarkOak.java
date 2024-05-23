package com.greymerk.roguelike.settings.level;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.settings.LevelSettingsBase;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

public class LevelSettingsDarkOak extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsDarkOak() {
		super();
		this.theme = Theme.getTheme(Theme.DARKOAK);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 10);
		rooms.addRandomChoice(Room.CROSS, 2);
		
		this.walls = new WeightedRandomizer<Fragment>(10);
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 20));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CANDLES, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SPAWNER, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_BANNER, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_DECORATED_POT, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FOOD_BARREL, 1));
		
		this.alcoves.add(walls);
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.BOOK_SHELF, 1));
		
	}

	@Override
	public String getName() {
		return LevelSettings.DARK_OAK.name();
	}
	
}
