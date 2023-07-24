package com.greymerk.roguelike.editor.blocks.door;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;

public class Door implements IDoor {

	MetaBlock block;
	
	public Door(MetaBlock block){
		this.block = block;
	}
	
	public Door(DoorType type){
		this.block = DoorType.get(type);
	}

	@Override
	public void generate(IWorldEditor editor, Coord pos, Cardinal dir) {
		Door.generate(editor, this.block, pos, dir, false);
	}
	
	@Override
	public void generate(IWorldEditor editor, Coord pos, Cardinal dir, boolean open) {
		Door.generate(editor, this.block, pos, dir, open);
	}
	
	public static void generate(IWorldEditor editor, Coord pos, Cardinal dir, DoorType type){
		MetaBlock door = DoorType.get(type);
		generate(editor, door, pos, dir, false);
	}

	public static void generate(IWorldEditor editor, MetaBlock door, Coord pos, Cardinal dir, boolean open){
		Coord cursor = new Coord(pos);
		MetaBlock doorBase = setProperties(door, false, dir, open, false);
		doorBase.set(editor, cursor);
		cursor.add(Cardinal.UP);
		MetaBlock doorTop = setProperties(door, true, dir, open, false);
		doorTop.set(editor, cursor);
	}
	
	private static MetaBlock setProperties(MetaBlock doorblock, boolean top, Cardinal dir, boolean open, boolean hingeLeft){
		
		BlockState door = doorblock.getBlock().getDefaultState();
		door = door.with(DoorBlock.HALF, top ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER);
		door = door.with(DoorBlock.FACING, Cardinal.facing(dir));
		door = door.with(DoorBlock.OPEN, open);
		door = door.with(DoorBlock.HINGE, hingeLeft ? DoorHinge.LEFT : DoorHinge.RIGHT);
		
		return new MetaBlock(door);
	}
	
}
