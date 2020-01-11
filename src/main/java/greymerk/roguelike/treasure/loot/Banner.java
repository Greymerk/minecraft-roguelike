package greymerk.roguelike.treasure.loot;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;

import java.util.Random;

public class Banner {

  public static ItemStack get(Random rand) {

    ItemStack banner = new ItemStack(Items.BANNER);
    int n = rand.nextInt(8) + 1;
    for (int i = 0; i < n; ++i) {
      addPattern(banner, rand);
    }

    return banner;
  }

  public static ItemStack addPattern(ItemStack banner, Random rand) {
    BannerPattern pattern = BannerPattern.values()[rand.nextInt(BannerPattern.values().length)];
    EnumDyeColor color = EnumDyeColor.values()[rand.nextInt(EnumDyeColor.values().length)];

    return addPattern(banner, pattern, color);
  }

  public static ItemStack addPattern(ItemStack banner, BannerPattern pattern, EnumDyeColor color) {

    NBTTagCompound nbt = banner.getTagCompound();
    if (nbt == null) {
      banner.setTagCompound(new NBTTagCompound());
      nbt = banner.getTagCompound();
    }

    NBTTagCompound tag;

    if (nbt.hasKey("BlockEntityTag")) {
      tag = nbt.getCompoundTag("BlockEntityTag");
    } else {
      tag = new NBTTagCompound();
      nbt.setTag("BlockEntityTag", tag);
    }

    NBTTagList patterns;

    if (tag.hasKey("Patterns")) {
      patterns = tag.getTagList("Patterns", 10);
    } else {
      patterns = new NBTTagList();
      tag.setTag("Patterns", patterns);
    }

    NBTTagCompound toAdd = new NBTTagCompound();
    toAdd.setInteger("Color", color.getDyeDamage());
    toAdd.setString("Pattern", pattern.getHashname());
    patterns.appendTag(toAdd);

    return banner;
  }

}
