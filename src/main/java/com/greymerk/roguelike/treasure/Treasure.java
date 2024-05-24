package com.greymerk.roguelike.treasure;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.treasure.chest.ChestPlacementException;
import com.greymerk.roguelike.treasure.chest.ChestType;
import com.greymerk.roguelike.treasure.chest.ITreasureChest;
import com.greymerk.roguelike.treasure.chest.TreasureChest;

import net.minecraft.util.math.random.Random;

public enum Treasure {

	ALL, ARMOUR, WEAPONS, BLOCKS, ENCHANTING, FOOD, ORE, POTIONS,
	STARTER, TOOLS, SUPPLIES, MUSIC, REWARD, EMPTY, BREWING;

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
}
