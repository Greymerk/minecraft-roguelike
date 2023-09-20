package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.settings.ILevelSettings;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.math.random.Random;

public abstract class AbstractRoom implements IRoom{
	
	protected Coord floorPos;
	protected Coord worldPos;
	protected ILevelSettings settings;
	protected ITheme theme;
	protected Cardinal direction;
	protected boolean generated;
	protected List<Cardinal> entrances;
	
	public AbstractRoom() {
		this.direction = Cardinal.EAST;
		this.generated = false;
		this.entrances = new ArrayList<Cardinal>();
	}
	
	public AbstractRoom(ILevelSettings settings, IBounded box, Coord worldPos) {
		this(settings, box, worldPos, Cardinal.DOWN);
	}
	
	public AbstractRoom(ILevelSettings settings, IBounded box, Coord worldPos, Cardinal dir) {
		this.settings = settings;
		this.theme = settings.getTheme();
		this.worldPos = worldPos;
		this.generated = false;
		this.direction = dir;
		this.entrances = new ArrayList<Cardinal>();
	}
	
	@Override
	public NbtCompound getNbt() {
		ITheme theme = this.getTheme();
		Coord pos = this.getWorldPos();
		
		NbtCompound nbt = new NbtCompound();
		nbt.put("type", NbtString.of(getName()));
		nbt.put("settings", NbtString.of(settings.getName()));
		nbt.put("theme", NbtString.of(theme.getName()));
		nbt.put("pos", pos.getNbt());
		nbt.putBoolean("generated", this.generated);
		nbt.putInt("dir", Arrays.asList(Cardinal.values()).indexOf(this.direction));
		int[] ent;
		List<Integer> c = new ArrayList<Integer>();
		for(Cardinal dir : this.entrances) {
			c.add(Cardinal.directions.indexOf(dir));
		}
		ent = c.stream().mapToInt(Integer::intValue).toArray();
		nbt.putIntArray("entrances", ent);
		return nbt;
	}
	
	@Override
	public void setFloorPos(Coord floorPos) {
		this.floorPos = floorPos;
	}
	
	@Override
	public Coord getFloorPos() {
		return new Coord(this.floorPos);
	}
	
	@Override
	public void setWorldPos(Coord worldPos) {
		this.worldPos = worldPos;
	}
	
	@Override
	public Coord getWorldPos() {
		return worldPos.copy();
	}

	public void setLevelSettings(ILevelSettings settings) {
		this.settings = settings;
		this.theme = settings.getTheme();
	}
	
	@Override
	public ITheme getTheme() {
		return this.theme;
	}
	
	@Override
	public IBounded getBoundingBox() {
		BoundingBox bb = new BoundingBox(worldPos.copy());
		bb.grow(Cardinal.directions, 4);
		bb.grow(Cardinal.UP, 6).grow(Cardinal.DOWN, 3);
		return bb;
	}
	
	@Override
	public void setGenerated(boolean generated) {
		this.generated = generated;
	}
	
	@Override
	public boolean isGenerated() {
		return this.generated;
	}
	
	@Override
	public boolean isDirectional() {
		return false;
	}
	
	@Override
	public Cardinal getDirection() {
		return this.direction;
	}
	
	@Override
	public void setDirection(Cardinal dir) {
		this.direction = dir;
	}
	
	@Override
	public void addEntrance(Cardinal dir) {
		this.entrances.add(dir);
	}
	
	@Override
	public CellManager getCells() {
		CellManager cells = new CellManager();

		cells.add(new Cell(new Coord(0,0,0), CellState.OBSTRUCTED));
		
		for(Cardinal dir : Cardinal.directions) {
			cells.add(new Cell(new Coord(0,0,0).add(dir), CellState.POTENTIAL));
		}
		
		return cells;
	}
	

	@Override
	public void determineEntrances(Floor f, Coord floorPos) {
		
		for(Cardinal dir : Cardinal.directions) {
			Coord fp = floorPos.copy();
			fp.add(dir);
			Cell c = f.getCell(fp);
			if(c.isRoom() && !c.getWalls().contains(Cardinal.reverse(dir))){
				this.entrances.add(dir);
			}
		}
		
	}
	
	public void applyFilters(IWorldEditor editor) {
		Random rand = editor.getRandom(this.getWorldPos());
		this.getTheme().applyFilters(editor, rand, getBoundingBox());
	}
}
