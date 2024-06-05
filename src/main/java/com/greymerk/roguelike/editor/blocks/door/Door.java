package com.greymerk.roguelike.editor.blocks.door;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;

public class Door implements IDoor {

	DoorType type;
	
	public static Door of(DoorType type) {
		return new Door(type);
	}
	
	private Door(DoorType type){
		this.type = type;
	}

	@Override
	public void generate(IWorldEditor editor, Coord pos, Cardinal dir) {
		Door.generate(editor, this.type, pos, dir, false);
	}
	
	@Override
	public void generate(IWorldEditor editor, Coord pos, Cardinal dir, boolean open) {
		Door.generate(editor, this.type, pos, dir, open);
	}
	
	public static void generate(IWorldEditor editor, Coord pos, Cardinal dir, DoorType type){
		generate(editor, type, pos, dir, false);
	}

	public static void generate(IWorldEditor editor, DoorType type, Coord pos, Cardinal dir, boolean open){
		Coord cursor = pos.copy();
		MetaBlock doorBase = setProperties(type, false, dir, open, false);
		doorBase.set(editor, cursor);
		cursor.add(Cardinal.UP);
		MetaBlock doorTop = setProperties(type, true, dir, open, false);
		doorTop.set(editor, cursor);
	}
	
	private static MetaBlock setProperties(DoorType type, boolean top, Cardinal dir, boolean open, boolean hingeLeft){
		return DoorType.get(type)
				.withProperty(DoorBlock.HALF, top ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER)
				.withProperty(DoorBlock.FACING, Cardinal.facing(dir))
				.withProperty(DoorBlock.OPEN, open)
				.withProperty(DoorBlock.HINGE, hingeLeft ? DoorHinge.LEFT : DoorHinge.RIGHT);
		
		
	}
	
}
