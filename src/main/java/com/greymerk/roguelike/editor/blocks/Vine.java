package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.shapes.RectSolid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public class Vine {

	public static void fill(IWorldEditor editor, Random rand, Coord start, Coord end){
		for(Coord cursor : new RectSolid(start, end)){
			set(editor, cursor);
		}
	}
	
	public static void set(IWorldEditor editor, Coord origin){
		if(!canPlace(editor, origin)) return;
		setSides(editor, origin);
	}
	
	public static boolean canPlace(IWorldEditor editor, Coord origin) {
		if(!editor.isAir(origin)) return false;
		for(Cardinal dir : Cardinal.directions) {
			Coord pos = origin.copy();
			pos.add(dir);
			if(editor.isFaceFullSquare(pos, Cardinal.reverse(dir))) return true;
		}
		return false;
	}
	
	public static void setSides(IWorldEditor editor, Coord origin){
		for(Cardinal dir : Cardinal.values()) {
			if(dir == Cardinal.DOWN) return;
			setFace(editor, origin, Cardinal.reverse(dir));
		}
	}
	
	public static void setFace(IWorldEditor editor, Coord origin, Cardinal dir) {
		Coord pos = origin.copy();
		pos.add(dir);
		if(!editor.isFaceFullSquare(pos, dir)) return;
		Direction facing = Cardinal.directions.contains(dir) 
				? Cardinal.facing(Cardinal.reverse(dir))
				: Cardinal.facing(dir);
		BooleanProperty facingProperty = VineBlock.getFacingProperty(facing);
		if(facingProperty == null) return;
		BlockState vine = (BlockState)Blocks.VINE.getDefaultState().with(facingProperty, true);
		editor.set(origin, new MetaBlock(vine));
	}
}
