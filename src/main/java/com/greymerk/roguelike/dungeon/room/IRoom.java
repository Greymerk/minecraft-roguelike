package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
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
	
	public List<Cell> getCells();
	
	public NbtCompound getNbt();
	
	public void setFloorPos(Coord floorPos);
	
	public Coord getFloorPos();
	
	public void setWorldPos(Coord worldPos);
	
	public Coord getWorldPos();
	
	public void setTheme(ITheme theme);
	
	public ITheme getTheme();
	
	public IBounded getBoundingBox();
	
	public void addEntrance(Cardinal dir);
	
	public void setDirection(Cardinal dir);
	
	public String getName();
	
	public void applyFilters(IWorldEditor editor);

	

	
	
	
}
