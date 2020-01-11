package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeBling extends ThemeBase {

  public ThemeBling() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.IRON_BLOCK), 10);
    walls.addBlock(BlockType.get(BlockType.GOLD_BLOCK), 3);
    walls.addBlock(BlockType.get(BlockType.EMERALD_BLOCK), 10);
    walls.addBlock(BlockType.get(BlockType.DIAMOND_BLOCK), 20);

    MetaStair stair = new MetaStair(StairType.QUARTZ);
    MetaBlock pillar = BlockType.get(BlockType.LAPIS_BLOCK);
    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = this.primary;
  }
}
