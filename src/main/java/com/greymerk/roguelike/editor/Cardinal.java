package com.greymerk.roguelike.editor;

import net.minecraft.util.math.Direction;

public enum Cardinal {
	NORTH, EAST, WEST, SOUTH, UP, DOWN;
	
	public static Cardinal[] directions = {NORTH, EAST, SOUTH, WEST};
	
	public static Cardinal reverse(Cardinal dir){
		switch(dir){
		case NORTH: return SOUTH;
		case EAST: return WEST;
		case WEST: return EAST;
		case SOUTH: return NORTH;
		case DOWN: return UP;
		case UP: return DOWN;
		default: return null;
		}
	}

	public static Cardinal left(Cardinal dir){
		switch(dir){
		case NORTH: return WEST;
		case EAST: return NORTH;
		case SOUTH: return EAST;
		case WEST: return SOUTH;
		default: return dir;
		}
	}

	public static Cardinal right(Cardinal dir){
		switch(dir){
		case NORTH: return EAST;
		case EAST: return SOUTH;
		case SOUTH: return WEST;
		case WEST: return NORTH;
		default: return dir;
		}
	}
	
	public static Cardinal[] orthogonal(Cardinal dir) {
		
		switch(dir){
		case NORTH: return new Cardinal[] {WEST, EAST};
		case SOUTH: return new Cardinal[] {EAST, WEST};
		case EAST: return new Cardinal[] {NORTH, SOUTH};
		case WEST: return new Cardinal[] {SOUTH, NORTH};
		default: return new Cardinal[]{dir, dir};
		}
	}

	public static Direction facing(Cardinal dir){
		
		switch(dir){
		case NORTH: return Direction.SOUTH;
		case EAST: return Direction.WEST;
		case WEST: return Direction.EAST;
		case SOUTH: return Direction.NORTH;
		case UP: return Direction.UP;
		case DOWN: return Direction.DOWN;
		default: return null;
		}
	}

	/*
	
	public static EnumOrientation orientation(Cardinal dir){
		
		switch(dir){
		case NORTH: return EnumOrientation.SOUTH;
		case EAST: return EnumOrientation.WEST;
		case WEST: return EnumOrientation.EAST;
		case SOUTH: return EnumOrientation.NORTH;
		case UP: return EnumOrientation.UP_X;
		case DOWN: return EnumOrientation.DOWN_X;
		default: return null;
		}
	}
	*/
}
