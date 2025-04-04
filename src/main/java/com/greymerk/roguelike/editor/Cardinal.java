package com.greymerk.roguelike.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public enum Cardinal {
	NORTH, EAST, WEST, SOUTH, UP, DOWN;
	
	public static final List<Cardinal> all = Arrays.asList(new Cardinal[] {NORTH, EAST, SOUTH, WEST, UP, DOWN});
	public static final List<Cardinal> directions = Arrays.asList(new Cardinal[] {NORTH, EAST, SOUTH, WEST});
	private static final List<String> names = all.stream().map(dir -> dir.name()).collect(Collectors.toList());
	private static final List<String> ordinalStrings = all.stream().map(dir -> Integer.toString(dir.ordinal())).collect(Collectors.toList());
	
	public static int index(Cardinal dir) {
		return Arrays.asList(Cardinal.values()).indexOf(dir);
	}
	
	public static Cardinal of(String dir) {
		if(names.contains(dir)) return Cardinal.valueOf(dir);
		if(ordinalStrings.contains(dir)) return all.get(Integer.parseInt(dir));
		return NORTH;
	}

	public static Cardinal of(Direction direction) {
		switch(direction) {
		case DOWN: return Cardinal.DOWN;
		case EAST: return Cardinal.WEST;
		case NORTH: return Cardinal.SOUTH;
		case SOUTH: return Cardinal.NORTH;
		case UP: return Cardinal.UP;
		case WEST: return Cardinal.EAST;
		default: return null;
		}
	}
	
	public static Cardinal get(String name) {
		switch(name) {
		case "NORTH" : return NORTH;
		case "EAST" : return EAST;
		case "WEST" : return WEST;
		case "SOUTH" : return SOUTH;
		case "UP" : return UP;
		case "DOWN" : return DOWN;
		default: return NORTH;
		}
	}
	
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
	
	public static List<Cardinal> parallel(Cardinal dir){
		switch(dir){
		case NORTH: return List.of(NORTH, SOUTH);
		case SOUTH: return List.of(SOUTH, NORTH);
		case EAST: return List.of(EAST, WEST);
		case WEST: return List.of(WEST, EAST);
		default: return List.of(dir, dir);
		}
	}
	
	public static List<Cardinal> orthogonal(Cardinal dir) {
		
		switch(dir){
		case NORTH: return List.of(WEST, EAST);
		case SOUTH: return List.of(WEST, EAST);
		case EAST: return List.of(NORTH, SOUTH);
		case WEST: return List.of(NORTH, SOUTH);
		default: return List.of(dir, dir);
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

	public static Direction.Axis axis(Cardinal dir){
		switch(dir) {
		case NORTH: return Direction.Axis.Z;
		case EAST: return Direction.Axis.X;
		case WEST: return Direction.Axis.X;
		case SOUTH: return Direction.Axis.Z;
		case UP: return Direction.Axis.Y;
		case DOWN: return Direction.Axis.Y;
		default: return Direction.Axis.Y;
		}
	}
	
	public static List<Cardinal> randDirs(Random rand){
		List<Cardinal> dirs = new ArrayList<Cardinal>(directions);
		RandHelper.shuffle(dirs, rand);
		return dirs;
	}

}
