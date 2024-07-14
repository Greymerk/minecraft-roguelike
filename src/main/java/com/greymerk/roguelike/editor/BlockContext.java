package com.greymerk.roguelike.editor;

public class BlockContext {
	
	/* This class is intended for use with Predicates in the world editor
	 * Commonly used Predicate<BlockContext> instances are found in the Fill class
	 */
	
	private IWorldEditor editor;
	private Coord pos;
	private MetaBlock block;
	
	public static BlockContext of(IWorldEditor editor, Coord pos, MetaBlock block) {
		return new BlockContext(editor, pos, block);
	}
	
	private BlockContext(IWorldEditor editor, Coord pos, MetaBlock block) {
		this.editor = editor;
		this.pos = pos;
		this.block = block;
	}
	
	public IWorldEditor editor() {
		return this.editor;
	}
	
	public Coord pos() {
		return this.pos;
	}
	
	public MetaBlock block() {
		return this.block;
	}
}
