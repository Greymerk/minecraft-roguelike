package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.ItemSpecialty;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemWritableBook;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.TileEntityChest;

public class TreasureChestStarter extends TreasureChestBase{

	private ItemStack getStarterLoot(int choice){
		
		switch (choice){
		case 5: return Loot.getLootByCategory(Loot.TOOL, rand, 0, false);
		case 4: return new ItemStack(Item.swordStone);
		case 3: return Loot.getLootByCategory(Loot.BLOCK, rand, 0);
		case 2: return Loot.getLootByCategory(Loot.FOOD, rand, 0);
		case 1: return ItemSpecialty.getRandomItem(ItemSpecialty.LEGS, rand, 0);
		default: return new ItemStack(Block.torchWood, 3 + rand.nextInt(6));
		}
		
	}	
	
	@Override
	protected void fillChest(TileEntityChest chest) {

		int size = chest.getSizeInventory();
				
		int quantity = 18;

		for (int i = 0; i < quantity; i++) {
			ItemStack item;
			item = getStarterLoot(i % 6);
			chest.setInventorySlotContents(rand.nextInt(size), item);
		}
		

		
		chest.setInventorySlotContents(0, book());
	}
	
	private ItemStack book(){
		ItemStack book = new ItemStack(Item.writableBook);
		
		book.setTagInfo("author", new NBTTagString("author", "greymerk"));
		book.setTagInfo("title", new NBTTagString("title", "Roguelike Dungeons Mod"));
		
		String page1 = 
				"This dungeon was generated with the roguelike dungeons " +
				"mod version 1.2.7\n\n" +
				"Thanks for playing\n\n" +
				"-greymerk\n" +
				"November 20th 2013";
		
		String page2 = 
				"You can find updates and information about the mod at http://dungeons.homelinux.org/";

		String page3 = 
				"Credits\n\n" +
				"Author: Greymerk\n\n" +
				"Bits: Drainedsoul\n\n" +
				"Ideas: Eniko";
	
		
		NBTTagList pages = new NBTTagList();
		pages.appendTag(new NBTTagString("1", page1));
		pages.appendTag(new NBTTagString("2", page2));
		pages.appendTag(new NBTTagString("3", page3));
		
		book.setTagInfo("pages", pages);
		book.itemID = Item.writtenBook.itemID;
		
		return book;
	}
}
