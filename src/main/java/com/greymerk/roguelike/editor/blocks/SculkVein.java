package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.SculkVeinBlock;
import net.minecraft.util.math.Direction;

public class SculkVein {

	public static boolean set(IWorldEditor editor, Coord origin) {
		if(!canPlace(editor, origin)) return false;
		MetaBlock vein = MetaBlock.of(Blocks.SCULK_VEIN);
		setSides(editor, origin, vein);
		vein.set(editor, origin);
		return true;
	}
	
	public static boolean canPlace(IWorldEditor editor, Coord origin) {
		if(!editor.isAir(origin)) return false;
		for(Cardinal dir : Cardinal.all) {
			if(editor.isFaceFullSquare(origin.copy().add(dir).freeze(), Cardinal.reverse(dir))) return true;
		}
		return false;
	}
	
	public static void setSides(IWorldEditor editor, Coord origin, MetaBlock vein){
		Cardinal.all.forEach(dir -> {
			if(!editor.isFaceFullSquare(origin.copy().add(dir), dir)) return;
			setFace(editor, origin, vein, dir);
		});
	}

	private static void setFace(IWorldEditor editor, Coord origin, MetaBlock vein, Cardinal dir) {
		Direction facing = Cardinal.directions.contains(dir) 
				? Cardinal.facing(Cardinal.reverse(dir))
				: Cardinal.facing(dir);
		vein.withProperty(SculkVeinBlock.getProperty(facing), true);
	}
}
