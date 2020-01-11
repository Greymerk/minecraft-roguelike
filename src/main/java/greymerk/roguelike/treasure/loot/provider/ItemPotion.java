package greymerk.roguelike.treasure.loot.provider;

import net.minecraft.item.ItemStack;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Potion;
import greymerk.roguelike.treasure.loot.PotionForm;

public class ItemPotion extends ItemBase {

  public ItemPotion(int weight, int level) {
    super(weight, level);
  }

  @Override
  public ItemStack getLootItem(Random rand, int level) {
    final Potion[] potions = new Potion[]{
        Potion.HEALING,
        Potion.STRENGTH,
        Potion.SWIFTNESS,
        Potion.REGEN
    };
    Potion type = potions[rand.nextInt(potions.length)];
    return Potion.getSpecific(PotionForm.REGULAR, type, true, false);
  }
}
