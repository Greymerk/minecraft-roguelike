package com.greymerk.roguelike.dungeon.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;
import com.greymerk.roguelike.editor.shapes.RectSolid;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;

public abstract class AbstractRoom implements IRoom{
	
	protected Coord floorPos;
	protected Coord worldPos;
	protected ITheme theme;
	protected Cardinal direction;
	protected boolean generated;
	protected List<Cardinal> entrances;
	
	public AbstractRoom() {
		this.direction = Cardinal.DOWN;
		this.generated = false;
		this.entrances = new ArrayList<Cardinal>();
	}
	
	public AbstractRoom(ITheme theme, IBounded box, Coord worldPos) {
		this(theme, box, worldPos, Cardinal.DOWN);
	}
	
	public AbstractRoom(ITheme theme, IBounded box, Coord worldPos, Cardinal dir) {
		this.theme = theme;
		this.worldPos = worldPos;
		this.generated = false;
		this.direction = dir;
		this.entrances = new ArrayList<Cardinal>();
	}
	
	public AbstractRoom(NbtCompound tag) {
		this.theme = Theme.get(tag.getString("theme"));
		this.worldPos = new Coord(tag.getCompound("pos"));
		this.generated = tag.getBoolean("generated");
		this.direction = Cardinal.values()[tag.getInt("dir")];
		this.entrances = new ArrayList<Cardinal>();
		int[] ent = tag.getIntArray("entrances");
		for(int e : ent) {
			Cardinal dir = Cardinal.directions[e];
			this.entrances.add(dir);
		}
	}
	
	@Override
	public NbtCompound getNbt() {
		ITheme theme = this.getTheme();
		Coord pos = this.getWorldPos();
		
		NbtCompound nbt = new NbtCompound();
		nbt.put("type", NbtString.of(getName()));
		nbt.put("theme", NbtString.of(theme.getName()));
		nbt.put("pos", pos.getNbt());
		nbt.putBoolean("generated", this.generated);
		nbt.putInt("dir", Arrays.asList(Cardinal.values()).indexOf(this.direction));
		int[] ent;
		List<Integer> c = new ArrayList<Integer>();
		for(Cardinal dir : this.entrances) {
			c.add(Arrays.asList(Cardinal.directions).indexOf(dir));
		}
		ent = c.stream().mapToInt(Integer::intValue).toArray();
		nbt.putIntArray("entrances", ent);
		return nbt;
	}
	
	public int getSize() {
		return 3;
	}
	
	@Override
	public void setFloorPos(Coord floorPos) {
		this.floorPos = floorPos;
	}
	
	@Override
	public Coord getFloorPos() {
		return new Coord(this.floorPos);
	}
	
	@Override
	public void setWorldPos(Coord worldPos) {
		this.worldPos = worldPos;
	}
	
	@Override
	public Coord getWorldPos() {
		return new Coord(this.worldPos);
	}

	@Override
	public void setTheme(ITheme theme) {
		this.theme = theme;
	}
	
	@Override
	public ITheme getTheme() {
		return this.theme;
	}
	
	@Override
	public IBounded getBoundingBox() {
		Coord start = new Coord(worldPos);
		start.add(Cardinal.DOWN);
		start.add(Cardinal.NORTH, this.getSize());
		start.add(Cardinal.WEST, this.getSize());
		Coord end = new Coord(worldPos);
		end.add(Cardinal.UP, 8);
		end.add(Cardinal.SOUTH, this.getSize());
		end.add(Cardinal.EAST, this.getSize());
		return new BoundingBox(start, end);
	}
	
	@Override
	public void setGenerated(boolean generated) {
		this.generated = generated;
	}
	
	@Override
	public boolean isGenerated() {
		return this.generated;
	}
	
	@Override
	public void setDirection(Cardinal dir) {
		this.direction = dir;
	}
	
	@Override
	public void addEntrance(Cardinal dir) {
		this.entrances.add(dir);
	}
	
	public void clearDoors(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			if(!(this.entrances.contains(dir))) continue;
			door(editor, rand, theme, origin, dir);	
		}
	}
	
	public void door(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		Coord start = new Coord(origin);
		start.add(dir, 3);
		Coord end = new Coord(start);
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 2);
		end.add(Cardinal.UP, 3);
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = new Coord(origin);
			pos.add(dir, 3);
			pos.add(Cardinal.UP, 2);
			pos.add(o, 2);
			IStair stair = theme.getPrimary().getStair();
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, pos);
			
			pos.add(Cardinal.UP);
			theme.getPrimary().getWall().set(editor, rand, pos);
			
			pos.add(Cardinal.reverse(o));
			stair.set(editor, pos);
		}
	}
	
	public void applyFilters(IWorldEditor editor) {
		Random rand = editor.getRandom(this.getWorldPos());
		this.getTheme().applyFilters(editor, rand, getBoundingBox());
	}
}
