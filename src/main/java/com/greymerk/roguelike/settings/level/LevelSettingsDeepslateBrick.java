package com.greymerk.roguelike.settings.level;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.settings.LevelSettingsBase;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

public class LevelSettingsDeepslateBrick extends LevelSettingsBase implements ILevelSettings {

	public LevelSettingsDeepslateBrick() {
		super();
		this.theme = Theme.getTheme(Theme.SLATE);
		
		this.rooms = new RoomProvider();
		rooms.addRoomOnce(Room.ABYSS);
		rooms.addRandomChoice(Room.CORRIDOR, 10);
		rooms.addRandomChoice(Room.CROSS, 2);
		rooms.addRandomChoice(Room.CRYPT, 2);
		rooms.addRandomChoice(Room.OSSUARY, 2);
		
		this.walls = new WeightedRandomizer<Fragment>();
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_EMPTY, 10));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_SPAWNER, 1));
		this.walls.add(new WeightedChoice<Fragment>(Fragment.WALL_CHEST, 1));
	}

	@Override
	public String getName() {
		return LevelSettings.SLATE.name();
	}

	@Override
	public Difficulty getDifficulty() {
		return Difficulty.HARDEST;
	}
}
