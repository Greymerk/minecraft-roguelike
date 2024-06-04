package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;

public enum BlockType {

	WATER_STILL, WATER_FLOWING, LAVA_STILL, LAVA_FLOWING, FIRE, IRON_BAR,
	STONE_SMOOTH, GRANITE, GRANITE_POLISHED, DIORITE, DIORITE_POLISHED, ANDESITE, 
	ANDESITE_POLISHED, GRASS, DIRT, DIRT_COARSE, DIRT_PODZOL, COBBLESTONE, COBBLESTONE_WALL, BEDROCK,
	SAND, SAND_RED, GRAVEL, ORE_GOLD, ORE_IRON, ORE_COAL, GLASS, ORE_LAPIS, ORE_EMERALD, LAPIS_BLOCK,
	SANDSTONE, SANDSTONE_CHISELED, SANDSTONE_SMOOTH, GOLD_BLOCK, IRON_BLOCK,
	BRICK, COBBLESTONE_MOSSY, OBSIDIAN, ORE_DIAMOND, DIAMOND_BLOCK, FARMLAND, 
	ORE_REDSTONE, ICE, SNOW, CLAY, NETHERRACK, SOUL_SAND, GLOWSTONE,
	STONE_BRICK, STONE_BRICK_MOSSY, STONE_BRICK_CRACKED, STONE_BRICK_CHISELED,
	MYCELIUM, NETHERBRICK, END_STONE, EMERALD_BLOCK, ORE_QUARTZ,
	PRISMITE, PRISMARINE, PRISMARINE_DARK, SEA_LANTERN, COAL_BLOCK, ICE_PACKED,
	SANDSTONE_RED, SANDSTONE_RED_CHISELED, SANDSTONE_RED_SMOOTH,
	QUARTZ, REDSTONE_BLOCK, PRESSURE_PLATE_STONE, PRESSURE_PLATE_WOODEN, SHELF, REDSTONE_WIRE,
	COCAO, REEDS, CRAFTING_TABLE, NOTEBLOCK, REDSTONE_LAMP, REDSTONE_LAMP_LIT, JUKEBOX, FENCE,
	TNT, ENCHANTING_TABLE, FENCE_NETHER_BRICK, WEB, PUMPKIN_LIT, VINE,
	PURPUR_BLOCK, PURPUR_PILLAR, PURPUR_STAIR, PURPUR_DOUBLE_SLAB, PURPUR_SLAB, ENDER_BRICK,
	MAGMA, RED_NETHERBRICK, NETHER_WART_BLOCK, BONE_BLOCK,
	;
	
	public static MetaBlock get(BlockType type){
		
		switch(type){
		case WATER_STILL: return new MetaBlock(Blocks.WATER);
		case WATER_FLOWING: return new MetaBlock(Blocks.WATER);
		case LAVA_STILL: return new MetaBlock(Blocks.LAVA);
		case LAVA_FLOWING: return new MetaBlock(Blocks.LAVA);
		case FIRE: return new MetaBlock(Blocks.FIRE);
		case IRON_BAR: return new MetaBlock(Blocks.IRON_BARS);
		case STONE_SMOOTH: return new MetaBlock(Blocks.STONE);
		case GRANITE: return new MetaBlock(Blocks.GRANITE);
		case GRANITE_POLISHED: return new MetaBlock(Blocks.POLISHED_GRANITE);
		case DIORITE: return new MetaBlock(Blocks.DIORITE);
		case DIORITE_POLISHED: return new MetaBlock(Blocks.POLISHED_DIORITE);
		case ANDESITE: return new MetaBlock(Blocks.ANDESITE);
		case ANDESITE_POLISHED: return new MetaBlock(Blocks.POLISHED_ANDESITE);
		case GRASS: return new MetaBlock(Blocks.GRASS_BLOCK);
		case DIRT: return new MetaBlock(Blocks.DIRT);
		case DIRT_COARSE: return new MetaBlock(Blocks.COARSE_DIRT);
		case DIRT_PODZOL: return new MetaBlock(Blocks.PODZOL);
		case COBBLESTONE: return new MetaBlock(Blocks.COBBLESTONE);
		case COBBLESTONE_WALL: return new MetaBlock(Blocks.COBBLESTONE_WALL);
		case BEDROCK: return new MetaBlock(Blocks.BEDROCK);
		case SAND: return new MetaBlock(Blocks.SAND);
		case SAND_RED: return new MetaBlock(Blocks.RED_SAND);
		case GRAVEL: return new MetaBlock(Blocks.GRAVEL);
		case ORE_GOLD: return new MetaBlock(Blocks.GOLD_ORE);
		case ORE_IRON: return new MetaBlock(Blocks.IRON_ORE);
		case GLASS: return new MetaBlock(Blocks.GLASS);
		case ORE_LAPIS: return new MetaBlock(Blocks.LAPIS_ORE);
		case LAPIS_BLOCK: return new MetaBlock(Blocks.LAPIS_BLOCK);
		case ORE_EMERALD: return new MetaBlock(Blocks.EMERALD_ORE);
		case SANDSTONE: return new MetaBlock(Blocks.SANDSTONE);
		case SANDSTONE_CHISELED: return new MetaBlock(Blocks.CHISELED_SANDSTONE);
		case SANDSTONE_SMOOTH: return new MetaBlock(Blocks.CUT_SANDSTONE);
		case GOLD_BLOCK: return new MetaBlock(Blocks.GOLD_BLOCK);
		case IRON_BLOCK: return new MetaBlock(Blocks.IRON_BLOCK);
		case BRICK: return new MetaBlock(Blocks.BRICKS);
		case COBBLESTONE_MOSSY: return new MetaBlock(Blocks.MOSSY_COBBLESTONE);
		case OBSIDIAN: return new MetaBlock(Blocks.OBSIDIAN);
		case ORE_DIAMOND: return new MetaBlock(Blocks.DIAMOND_ORE);
		case DIAMOND_BLOCK: return new MetaBlock(Blocks.DIAMOND_BLOCK);
		case FARMLAND: return new MetaBlock(Blocks.FARMLAND);
		case ORE_REDSTONE: return new MetaBlock(Blocks.REDSTONE_ORE);
		case ICE: return new MetaBlock(Blocks.ICE);
		case SNOW: return new MetaBlock(Blocks.SNOW);
		case CLAY: return new MetaBlock(Blocks.CLAY);
		case NETHERRACK: return new MetaBlock(Blocks.NETHERRACK);
		case SOUL_SAND: return new MetaBlock(Blocks.SOUL_SAND);
		case GLOWSTONE: return new MetaBlock(Blocks.GLOWSTONE);
		case STONE_BRICK: return new MetaBlock(Blocks.STONE_BRICKS);
		case STONE_BRICK_MOSSY: return new MetaBlock(Blocks.MOSSY_STONE_BRICKS);
		case STONE_BRICK_CRACKED: return new MetaBlock(Blocks.CRACKED_STONE_BRICKS);
		case STONE_BRICK_CHISELED: return new MetaBlock(Blocks.CHISELED_STONE_BRICKS);
		case MYCELIUM: return new MetaBlock(Blocks.MYCELIUM);
		case NETHERBRICK: return new MetaBlock(Blocks.NETHER_BRICKS);
		case END_STONE: return new MetaBlock(Blocks.END_STONE);
		case EMERALD_BLOCK: return new MetaBlock(Blocks.EMERALD_BLOCK);
		case ORE_QUARTZ: return new MetaBlock(Blocks.NETHER_QUARTZ_ORE);
		case PRISMITE: return new MetaBlock(Blocks.PRISMARINE);
		case PRISMARINE: return new MetaBlock(Blocks.PRISMARINE_BRICKS);
		case PRISMARINE_DARK: return new MetaBlock(Blocks.DARK_PRISMARINE);
		case SEA_LANTERN: return new MetaBlock(Blocks.SEA_LANTERN);
		case COAL_BLOCK: return new MetaBlock(Blocks.COAL_BLOCK);
		case ICE_PACKED: return new MetaBlock(Blocks.PACKED_ICE);
		case SANDSTONE_RED: return new MetaBlock(Blocks.RED_SANDSTONE);
		case SANDSTONE_RED_CHISELED: return new MetaBlock(Blocks.CHISELED_RED_SANDSTONE);
		case SANDSTONE_RED_SMOOTH: return new MetaBlock(Blocks.SMOOTH_RED_SANDSTONE);
		case QUARTZ: return new MetaBlock(Blocks.QUARTZ_BLOCK);
		case REDSTONE_BLOCK: return new MetaBlock(Blocks.REDSTONE_BLOCK);
		case PRESSURE_PLATE_STONE: return new MetaBlock(Blocks.STONE_PRESSURE_PLATE);
		case PRESSURE_PLATE_WOODEN: return new MetaBlock(Blocks.OAK_PRESSURE_PLATE);
		case SHELF: return new MetaBlock(Blocks.BOOKSHELF);
		case REDSTONE_WIRE: return new MetaBlock(Blocks.REDSTONE_WIRE);
		case COCAO: return new MetaBlock(Blocks.COCOA);
		case REEDS: return new MetaBlock(Blocks.SUGAR_CANE);
		case CRAFTING_TABLE: return new MetaBlock(Blocks.CRAFTING_TABLE);
		case NOTEBLOCK: return new MetaBlock(Blocks.NOTE_BLOCK);
		case REDSTONE_LAMP: return new MetaBlock(Blocks.REDSTONE_LAMP);
		case REDSTONE_LAMP_LIT: return new MetaBlock(Blocks.REDSTONE_LAMP);
		case JUKEBOX: return new MetaBlock(Blocks.JUKEBOX);
		case FENCE: return new MetaBlock(Blocks.OAK_FENCE);
		case TNT: return new MetaBlock(Blocks.TNT);
		case ENCHANTING_TABLE: return new MetaBlock(Blocks.ENCHANTING_TABLE);
		case FENCE_NETHER_BRICK: return new MetaBlock(Blocks.NETHER_BRICK_FENCE);
		case WEB: return new MetaBlock(Blocks.COBWEB);
		case PUMPKIN_LIT: return new MetaBlock(Blocks.JACK_O_LANTERN);
		case VINE: return new MetaBlock(Blocks.VINE);
		case PURPUR_BLOCK: return new MetaBlock(Blocks.PURPUR_BLOCK);
		case PURPUR_PILLAR: return new MetaBlock(Blocks.PURPUR_PILLAR);
		case PURPUR_STAIR: return new MetaBlock(Blocks.PURPUR_STAIRS);
		case PURPUR_SLAB: return new MetaBlock(Blocks.PURPUR_SLAB);
		case ENDER_BRICK: return new MetaBlock(Blocks.END_STONE_BRICKS);
		case MAGMA: return new MetaBlock(Blocks.MAGMA_BLOCK);
		case RED_NETHERBRICK: return new MetaBlock(Blocks.RED_NETHER_BRICKS);
		case NETHER_WART_BLOCK: return new MetaBlock(Blocks.NETHER_WART_BLOCK);
		case BONE_BLOCK: return new MetaBlock(Blocks.BONE_BLOCK);
		default: return new MetaBlock(Blocks.AIR);
		}
	}
}
