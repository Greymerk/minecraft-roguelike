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

public class LevelSettingsOak extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsOak() {
		super();
		this.theme = Theme.getTheme(Theme.OAK);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 1);
		rooms.addRoomOnce(Room.KITCHEN);
		rooms.addRoomOnce(Room.MUSIC);
		rooms.addRoomAfter(Room.BEDROOM);
		
		this.walls = new WeightedRandomizer<Fragment>();
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FLOWER, 5));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_PLANT, 3));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CANDLES, 2));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_BANNER, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FOOD_BARREL, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_DECORATED_POT, 1));
		
	}

	@Override
	public String getName() {
		return LevelSettings.OAK.name();
	}

}
