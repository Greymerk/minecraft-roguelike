package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemePyramid extends ThemeBase {

  public ThemePyramid() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.SANDSTONE_SMOOTH), 100);
    walls.addBlock(BlockType.get(BlockType.SANDSTONE), 10);
    walls.addBlock(BlockType.get(BlockType.SANDSTONE_CHISELED), 5);

    MetaStair stair = new MetaStair(StairType.SANDSTONE);
    MetaBlock pillar = BlockType.get(BlockType.SANDSTONE_SMOOTH);
    MetaBlock SegmentWall = BlockType.get(BlockType.SANDSTONE_CHISELED);

    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = new BlockSet(SegmentWall, stair, pillar);

  }

}
