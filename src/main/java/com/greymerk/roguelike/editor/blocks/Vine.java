package com.greymerk.roguelike.editor.blocks;

import java.util.Random;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.block.VineBlock;

public class Vine {

	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end){
		for(Coord cursor : new RectSolid(start, end)){
			set(editor, cursor);
		}
	}
	
	public static void set(IWorldEditor editor, Coord origin){
		if(!editor.isAir(origin)) return;
		MetaBlock vine = BlockType.get(BlockType.VINE);
		if(!canPlace(editor, vine, origin)) return;
		setSides(editor, vine, origin);
		vine.set(editor, origin);
	}
	
	public static boolean canPlace(IWorldEditor editor, MetaBlock vine, Coord origin) {
		for(Cardinal dir : Cardinal.values()) {
			Coord pos = new Coord(origin);
			pos.add(dir);
			if(editor.isSolid(pos)) return true;
		}
		return false;
	}
	
	public static MetaBlock setSides(IWorldEditor editor, MetaBlock vine, Coord origin){
		for(Cardinal dir : Cardinal.values()) {
			if(dir == Cardinal.DOWN) continue;
			Coord pos = new Coord(origin);
			pos.add(dir);
			boolean solid = editor.isSolid(pos);
			if(!solid) continue;
			addFace(vine, dir);
		}
		return vine;
	}
	
	public static MetaBlock addFace(MetaBlock vine, Cardinal dir) {
		switch(dir) {
		case NORTH: vine.withProperty(VineBlock.NORTH, true);
		case SOUTH: vine.withProperty(VineBlock.SOUTH, true);
		case EAST: vine.withProperty(VineBlock.EAST, true);
		case WEST: vine.withProperty(VineBlock.WEST, true);
		case UP: vine.withProperty(VineBlock.UP, true);
		default:
		}
		return vine;
	}
}
