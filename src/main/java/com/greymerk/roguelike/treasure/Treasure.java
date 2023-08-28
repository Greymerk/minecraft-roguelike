package com.greymerk.roguelike.treasure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.random.Random;

public enum Treasure {

	ALL, ARMOUR, WEAPONS, BLOCKS, ENCHANTING, FOOD, ORE, POTIONS,
	STARTER, TOOLS, SUPPLIES, SMITH, MUSIC, REWARD, EMPTY, BREWING;
	
	private static final List<Treasure> common = new ArrayList<Treasure>(Arrays.asList(TOOLS, ARMOUR, WEAPONS));

	public static void generate(IWorldEditor editor, Random rand, Coord pos, Treasure type, int level, boolean trapped){
		try {
			create(editor, rand, pos, type, level, trapped);
		} catch (ChestPlacementException e) {
			// do nothing
		}
	}
	
	public static ITreasureChest create(IWorldEditor editor, Random rand, Coord pos, Treasure type, int level, boolean trapped) throws ChestPlacementException{
		ITreasureChest chest = new TreasureChest(type);
		return chest.generate(editor, rand, pos, level, trapped);
		
	}
	
	public static ITreasureChest create(IWorldEditor editor, Random rand, Coord pos, Treasure type, int level) throws ChestPlacementException{
		return create(editor, rand, pos, type, level, false);
	}
	
	public static List<ITreasureChest> createChests(IWorldEditor editor, Random rand, int numChests, List<Coord> space, int level) throws ChestPlacementException{
		return createChests(editor, rand, numChests, space, level, false);
	}
	
	public static List<ITreasureChest> createChests(IWorldEditor editor, Random rand, int numChests, List<Coord> space, int level, boolean trapped) throws ChestPlacementException{
		
		List<ITreasureChest> chests = new ArrayList<ITreasureChest>();
		
		RandHelper.shuffle(space, rand);
		
		int count = 0;
		
		for (Coord block : space){
			
			if(count == numChests){
				break;
			}
			
			if (isValidChestSpace(editor, block)) {
				create(editor, rand, block, getChestType(rand, level), level);		
			}
		}
		
		return chests;
	}
	
	public static List<ITreasureChest> createChests(IWorldEditor editor, Random rand, int numChests, List<Coord> space, List<Treasure> types, int level) throws ChestPlacementException{
		
		List<ITreasureChest> chests = new ArrayList<ITreasureChest>();
		
		RandHelper.shuffle(space, rand);
		
		int count = 0;
		
		for (Coord block : space){
			
			if(count == numChests){
				return chests;
			}
			
			if (isValidChestSpace(editor, block)) {		
				ITreasureChest chest = create(editor, rand, block, types.get(rand.nextInt(types.size())), level);
				chests.add(chest);
				count++;
			}
		}
		
		return chests;
	}
	
	private static Treasure getChestType(Random rand, int level){		
		return common.get(rand.nextInt(common.size()));
	}

	public static boolean isValidChestSpace(IWorldEditor editor, Coord pos) {

		if (!editor.isAir(pos)) {
			return false;
		}
		
		Coord cursor;
		cursor = new Coord(pos);
		cursor.add(Cardinal.DOWN);
		
		if (!editor.isSolid(cursor)) return false;
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(pos);
			cursor.add(dir);
			if(editor.getBlock(cursor).getBlock() == Blocks.CHEST) return false;
		}
		
		return true;
	}
}
