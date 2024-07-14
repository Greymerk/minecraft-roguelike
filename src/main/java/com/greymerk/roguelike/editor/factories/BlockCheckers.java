package com.greymerk.roguelike.editor.factories;

import java.util.function.Predicate;

import com.greymerk.roguelike.editor.BlockContext;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.util.math.random.Random;

public class BlockCheckers extends BlockBase {

	private IBlockFactory fillOne;
	private IBlockFactory fillTwo;
	private Coord offset;
	
	
	public BlockCheckers(IBlockFactory fillOne, IBlockFactory fillTwo, Coord offset){
		this.fillOne = fillOne;
		this.fillTwo = fillTwo;
		this.offset = offset.copy();
	}
	
	public BlockCheckers(IBlockFactory fillOne, IBlockFactory fillTwo){
		this(fillOne, fillTwo, Coord.ZERO);
	}
	
	
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord origin, Predicate<BlockContext> p) {
		
		int x = origin.getX() - this.offset.getX();
		int y = origin.getY() - this.offset.getY();
		int z = origin.getZ() - this.offset.getY();
		
		if (x % 2 == 0) {
			if(z % 2 == 0){
				if(y % 2 == 0){
					return fillOne.set(editor, rand, origin.copy(), p);
				} else {
					return fillTwo.set(editor, rand, origin.copy(), p);
				}
			} else {
				if(y % 2 == 0){
					return fillTwo.set(editor, rand, origin.copy(), p);
				} else {
					return fillOne.set(editor, rand, origin.copy(), p);
				}
			}
		} else {
			if(z % 2 == 0){
				if(y % 2 == 0){
					return fillTwo.set(editor, rand, origin.copy(), p);
				} else {
					return fillOne.set(editor, rand, origin.copy(), p);
				}
			} else {
				if(y % 2 == 0){
					return fillOne.set(editor, rand, origin.copy(), p);
				} else {
					return fillTwo.set(editor, rand, origin.copy(), p);
				}
			}
		}
	}
}
