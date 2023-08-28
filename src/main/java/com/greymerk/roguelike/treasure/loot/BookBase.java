package com.greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

public class BookBase implements IBook{

	private List<String> pages;
	//private String author;
	//private String title;
	
	public BookBase(){
		this.pages = new ArrayList<String>();
	}
	
	public BookBase(String author, String title){
		this.pages = new ArrayList<String>();
		//this.author = author;
		//this.title = title;
	}
	
	public void setAuthor(String author){
		//this.author = author;
	}
	
	public void setTitle(String title){
		//this.title = title;
	}
	
	public void addPage(String page){
		this.pages.add(page);
	}
	
	@Override
	public ItemStack get(){
		ItemStack book = new ItemStack(Items.WRITTEN_BOOK, 1);
		
		NbtList nbtPages = new NbtList();
		
		for(String page : this.pages){
			Text text = Text.of(page);
			String json = Text.Serializer.toJson(text);
			NbtString nbtPage = NbtString.of(json);
			nbtPages.add(nbtPage);
		}
		
		//book.setTagInfo("pages", nbtPages);
		//book.setTagInfo("author", NbtString.of(this.author == null ? "Anonymous" : this.author));
		//book.setTagInfo("title", NbtString.of(this.title == null ? "Book" : this.title));
		
		return book;
	}
}
