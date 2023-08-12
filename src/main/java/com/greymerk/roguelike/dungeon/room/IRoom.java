package com.greymerk.roguelike.dungeon.room;

import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
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
	
	public Coord getFloorPos();
	
	public Coord getWorldPos();
	
	public ITheme getTheme();
	
	public IBounded getBoundingBox();
	
	public String getName();
	
	
}
