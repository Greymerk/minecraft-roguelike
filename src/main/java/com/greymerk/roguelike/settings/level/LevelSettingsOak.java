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

public class LevelSettingsOak extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsOak() {
		super();
		this.theme = Theme.getTheme(Theme.OAK);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 1);
		rooms.addRoomOnce(Room.KITCHEN);
		rooms.addRoomOnce(Room.BEDROOM);
		
		this.walls = new WeightedRandomizer<Fragment>();
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FLOWER, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_BANNER, 1));
		
	}

	@Override
	public String getName() {
		return LevelSettings.OAK.name();
	}

}
