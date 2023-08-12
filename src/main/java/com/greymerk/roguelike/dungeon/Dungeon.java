package com.greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.greymerk.roguelike.dungeon.layout.LayoutManager;
import com.greymerk.roguelike.dungeon.room.EntranceRoom;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.tower.RogueTower;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.theme.Theme;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

public class Dungeon implements Iterable<IRoom>{

	public static boolean canSpawn(IWorldEditor editor, Coord pos) {
		if(!editor.isOverworld()) return false;
		
		Coord surface = editor.findSurface(pos);
		if(!editor.isGround(surface)) return false;
		return true;
	}
	
	List<IRoom> rooms;
	Coord origin;
	
	public Dungeon(Coord pos) {
		this.rooms = new ArrayList<IRoom>();
		this.origin = pos;
	}
	
	public Dungeon(NbtCompound tag) {
		NbtList rooms = tag.getList("rooms", NbtElement.COMPOUND_TYPE);
		for(NbtElement nbt : rooms) {
			this.rooms.add(Room.createFromNBT((NbtCompound)nbt));
		}
		this.origin = new Coord(tag.getCompound("pos"));
	}
	
	public NbtCompound getNbt() {
		NbtCompound data = new NbtCompound();
		NbtList roomlist = new NbtList();
		for(IRoom room : this.rooms) {
			roomlist.add(room.getNbt());
		}
		data.put("rooms", roomlist);
		data.put("pos", this.origin.getNbt());
		return data;
	}
	
	public void generate(IWorldEditor editor) {
		long seed = editor.getSeed() * this.origin.getBlockPos().asLong();
		Random rand = new Random(seed);
		
		Coord surface = editor.findSurface(this.origin);
		int entranceY = (surface.getY() - surface.getY() % 10) - 10;
		Coord firstFloor = new Coord(this.origin.getX(), entranceY, this.origin.getZ());

		LayoutManager layout = new LayoutManager(firstFloor);
		IRoom entrance = new EntranceRoom(Theme.getTheme(Theme.STONE), new Coord(0, 0, 0), firstFloor);
		entrance.generate(editor);
		entrance.setGenerated(true);
		layout.addRoom(entrance, 0);
		layout.generate(editor);
		this.rooms = layout.getRooms();
		
		RogueTower tower = new RogueTower();
		tower.generate(editor, rand, Theme.getTheme(Theme.TOWER), firstFloor);
		
		

	}
	
	@Override
	public Iterator<IRoom> iterator(){
		return this.rooms.iterator();
	}
}
