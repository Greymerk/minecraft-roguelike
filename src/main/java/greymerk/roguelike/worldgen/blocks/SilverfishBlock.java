package greymerk.roguelike.worldgen.blocks;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

public enum SilverfishBlock {

  STONE,
  COBBLE,
  STONEBRICK,
  STONEBRICK_MOSSY,
  STONEBRICK_CRACKED,
  STONEBRICK_CHISELED;

  public static MetaBlock get(SilverfishBlock type) {

    MetaBlock block = new MetaBlock(Blocks.MONSTER_EGG);

    switch (type) {
      case STONE:
        block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE);
        break;
      case COBBLE:
        block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.COBBLESTONE);
        break;
      case STONEBRICK:
        block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONEBRICK);
        break;
      case STONEBRICK_MOSSY:
        block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.MOSSY_STONEBRICK);
        break;
      case STONEBRICK_CRACKED:
        block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.CRACKED_STONEBRICK);
        break;
      case STONEBRICK_CHISELED:
        block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.CHISELED_STONEBRICK);
        break;
      default:
        block.withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE);
        break;
    }

    return block;

  }

  public static IBlockFactory getJumble() {

    BlockJumble jumble = new BlockJumble();

    SilverfishBlock[] types = new SilverfishBlock[]{
        COBBLE,
        STONEBRICK,
        STONEBRICK_MOSSY,
        STONEBRICK_CRACKED
    };

    for (SilverfishBlock type : types) {
      jumble.addBlock(get(type));
    }

    return jumble;

  }

}
