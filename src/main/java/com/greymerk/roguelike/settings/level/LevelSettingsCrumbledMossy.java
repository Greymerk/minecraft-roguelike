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

public class LevelSettingsCrumbledMossy extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsCrumbledMossy() {
		this.theme = Theme.getTheme(Theme.CRUMBLEDMOSSY);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 10);
		rooms.addRandomChoice(Room.CISTERN, 5);
		rooms.addRandomChoice(Room.CREEPER, 3);
		rooms.addRandomChoice(Room.RESERVOIR, 2);
		rooms.addRandomChoice(Room.CROSS, 2);
		rooms.addRandomChoice(Room.CRYPT, 2);
		rooms.addRandomChoice(Room.OSSUARY, 2);
		
		this.walls = new WeightedRandomizer<Fragment>();
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FLOWER, 1));
	}

	@Override
	public String getName() {
		return LevelSettings.CRUMBLED_MOSSY.name();
	}
}
