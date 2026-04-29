package com.greymerk.roguelike.editor.shapes;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.RandomSource;
import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

public interface IShape extends Iterable<Coord>{

	public void fill(IWorldEditor editor, RandomSource rand, IBlockFactory block);
	
	public void fill(IWorldEditor editor, RandomSource rand, IBlockFactory block, Predicate<BlockContext> p);
	
	public List<Coord> get();	
}
