package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class MockChest implements ITreasureChest {

  Treasure type;
  int level;
  Inventory inv;
  TileEntityChest chest;

  public MockChest(Treasure type, int level) {
    this.type = type;
    this.level = level;
    this.chest = new TileEntityChest();
    this.inv = new Inventory(new Random(), chest);
  }

  @Override
  public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) {
    return this;
  }

  @Override
  public boolean setSlot(int slot, ItemStack item) {
    return this.setSlot(slot, item);
  }

  @Override
  public boolean setRandomEmptySlot(ItemStack item) {
    return this.inv.setRandomEmptySlot(item);
  }

  @Override
  public boolean isEmptySlot(int slot) {
    return this.inv.isEmptySlot(slot);
  }

  @Override
  public Treasure getType() {
    return this.type;
  }

  @Override
  public int getSize() {
    return this.inv.getInventorySize();
  }

  @Override
  public int getLevel() {
    return this.level;
  }

  public boolean contains(ItemStack item) {

    for (int i = 0; i < 27; ++i) {
      ItemStack slot = chest.getStackInSlot(i);
      if (sameItem(item, slot)) {
        return true;
      }
    }

    return false;
  }

  private boolean sameItem(ItemStack item, ItemStack other) {
    if (item == other) {
      return true;
    }
    if (item != null && other == null) {
      return false;
    }
    if (item == null) {
      return false;
    }
    if (item.getItem() != other.getItem()) {
      return false;
    }
    return item.getItemDamage() == other.getItemDamage();
  }

  @Override
  public void setLootTable(ResourceLocation table) {

  }

}
