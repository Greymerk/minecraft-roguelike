package com.greymerk.roguelike.editor.blocks;

import java.util.Arrays;
import java.util.List;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public enum FlowerPot {

	DANDELION, POPPY, ORCHID, ALLIUM, BLUET, REDTULIP, 
	ORANGETULIP, WHITETULIP, PINKTULIP, DAISY, LILY, 
	REDMUSHROOM, BROWNMUSHROOM,
	OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK,
	MANGROVE, AZALEA, AZALEA_FLOWERING, CHERRY,
	SHRUB, FERN, CACTUS, BAMBOO, WITHER,
	CRIMSON_FUNGUS, WARPED_FUNGUS, CRIMSON_ROOTS, WARPED_ROOTS;

	public static List<FlowerPot> mushrooms = List.of(
			REDMUSHROOM, BROWNMUSHROOM, CRIMSON_FUNGUS, WARPED_FUNGUS, CRIMSON_ROOTS, WARPED_ROOTS, WITHER, SHRUB);
	
	public static List<FlowerPot> saplings = List.of(
			OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK, MANGROVE, AZALEA, CHERRY);
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin){
		generate(editor, rand, origin, Arrays.asList(FlowerPot.values()));
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin, List<FlowerPot> types) {
		generate(editor, origin, types.get(rand.nextInt(types.size())));
	}
	
	public static void generate(IWorldEditor editor, Coord origin, FlowerPot type){
		MetaBlock.of(FlowerPot.getFlowerPot(type)).set(editor, origin);
	}
	
	public static Block getFlowerPot(FlowerPot type) {
		switch(type){
		case DANDELION: return Blocks.POTTED_DANDELION;
		case POPPY: return Blocks.POTTED_POPPY;
		case ORCHID: return Blocks.POTTED_BLUE_ORCHID;
		case ALLIUM: return Blocks.POTTED_ALLIUM;
		case BLUET: return Blocks.POTTED_AZURE_BLUET;
		case REDTULIP: return Blocks.POTTED_RED_TULIP;
		case ORANGETULIP: return Blocks.POTTED_ORANGE_TULIP;
		case WHITETULIP: return Blocks.POTTED_WHITE_TULIP;
		case PINKTULIP: return Blocks.POTTED_PINK_TULIP;
		case DAISY: return Blocks.POTTED_OXEYE_DAISY;
		case LILY: return Blocks.POTTED_LILY_OF_THE_VALLEY;
		case REDMUSHROOM: return Blocks.POTTED_RED_MUSHROOM;
		case BROWNMUSHROOM: return Blocks.POTTED_BROWN_MUSHROOM;
		case CACTUS: return Blocks.POTTED_CACTUS;
		case BAMBOO: return Blocks.POTTED_BAMBOO;
		case OAK: return Blocks.POTTED_OAK_SAPLING;
		case SPRUCE: return Blocks.POTTED_SPRUCE_SAPLING;
		case BIRCH: return Blocks.POTTED_BIRCH_SAPLING;
		case JUNGLE: return Blocks.POTTED_JUNGLE_SAPLING;
		case ACACIA: return Blocks.POTTED_ACACIA_SAPLING;
		case DARKOAK: return Blocks.POTTED_DARK_OAK_SAPLING;
		case CHERRY: return Blocks.POTTED_CHERRY_SAPLING;
		case SHRUB: return Blocks.POTTED_DEAD_BUSH;
		case FERN: return Blocks.POTTED_FERN;
		case AZALEA: return Blocks.POTTED_AZALEA_BUSH;
		case AZALEA_FLOWERING: return Blocks.POTTED_FLOWERING_AZALEA_BUSH;
		case CRIMSON_FUNGUS: return Blocks.POTTED_CRIMSON_FUNGUS;
		case CRIMSON_ROOTS: return Blocks.POTTED_CRIMSON_ROOTS;
		case MANGROVE: return Blocks.POTTED_MANGROVE_PROPAGULE;
		case WARPED_FUNGUS: return Blocks.POTTED_WARPED_FUNGUS;
		case WARPED_ROOTS: return Blocks.POTTED_WARPED_ROOTS;
		case WITHER: return Blocks.POTTED_WITHER_ROSE;
		default: return Blocks.POTTED_DEAD_BUSH;
		}
	}
	
	public static ItemStack getFlowerItem(FlowerPot type){
		return new ItemStack(getFlower(type));
	}
	
	public static Block getFlower(FlowerPot type){
		switch(type){
		case DANDELION: return Blocks.DANDELION;
		case POPPY: return Blocks.POPPY;
		case ORCHID: return Blocks.BLUE_ORCHID;
		case ALLIUM: return Blocks.ALLIUM;
		case BLUET: return Blocks.AZURE_BLUET;
		case REDTULIP: return Blocks.RED_TULIP;
		case ORANGETULIP: return Blocks.ORANGE_TULIP;
		case WHITETULIP: return Blocks.WHITE_TULIP;
		case PINKTULIP: return Blocks.PINK_TULIP;
		case DAISY: return Blocks.OXEYE_DAISY;
		case REDMUSHROOM: return Blocks.RED_MUSHROOM;
		case BROWNMUSHROOM: return Blocks.BROWN_MUSHROOM;
		case CACTUS: return Blocks.CACTUS;
		case OAK: return Blocks.OAK_SAPLING;
		case SPRUCE: return Blocks.SPRUCE_SAPLING;
		case BIRCH: return Blocks.BIRCH_SAPLING;
		case JUNGLE: return Blocks.JUNGLE_SAPLING;
		case ACACIA: return Blocks.ACACIA_SAPLING;
		case DARKOAK: return Blocks.DARK_OAK_SAPLING;
		case SHRUB: return Blocks.DEAD_BUSH;
		case FERN: return Blocks.FERN;
		case AZALEA: return Blocks.AZALEA;
		case AZALEA_FLOWERING: return Blocks.FLOWERING_AZALEA;
		case BAMBOO: return Blocks.BAMBOO;
		case CHERRY: return Blocks.CHERRY_SAPLING;
		case CRIMSON_FUNGUS: return Blocks.CRIMSON_FUNGUS;
		case CRIMSON_ROOTS: return Blocks.CRIMSON_ROOTS;
		case LILY: return Blocks.LILY_OF_THE_VALLEY;
		case MANGROVE: return Blocks.MANGROVE_PROPAGULE;
		case WARPED_FUNGUS: return Blocks.WARPED_FUNGUS;
		case WARPED_ROOTS: return Blocks.WARPED_ROOTS;
		case WITHER: return Blocks.WITHER_ROSE;
		default: return Blocks.DEAD_BUSH;
		
		}
	}
}
