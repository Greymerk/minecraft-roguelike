package com.greymerk.roguelike.treasure;

import java.util.Optional;

import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.treasure.chest.ChestPlacementException;
import com.greymerk.roguelike.treasure.chest.ChestType;
import com.greymerk.roguelike.treasure.chest.ITreasureChest;
import com.greymerk.roguelike.treasure.chest.TreasureChest;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

public enum Treasure {

	ALL, EMPTY, STARTER, ARMOR, WEAPON, TOOL, 
	BLOCK, ORE, FOOD, SUPPLY, MUSIC, BREWING;

	public static void generate(IWorldEditor editor, Random rand, Coord pos, Treasure type) {
		Treasure.generate(editor, rand, pos, type, ChestType.CHEST);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord pos, Cardinal dir, Treasure type) {
		Treasure.generate(editor, rand, pos, dir, type, ChestType.CHEST);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord pos, Treasure type, ChestType block){
		try {
			ITreasureChest chest = new TreasureChest(type);
			chest.generate(editor, rand, pos, block);
		} catch (ChestPlacementException e) {
			// do nothing
		}
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord pos, Cardinal dir, Treasure type, ChestType block){
		try {
			ITreasureChest chest = new TreasureChest(type);
			chest.generate(editor, rand, pos, dir, block);
		} catch (ChestPlacementException e) {
			// do nothing
		}
	}
	
	public static Optional<Identifier> getTableIdentifier(Treasure type, Difficulty diff){
		String tier = diff.lt(Difficulty.HARD) ? "1" : "2";
		String path = "chests/";
		
		switch(type) {
		case STARTER: return Optional.of(Identifier.of(Roguelike.MODID, path + "starter"));
		case ARMOR: return Optional.of(Identifier.of(Roguelike.MODID, path + "armor" + tier));
		case BLOCK: return Optional.of(Identifier.of(Roguelike.MODID, path + "block" + tier));
		case BREWING: return Optional.of(Identifier.of(Roguelike.MODID, path + "brewing"));
		case FOOD: return Optional.of(Identifier.of(Roguelike.MODID, path + "food" + tier));
		case MUSIC: return Optional.of(Identifier.of(Roguelike.MODID, path + "music"));
		case ORE: return Optional.of(Identifier.of(Roguelike.MODID, path + "ore" + tier));
		case SUPPLY: return Optional.of(Identifier.of(Roguelike.MODID, path + "supply" + tier));
		case TOOL: return Optional.of(Identifier.of(Roguelike.MODID, path + "tool" + tier));
		case WEAPON: return Optional.of(Identifier.of(Roguelike.MODID, path + "weapon" + tier));
		default: return Optional.empty();
		}
	}
}
