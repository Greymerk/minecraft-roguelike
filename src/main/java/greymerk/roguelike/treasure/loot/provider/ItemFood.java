package greymerk.roguelike.treasure.loot.provider;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.util.WeightedRandomizer;

public class ItemFood extends ItemBase {

  private Map<Integer, WeightedRandomizer<ItemStack>> loot;

  public ItemFood(int weight, int level) {
    super(weight, level);
    this.loot = new HashMap<>();
    for (int i = 0; i < 5; ++i) {

      WeightedRandomizer<ItemStack> randomizer = new WeightedRandomizer<>();

      switch (i) {
        case 4:
          randomizer.add(new WeightedRandomLoot(Items.GOLDEN_APPLE, 0, 1, 1, 1));
          randomizer.add(new WeightedRandomLoot(Items.GOLDEN_CARROT, 0, 1, 1, 2));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 0, 1, 5, 3));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 0, 1, 5, 3));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 0, 1, 5, 3));
          break;
        case 3:
          randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 0, 1, 3, 3));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 0, 1, 3, 3));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 0, 1, 3, 3));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 0, 1, 2, 1));
          randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 0, 1, 2, 1));
          break;
        case 2:
          randomizer.add(new WeightedRandomLoot(Items.COOKED_BEEF, 0, 1, 3, 1));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_PORKCHOP, 0, 1, 3, 1));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_MUTTON, 0, 1, 3, 1));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 0, 1, 2, 2));
          randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 0, 1, 2, 2));
          break;
        case 1:
          randomizer.add(new WeightedRandomLoot(Items.BREAD, 0, 1, 3, 5));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_FISH, 0, 1, 3, 5));
          randomizer.add(new WeightedRandomLoot(Items.APPLE, 0, 1, 3, 2));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_CHICKEN, 0, 1, 2, 2));
          randomizer.add(new WeightedRandomLoot(Items.BAKED_POTATO, 0, 1, 2, 2));
          break;
        case 0:
          randomizer.add(new WeightedRandomLoot(Items.BREAD, 0, 1, 2, 5));
          randomizer.add(new WeightedRandomLoot(Items.COOKED_FISH, 0, 1, 2, 5));
          randomizer.add(new WeightedRandomLoot(Items.APPLE, 0, 1, 2, 5));
          randomizer.add(new WeightedRandomLoot(Items.COOKIE, 0, 1, 4, 1));
          break;
        default:
          randomizer.add(new WeightedRandomLoot(Items.BREAD, 1));
      }

      loot.put(i, randomizer);
    }
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    return this.loot.get(level).get(rand);
  }


}
