package greymerk.roguelike.treasure.loot;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public enum Record {

  THIRTEEN,
  CAT,
  BLOCKS,
  CHIRP,
  FAR,
  MALL,
  MELLOHI,
  STAL,
  STRAD,
  WARD,
  ELEVEN,
  WAIT;

  public static ItemStack getRecord(Record type) {
    return new ItemStack(getId(type), 1, 0);
  }

  public static ItemStack getRandomRecord(Random rand) {
    return getRecord(Record.values()[rand.nextInt(Record.values().length)]);
  }

  public static Item getId(Record type) {

    switch (type) {
      case THIRTEEN:
        return Items.RECORD_13;
      case CAT:
        return Items.RECORD_CAT;
      case BLOCKS:
        return Items.RECORD_BLOCKS;
      case CHIRP:
        return Items.RECORD_CHIRP;
      case FAR:
        return Items.RECORD_FAR;
      case MALL:
        return Items.RECORD_MALL;
      case MELLOHI:
        return Items.RECORD_MELLOHI;
      case STAL:
        return Items.RECORD_STAL;
      case STRAD:
        return Items.RECORD_STRAD;
      case WARD:
        return Items.RECORD_WARD;
      case ELEVEN:
        return Items.RECORD_11;
      case WAIT:
        return Items.RECORD_WAIT;
      default:
        return Items.RECORD_CAT;
    }
  }

}
