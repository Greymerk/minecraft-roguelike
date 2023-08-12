package com.greymerk.roguelike.dungeon.room;

import java.util.Arrays;
import java.util.Random;

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
	protected IBounded box;
	protected Cardinal direction;
	protected boolean generated;
	
	public AbstractRoom(ITheme theme, Coord floorPos, Coord worldPos) {
		this(theme, floorPos, worldPos, Cardinal.DOWN);
	}
	
	public AbstractRoom(ITheme theme, Coord floorPos, Coord worldPos, Cardinal dir) {
		this.floorPos = floorPos;
		this.worldPos = worldPos;
		this.theme = theme;
		this.box = createBox(3);
		this.generated = false;
		this.direction = dir;
	}
	
	public AbstractRoom(ITheme theme, IBounded box, Coord worldPos) {
		this(theme, box, worldPos, Cardinal.DOWN);
	}
	
	public AbstractRoom(ITheme theme, IBounded box, Coord worldPos, Cardinal dir) {
		this.theme = theme;
		this.box = box;
		this.worldPos = worldPos;
		this.generated = false;
		this.direction = dir;
	}
	
	public AbstractRoom(NbtCompound tag) {
		this.theme = Theme.get(tag.getString("theme"));
		this.box = new BoundingBox(tag.getCompound("box")); 
		this.worldPos = new Coord(tag.getCompound("pos"));
		this.generated = tag.getBoolean("generated");
		this.direction = Cardinal.directions[tag.getInt("dir")];
	}
	
	@Override
	public NbtCompound getNbt() {
		ITheme theme = this.getTheme();
		Coord pos = this.getWorldPos();
		IBounded box = this.getBoundingBox();
		
		NbtCompound nbt = new NbtCompound();
		nbt.put("type", NbtString.of(getName()));
		nbt.put("theme", NbtString.of(theme.getName()));
		nbt.put("pos", pos.getNbt());
		nbt.put("box", box.getNbt());
		nbt.putBoolean("generated", this.generated);
		nbt.putInt("dir", Arrays.asList(Cardinal.directions).indexOf(this.direction));
		return nbt;
	}
	
	public IBounded createBox(int size) {
		Coord start = new Coord(worldPos);
		start.add(Cardinal.DOWN);
		start.add(Cardinal.NORTH, size);
		start.add(Cardinal.WEST, size);
		Coord end = new Coord(worldPos);
		end.add(Cardinal.UP, 10);
		end.add(Cardinal.SOUTH, size);
		end.add(Cardinal.EAST, size);
		return new BoundingBox(start, end);
	}
	
	public int getSize() {
		return 3;
	}
	
	@Override
	public Coord getFloorPos() {
		return new Coord(this.floorPos);
	}
	
	@Override
	public Coord getWorldPos() {
		return new Coord(this.worldPos);
	}

	@Override
	public ITheme getTheme() {
		return this.theme;
	}

	@Override
	public IBounded getBoundingBox() {
		return this.box;
	}
	
	public void setGenerated(boolean generated) {
		this.generated = generated;
	}
	
	public boolean isGenerated() {
		return this.generated;
	}
	
	public void clearDoors(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {
		for(Cardinal dir : Cardinal.directions) {
			door(editor, rand, theme, origin, dir);	
		}
	}
	
	public void door(IWorldEditor editor, Random rand, ITheme theme, Coord origin, Cardinal dir) {
		Coord start = new Coord(origin);
		start.add(dir, 4);
		Coord end = new Coord(start);
		start.add(Cardinal.left(dir), 1);
		end.add(Cardinal.right(dir), 1);
		end.add(Cardinal.UP);
		RectSolid r = new RectSolid(start, end);
		for(Coord c : r) {
			if(!editor.isAir(c)) return;
		}
		
		start.add(Cardinal.reverse(dir));
		start.add(Cardinal.left(dir));
		end.add(Cardinal.reverse(dir));
		end.add(Cardinal.right(dir));
		end.add(Cardinal.UP, 2);
		RectSolid.fill(editor, rand, start, end, BlockType.get(BlockType.AIR));
		
		for(Cardinal o : Cardinal.orthogonal(dir)) {
			Coord pos = new Coord(origin);
			pos.add(dir, 3);
			pos.add(Cardinal.UP, 3);
			pos.add(o, 2);
			IStair stair = theme.getPrimary().getStair();
			stair.setOrientation(Cardinal.reverse(o), true);
			stair.set(editor, pos);
		}
	}
}
