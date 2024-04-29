package com.greymerk.roguelike.dungeon.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.greymerk.roguelike.debug.Debug;
import com.greymerk.roguelike.dungeon.Dungeon;
import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.room.RoomProvider;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.settings.IDungeonSettings;
import com.greymerk.roguelike.settings.dungeon.DungeonSettingsDefault;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.util.math.random.Random;

public class LayoutManager {

	private Coord origin;
	private List<Floor> floors;
	private IDungeonSettings settings;
	
	
	public LayoutManager(Coord origin) {
		this.origin = origin;
		this.floors = createFloors();
		this.settings = new DungeonSettingsDefault();
	}
	
	public void generate(IWorldEditor editor) {
		Debug debug = new Debug(editor.getWorldDirectory());
		Random rand = editor.getRandom(origin);
		
		for(Floor floor : this.floors) {
			int level = Dungeon.getLevelFromY(floor.getOrigin().getY());
			
			if(level == 0) {
				this.addStair(editor, rand, floor);
				continue;
			}
			
			RoomProvider roomProvider = this.settings.getLevel(floor.getOrigin().getY()).getRooms();
			List<Room> rooms = roomProvider.getRooms(rand, (level * 2) + (13 - level));

			for(Room r : rooms) {
				IRoom room = Room.getInstance(r, this.settings.getLevel(floor.getOrigin().getY()));
				this.placeRoom(room, rand, floor);
			}
			
			if(level > 0) this.addStair(editor, rand, floor);
			
			floor.getRooms().forEach(r -> r.determineEntrances(floor, r.getFloorPos()));
		}
		debug.toFile(origin.getX() + "_" + origin.getZ() + ".json", this.asJson());
	}
	
	private void addStair(IWorldEditor editor, Random rand, Floor floor) {
		if(floors.indexOf(floor) >= floors.size() - 1) return;
		
		List<Cell> potentials = floor.getCells(CellState.POTENTIAL);
		if(potentials.size() == 0) return;
		RandHelper.shuffle(potentials, rand);
		Cell p = floor.findValidStair(potentials, rand);
		Cardinal dir = floor.findStairDir(p);
		Coord rfp = p.getFloorPos();
		Coord rwp = p.getWorldPos(floor.getOrigin());
		IRoom stair = Room.getInstance(Room.STAIRWAY, this.settings.getLevel(floor.getOrigin().getY()), rfp, rwp, dir);
		this.addRoom(stair, rfp, rwp, floor);
	}
	
	public void addEntrance(IRoom room) {
		Floor f = this.floors.get(0);
		Coord fp = new Coord(0,0,0);
		Coord wp = f.getOrigin();
		this.addRoom(room, fp, wp, f);
	}
	
	public void addRoom(IRoom toAdd, Coord fp, Coord wp, Floor floor) {
		int level = this.floors.indexOf(floor);
		toAdd.determineEntrances(floor, fp);
		toAdd.setFloorPos(fp);
		toAdd.setWorldPos(wp);
		floor.addRoom(toAdd);
		CellManager cells = toAdd.getCells();
		for(Cell c : cells) {
			Coord pos = new Coord(c.getFloorPos().getX(), 0, c.getFloorPos().getZ());
			pos.add(fp);
			CellState state = c.getState();
			int offsetLevel = level + c.getLevelOffset();
			Floor f = this.floors.get(offsetLevel);
			Cell copy = new Cell(pos, state);
			c.getWalls().forEach(w -> copy.addWall(w));
			f.addCell(copy);
		}
	}
	
	private List<Floor> createFloors() {
		int depth = -50;
		List<Floor> floors = new ArrayList<Floor>();
		for(int y = origin.getY(); y >= depth; y -= 10) {
			floors.add(new Floor(new Coord(this.origin.getX(), y, this.origin.getZ())));
		}
		return floors;
	}
	
	public List<IRoom> getRooms(){
		List<IRoom> rooms = new ArrayList<IRoom>();
		for(Floor floor : floors) {
			rooms.addAll(floor.getRooms());
		}
		return rooms;
	}
	
	public boolean placeRoom(IRoom room, Random rand, Floor floor) {
		List<Cell> potentials = floor.getCells(CellState.POTENTIAL);
		Collections.sort(potentials, new CenterSort());
		CellManager roomCells = room.getCells();
		List<Cell> roomPotentials = roomCells.getCells(CellState.POTENTIAL);
		roomPotentials.removeIf(c -> c.getFloorPos().getY() != 0);
		RandHelper.shuffle(roomPotentials, rand);
		
		if(room.isDirectional()) {
			for(Cell p : potentials) {
				for(Cardinal dir : Cardinal.randDirs(rand)) {
					room.setDirection(dir);
					Coord fp = p.getFloorPos();
					fp.add(Cardinal.reverse(dir));
					Cell c = floor.getCell(fp);
					if(c.getState() == CellState.OBSTRUCTED) {
						if(c.getWalls().contains(dir)) continue;
						Coord rfp = p.getFloorPos();
						Coord rwp = p.getWorldPos(floor.getOrigin());
						boolean fit = this.roomFits(room, rfp, floor);
						if(fit) this.addRoom(room, rfp, rwp, floor);
						return true;
					}
				}
			}
		} else {
			for(Cell rp : roomPotentials) {			
				for(Cell p : potentials) {
					for(Cardinal dir : Cardinal.randDirs(rand)) {
						room.setDirection(dir);
						Coord pos = p.getFloorPos();
						pos.add(Cardinal.reverse(room.getDirection()));
						Cell c = floor.getCell(pos);
						if(c.isRoom() && (!c.getWalls().contains(room.getDirection()))) {
							Coord roomPos = c.getFloorPos().copy().add(rp.getFloorPos());
							Cell roomCenter = floor.getCell(roomPos);
							if(this.roomFits(room, roomPos, floor)) {
								this.addRoom(room, roomPos, roomCenter.getWorldPos(floor.getOrigin()), floor);
								return true;
							}
						}	
					}
				}
			}	
		}
		return false;
	}
	
	public boolean roomFits(IRoom room, Coord floorPos, Floor floor) {
		
		int level = this.floors.indexOf(floor);
		CellManager cells = room.getCells();
		
		for(Cell rc : cells) {
			if(rc.getState() != CellState.OBSTRUCTED) continue;
			if(rc.getLevelOffset() + level >= this.floors.size()) return false;
			if(rc.getLevelOffset() != 0) continue;
			
			Coord fp = new Coord(rc.getFloorPos().getX(), 0, rc.getFloorPos().getZ());
			fp.add(floorPos);
			CellState state = floor.getCell(fp).getState();
			if(state == CellState.OBSTRUCTED) return false;
		}
		return true;
	}
	
	public boolean outerWallBlockingPassage(Cell cell, Coord fp, Floor f) {
		if(cell.getWalls().isEmpty()) return false;
		for(Cardinal dir : cell.getWalls()) {
			Coord pos = fp.copy();
			pos.add(dir);
			Cell other = f.getCell(pos);
			if(!other.isRoom()) continue;
			if(!other.getWalls().contains(Cardinal.reverse(dir))) return true;
		}
		return false;
	}
	
	public JsonObject asJson() {
		JsonObject json = new JsonObject();
		JsonArray jsonFloors = new JsonArray();
		json.add("floors", jsonFloors);
		this.floors.forEach(f -> jsonFloors.add(f.asJson()));
		return json;
	}
	
	private class CenterSort implements Comparator<Cell>{
		@Override
		public int compare(Cell c, Cell other) {
			Coord center = new Coord(0,0,0);			
			int distC = (int) c.getFloorPos().distance(center);
			int distOther = (int) other.getFloorPos().distance(center);
			return distC - distOther;
		}
	}
	
}
