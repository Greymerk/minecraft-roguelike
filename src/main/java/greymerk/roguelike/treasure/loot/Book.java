package greymerk.roguelike.treasure.loot;

import greymerk.roguelike.treasure.loot.books.BookStarter;

public enum Book {

	CREDITS;
	
	public static IBook get(Book type){
		switch(type){
		
		default: return new BookStarter();
		}
	}
	
}
