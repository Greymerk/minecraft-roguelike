package greymerk.roguelike.treasure;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.ItemNovelty;
import greymerk.roguelike.treasure.loot.ItemSpecialty;
import greymerk.roguelike.treasure.loot.Quality;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestSpecialty extends TreasureChestBase{

	@Override
	protected void fillChest(TileEntityChest chest, int level){
		Equipment type = Equipment.values()[rand.nextInt(Equipment.values().length)];
		Quality quality = level == 4 ? Quality.DIAMOND : Quality.getQuality(rand, level, type); 
		ItemStack item = ItemSpecialty.getRandomItem(type, rand, quality);
		int middle = chest.getSizeInventory()/2;
		chest.setInventorySlotContents(middle, item);
	}
	
}
		
