package greymerk.roguelike.treasure.loot.provider;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ItemSupply extends ItemBase {

  public ItemSupply(int weight, int level) {
    super(weight, level);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {

    if (rand.nextInt(20) == 0) {
      return new ItemStack(Items.CARROT, 1);
    }
    if (rand.nextInt(20) == 0) {
      return new ItemStack(Items.POTATO, 1);
    }

    switch (rand.nextInt(8)) {
      case 0:
        return new ItemStack(Items.WHEAT_SEEDS, rand.nextInt(8) + 1);
      case 1:
        return new ItemStack(Items.PUMPKIN_SEEDS, rand.nextInt(8) + 1);
      case 2:
        return new ItemStack(Items.MELON_SEEDS, rand.nextInt(8) + 1);
      case 3:
        return new ItemStack(Items.WHEAT, rand.nextInt(8) + 1);
      case 4:
        return new ItemStack(Blocks.TORCH, 10 + rand.nextInt(10));
      case 5:
        return new ItemStack(Items.PAPER, rand.nextInt(8) + 1);
      case 6:
        return new ItemStack(Items.BOOK, rand.nextInt(4) + 1);
      case 7:
        return new ItemStack(Blocks.SAPLING, rand.nextInt(4) + 1, rand.nextInt(4));
      default:
        return new ItemStack(Items.STICK, 1);
    }
  }
}
