package com.greymerk.roguelike.dungeon.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.greymerk.roguelike.config.Config;
import com.greymerk.roguelike.debug.Debug;
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
import com.greymerk.roguelike.util.math.MathHelper;
import com.greymerk.roguelike.util.math.RandHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.random.Random;

public class LayoutManager {

	public static final Codec<LayoutManager> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				Coord.CODEC.fieldOf("origin").forGetter(lm -> lm.origin),
				Coord.CODEC.fieldOf("entry").forGetter(lm -> lm.entry),
				Codec.list(Floor.CODEC).fieldOf("floors").forGetter(lm -> lm.floors)
		).apply(instance, (origin, entry, floors) -> new LayoutManager(origin, entry, floors))
	);
	
	private Coord origin;
	private Coord entry;
	private List<Floor> floors;
	private ExclusionZones zones; 
	
	public LayoutManager(Coord origin, Coord entry, int depth) {
		this.origin = origin;
		this.entry = entry;
		this.floors = createFloors(entry, depth);
		this.zones = new ExclusionZones();
	}
	
	public LayoutManager(Coord origin, Coord entry, List<Floor> floors) {
		this.origin = origin;
		this.entry = entry;
		this.floors = floors;
		this.zones = new ExclusionZones();
	}
	
	public void generate(IWorldEditor editor) {

		final int ROOMS_PER_LEVEL = 20;
		
		IDungeonSettings settings = new DungeonSettingsDefault(
				Config.ofBoolean(Config.BELOW_SEA_LEVEL) 
					? editor.getInfo().getFirstFloorDepth()
					: entry.getY(),
				editor.getInfo().getLastFloorDepth());
		
		for(Floor floor : this.floors) {
		
			Random rand = editor.getRandom(floor.getOrigin());
			ILevelSettings levelSettings = settings.getLevel(floor.getOrigin().getY());
			
			final boolean aboveDungeon = Config.ofBoolean(Config.BELOW_SEA_LEVEL) 
					&& floor.getOrigin().getY() > editor.getInfo().getFirstFloorDepth();
			
			if(aboveDungeon) {
				this.placeRoom(Room.getInstance(Room.STAIRWAY, levelSettings), rand, floor, false);
				continue;
			}
			
			this.connectFloorBranches(editor, rand, floor, levelSettings);
			
			RoomProvider roomProvider = levelSettings.getRooms();
			final int numRooms = MathHelper.clamp(Config.ofInteger(Config.ROOMS_PER_LEVEL).orElse(ROOMS_PER_LEVEL), 1, 1000);
			List<Room> rooms = roomProvider.getRooms(rand, numRooms);

			rooms.forEach(r -> {
				this.placeRoom(Room.getInstance(r, levelSettings), rand, floor, rand.nextBoolean());
			});	

			this.determineExits(editor, rand, floor);
		}
				
		if(Debug.isOn()) {
			Debug.toFile(editor, 
				"RoguelikeDungeons_" + origin.toString().replaceAll(" ", "_") + "_Layout.json", 
				CODEC.encodeStart(JsonOps.INSTANCE, this).result().get());	
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
						if(exit.type() == ExitType.ALCOVE) {
							Cell other = cells.get(cell.getWorldPos(floor.getOrigin()).add(dir));
							other.addWalls(Cardinal.directions);
						}
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

	private void placeRoom(IRoom room, Random rand, Floor floor, boolean shuffle) {
		// try to find a place that avoids exclusion zones :)
		if(findFit(room, rand, floor, true, shuffle)) return;
		
		// try it again without checking exclusion zones :(
		findFit(room, rand, floor, false, shuffle);
	}
	
	private boolean findFit(IRoom room, Random rand, Floor floor, boolean avoidZones, boolean shuffle) {
		for(Cell c : room.getCells(Cardinal.NORTH)) {
			if(c.getLevelOffset() + this.floors.indexOf(floor) > this.floors.size() - 1) return false;
		}
		
		List<Cell> cells = floor.getCells(CellState.POTENTIAL);
		
		if(shuffle) {
			RandHelper.shuffle(cells, rand);
		} else {
			Collections.sort(cells, (a, b) -> {
				Coord ac = a.getWorldPos(floor.getOrigin());
				Coord bc = b.getWorldPos(floor.getOrigin());
				Double adist = ac.distance(floor.getOrigin());
				Double bdist = bc.distance(floor.getOrigin());
				return Double.compare(adist, bdist);
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

	public void addEntrance(IRoom room) {
		Floor f = this.floors.get(0);
		Coord wp = f.getOrigin();
		this.addRoom(room, Cardinal.NORTH, Coord.ZERO, wp, f);
	}
	
	public void addRoom(IRoom toAdd, Cardinal dir, Coord fp, Coord wp, Floor floor) {
		int level = this.floors.indexOf(floor);
		toAdd.setDirection(dir);
		toAdd.setFloorPos(fp);
		toAdd.setWorldPos(wp);
		floor.addRoom(toAdd);
		CellManager cells = toAdd.getCells(dir);
		cells.getLevelOffsets().forEach(offset -> {
			Floor f = this.floors.get(level + offset);
			cells.getByOffset(offset).forEach(c -> {
				Coord cfp = c.getFloorPos().withY(0);
				cfp.add(fp);
				f.addCell(Cell.of(cfp, c.getState(), c.getOwner().orElse(null)).addWalls(c.getWalls()));
			});
		});
	}
	
	private List<Floor> createFloors(Coord entry, int depth) {
		List<Floor> floors = new ArrayList<Floor>();
		for(int y = entry.getY(); y >= depth; y -= 10) {
			floors.add(Floor.of(entry.withY(y)));
		}
		return floors;
	}
	
	public List<IRoom> getRooms(){
		return this.floors.stream()
			.flatMap(floor -> StreamSupport.stream(floor.getRooms().spliterator(), false))
			.toList();
	}
	
	public class CenterSort implements Comparator<Cell>{
		@Override
		public int compare(Cell c, Cell other) {
			int distC = (int) c.getFloorPos().distance(Coord.ZERO);
			int distOther = (int) other.getFloorPos().distance(Coord.ZERO);
			return Integer.compare(distC, distOther);
		}
	}
}
