package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;

public enum Crops {

  WHEAT,
  CARROTS,
  NETHERWART,
  MELON,
  PUMPKIN,
  POTATOES;

  public static MetaBlock get(Crops type) {
    switch (type) {
      case WHEAT:
        return new MetaBlock(Blocks.WHEAT);
      case CARROTS:
        return new MetaBlock(Blocks.CARROTS);
      case NETHERWART:
        return new MetaBlock(Blocks.NETHER_WART);
      case MELON:
        return new MetaBlock(Blocks.MELON_STEM);
      case PUMPKIN:
        return new MetaBlock(Blocks.PUMPKIN_STEM);
      case POTATOES:
        return new MetaBlock(Blocks.POTATOES);
      default:
        return new MetaBlock(Blocks.WHEAT);
    }
  }

  public static MetaBlock getCocao(Cardinal dir) {
    MetaBlock cocao = new MetaBlock(Blocks.COCOA);
    cocao.withProperty(BlockCocoa.FACING, Cardinal.facing(Cardinal.reverse(dir)));
    cocao.withProperty(BlockCocoa.AGE, 2);
    return cocao;
  }

  public static MetaBlock getPumpkin(Cardinal dir, boolean lit) {
    MetaBlock pumpkin = new MetaBlock(lit ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN);
    pumpkin.withProperty(BlockPumpkin.FACING, Cardinal.facing(Cardinal.reverse(dir)));
    return pumpkin;
  }


}
