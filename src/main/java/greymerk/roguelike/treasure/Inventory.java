package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Inventory {
  List<Integer> shuffledSlots;
  private TileEntityChest chest;

  public Inventory(Random rand, TileEntityChest chest) {
    this.chest = chest;
    this.shuffledSlots = new ArrayList<>();
    for (int i = 0; i < this.getInventorySize(); ++i) {
      shuffledSlots.add(i);
    }

    Collections.shuffle(shuffledSlots, rand);
  }

  public boolean setRandomEmptySlot(ItemStack item) {
    int slot = this.getRandomEmptySlot();
    if (slot < 0) {
      return false;
    }
    return setInventorySlot(slot, item);
  }

  private int getRandomEmptySlot() {
    for (int slot : this.shuffledSlots) {
      if (isEmptySlot(slot)) {
        return slot;
      }
    }
    return -1;
  }

  public boolean isEmptySlot(int slot) {
    try {
      ItemStack item = chest.getStackInSlot(slot);
      return item.isEmpty();
    } catch (NullPointerException e) {
      return false;
    }
  }

  public boolean setInventorySlot(int slot, ItemStack item) {
    try {
      chest.setInventorySlotContents(slot, item);
      return true;
    } catch (NullPointerException e) {
      return false;
    }
  }

  public int getInventorySize() {

    if (chest == null) {
      return 0;
    }

    try {
      return chest.getSizeInventory();
    } catch (NullPointerException e) {
      return 0;
    }
  }
}
