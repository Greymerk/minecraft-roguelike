package com.greymerk.roguelike.editor.blocks.spawners;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.random.Random;

public enum Spawner {
	
	CREEPER("creeper"),
	CAVESPIDER("cave_spider"),
	SPIDER("spider"),
	SKELETON("skeleton"),
	ZOMBIE("zombie"),
	SILVERFISH("silverfish"),
	ENDERMAN("enderman"),
	WITCH("witch"),
	WITHERBOSS("wither"),
	BAT("bat"),
	LAVASLIME("magma_cube"),
	BLAZE("blaze"),
	SLIME("slime"),
	PRIMEDTNT("tnt"),
	PIGZOMBIE("zombie_pigman");
	
	private String name;
	Spawner(String name){
		this.name = name;
	}
	
	private static final Spawner[] common = {SPIDER, SKELETON, ZOMBIE};
	
	public static String getName(Spawner type) {
		return "minecraft:" + type.name;
	}
	
	public static EntityType<?> getType(Spawner type){
		
		switch(type) {
		case BAT: return EntityType.BAT;
		case BLAZE: return EntityType.BLAZE;
		case CAVESPIDER: return EntityType.CAVE_SPIDER;
		case CREEPER: return EntityType.CREEPER;
		case ENDERMAN: return EntityType.ENDERMAN;
		case LAVASLIME: return EntityType.MAGMA_CUBE;
		case PIGZOMBIE: return EntityType.ZOMBIFIED_PIGLIN;
		case PRIMEDTNT: return EntityType.TNT;
		case SILVERFISH: return EntityType.SILVERFISH;
		case SKELETON: return EntityType.SKELETON;
		case SLIME: return EntityType.SLIME;
		case SPIDER: return EntityType.SPIDER;
		case WITCH: return EntityType.WITCH;
		case WITHERBOSS: return EntityType.WITHER;
		case ZOMBIE: return EntityType.ZOMBIE;
		default: return EntityType.ZOMBIE;
		
		}
	}
	
	public static void generate(IWorldEditor editor, Random rand, Difficulty diff, Coord pos){
		Spawner type = common[rand.nextInt(common.length)];
		generate(editor, rand, diff, pos, type);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Difficulty diff, Coord pos, Spawner type) {
		new Spawnable(type).generate(editor, rand, pos, diff);
	}
}
