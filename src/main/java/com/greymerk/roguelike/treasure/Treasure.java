package com.greymerk.roguelike.treasure;

import java.util.Optional;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.treasure.chest.ChestType;
import com.greymerk.roguelike.treasure.chest.ITreasureChest;
import com.greymerk.roguelike.treasure.chest.TreasureChest;

import net.minecraft.util.math.random.Random;

public enum Treasure {

	ALL, EMPTY, STARTER, ARMOUR, WEAPONS, TOOLS, 
	BLOCKS, ORE, FOOD, SUPPLIES, MUSIC, BREWING;

	public static Optional<ITreasureChest> generate(IWorldEditor editor, Random rand, Coord pos, Treasure type) {
		return Treasure.generate(editor, rand, pos, type, ChestType.CHEST);
	}
	
	public static Optional<ITreasureChest> generate(IWorldEditor editor, Random rand, Coord pos, Cardinal dir, Treasure type) {
		return Treasure.generate(editor, rand, pos, dir, type, ChestType.CHEST);
	}
	
	public static Optional<ITreasureChest> generate(IWorldEditor editor, Random rand, Coord pos, Treasure type, ChestType block){
		return TreasureChest.generate(editor, rand, pos, type, block);
	}
	
	public static Optional<ITreasureChest> generate(IWorldEditor editor, Random rand, Coord pos, Cardinal dir, Treasure type, ChestType block){
		return TreasureChest.generate(editor, rand, pos, dir, type, block);
	}
}
