package com.greymerk.roguelike.dungeon.layout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.greymerk.roguelike.config.Config;
import com.greymerk.roguelike.debug.Debug;
import com.greymerk.roguelike.debug.DebugLayout;
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
	private ExclusionZones zones; 
	
	public LayoutManager(Coord origin) {
		this.origin = origin;
		this.floors = createFloors();
		this.settings = new DungeonSettingsDefault();
		this.zones = new ExclusionZones();
		//this.zones.add(BoundingBox.of(new Coord(-150, 0, 50)).grow(Cardinal.all, 200));
	}
	
	public void generate(IWorldEditor editor) {
		
		Random rand = editor.getRandom(origin);
				
		for(Floor floor : this.floors) {
			int level = Dungeon.getLevelFromY(floor.getOrigin().getY());
			
			if(level == 0) {
				this.addStair(editor, rand, floor);
				continue;
			}
			
			RoomProvider roomProvider = this.settings.getLevel(floor.getOrigin().getY()).getRooms();
			
			int numRooms = Math.clamp(Config.ofInteger(Config.ROOMS_PER_LEVEL).orElse(20), 1, 1000);
			
			List<Room> rooms = roomProvider.getRooms(rand, numRooms);

			rooms.forEach(r -> {
				IRoom room = Room.getInstance(r, this.settings.getLevel(floor.getOrigin().getY()));
				this.placeRoom(room, rand, floor);
			});
			
			if(level > 0) this.addStair(editor, rand, floor);
			
			floor.getRooms().forEach(r -> r.determineEntrances(floor, r.getFloorPos()));
		}
		
		if(Config.ofBoolean(Config.DEBUG)) {
			DebugLayout debug = new DebugLayout(editor.getWorldDirectory());
			debug.toFile(origin.getX() + "_" + origin.getZ() + ".json", this.asJson());	
		}
		
	}
	
	private void placeRoom(IRoom room, Random rand, Floor floor) {
		List<Cell> cells = floor.getCells(CellState.POTENTIAL);
		//cells.sort(new CenterSort());
		RandHelper.shuffle(cells, rand);
		CellManager fcm = floor.getCells();
		
		// try to find a place that avoids exclusion zones :)
		for(Cell potential : cells) {
			List<Cardinal> dirs = getPotentialDir(floor, potential);
			RandHelper.shuffle(dirs, rand);
			for(Cardinal dir : dirs) {
				Coord wp = potential.getWorldPos(floor.getOrigin());
				if(zones.collides(room.getBoundingBox(wp, dir))) {
					Debug.info("colliding at " + wp);
					continue;
				}
				CellManager rcm = room.getCells(dir);
				if(fcm.roomFits(potential, rcm)) {
					this.addRoom(room, dir, potential.getFloorPos(), wp, floor);
					return;
				}
			}
		}
		
		// try it again without checking exclusion zones :(
		for(Cell potential : cells) {
			List<Cardinal> dirs = getPotentialDir(floor, potential);
			RandHelper.shuffle(dirs, rand);
			for(Cardinal dir : dirs) {
				CellManager rcm = room.getCells(dir);
				if(fcm.roomFits(potential, rcm)) {
					this.addRoom(room, dir, potential.getFloorPos(), potential.getWorldPos(floor.getOrigin()), floor);
					return;
				}
			}
		}
	}

	private List<Cardinal> getPotentialDir(Floor floor, Cell cell) {
		List<Cardinal> dirs = new ArrayList<Cardinal>();
		for(Cardinal dir : Cardinal.directions) {
			Coord fp = cell.getFloorPos();
			fp.add(Cardinal.reverse(dir));
			Cell target = floor.getCell(fp);
			if(target.getState() != CellState.OBSTRUCTED) continue;
			if(target.hasWall(dir)) continue;
			dirs.add(dir);
		}
		return dirs;
	}

	private void addStair(IWorldEditor editor, Random rand, Floor floor) {
		if(floors.indexOf(floor) >= floors.size() - 1) return;
		IRoom stairway = Room.getInstance(Room.STAIRWAY, this.settings.getLevel(floor.getOrigin().getY()));
		placeRoom(stairway, rand, floor);
		zones.scan(editor, stairway.getWorldPos(), 500);
	}

	public void addEntrance(IRoom room) {
		Floor f = this.floors.get(0);
		Coord fp = new Coord(0,0,0);
		Coord wp = f.getOrigin();
		this.addRoom(room, Cardinal.NORTH, fp, wp, f);
	}
	
	public void addRoom(IRoom toAdd, Cardinal dir, Coord fp, Coord wp, Floor floor) {
		int level = this.floors.indexOf(floor);
		//toAdd.determineEntrances(floor, fp);
		toAdd.setDirection(dir);
		toAdd.setFloorPos(fp);
		toAdd.setWorldPos(wp);
		floor.addRoom(toAdd);
		CellManager cells = toAdd.getCells(dir);
		cells.getLevelOffsets().forEach(offset -> {
			Floor f = this.floors.get(level + offset);
			cells.getByOffset(offset).forEach(c -> {
				Coord cfp = new Coord(c.getFloorPos().getX(), 0, c.getFloorPos().getZ());
				cfp.add(fp);
				f.addCell(Cell.of(cfp, c.getState()).addWalls(c.getWalls()));
			});
		});
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
	

		
	public JsonObject asJson() {
		JsonObject json = new JsonObject();
		JsonArray jsonFloors = new JsonArray();
		json.add("floors", jsonFloors);
		this.floors.forEach(f -> jsonFloors.add(f.asJson()));
		return json;
	}
	
	public class CenterSort implements Comparator<Cell>{
		@Override
		public int compare(Cell c, Cell other) {
			Coord center = new Coord(0,0,0);			
			int distC = (int) c.getFloorPos().distance(center);
			int distOther = (int) other.getFloorPos().distance(center);
			return Integer.compare(distC, distOther);
		}
	}
}
