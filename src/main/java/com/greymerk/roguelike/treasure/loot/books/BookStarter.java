package com.greymerk.roguelike.treasure.loot.books;

import com.greymerk.roguelike.treasure.loot.BookBase;

public class BookStarter extends BookBase{

	public BookStarter(){
		super("greymerk", "Roguelike Dungeons");
				
		this.addPage(
			"Roguelike Dungeons v" + "VERSION" + "\n"
			+ "DATE" + "\n\n"
			+ "Credits\n\n"
			+ "Author: Greymerk\n\n"
			+ "Bits: Drainedsoul\n\n"
			+ "Ideas: Eniko @enichan"
			);
	}
}
