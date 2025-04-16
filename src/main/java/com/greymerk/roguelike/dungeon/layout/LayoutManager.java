package com.greymerk.roguelike.dungeon.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.greymerk.roguelike.config.Config;
import com.greymerk.roguelike.debug.DebugLayout;
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
import com.greymerk.roguelike.settings.ILevelSettings;
import com.greymerk.roguelike.settings.dungeon.DungeonSettingsDefault;
import com.greymerk.roguelike.util.math.RandHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.random.Random;

public class LayoutManager {

	public static final Codec<LayoutManager> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				Coord.CODEC.fieldOf("origin").forGetter(lm -> lm.origin),
				Codec.list(Floor.CODEC).fieldOf("floors").forGetter(lm -> lm.floors)
		).apply(instance, (origin, floors) -> new LayoutManager(origin, floors))
	);
	
	private Coord origin;
	private List<Floor> floors;
	private IDungeonSettings settings;
	private ExclusionZones zones; 
	
	public LayoutManager(Coord origin) {
		this.origin = origin;
		this.floors = createFloors();
		this.settings = new DungeonSettingsDefault(Config.ofBoolean(Config.BELOW_SEA_LEVEL) ? 50 : origin.getY(), -50);
		this.zones = new ExclusionZones();
	}
	
	public LayoutManager(Coord origin, List<Floor> floors) {
		this.origin = origin;
		this.floors = floors;
		this.settings = new DungeonSettingsDefault(Config.ofBoolean(Config.BELOW_SEA_LEVEL) ? 50 : origin.getY(), -50);
		this.zones = new ExclusionZones();
	}
	
	public void generate(IWorldEditor editor) {

		for(Floor floor : this.floors) {
		
			Random rand = editor.getRandom(floor.getOrigin());
			
			if(Config.ofBoolean(Config.BELOW_SEA_LEVEL) && floor.getOrigin().getY() > 55) {
				this.addStair(editor, rand, floor);
				continue;
			}
			
			ILevelSettings levelSettings = this.settings.getLevel(floor.getOrigin().getY());
			
			this.connectFloorBranches(editor, rand, floor, levelSettings);
			
			RoomProvider roomProvider = levelSettings.getRooms();
			
			int numRooms = Math.clamp(Config.ofInteger(Config.ROOMS_PER_LEVEL).orElse(20), 1, 1000);
			
			List<Room> rooms = roomProvider.getRooms(rand, numRooms);

			rooms.forEach(r -> {
				IRoom room = Room.getInstance(r, levelSettings);
				this.placeRoom(room, rand, floor);
			});
			
			this.addStair(editor, rand, floor);	

			this.determineExits(editor, rand, floor);
		}
				
		if(Config.ofBoolean(Config.DEBUG)) {
			DebugLayout debug = new DebugLayout(editor.getWorldDirectory());
			debug.toFile(origin.getX() + "_" + origin.getZ() + ".json", CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow());	
		}
	}
	
	private void determineExits(IWorldEditor editor, Random rand, Floor floor) {
		CellManager cells = floor.getCells();
		cells.forEach(cell -> {
			Cardinal.directions.forEach(dir -> {		
				cells.getExitType(cell, dir).ifPresent(type -> {
					cell.getOwner().ifPresent(room -> {
						Exit exit = Exit.of(type, cell.getWorldPos(floor.getOrigin()), dir);
						room.addExit(exit);
					});
				});
			});
		});
	}

	private void connectFloorBranches(IWorldEditor editor, Random rand, Floor floor, ILevelSettings settings) {
		while(!floor.getCells().isConnected()) {
			CellManager cm = floor.getCells();
			List<Cell> nearest = cm.getNearestPotentials();
			if(nearest.isEmpty()) return;
			nearest.forEach(c -> {
				IRoom room = Room.getInstance(Room.CORRIDOR, settings);
				this.addRoom(room, Cardinal.NORTH, c.getFloorPos(), c.getWorldPos(floor.getOrigin()), floor);
			});
		}
	}

	private void placeRoom(IRoom room, Random rand, Floor floor) {
		// try to find a place that avoids exclusion zones :)
		if(findFit(room, rand, floor, true)) return;
		
		// try it again without checking exclusion zones :(
		findFit(room, rand, floor, false);
	}
	
	private boolean findFit(IRoom room, Random rand, Floor floor, boolean avoidZones) {
		for(Cell c : room.getCells(Cardinal.NORTH)) {
			if(c.getLevelOffset() + this.floors.indexOf(floor) > this.floors.size() - 1) return false;
		}
		
		List<Cell> cells = floor.getCells(CellState.POTENTIAL);
		
		if(rand.nextBoolean()) {
			RandHelper.shuffle(cells, rand);
		} else {
			Collections.sort(cells, (a, b) -> {
				return a.getWorldPos(origin).manhattanDistance(origin) - b.getWorldPos(origin).manhattanDistance(origin);
			});
		}	
		
		for(Cell potential : cells) {
			List<Cardinal> dirs = getPotentialDir(floor, potential);
			RandHelper.shuffle(dirs, rand);
			for(Cardinal dir : dirs) {
				Coord wp = potential.getWorldPos(floor.getOrigin());
				if(avoidZones && zones.collides(room.getBoundingBox(wp, dir))) {
					continue;
				}
				CellManager rcm = room.getCells(dir);
				if(floor.getCells().roomFits(potential, rcm)) {
					this.addRoom(room, dir, potential.getFloorPos(), wp, floor);
					return true;
				}
			}
		}
		return false;
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
				f.addCell(Cell.of(cfp, c.getState(), c.getOwner().orElse(null)).addWalls(c.getWalls()));
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
