package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.layout.Entrance;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.theme.ITheme;

import net.minecraft.util.math.random.Random;

public abstract class AbstractRoom implements IRoom{
	
	protected Coord floorPos;
	protected Coord worldPos;
	protected ILevelSettings settings;
	protected ITheme theme;
	protected Cardinal direction;
	protected boolean generated;
	protected Map<Cardinal, Entrance> entrances;
	
	public AbstractRoom() {
		this.direction = Cardinal.EAST;
		this.generated = false;
		this.entrances = new HashMap<Cardinal, Entrance>();
	}
	
	public AbstractRoom(ILevelSettings settings, IBounded box, Coord worldPos) {
		this(settings, box, worldPos, Cardinal.DOWN);
	}
	
	public AbstractRoom(ILevelSettings settings, IBounded box, Coord worldPos, Cardinal dir) {
		this.settings = settings;
		this.theme = settings.getTheme();
		this.worldPos = worldPos.copy().freeze();
		this.generated = false;
		this.direction = dir;
		this.entrances = new HashMap<Cardinal, Entrance>();
	}
	
	@Override
	public void setFloorPos(Coord floorPos) {
		this.floorPos = floorPos.copy().freeze();
	}
	
	@Override
	public Coord getFloorPos() {
		return this.floorPos.copy().freeze();
	}
	
	@Override
	public void setWorldPos(Coord worldPos) {
		this.worldPos = worldPos.copy().freeze();
	}
	
	@Override
	public Coord getWorldPos() {
		return worldPos.copy().freeze();
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
	public Optional<IBounded> getBoundingBox() {
		if(this.direction == null) return Optional.empty();
		if(this.worldPos == null) return Optional.empty();
		return Optional.of(this.getBoundingBox(worldPos, direction));
	}
	
	@Override
	public IBounded getBoundingBox(Coord origin, Cardinal dir) {
		return BoundingBox.of(origin)
			.grow(Cardinal.directions, 3)
			.grow(Cardinal.UP, 6)
			.grow(Cardinal.DOWN, 3);
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
	public Cardinal getDirection() {
		return this.direction;
	}
	
	@Override
	public void setDirection(Cardinal dir) {
		this.direction = dir;
	}
	
	@Override
	public void addEntrance(Cardinal dir, Entrance type) {
		this.entrances.put(dir, type);
	}
	
	@Override
	public CellManager getCells(Cardinal dir) {
		Coord origin = Coord.ZERO;
		CellManager cells = new CellManager();

		cells.add(new Cell(origin, CellState.OBSTRUCTED));
		
		for(Cardinal d : Cardinal.directions) {
			if(d == Cardinal.reverse(dir)) continue;
			cells.add(new Cell(origin.copy().add(d), CellState.POTENTIAL));
		}
		
		return cells;
	}
	
	@Override
	public Entrance getEntrance(Cardinal dir) {
		if(!this.entrances.containsKey(dir)) return Entrance.ALCOVE;
		return this.entrances.get(dir);
	}
	
	@Override
	public List<Cardinal> getEntrancesFromType(Entrance type){
		List<Cardinal> dirs = new ArrayList<Cardinal>();
		for(Cardinal dir : this.entrances.keySet()) {
			if(this.entrances.get(dir) == type) {
				dirs.add(dir);
			}
		}
		return dirs;
	}
	
	@Override
	public Map<Cardinal, Entrance> getEntrances(){
		return this.entrances;
	}

	@Override
	public void determineEntrances(Floor f, Coord floorPos) {
		for(Cardinal dir : Cardinal.directions) {
			Coord fp = floorPos.copy();
			fp.add(dir);
			Cell c = f.getCell(fp);
			if(c.isRoom() && !c.getWalls().contains(Cardinal.reverse(dir))){
				this.entrances.put(dir, Entrance.DOOR);
			} else if(c.getState() == CellState.POTENTIAL) {
				this.entrances.put(dir, Entrance.ALCOVE);
				Cell alcove = new Cell(fp, CellState.OBSTRUCTED);
				Cardinal.directions.forEach(d -> alcove.addWall(d));
				c.replace(alcove);
			} else {
				this.entrances.put(dir, Entrance.WALL);	
			}
		}
	}
	
	public void applyFilters(IWorldEditor editor) {
		Random rand = editor.getRandom(this.getWorldPos());
		this.settings.applyFilters(editor, rand, getBoundingBox().get());
	}
	
	public ILevelSettings getLevelSettings() {
		return this.settings;
	}
}
