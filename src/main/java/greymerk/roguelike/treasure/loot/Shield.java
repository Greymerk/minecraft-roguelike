package greymerk.roguelike.treasure.loot;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class Shield {

  public static ItemStack get(Random rand) {

    ItemStack banner = Banner.get(rand);

    ItemStack shield = new ItemStack(Items.SHIELD, 1, 0);

    applyBanner(banner, shield);

    return shield;
  }

  public static void applyBanner(ItemStack banner, ItemStack shield) {

    NBTTagCompound bannerNBT = banner.getSubCompound("BlockEntityTag");
    NBTTagCompound shieldNBT = bannerNBT == null ? new NBTTagCompound() : bannerNBT.copy();
    shieldNBT.setInteger("Base", banner.getMetadata() & 15);
    shield.setTagInfo("BlockEntityTag", shieldNBT);

  }

}
