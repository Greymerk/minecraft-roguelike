package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.layout.Exit;
import com.greymerk.roguelike.dungeon.layout.ExitType;
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
	protected Set<Exit> exits;
	
	public AbstractRoom() {
		this.direction = Cardinal.EAST;
		this.generated = false;
		this.exits = new TreeSet<Exit>();
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
		this.exits = new TreeSet<Exit>();
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
	public CellManager getCells(Cardinal dir) {
		Coord origin = Coord.ZERO;
		CellManager cells = new CellManager();

		cells.add(Cell.of(origin, CellState.OBSTRUCTED, this));
		
		for(Cardinal d : Cardinal.directions) {
			if(d == Cardinal.reverse(dir)) continue;
			cells.add(Cell.of(origin.copy().add(d), CellState.POTENTIAL, this));
		}
		
		return cells;
	}

	@Override
	public void generateExits(IWorldEditor editor, Random rand) {
		for(Exit exit : this.exits) {
			Coord origin = exit.origin();
			Cardinal dir = exit.dir();
			
			switch(exit.type()) {
			case ALCOVE:
				settings.getAlcove(rand).generate(editor, rand, settings, origin, dir); break;
			case DOOR:
				Fragment.generate(Fragment.ARCH, editor, rand, settings, origin, dir); break;
			case WALL:
				settings.getWallFragment(rand).generate(editor, rand, settings, origin, dir); break;
			default:
				break;
			}
		}
	}
	
	@Override
	public ExitType getExitType(Cardinal dir) {
		if(dir == Cardinal.reverse(this.direction)) return ExitType.DOOR;
		
		for(Exit e : this.exits) {
			if(e.dir() == dir 
				&& e.origin().getY() == this.getWorldPos().getY())
				return e.type();
		}
		
		return ExitType.WALL;
	}
	
	@Override
	public List<Exit> getExits(){
		return new ArrayList<Exit>(this.exits);
	}
	
	@Override
	public void addExit(Exit exit) {
		this.exits.add(exit);
	}
	
	public void applyFilters(IWorldEditor editor) {
		Random rand = editor.getRandom(this.getWorldPos());
		this.settings.applyFilters(editor, rand, getBoundingBox().get());
	}
	
	public ILevelSettings getLevelSettings() {
		return this.settings;
	}
}
