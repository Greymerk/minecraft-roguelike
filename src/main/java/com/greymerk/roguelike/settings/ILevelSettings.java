package com.greymerk.roguelike.settings;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.dungeon.fragment.IFragment;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public interface ILevelSettings {

	public IFragment getWallFragment(Random rand);
	
	public IFragment getAlcove(Random rand);
	
	public ITheme getTheme();
	
	public RoomProvider getRooms();
	
	public void applyFilters(IWorldEditor editor, Random rand, IBounded box);
	
	public String getName();
	
	public Difficulty getDifficulty();
	
}
