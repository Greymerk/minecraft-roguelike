package com.greymerk.roguelike.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.greymerk.roguelike.debug.Debug;
import com.greymerk.roguelike.dungeon.layout.ExclusionZones;
import com.greymerk.roguelike.dungeon.layout.LayoutManager;
import com.greymerk.roguelike.dungeon.room.IRoom;
import com.greymerk.roguelike.dungeon.room.Room;
import com.greymerk.roguelike.dungeon.tower.RogueTower;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.settings.LevelSettings;
import com.greymerk.roguelike.state.RoguelikeState;
import com.greymerk.roguelike.theme.Theme;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.random.Random;

public class Dungeon implements Iterable<IRoom>{

	List<IRoom> rooms;
	Coord origin;
	BoundingBox bb;
	
	public static final Codec<List<IRoom>> LIST_ROOM_CODEC = Codec.list(Room.CODEC);
	
	public static final Codec<Dungeon> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			LIST_ROOM_CODEC.fieldOf("rooms").forGetter(dungeon -> dungeon.rooms),
			Coord.CODEC.fieldOf("pos").forGetter(dungeon -> dungeon.origin),
			BoundingBox.CODEC.fieldOf("bounds").forGetter(dungeon -> dungeon.bb)
		).apply(instance, Dungeon::new)
	);
	
	public static boolean generate(IWorldEditor editor, Coord pos) {
		return generate(editor, pos, false);
	}
	
	public static boolean generate(IWorldEditor editor, Coord pos, boolean force) {
		if(!Dungeon.canSpawn(editor, pos) && !force) return false;
		Dungeon donjon = new Dungeon(pos.copy());
		donjon.generate(editor);
		editor.getInfo().getState().addDungeon(donjon);
		RoguelikeState.flagForGenerationCheck = true;
		return true;
	}
	
	public Dungeon(Coord pos) {
		this.rooms = new ArrayList<IRoom>();
		this.bb = BoundingBox.of(pos.copy());
		this.origin = pos;
	}
	
	public Dungeon(List<IRoom> rooms, Coord origin, BoundingBox bb) {
		this.rooms = rooms;
		this.origin = origin;
		this.bb = bb;
	}

	public static boolean canSpawn(IWorldEditor editor, Coord pos) {
		if(!editor.getInfo().isOverworld()) return false;
		ExclusionZones zones = new ExclusionZones();
		final int SCAN_DISTANCE = 300;
		final int COLLISION_RANGE = 50;
		zones.scan(editor, pos, SCAN_DISTANCE);
		if(zones.collides(pos.withY(0), COLLISION_RANGE)) {
			Debug.info("Dungeon @" + pos.toString() + " failed: nearby trial chamber");
			return false;
		}
		
		Coord surface = editor.findSurface(pos);
		MetaBlock block = editor.getBlock(surface);
		if(!block.isGround()) {
			Debug.info("Dungeon @" + surface.toString() + " failed: " + block.getBlock().toString() + " isn't valid ground");
			return false;
		}
		
		return true;
	}
	
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(origin);
		
		Stopwatch watch = Stopwatch.createStarted();
		
		Coord surface = editor.findSurface(this.origin);
		this.origin = surface.copy().freeze();
		Coord entry = this.origin.withY(editor.getDungeonEntryDepth(origin)).freeze();
		
		LayoutManager layout = new LayoutManager(this.origin, entry, editor.getInfo().getLastFloorDepth());
		
		IRoom entrance = Room.getInstance(Room.ENTRANCE, LevelSettings.fromType(LevelSettings.OAK), Coord.ZERO, entry);
		entrance.generate(editor);
		entrance.setGenerated(true);
		layout.addEntrance(entrance);
		layout.generate(editor);
		this.rooms = layout.getRooms();
		this.rooms.forEach(r -> bb.combine(r.getBoundingBox().get()));
		
		RogueTower tower = new RogueTower();
		tower.generate(editor, rand, Theme.getTheme(Theme.TOWER), entry);
		
		Debug.info("Dungeon @" + surface + " generated layout in: " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
	}
	
	public boolean hasLayout() {
		return this.rooms.size() > 0;
	}
	
	public boolean isGenerated() {
		return this.hasLayout() && this.rooms.stream().allMatch(room -> room.isGenerated());
	}
	
	public IBounded getBounds() {
		return this.bb;
	}
	
	public Coord getPos() {
		return this.origin.copy();
	}
	
	@Override
	public Iterator<IRoom> iterator(){
		return this.rooms.iterator();
	}
}
