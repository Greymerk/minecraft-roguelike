package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeSandstone extends ThemeBase {

  public ThemeSandstone() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.SANDSTONE), 100);
    walls.addBlock(BlockType.get(BlockType.SAND), 5);

    MetaStair stair = new MetaStair(StairType.SANDSTONE);

    MetaBlock pillar = BlockType.get(BlockType.SANDSTONE_SMOOTH);

    this.primary = new BlockSet(walls, stair, pillar);

    MetaBlock segmentWall = BlockType.get(BlockType.SANDSTONE_CHISELED);

    this.secondary = new BlockSet(segmentWall, stair, pillar);

  }
}
