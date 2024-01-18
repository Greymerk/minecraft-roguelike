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
		rooms.addRandomChoice(Room.CORRIDOR, 1);
		rooms.addRandomChoice(Room.PRISON, 1);
		
		this.walls = new WeightedRandomizer<Fragment>(10);
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 5));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_BANNER, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CANDLES, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SPAWNER, 1));

	}

	@Override
	public String getName() {
		return LevelSettings.DARK_OAK.name();
	}
	
}
