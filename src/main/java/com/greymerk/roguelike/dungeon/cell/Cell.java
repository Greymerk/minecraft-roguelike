package com.greymerk.roguelike.dungeon.cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.dynamic.Codecs;

public class Cell {

	private static final Codec<List<String>> LIST_CODEC = Codec.list(Codecs.NON_EMPTY_STRING);
	
	public static final Codec<Cell> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				Coord.CODEC.fieldOf("floorPos").forGetter(cell -> cell.floorPos),
				Codecs.NON_EMPTY_STRING.fieldOf("state").forGetter(cell -> cell.state.name()),
				LIST_CODEC.fieldOf("walls").forGetter(cell -> cell.getWalls().stream().map(dir -> dir.name()).collect(Collectors.toList()))
			).apply(instance, (fp, state, walls) -> Cell.of(fp, CellState.of(state), 
					walls.stream().map(dir -> Cardinal.of(dir)).collect(Collectors.toList())))
	);
	
	public static final int SIZE = 6;
	private CellState state;
	private Coord floorPos;
	private Set<Cardinal> walls;
	
	public static Cell of(Coord floorPos, CellState state) {
		return new Cell(floorPos, state);
	}
	
	public static Cell of(Coord floorPos, CellState state, List<Cardinal> walls) {
		return Cell.of(floorPos, state).addWalls(walls);
	}
	
	public Cell(Coord floorPos, CellState state) {
		this.floorPos = floorPos.copy();
		this.state = state;
		this.walls = new HashSet<Cardinal>();
	}
	
	public CellState getState() {
		return this.state;
	}
	
	public void setState(CellState state) {
		this.state = state;
	}
	
	public void replace(Cell other) {
		this.floorPos = other.floorPos.copy();
		this.state = other.state;
		this.walls.addAll(other.walls);
	}
	
	public boolean isRoom() {
 		if(this.state == CellState.OBSTRUCTED) return true;
		return false;
	}
	
	public Coord getFloorPos() {
		return this.floorPos.copy();
	}
	
	public int getLevelOffset() {
		// the floors are counted from zero, but down is negative in position
		// for now i'm just reversing the position value to get the level offset
		return this.floorPos.getY() * -1;
	}
	
	public Coord getWorldPos(Coord origin) {
		return this.floorPos.copy()
				.mul(new Coord(Cell.SIZE, 1, Cell.SIZE))
				.add(origin);
	}
	
	public Cell addWall(Cardinal dir) {
		this.walls.add(dir);
		return this;
	}
	
	public Cell addWalls(List<Cardinal> directions) {
		directions.forEach(dir -> {
			this.addWall(dir);
		});
		return this;
	}
	
	public boolean hasWall(Cardinal dir) {
		return this.walls.contains(dir);
	}
	
	public boolean hasWalls() {
		return !this.walls.isEmpty();
	}
	
	public List<Cardinal> getWalls(){
		return new ArrayList<Cardinal>(this.walls);
	}
	
	public boolean connectedTo(Cell other) {
		Cardinal dirTo = this.floorPos.dirTo(other.floorPos);
		if(!this.floorPos.copy().add(dirTo).equals(other.floorPos)) return false;
		
		if(this.hasWall(dirTo)) return false;
		if(other.hasWall(Cardinal.reverse(dirTo))) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(floorPos, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Cell other = (Cell) obj;
		return Objects.equals(floorPos, other.floorPos)
				&& state == other.state;
	}

	@Override
	public String toString() {
		return this.floorPos.toString() + ' ' + this.state + ' ' + this.walls;
	}

}
