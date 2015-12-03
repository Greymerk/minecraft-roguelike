package greymerk.roguelike.treasure;

import greymerk.roguelike.Roguelike;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.treasure.loot.Quality;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class TreasureChestStarter extends TreasureChest{

	@Override
	protected void fillChest(LootSettings loot, int level) {

		int amount = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 9 : 4;
		for(int i = 0; i < amount; ++i){
			this.setRandomEmptySlot(getStarterLoot(loot, i % 5));
		}
		this.setRandomEmptySlot(book());
		this.type = Treasure.STARTER;

	}
	
	private ItemStack getStarterLoot(LootSettings loot, int choice){
		
		if(!RogueConfig.getBoolean(RogueConfig.GENEROUS)) return loot.get(Loot.JUNK, rand);
		
		switch (choice){
		case 4: return loot.get(Loot.TOOL, rand);
		case 3: return loot.get(Loot.WEAPON, rand);
		case 2: return loot.get(Loot.FOOD, rand);
		case 1: if(RogueConfig.getBoolean(RogueConfig.GENEROUS)) return ItemSpecialty.getRandomItem(Equipment.LEGS, rand, Quality.WOOD);
		default: return new ItemStack(Blocks.torch, 1 + rand.nextInt(RogueConfig.getBoolean(RogueConfig.GENEROUS) ? 7 : 3));
		}
	}	
	
	private ItemStack book(){
		ItemStack book = new ItemStack(Items.written_book);
		
		book.setTagInfo("author", new NBTTagString("Eniko"));
		book.setTagInfo("title", new NBTTagString("To Greymerk"));
		
		String page1 = 
				"I took the enchanting table\n\n " +
				"- Eniko";
		
		String page2 = 
				"Roguelike Dungeons v" + Roguelike.version + "\n" +
				"Feb 16th 2015\n\n" + 
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
