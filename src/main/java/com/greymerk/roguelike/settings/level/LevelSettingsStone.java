package com.greymerk.roguelike.settings.level;

import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.settings.LevelSettingsBase;
import com.greymerk.roguelike.util.WeightedChoice;

public class LevelSettingsStone extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsStone() {
		super();
		this.theme = Theme.getTheme(Theme.STONE);
		
		this.rooms = new RoomProvider();
		rooms.addRandomChoice(Room.CORRIDOR, 10);
		rooms.addRandomChoice(Room.PRISON, 5);
		rooms.addRandomChoice(Room.CRYPT, 3);
		rooms.addRoomOnce(Room.CROSS);
		rooms.addRoomOnce(Room.OSSUARY);
		
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 40));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SPAWNER, 3));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 3));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_BOOK_SHELF, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_FOOD_BARREL, 1));
		
		this.alcoves.add(this.walls);
		this.alcoves.add(new WeightedChoice<Fragment>(Fragment.ALCOVE_CRYPT, 1));
	}

	@Override
	public String getName() {
		return LevelSettings.STONE.name();
	}
	
}
