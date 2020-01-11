package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.SilverfishBlock;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.door.Door;
import greymerk.roguelike.worldgen.blocks.door.DoorType;

public class ThemeMossy extends ThemeBase {

  public ThemeMossy() {

    MetaBlock mossBrick = BlockType.get(BlockType.STONE_BRICK_MOSSY);
    MetaBlock mossy = BlockType.get(BlockType.COBBLESTONE_MOSSY);
    MetaBlock cobble = BlockType.get(BlockType.COBBLESTONE);
    IBlockFactory egg = SilverfishBlock.getJumble();
    MetaBlock gravel = BlockType.get(BlockType.GRAVEL);

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(cobble, 60);
    walls.addBlock(mossBrick, 30);
    walls.addBlock(egg, 5);
    walls.addBlock(mossy, 10);
    walls.addBlock(gravel, 15);

    BlockWeightedRandom pillar = new BlockWeightedRandom();
    pillar.addBlock(mossBrick, 20);
    pillar.addBlock(cobble, 5);
    pillar.addBlock(egg, 3);
    pillar.addBlock(mossy, 5);

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(mossy, 10);
    floor.addBlock(mossBrick, 4);
    floor.addBlock(cobble, 2);
    floor.addBlock(gravel, 1);

    MetaStair stair = new MetaStair(StairType.COBBLE);

    this.primary = new BlockSet(floor, walls, stair, walls,
        new Door(DoorType.IRON)
    );
    this.secondary = this.primary;
  }
}
