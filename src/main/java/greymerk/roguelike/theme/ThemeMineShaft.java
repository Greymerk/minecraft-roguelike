package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;

public class ThemeMineShaft extends ThemeBase {

  public ThemeMineShaft() {

    BlockJumble floor = new BlockJumble();
    floor.addBlock(BlockType.get(BlockType.GRAVEL));
    floor.addBlock(BlockType.get(BlockType.DIRT));
    floor.addBlock(BlockType.get(BlockType.COBBLESTONE));

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.STONE_SMOOTH), 50);
    walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 15);
    walls.addBlock(BlockType.get(BlockType.ORE_REDSTONE), 1);

    MetaStair stair = new MetaStair(StairType.COBBLE);
    MetaBlock pillar = BlockType.get(BlockType.COBBLESTONE_WALL);

    this.primary = new BlockSet(floor, walls, stair, pillar);

    MetaBlock walls2 = Wood.getPlank(Wood.OAK);
    MetaStair stair2 = new MetaStair(StairType.OAK);
    MetaBlock pillar2 = Wood.get(WoodBlock.LOG);

    this.secondary = new BlockSet(floor, walls2, stair2, pillar2);
  }
}
