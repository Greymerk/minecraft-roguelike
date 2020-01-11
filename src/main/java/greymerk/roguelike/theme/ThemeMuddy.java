package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;

public class ThemeMuddy extends ThemeBase {

  public ThemeMuddy() {

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(BlockType.get(BlockType.SOUL_SAND), 1);
    floor.addBlock(BlockType.get(BlockType.CLAY), 4);
    floor.addBlock(BlockType.get(BlockType.DIRT), 3);
    floor.addBlock(BlockType.get(BlockType.MYCELIUM), 1);
    floor.addBlock(BlockType.get(BlockType.GRAVEL), 3);
    floor.addBlock(BlockType.get(BlockType.DIRT_COARSE), 1);

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 50);
    walls.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 30);
    MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);
    walls.addBlock(cracked, 10);
    walls.addBlock(BlockType.get(BlockType.DIRT), 15);
    walls.addBlock(BlockType.get(BlockType.CLAY), 30);
    walls.addBlock(BlockType.get(BlockType.GRAVEL), 15);

    MetaStair stair = new MetaStair(StairType.COBBLE);
    MetaBlock mossy = BlockType.get(BlockType.STONE_BRICK_MOSSY);
    MetaBlock pillar = mossy;

    this.primary = new BlockSet(floor, walls, stair, pillar);

    MetaBlock segmentWall = Log.getLog(Wood.DARKOAK);
    MetaStair segmentStair = new MetaStair(StairType.DARKOAK);

    this.secondary = new BlockSet(floor, segmentWall, segmentStair, segmentWall);
  }
}
