package greymerk.roguelike.treasure.loot;

import net.minecraft.item.ItemStack;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

public enum ItemHideFlags {

  ENCHANTMENTS,
  ATTRIBUTES,
  UNBREAKABLE,
  CANDESTROY,
  CANPLACEON,
  EFFECTS;

  public static void set(ItemHideFlags[] flags, ItemStack item) {
    ofNullable(item.getTagCompound())
        .ifPresent(nbt ->
            stream(flags)
                .map(ItemHideFlags::get)
                .reduce(Integer::sum)
                .ifPresent(value ->
                    nbt.setInteger("HideFlags", value)));
  }

  public static void set(ItemHideFlags flag, ItemStack item) {
    set(new ItemHideFlags[]{flag}, item);
  }

  public static int get(ItemHideFlags flag) {
    switch (flag) {
      case ENCHANTMENTS:
        return 1;
      case ATTRIBUTES:
        return 2;
      case UNBREAKABLE:
        return 4;
      case CANDESTROY:
        return 8;
      case CANPLACEON:
        return 16;
      case EFFECTS:
        return 32;
      default:
        return 0;
    }
  }


}
