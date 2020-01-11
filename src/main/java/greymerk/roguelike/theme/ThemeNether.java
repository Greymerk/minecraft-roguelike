package greymerk.roguelike.theme;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.door.Door;
import greymerk.roguelike.worldgen.blocks.door.DoorType;
import greymerk.roguelike.worldgen.blocks.door.IDoor;

public class ThemeNether extends ThemeBase {

  public ThemeNether() {

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.NETHERBRICK), 200);
    walls.addBlock(BlockType.get(BlockType.NETHERRACK), 20);
    walls.addBlock(BlockType.get(BlockType.ORE_QUARTZ), 20);
    walls.addBlock(BlockType.get(BlockType.SOUL_SAND), 15);
    walls.addBlock(BlockType.get(BlockType.COAL_BLOCK), 5);

    BlockWeightedRandom floor = new BlockWeightedRandom();
    floor.addBlock(walls, 2000);
    floor.addBlock(BlockType.get(BlockType.REDSTONE_BLOCK), 50);
    if (RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS)) {
      floor.addBlock(BlockType.get(BlockType.GOLD_BLOCK), 2);
    }
    if (RogueConfig.getBoolean(RogueConfig.PRECIOUSBLOCKS)) {
      floor.addBlock(BlockType.get(BlockType.DIAMOND_BLOCK), 1);
    }

    MetaStair stair = new MetaStair(StairType.NETHERBRICK);

    MetaBlock pillar = BlockType.get(BlockType.OBSIDIAN);

    IDoor door = new Door(DoorType.IRON);

    IBlockFactory lightstone = BlockType.get(BlockType.GLOWSTONE);

    IBlockFactory liquid = BlockType.get(BlockType.LAVA_FLOWING);

    this.primary = new BlockSet(floor, walls, stair, pillar, door, lightstone, liquid);
    this.secondary = this.primary;

  }
}
