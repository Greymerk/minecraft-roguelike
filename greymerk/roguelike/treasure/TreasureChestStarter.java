package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Potion;
import greymerk.roguelike.treasure.loot.PotionEffect;
import greymerk.roguelike.treasure.loot.PotionMixture;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.config.RogueConfig;

import java.util.ArrayList;
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

public class TreasureChestStarter extends TreasureChestBase implements Iterable{


	
	private ItemStack getStarterLoot(int choice){
		switch (choice){
		case 4: return new ItemStack(Item.pickaxeStone);
		case 3: return new ItemStack(Item.swordStone);
		case 2: return Loot.getLoot(Loot.FOOD, rand, 0);
		case 1: return ItemSpecialty.getRandomItem(Equipment.LEGS, rand, Quality.WOOD);
		default: return new ItemStack(Block.torchWood, 1 + rand.nextInt(RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 7 : 3));
		}
	}	
	
	@Override
	protected void fillChest(TileEntityChest chest, int level) {

		int quantity = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 15 : 5;

		Iterator<InventorySlot> itr = this.iterator();
		
		for (int i = 0; i < quantity; i++) {
			ItemStack item;
			item = getStarterLoot(i % 5);
			if(itr.hasNext()) itr.next().set(item);
		}

		if(itr.hasNext()) itr.next().set(book());

	}
	
	private ItemStack book(){
		ItemStack book = new ItemStack(Item.writableBook);
		
		book.setTagInfo("author", new NBTTagString("author", "greymerk"));
		book.setTagInfo("title", new NBTTagString("title", "Memo"));
		
		String page1 = 
				"Dear Eniko,\n\n " +
				"Please stop storing the TNT under the floor, it's not very safe." +
				" One of these days there's going to be an accident and we'll be" + 
				" left with a smoking crater in the middle of the base.\n\n" +
				"-greymerk\n";
		
		String page2 = 
				"Roguelike Dungeons v1.3.3\n" +
				"June 8th 2014\n\n" + 
				"Credits\n\n" +
				"Author: Greymerk\n\n" +
				"Bits: Drainedsoul\n\n" +
				"Ideas: Eniko @enichan";
	
		
		NBTTagList pages = new NBTTagList();
		pages.appendTag(new NBTTagString("1", page1));
		pages.appendTag(new NBTTagString("2", page2));
		
		book.setTagInfo("pages", pages);
		book.itemID = Item.writtenBook.itemID;
		
		return book;
	}
}
