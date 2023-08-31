package com.greymerk.roguelike.dungeon.layout;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.util.math.random.Random;

public class LayoutManager {

	private Coord origin;
	private List<Floor> floors;
	
	
	public LayoutManager(Coord origin) {
		this.origin = origin;
		this.floors = createFloors(); 
	}
	
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(origin);
		Floor previous = null;
		int lvl = 0;
		
		for(Floor floor : this.floors) {
			if(previous != null) {
				List<Cell> potentials = previous.getOffsetCells();
				floor.addCells(potentials);
			}
			
			for(int i = 0; i < lvl * 3; ++i) {
				floor.addRandomBranch(rand, 3, 5 + lvl/2);
			}
			
			if(lvl < this.floors.size() - 1) {
				for(int i = 0; i < lvl / 4; ++i) {
					floor.addStair(rand);
				}
				floor.addStair(rand);
			}
			
			lvl++;
			previous = floor;
		}
		
		for(int i = 0; i < this.floors.size(); ++i) {
			Floor floor = this.floors.get(i);
			floor.addCellWalls();
		}
		
		
		for(int i = 0; i < this.floors.size(); ++i) {
			this.addRooms(editor, rand, i);
		}
		
		for(Floor floor : this.floors) {
			floor.convertCorridors();
		}
	}
	
	public void addRooms(IWorldEditor editor, Random rand, int level) {
		Floor floor = this.floors.get(level);
		if(level < 2) {
			for(int i = 0; i < 2; ++i) {
				IRoom bedroom = Room.getInstance(Room.BEDROOM, floor.getTheme());
				this.placeRoom(bedroom, rand, level);	
			}
		}
		
		for(int i = 0; i < 10; ++i) {
			IRoom room = Room.getInstance(Room.CROSS, floor.getTheme());
			this.placeRoom(room, rand, level);
		}
		
	}
	
	public void addRoom(IRoom toAdd, Coord fp, Coord wp, int level) {
		toAdd.determineEntrances(this.floors.get(level), fp);
		toAdd.setFloorPos(fp);
		toAdd.setWorldPos(wp);
		this.floors.get(level).addRoom(toAdd);
		List<Cell> cells = toAdd.getCells();
		for(Cell c : cells) {
			Coord pos = new Coord(c.getFloorPos().getX(), 0, c.getFloorPos().getZ());
			pos.add(fp);
			CellState state = c.getState();
			int offsetLevel = level + c.getLevelOffset();
			Floor f = this.floors.get(offsetLevel);
			f.addCell(new Cell(pos, state));
		}
	}
	
	private List<Floor> createFloors() {
		int depth = -50;
		List<Floor> floors = new ArrayList<Floor>();
		for(int y = origin.getY(); y >= depth; y -= 10) {
			ITheme theme = Theme.getFromDepth(y);
			floors.add(new Floor(theme, new Coord(this.origin.getX(), y, this.origin.getZ())));
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
	
	public boolean placeRoom(IRoom room, Random rand, int level) {
		Floor floor = this.floors.get(level);
		List<Cell> corridors = floor.getCells(CellState.CORRIDOR);
		RandHelper.shuffle(corridors, rand);
		for(Cell c : corridors) {
			for(Cardinal dir : Cardinal.randDirs(rand)) {
				if(!c.getWalls().contains(dir)) continue;
				room.setDirection(dir);
				boolean fit = doesRoomFit(room, c.getFloorPos(), level);
				if(fit) this.addRoom(room, c.getFloorPos(), c.getWorldPos(floor.getOrigin()), level);
				return true;	
			}
		}
		return false;
	}
	
	public boolean doesRoomFit(IRoom room, Coord floorPos, int level) {
		
		List<Cell> cells = room.getCells();
		for(Cell cell : cells) {
			if(cell.getState() != CellState.OBSTRUCTED) continue;
			
			int offsetFloor = level + cell.getLevelOffset();
			if(offsetFloor >= this.floors.size()) return false;
			Floor floor = this.floors.get(offsetFloor);
			Coord fp = new Coord(cell.getFloorPos().getX(), 0, cell.getFloorPos().getZ());
			fp.add(floorPos);
			CellState state = floor.getCellState(fp);
			if(state == CellState.OBSTRUCTED) return false;
			
			if(!cell.getWalls().isEmpty()) {
				for(Cardinal dir : cell.getWalls()) {
					Coord pos = new Coord(fp);
					pos.add(dir);
					Cell other = floor.getCell(pos);
					if(!(other.getState() == CellState.CORRIDOR
						|| other.getState() == CellState.OBSTRUCTED)) continue;
					if(!other.getWalls().contains(Cardinal.reverse(dir))) return false;
				}
			}
		}
		return true;
		
	}
	
	
	public IBounded getBounds() {
		//@TODO: implement me
		return null;
	}
	
}
