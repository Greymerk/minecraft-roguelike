package greymerk.roguelike.treasure.loot;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public enum PotionEffect {

  SPEED(1),
  SLOWNESS(2),
  HASTE(3),
  FATIGUE(4),
  STRENGTH(5),
  HEALTH(6),
  DAMAGE(7),
  JUMP(8),
  NAUSIA(9),
  REGEN(10),
  RESISTANCE(11),
  FIRERESIST(12),
  WATERBREATH(13),
  INVISIBILITY(14),
  BLINDNESS(15),
  NIGHTVISION(16),
  HUNGER(17),
  WEAKNESS(18),
  POISON(19),
  WITHER(20),
  HEALTHBOOST(21),
  ABSORPTION(22),
  SATURATION(23),
  GLOWING(24),
  LEVITATION(25),
  LUCK(26),
  BAD_LUCK(27);

  public static int TICKS_PER_SECOND = 20;

  private int id;

  PotionEffect(int id) {
    this.id = id;
  }

  public static int getEffectID(PotionEffect type) {
    return type.id;
  }

  public static void addCustomEffect(ItemStack potion, PotionEffect type, int amplifier, int duration) {

    final String CUSTOM = "CustomPotionEffects";

    NBTTagCompound tag = potion.getTagCompound();
    if (tag == null) {
      tag = new NBTTagCompound();
      potion.setTagCompound(tag);
    }


    NBTTagList effects;
    effects = tag.getTagList(CUSTOM, 10);
    if (effects == null) {
      effects = new NBTTagList();
      tag.setTag(CUSTOM, effects);
    }

    NBTTagCompound toAdd = new NBTTagCompound();

    toAdd.setByte("Id", (byte) type.id);
    toAdd.setByte("Amplifier", (byte) (amplifier - 1));
    toAdd.setInteger("Duration", duration * TICKS_PER_SECOND);
    toAdd.setBoolean("Ambient", true);

    effects.appendTag(toAdd);
    tag.setTag(CUSTOM, effects);
    potion.setTagCompound(tag);
  }
}
