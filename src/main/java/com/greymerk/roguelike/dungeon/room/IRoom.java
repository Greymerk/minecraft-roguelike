package com.greymerk.roguelike.dungeon.room;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.theme.ITheme;

import net.minecraft.nbt.NbtCompound;

public interface IRoom {

	public void generate(IWorldEditor editor);
	
	public void setGenerated(boolean generated);
	
	public boolean isGenerated();
	
	public boolean isDirectional();
	
	public Cardinal getDirection();
	
	public CellManager getCells();
	
	public NbtCompound getNbt();
	
	public void setFloorPos(Coord floorPos);
	
	public Coord getFloorPos();
	
	public void setWorldPos(Coord worldPos);
	
	public Coord getWorldPos();
	
	public void setTheme(ITheme theme);
	
	public ITheme getTheme();
	
	public IBounded getBoundingBox();
	
	public void determineEntrances(Floor f, Coord floorPos);
	
	public void addEntrance(Cardinal dir);
	
	public void setDirection(Cardinal dir);
	
	public String getName();
	
	public void applyFilters(IWorldEditor editor);

	

	
	
	
}
