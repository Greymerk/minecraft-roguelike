package greymerk.roguelike;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;

public class TreasureChestStarter extends TreasureChestBase{

	private ItemStack getStarterLoot(int choice){
		
		switch (choice){
		case 5: return ItemLoot.getTool(rand, 0, false);
		case 4: return new ItemStack(Item.swordStone);
		case 3: return ItemLoot.getBlocks(rand, 0);	
		case 2: return ItemLoot.getFood(rand, 0);
		case 1: return ItemSpecialty.getItem(ItemSpecialty.LEGS, rand, 0);
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
	}
}
