package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeSewer extends ThemeBase {

  public ThemeSewer() {

    MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);
    MetaBlock mossy = BlockType.get(BlockType.STONE_BRICK_MOSSY);

    BlockWeightedRandom wall = new BlockWeightedRandom();
    wall.addBlock(BlockType.get(BlockType.STONE_BRICK), 10);
    wall.addBlock(mossy, 4);
    wall.addBlock(cracked, 1);

    MetaBlock floor = BlockType.get(BlockType.STONE_BRICK);

    MetaStair stair = new MetaStair(StairType.STONEBRICK);


    this.primary = new BlockSet(floor, wall, stair, wall);

    this.secondary = this.primary;
  }
}
