package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;

public class ThemeTemple extends ThemeBase {

  public ThemeTemple() {

    MetaBlock prismLight = BlockType.get(BlockType.PRISMARINE);
    MetaBlock prismDark = BlockType.get(BlockType.PRISMARINE_DARK);
    MetaBlock prismRough = BlockType.get(BlockType.PRISMITE);

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(prismLight, 5);
    walls.addBlock(prismDark, 10);
    walls.addBlock(prismRough, 5);
    walls.addBlock(BlockType.get(BlockType.SEA_LANTERN), 3);

    MetaStair stair = new MetaStair(StairType.QUARTZ);
    MetaBlock pillar = prismDark;
    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = this.primary;
  }

}
