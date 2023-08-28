package com.greymerk.roguelike.editor.blocks;

import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SkullBlock;

public enum Skull {

	SKELETON, WITHER, ZOMBIE, STEVE, CREEPER;
	
	public static void set(IWorldEditor editor, Random rand, Coord origin, Cardinal dir, Skull type){
		MetaBlock skullBlock = new MetaBlock(Skull.fromType(type));
		setRotation(rand, skullBlock, dir);
		if(!skullBlock.set(editor, origin)) return;
	}
	
	public static void setRotation(Random rand, MetaBlock skull, Cardinal dir){
		int directionValue = getDirectionValue(dir);
		
		// nudge the skull so that it isn't perfectly aligned.
		directionValue += -1 + rand.nextInt(3);
		
		// make sure the skull direction value is less than 16
		directionValue = directionValue % 16;
		
		skull.withProperty(SkullBlock.ROTATION, directionValue);
	}
	
	public static Block fromType(Skull type){
		switch(type){
		case SKELETON: return Blocks.SKELETON_SKULL;
		case WITHER: return Blocks.WITHER_SKELETON_SKULL;
		case ZOMBIE: return Blocks.ZOMBIE_HEAD;
		case STEVE: return Blocks.PLAYER_HEAD;
		case CREEPER: return Blocks.CREEPER_HEAD;
		default: return Blocks.SKELETON_SKULL;
		}
	}
	
	public static int getDirectionValue(Cardinal dir){
		switch(dir){
		case NORTH: return 0;
		case EAST: return 4;
		case SOUTH: return 8;
		case WEST: return 12;
		default: return 0;
		}
	}	
}
