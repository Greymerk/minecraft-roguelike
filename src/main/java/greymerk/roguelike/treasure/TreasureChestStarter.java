package greymerk.roguelike.treasure;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;

import java.util.Iterator;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntityChest;

public class TreasureChestStarter extends TreasureChestBase{


	
	private ItemStack getStarterLoot(int choice){
		
		if(!RogueConfig.getBoolean(RogueConfig.GENEROUS)) return Loot.getLoot(Loot.JUNK, rand, 0);
		
		switch (choice){
		case 4: return Loot.getLoot(Loot.TOOL, rand, 0);
		case 3: return Loot.getLoot(Loot.WEAPON, rand, 0);
		case 2: return Loot.getLoot(Loot.FOOD, rand, 0);
		case 1: return ItemSpecialty.getRandomItem(Equipment.LEGS, rand, Quality.WOOD);
		default: return new ItemStack(Blocks.torch, 1 + rand.nextInt(RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 7 : 3));
		}
	}	
	
	@Override
	protected void fillChest(TileEntityChest chest, int level) {

		int quantity = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 10 : 5;

		Iterator<InventorySlot> itr = this.iterator();
		
		for (int i = 0; i < quantity; i++) {
			ItemStack item;
			item = getStarterLoot(i % 5);
			if(itr.hasNext()) itr.next().set(item);
		}

		if(itr.hasNext()) itr.next().set(book());

	}
	
	private ItemStack book(){
		ItemStack book = new ItemStack(Items.written_book);
		
		book.setTagInfo("author", new NBTTagString("greymerk"));
		book.setTagInfo("title", new NBTTagString("Memo"));
		
		String page1 = 
				"Dear Eniko,\n\n " +
				"Please stop storing the TNT under the floor, it's not very safe." +
				" One of these days there's going to be an accident and we'll be" + 
				" left with a smoking crater in the middle of the base.\n\n" +
				"-greymerk\n";
		
		String page2 = 
				"Roguelike Dungeons v1.3.4\n" +
				"June 29th 2014\n\n" + 
				"Credits\n\n" +
				"Author: Greymerk\n\n" +
				"Bits: Drainedsoul\n\n" +
				"Ideas: Eniko @enichan";
	
		
		NBTTagList pages = new NBTTagList();
		pages.appendTag(new NBTTagString(page1));
		pages.appendTag(new NBTTagString(page2));
		
		book.setTagInfo("pages", pages);
		
		return book;
	}
}
