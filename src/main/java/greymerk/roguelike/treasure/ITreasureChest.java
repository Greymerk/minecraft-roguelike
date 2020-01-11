package greymerk.roguelike.treasure;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface ITreasureChest {

  ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) throws ChestPlacementException;

  boolean setSlot(int slot, ItemStack item);

  boolean setRandomEmptySlot(ItemStack item);

  void setLootTable(ResourceLocation table);

  boolean isEmptySlot(int slot);

  Treasure getType();

  int getSize();

  int getLevel();

}