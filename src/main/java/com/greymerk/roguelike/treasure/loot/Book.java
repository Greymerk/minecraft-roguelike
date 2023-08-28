package com.greymerk.roguelike.treasure.loot;

import com.greymerk.roguelike.treasure.loot.books.BookStarter;

import net.minecraft.item.ItemStack;

public enum Book {

	CREDITS;
	
	public static ItemStack get(Book type){
		switch(type){
		
		default: return new BookStarter().get();
		}
	}
	
}
