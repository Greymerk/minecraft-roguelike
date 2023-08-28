package com.greymerk.roguelike.treasure.loot.books;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.treasure.loot.BookBase;

public class BookStatistics extends BookBase{

	public BookStatistics(IWorldEditor editor){
		super("greymerk", "Statistics");
		
		for(String page : getPages(editor)){
			this.addPage(page);
		}		
	}
	
	private List<String> getPages(IWorldEditor editor){
		
		List<String> pages = new ArrayList<String>();
		/*
		Map<Block, Integer> stats = null; //editor.getStats();
		
		int counter = 0;
		String page = "";
		for(Block type : stats.keySet()){
			int count = stats.get(type);
			String name = StringUtils.abbreviate(type.getName().getString(), 16);
			String line = name + " : " + count + "\n";
			page += line;
			++counter;
			if(counter == 12){
				counter = 0;
				pages.add(page);
				page = "";
			}
		}
		pages.add(page);
		*/
		return pages;
	}
}
