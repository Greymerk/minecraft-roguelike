package greymerk.roguelike.worldgen.blocks.door;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import greymerk.roguelike.worldgen.MetaBlock;

public enum DoorType {

  IRON,
  OAK,
  BIRCH,
  SPRUCE,
  JUNGLE,
  ACACIA,
  DARKOAK;


  public static MetaBlock get(DoorType type) {

    IBlockState door;
    switch (type) {
      case IRON:
        door = new MetaBlock(Blocks.IRON_DOOR.getDefaultState());
        break;
      case BIRCH:
        door = new MetaBlock(Blocks.BIRCH_DOOR.getDefaultState());
        break;
      case SPRUCE:
        door = new MetaBlock(Blocks.SPRUCE_DOOR.getDefaultState());
        break;
      case JUNGLE:
        door = new MetaBlock(Blocks.JUNGLE_DOOR.getDefaultState());
        break;
      case ACACIA:
        door = new MetaBlock(Blocks.ACACIA_DOOR.getDefaultState());
        break;
      case DARKOAK:
        door = new MetaBlock(Blocks.DARK_OAK_DOOR.getDefaultState());
        break;
      default:
        door = new MetaBlock(Blocks.OAK_DOOR.getDefaultState());
        break;
    }

    return new MetaBlock(door);
  }


}
