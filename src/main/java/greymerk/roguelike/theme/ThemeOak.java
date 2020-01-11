package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;
import greymerk.roguelike.worldgen.blocks.door.Door;
import greymerk.roguelike.worldgen.blocks.door.DoorType;

public class ThemeOak extends ThemeBase {

  public ThemeOak() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 30);
    MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);
    walls.addBlock(cracked, 20);
    walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 5);
    walls.addBlock(BlockType.get(BlockType.GRAVEL), 1);

    MetaStair stair = new MetaStair(StairType.STONEBRICK);

    this.primary = new BlockSet(walls, walls, stair, walls, new Door(DoorType.SPRUCE));

    MetaBlock pillar = Wood.get(WoodBlock.LOG);
    MetaBlock segmentWall = Wood.get(Wood.OAK, WoodBlock.PLANK);
    MetaStair segmentStair = new MetaStair(StairType.OAK);

    this.secondary = new BlockSet(segmentWall, segmentWall, segmentStair, pillar, new Door(DoorType.SPRUCE));
  }
}
