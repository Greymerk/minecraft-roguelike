package com.greymerk.roguelike.dungeon.room;

import java.util.List;
import java.util.Optional;

import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.layout.Exit;
import com.greymerk.roguelike.dungeon.layout.ExitType;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public interface IRoom {

	public void generate(IWorldEditor editor);
	
	public void generateExits(IWorldEditor editor, Random rand);
	
	public void setGenerated(boolean generated);
	
	public boolean isGenerated();
	
	public Cardinal getDirection();
	
	public CellManager getCells(Cardinal dir);
	
	public void setFloorPos(Coord floorPos);
	
	public Coord getFloorPos();
	
	public void setWorldPos(Coord worldPos);
	
	public Coord getWorldPos();
	
	public void setLevelSettings(ILevelSettings settings);
	
	public ILevelSettings getLevelSettings();
	
	public ITheme getTheme();
	
	public Optional<IBounded> getBoundingBox();
	
	public IBounded getBoundingBox(Coord origin, Cardinal dir);
	
	public void addExit(Exit exit);
	
	public void setDirection(Cardinal dir);
	
	public String getName();
	
	public void applyFilters(IWorldEditor editor);

	public List<Exit> getExits();

	public ExitType getExitType(Cardinal dir);
	
}
