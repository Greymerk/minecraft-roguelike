package greymerk.roguelike.treasure.loot.provider;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.util.WeightedRandomizer;


public class ItemBrewing extends ItemBase {

  private WeightedRandomizer<ItemStack> items;

  public ItemBrewing(int weight, int level) {
    super(weight, level);
    this.items = new WeightedRandomizer<>();
    this.items.add(new WeightedRandomLoot(Items.SPIDER_EYE, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.BLAZE_POWDER, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.MAGMA_CREAM, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.GHAST_TEAR, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.NETHER_WART, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.REDSTONE, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.GLOWSTONE_DUST, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.SUGAR, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.SPECKLED_MELON, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.FERMENTED_SPIDER_EYE, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Blocks.BROWN_MUSHROOM, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Blocks.RED_MUSHROOM, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.RABBIT_FOOT, 0, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.FISH, 3, 1, 3, 1));
    this.items.add(new WeightedRandomLoot(Items.GLASS_BOTTLE, 0, 3, 12, 1));
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    return this.items.get(rand);
  }
}
