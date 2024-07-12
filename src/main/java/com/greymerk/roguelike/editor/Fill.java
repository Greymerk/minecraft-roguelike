package com.greymerk.roguelike.editor;

import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

public class Fill {
	
	public static Predicate<Pair<IWorldEditor, Coord>> SUPPORTED = (ctx -> {
		IWorldEditor editor = ctx.getLeft();
		Coord pos = ctx.getRight();
		return editor.isSupported(pos);
	});
	
	public static Predicate<Pair<IWorldEditor, Coord>> ALWAYS = (ctx -> true);
	public static Predicate<Pair<IWorldEditor, Coord>> NEVER = ALWAYS.negate();
	public static Predicate<Pair<IWorldEditor, Coord>> ONLY_AIR = (ctx -> ctx.getLeft().isAir(ctx.getRight())); // fillAir !replaceSolid
	public static Predicate<Pair<IWorldEditor, Coord>> ONLY_SOLID = ONLY_AIR.negate(); // !fillAir replaceSolid

}
