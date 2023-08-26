package com.greymerk.roguelike.editor.blocks;

import java.util.Random;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;

public enum FlowerPot {

	DANDELION, POPPY, ORCHID, ALLIUM, BLUET, REDTULIP, ORANGETULIP, WHITETULIP, PINKTULIP, DAISY,
	REDMUSHROOM, BROWNMUSHROOM, CACTUS, OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK,
	SHRUB, FERN;

	public static void generate(IWorldEditor editor, Coord pos, FlowerPot type){
		MetaBlock pot = new MetaBlock(FlowerPot.getFlowerPot(type));
		pot.set(editor, pos);
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord pos){
		FlowerPot choice = FlowerPot.values()[rand.nextInt(FlowerPot.values().length)];
		generate(editor, pos, choice);
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
		case REDMUSHROOM: return Blocks.POTTED_RED_MUSHROOM;
		case BROWNMUSHROOM: return Blocks.POTTED_BROWN_MUSHROOM;
		case CACTUS: return Blocks.POTTED_CACTUS;
		case OAK: return Blocks.POTTED_OAK_SAPLING;
		case SPRUCE: return Blocks.POTTED_SPRUCE_SAPLING;
		case BIRCH: return Blocks.POTTED_BIRCH_SAPLING;
		case JUNGLE: return Blocks.POTTED_JUNGLE_SAPLING;
		case ACACIA: return Blocks.POTTED_ACACIA_SAPLING;
		case DARKOAK: return Blocks.POTTED_DARK_OAK_SAPLING;
		case SHRUB: return Blocks.POTTED_DEAD_BUSH;
		case FERN: return Blocks.POTTED_FERN;
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
		default: return Blocks.DEAD_BUSH;
		}
	}
}
