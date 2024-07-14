package com.greymerk.roguelike.editor;

import java.util.function.Predicate;

public class Fill {
	
	public static Predicate<BlockContext> SUPPORTED = (ctx -> {
		IWorldEditor editor = ctx.editor();
		Coord pos = ctx.pos();
		return editor.isSupported(pos);
	});
	
	public static Predicate<BlockContext> IGNORE_BLOCK_ENTITIES = (ctx -> {
		return !ctx.editor().hasBlockEntity(ctx.pos());
	});
	
	public static Predicate<BlockContext> ALWAYS = (ctx -> true);
	public static Predicate<BlockContext> NEVER = ALWAYS.negate();
	public static Predicate<BlockContext> AIR = (ctx -> ctx.editor().isAir(ctx.pos()));
	public static Predicate<BlockContext> SOLID = AIR.negate();

}
