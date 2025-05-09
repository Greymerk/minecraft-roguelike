package com.greymerk.roguelike.editor;

/* This record is intended for use with Predicates in the world editor
 * Commonly used Predicate<BlockContext> instances are found in the Fill class
 */
public record BlockContext(IWorldEditor editor, Coord pos, MetaBlock block) {
	public static BlockContext of(IWorldEditor editor, Coord pos, MetaBlock block) {
		return new BlockContext(editor, pos, block);
	}
}
