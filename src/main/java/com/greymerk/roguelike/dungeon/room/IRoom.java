package com.greymerk.roguelike.dungeon.room;

import java.util.List;
import java.util.Optional;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.nbt.NbtCompound;

public interface IRoom {

	public void generate(IWorldEditor editor);
	
	public void setGenerated(boolean generated);
	
	public boolean isGenerated();
	
	public Cardinal getDirection();
	
	public CellManager getCells(Cardinal dir);
	
	public NbtCompound getNbt();
	
	public void setFloorPos(Coord floorPos);
	
	public Coord getFloorPos();
	
	public void setWorldPos(Coord worldPos);
	
	public Coord getWorldPos();
	
	public void setLevelSettings(ILevelSettings settings);
	
	public ITheme getTheme();
	
	public Optional<IBounded> getBoundingBox();
	
	public IBounded getBoundingBox(Coord origin, Cardinal dir);
	
	public void determineEntrances(Floor f, Coord floorPos);
	
	public void addEntrance(Cardinal dir, Entrance type);
	
	public Entrance getEntrance(Cardinal dir);
	
	public List<Cardinal> getEntrancesFromType(Entrance type);
	
	public void setDirection(Cardinal dir);
	
	public String getName();
	
	public void applyFilters(IWorldEditor editor);

	

	
	
	
}
