package com.greymerk.roguelike.settings.level;

import java.util.Arrays;
import java.util.List;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.settings.LevelSettingsBase;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.util.WeightedChoice;

import net.minecraft.util.math.random.Random;

public class LevelSettingsRandom extends LevelSettingsBase implements ILevelSettings{

	public LevelSettingsRandom() {
		super();
		Random rand = Random.create();
		this.theme = Theme.getRandom(rand);
		
		Arrays.asList(Room.values()).forEach(room -> {
			this.rooms.addRandomChoice(room, 1);	
		});
		
		List.of(Fragment.WALL_BANNER,
				Fragment.BOOK_SHELF,
				Fragment.WALL_CANDLES,
				Fragment.WALL_DECORATED_POT,
				Fragment.WALL_SPAWNER,
				Fragment.WALL_FLOWER
				).forEach(wall -> {
					this.walls.add(new WeightedChoice<Fragment>(wall, 1));
				});
		
		List.of(Fragment.ALCOVE_CRYPT,
				Fragment.ALCOVE_SAFETY,
				Fragment.ALCOVE_SILVERFISH
				).forEach(alcove -> {
					this.alcoves.add(new WeightedChoice<Fragment>(alcove, 1));
				});
	}
	
	@Override
	public String getName() {
		return LevelSettings.RANDOM.name();
	}

	@Override
	public Difficulty getDifficulty() {
		return Difficulty.HARDEST;
	}
}
