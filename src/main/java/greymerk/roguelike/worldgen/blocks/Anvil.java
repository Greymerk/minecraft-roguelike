package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockAnvil;
import net.minecraft.init.Blocks;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;

public enum Anvil {

  NEW_ANVIL,
  DAMAGED_ANVIL,
  VERY_DAMAGED_ANVIL;

  public static MetaBlock get(Anvil damage, Cardinal dir) {

    if (!RogueConfig.getBoolean(RogueConfig.FURNITURE)) {
      return BlockType.get(BlockType.ANDESITE_POLISHED);
    }

    MetaBlock anvil = new MetaBlock(Blocks.ANVIL);

    switch (damage) {
      case NEW_ANVIL:
        anvil.withProperty(BlockAnvil.DAMAGE, 0);
        break;
      case DAMAGED_ANVIL:
        anvil.withProperty(BlockAnvil.DAMAGE, 1);
        break;
      case VERY_DAMAGED_ANVIL:
        anvil.withProperty(BlockAnvil.DAMAGE, 2);
        break;
      default:
    }

    anvil.withProperty(BlockAnvil.FACING, Cardinal.facing(dir));

    return anvil;
  }

}
