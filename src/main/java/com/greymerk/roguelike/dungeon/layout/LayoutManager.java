package com.greymerk.roguelike.dungeon.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.greymerk.roguelike.dungeon.Floor;
import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;

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
			
			for(int i = 0; i < lvl; ++i) {
				floor.addRandomBranch(rand, 3 + lvl/2);
			}
			
			for(int i = 0; i < lvl / 4; ++i) {
				floor.addStair(rand);
			}
			floor.addStair(rand);
			
			lvl++;
			previous = floor;
		}
	}
	
	
	
	public void addRoom(IRoom toAdd, int level) {
		this.floors.get(level).addRoom(toAdd);
	}
	
	private List<Floor> createFloors() {
		int depth = -50;
		List<Floor> floors = new ArrayList<Floor>();
		ITheme theme = Theme.getTheme(Theme.STONE);
		for(int y = origin.getY(); y >= depth; y -= 10) {
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
	
	public IBounded getBounds() {
		//@TODO: implement me
		return null;
	}
	
}
