package com.greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Floor {

	public static final Codec<Floor> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			Coord.CODEC.fieldOf("origin").forGetter(floor -> floor.origin),
			CellManager.CODEC.fieldOf("cells").forGetter(floor -> floor.cells),
			Dungeon.LIST_ROOM_CODEC.fieldOf("rooms").forGetter(floor -> floor.rooms)
		).apply(instance, (pos, cm, rooms) -> new Floor(pos, cm, rooms))
	);
			
	
	private Coord origin;
	private List<IRoom> rooms;
	private CellManager cells;
	
	public static Floor of(Coord origin) {
		return new Floor(origin);
	}
	
	private Floor(Coord origin) {
		this.rooms = new ArrayList<IRoom>();
		this.cells = new CellManager();
		this.origin = origin;
	}
	
	public Floor(Coord origin, CellManager cm, List<IRoom> rooms) {
		this.origin = origin;
		this.cells = cm;
		this.rooms = rooms;
	}
	
	public void addRoom(IRoom room) {
		this.rooms.add(room);
	}
	
	public void generate(IWorldEditor editor) {
		for(IRoom room : this.rooms) {
			room.generate(editor);
		}
	}
	
	public List<IRoom> getRooms(){
		return this.rooms;
	}
	
	public CellManager getCells(){
		return this.cells;
	}
	
	public List<Cell> getCells(CellState type){
		return this.cells.getCells(type);
	}

	public Cell getCell(Coord floorPos) {
		return this.cells.get(floorPos);
	}
	
	public void addCell(Cell toAdd) {
		this.cells.add(toAdd);
	}
			
	public Coord getOrigin() {
		return this.origin.copy();
	}
}
