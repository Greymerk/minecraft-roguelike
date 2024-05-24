package com.greymerk.roguelike.treasure.loot.items;

import net.minecraft.util.math.random.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public enum MusicDisk{

	THIRTEEN, CAT, BLOCKS, CHIRP, FAR, MALL, MELLOHI,
	STAL, STRAD, WARD, ELEVEN, WAIT;
	
	public static ItemStack getRecord(MusicDisk type){
		return new ItemStack(getId(type));
	}
	
	public static ItemStack getRandomRecord(Random rand){
		return getRecord(MusicDisk.values()[rand.nextInt(MusicDisk.values().length)]);
	}
	
	public static Item getId(MusicDisk type){
		
		switch(type){
		case THIRTEEN: return Items.MUSIC_DISC_13;
		case CAT: return Items.MUSIC_DISC_CAT;
		case BLOCKS: return Items.MUSIC_DISC_BLOCKS;
		case CHIRP: return Items.MUSIC_DISC_CHIRP;
		case FAR: return Items.MUSIC_DISC_FAR;
		case MALL: return Items.MUSIC_DISC_MALL;
		case MELLOHI: return Items.MUSIC_DISC_MELLOHI;
		case STAL: return Items.MUSIC_DISC_STAL;
		case STRAD: return Items.MUSIC_DISC_STRAD;
		case WARD: return Items.MUSIC_DISC_WARD;
		case ELEVEN: return Items.MUSIC_DISC_11;
		case WAIT: return Items.MUSIC_DISC_WAIT;
		default: return Items.MUSIC_DISC_CAT;
		}
	}
	
}
