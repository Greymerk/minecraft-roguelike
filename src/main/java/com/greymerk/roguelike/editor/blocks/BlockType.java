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
		case WATER_STILL: return MetaBlock.of(Blocks.WATER);
		case WATER_FLOWING: return MetaBlock.of(Blocks.WATER);
		case LAVA_STILL: return MetaBlock.of(Blocks.LAVA);
		case LAVA_FLOWING: return MetaBlock.of(Blocks.LAVA);
		case FIRE: return MetaBlock.of(Blocks.FIRE);
		case IRON_BAR: return MetaBlock.of(Blocks.IRON_BARS);
		case STONE_SMOOTH: return MetaBlock.of(Blocks.STONE);
		case GRANITE: return MetaBlock.of(Blocks.GRANITE);
		case GRANITE_POLISHED: return MetaBlock.of(Blocks.POLISHED_GRANITE);
		case DIORITE: return MetaBlock.of(Blocks.DIORITE);
		case DIORITE_POLISHED: return MetaBlock.of(Blocks.POLISHED_DIORITE);
		case ANDESITE: return MetaBlock.of(Blocks.ANDESITE);
		case ANDESITE_POLISHED: return MetaBlock.of(Blocks.POLISHED_ANDESITE);
		case GRASS: return MetaBlock.of(Blocks.GRASS_BLOCK);
		case DIRT: return MetaBlock.of(Blocks.DIRT);
		case DIRT_COARSE: return MetaBlock.of(Blocks.COARSE_DIRT);
		case DIRT_PODZOL: return MetaBlock.of(Blocks.PODZOL);
		case COBBLESTONE: return MetaBlock.of(Blocks.COBBLESTONE);
		case COBBLESTONE_WALL: return MetaBlock.of(Blocks.COBBLESTONE_WALL);
		case BEDROCK: return MetaBlock.of(Blocks.BEDROCK);
		case SAND: return MetaBlock.of(Blocks.SAND);
		case SAND_RED: return MetaBlock.of(Blocks.RED_SAND);
		case GRAVEL: return MetaBlock.of(Blocks.GRAVEL);
		case ORE_GOLD: return MetaBlock.of(Blocks.GOLD_ORE);
		case ORE_IRON: return MetaBlock.of(Blocks.IRON_ORE);
		case GLASS: return MetaBlock.of(Blocks.GLASS);
		case ORE_LAPIS: return MetaBlock.of(Blocks.LAPIS_ORE);
		case LAPIS_BLOCK: return MetaBlock.of(Blocks.LAPIS_BLOCK);
		case ORE_EMERALD: return MetaBlock.of(Blocks.EMERALD_ORE);
		case SANDSTONE: return MetaBlock.of(Blocks.SANDSTONE);
		case SANDSTONE_CHISELED: return MetaBlock.of(Blocks.CHISELED_SANDSTONE);
		case SANDSTONE_SMOOTH: return MetaBlock.of(Blocks.CUT_SANDSTONE);
		case GOLD_BLOCK: return MetaBlock.of(Blocks.GOLD_BLOCK);
		case IRON_BLOCK: return MetaBlock.of(Blocks.IRON_BLOCK);
		case BRICK: return MetaBlock.of(Blocks.BRICKS);
		case COBBLESTONE_MOSSY: return MetaBlock.of(Blocks.MOSSY_COBBLESTONE);
		case OBSIDIAN: return MetaBlock.of(Blocks.OBSIDIAN);
		case ORE_DIAMOND: return MetaBlock.of(Blocks.DIAMOND_ORE);
		case DIAMOND_BLOCK: return MetaBlock.of(Blocks.DIAMOND_BLOCK);
		case FARMLAND: return MetaBlock.of(Blocks.FARMLAND);
		case ORE_REDSTONE: return MetaBlock.of(Blocks.REDSTONE_ORE);
		case ICE: return MetaBlock.of(Blocks.ICE);
		case SNOW: return MetaBlock.of(Blocks.SNOW);
		case CLAY: return MetaBlock.of(Blocks.CLAY);
		case NETHERRACK: return MetaBlock.of(Blocks.NETHERRACK);
		case SOUL_SAND: return MetaBlock.of(Blocks.SOUL_SAND);
		case GLOWSTONE: return MetaBlock.of(Blocks.GLOWSTONE);
		case STONE_BRICK: return MetaBlock.of(Blocks.STONE_BRICKS);
		case STONE_BRICK_MOSSY: return MetaBlock.of(Blocks.MOSSY_STONE_BRICKS);
		case STONE_BRICK_CRACKED: return MetaBlock.of(Blocks.CRACKED_STONE_BRICKS);
		case STONE_BRICK_CHISELED: return MetaBlock.of(Blocks.CHISELED_STONE_BRICKS);
		case MYCELIUM: return MetaBlock.of(Blocks.MYCELIUM);
		case NETHERBRICK: return MetaBlock.of(Blocks.NETHER_BRICKS);
		case END_STONE: return MetaBlock.of(Blocks.END_STONE);
		case EMERALD_BLOCK: return MetaBlock.of(Blocks.EMERALD_BLOCK);
		case ORE_QUARTZ: return MetaBlock.of(Blocks.NETHER_QUARTZ_ORE);
		case PRISMITE: return MetaBlock.of(Blocks.PRISMARINE);
		case PRISMARINE: return MetaBlock.of(Blocks.PRISMARINE_BRICKS);
		case PRISMARINE_DARK: return MetaBlock.of(Blocks.DARK_PRISMARINE);
		case SEA_LANTERN: return MetaBlock.of(Blocks.SEA_LANTERN);
		case COAL_BLOCK: return MetaBlock.of(Blocks.COAL_BLOCK);
		case ICE_PACKED: return MetaBlock.of(Blocks.PACKED_ICE);
		case SANDSTONE_RED: return MetaBlock.of(Blocks.RED_SANDSTONE);
		case SANDSTONE_RED_CHISELED: return MetaBlock.of(Blocks.CHISELED_RED_SANDSTONE);
		case SANDSTONE_RED_SMOOTH: return MetaBlock.of(Blocks.SMOOTH_RED_SANDSTONE);
		case QUARTZ: return MetaBlock.of(Blocks.QUARTZ_BLOCK);
		case REDSTONE_BLOCK: return MetaBlock.of(Blocks.REDSTONE_BLOCK);
		case PRESSURE_PLATE_STONE: return MetaBlock.of(Blocks.STONE_PRESSURE_PLATE);
		case PRESSURE_PLATE_WOODEN: return MetaBlock.of(Blocks.OAK_PRESSURE_PLATE);
		case SHELF: return MetaBlock.of(Blocks.BOOKSHELF);
		case REDSTONE_WIRE: return MetaBlock.of(Blocks.REDSTONE_WIRE);
		case COCAO: return MetaBlock.of(Blocks.COCOA);
		case REEDS: return MetaBlock.of(Blocks.SUGAR_CANE);
		case CRAFTING_TABLE: return MetaBlock.of(Blocks.CRAFTING_TABLE);
		case NOTEBLOCK: return MetaBlock.of(Blocks.NOTE_BLOCK);
		case REDSTONE_LAMP: return MetaBlock.of(Blocks.REDSTONE_LAMP);
		case REDSTONE_LAMP_LIT: return MetaBlock.of(Blocks.REDSTONE_LAMP);
		case JUKEBOX: return MetaBlock.of(Blocks.JUKEBOX);
		case FENCE: return MetaBlock.of(Blocks.OAK_FENCE);
		case TNT: return MetaBlock.of(Blocks.TNT);
		case ENCHANTING_TABLE: return MetaBlock.of(Blocks.ENCHANTING_TABLE);
		case FENCE_NETHER_BRICK: return MetaBlock.of(Blocks.NETHER_BRICK_FENCE);
		case WEB: return MetaBlock.of(Blocks.COBWEB);
		case PUMPKIN_LIT: return MetaBlock.of(Blocks.JACK_O_LANTERN);
		case VINE: return MetaBlock.of(Blocks.VINE);
		case PURPUR_BLOCK: return MetaBlock.of(Blocks.PURPUR_BLOCK);
		case PURPUR_PILLAR: return MetaBlock.of(Blocks.PURPUR_PILLAR);
		case PURPUR_STAIR: return MetaBlock.of(Blocks.PURPUR_STAIRS);
		case PURPUR_SLAB: return MetaBlock.of(Blocks.PURPUR_SLAB);
		case ENDER_BRICK: return MetaBlock.of(Blocks.END_STONE_BRICKS);
		case MAGMA: return MetaBlock.of(Blocks.MAGMA_BLOCK);
		case RED_NETHERBRICK: return MetaBlock.of(Blocks.RED_NETHER_BRICKS);
		case NETHER_WART_BLOCK: return MetaBlock.of(Blocks.NETHER_WART_BLOCK);
		case BONE_BLOCK: return MetaBlock.of(Blocks.BONE_BLOCK);
		default: return MetaBlock.of(Blocks.AIR);
		}
	}
}
