package greymerk.roguelike.treasure.loot.provider;

import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Enchant;

public class ItemEnchBook extends ItemBase {

  int ench;

  public ItemEnchBook(int weight, int level) {
    super(weight, level);
    ench = 0;
  }

  public ItemEnchBook(JsonObject data, int weight) {
    super(weight, data.has("level") ? data.get("level").getAsInt() : 0);
    ench = data.has("ench") ? data.get("ench").getAsInt() : 0;
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    ItemStack book = new ItemStack(Items.BOOK);
    int enchantLevel = this.ench != 0 ? this.ench : Enchant.getLevel(rand, level);
    return Enchant.enchantItem(rand, book, enchantLevel);
  }

}
