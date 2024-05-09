package com.greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.greymerk.roguelike.dungeon.layout.LayoutManager;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.tower.RogueTower;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.settings.LevelSettings;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.random.Random;

public class Dungeon implements Iterable<IRoom>{

	List<IRoom> rooms;
	Coord origin;
	BoundingBox bb;
	
	public static void generate(IWorldEditor editor, Coord pos) {
		Dungeon donjon = new Dungeon(pos.copy());
		donjon.generate(editor);
		editor.getState().addDungeon(donjon);
	}
	
	public Dungeon(Coord pos) {
		this.rooms = new ArrayList<IRoom>();
		this.bb = new BoundingBox(pos.copy());
		this.origin = pos;
	}
	
	public Dungeon(NbtCompound tag) {
		this.rooms = new ArrayList<IRoom>();
		NbtList rooms = tag.getList("rooms", NbtElement.COMPOUND_TYPE);
		for(NbtElement nbt : rooms) {
			this.rooms.add(Room.createFromNBT((NbtCompound)nbt));
		}
		this.origin = Coord.of(tag.getCompound("pos"));
		this.bb = new BoundingBox(tag.getCompound("bounds"));
	}

	public static boolean canSpawn(IWorldEditor editor, Coord pos) {
		if(!editor.isOverworld()) return false;
		
		Coord surface = editor.findSurface(pos);
		if(!editor.isGround(surface)) return false;
		return true;
	}
	
	public NbtCompound getNbt() {
		NbtCompound data = new NbtCompound();
		NbtList roomlist = new NbtList();
		for(IRoom room : this.rooms) {
			roomlist.add(room.getNbt());
		}
		data.put("rooms", roomlist);
		data.put("pos", this.origin.getNbt());
		data.put("bounds", this.bb.getNbt());
		return data;
	}
	
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(origin);
		
		Coord surface = editor.findSurface(this.origin);
		int entranceY = (surface.getY() - surface.getY() % 10) - 10;
		Coord firstFloor = new Coord(this.origin.getX(), entranceY, this.origin.getZ());

		LayoutManager layout = new LayoutManager(firstFloor);
		IRoom entrance = Room.getInstance(Room.ENTRANCE, LevelSettings.fromType(LevelSettings.OAK), new Coord(0, 0, 0), firstFloor);
		entrance.generate(editor);
		entrance.setGenerated(true);
		layout.addEntrance(entrance);
		layout.generate(editor);
		this.rooms = layout.getRooms();
		this.rooms.forEach(r -> bb.combine(r.getBoundingBox()));
		
		RogueTower tower = new RogueTower();
		tower.generate(editor, rand, Theme.getTheme(Theme.TOWER), firstFloor);
	}
	
	public boolean isGenerated() {
		for(IRoom room : this.rooms) {
			if(!room.isGenerated()) return false;
		}
		return true;
	}
	
	public IBounded getBounds() {
		return this.bb;
	}
	
	public static int getLevelFromY(int y) {
		if(y >= 60) return 0;
		return Math.abs((int)(y / 10 - 6));
	}
	
	@Override
	public Iterator<IRoom> iterator(){
		return this.rooms.iterator();
	}
}
