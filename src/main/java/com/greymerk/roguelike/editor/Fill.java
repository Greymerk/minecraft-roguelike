package com.greymerk.roguelike.editor;

import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

public class Fill {
	
	private static Predicate<Pair<IWorldEditor, Coord>> isAir = (ctx -> {
		return ctx.getLeft().isAir(ctx.getRight());
	});
	
	public static Predicate<Pair<IWorldEditor, Coord>> SUPPORTED = (ctx -> {
		IWorldEditor editor = ctx.getLeft();
		Coord pos = ctx.getRight();
		return editor.isSupported(pos);
	});
	
	public static Predicate<Pair<IWorldEditor, Coord>> ALWAYS = (ctx -> true);
	public static Predicate<Pair<IWorldEditor, Coord>> NEVER = ALWAYS.negate();
	public static Predicate<Pair<IWorldEditor, Coord>> ONLY_AIR = isAir; // fillAir !replaceSolid
	public static Predicate<Pair<IWorldEditor, Coord>> ONLY_SOLID = isAir.negate(); // !fillAir replaceSolid
	
	public static Predicate<Pair<IWorldEditor, Coord>> of(boolean fillAir, boolean replaceSolid) {
		if(!fillAir && !replaceSolid) return NEVER;
		if(!fillAir && replaceSolid) return ONLY_SOLID;
		if(fillAir && !replaceSolid) return ONLY_AIR;
		return ALWAYS;
	}
	
		
}
