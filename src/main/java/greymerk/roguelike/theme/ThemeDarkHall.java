package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.door.Door;
import greymerk.roguelike.worldgen.blocks.door.DoorType;
import greymerk.roguelike.worldgen.blocks.door.IDoor;

public class ThemeDarkHall extends ThemeBase {

  public ThemeDarkHall() {

    MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 30);
    walls.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 10);
    walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 20);
    walls.addBlock(cracked, 10);
    walls.addBlock(BlockType.get(BlockType.GRAVEL), 5);
    MetaStair stair = new MetaStair(StairType.STONEBRICK);
    MetaBlock pillar = BlockType.get(BlockType.STONE_BRICK_MOSSY);

    MetaBlock walls2 = Wood.getPlank(Wood.DARKOAK);
    MetaStair stair2 = new MetaStair(StairType.DARKOAK);
    MetaBlock pillar2 = Log.getLog(Wood.DARKOAK);

    IDoor door = new Door(DoorType.DARKOAK);

    this.primary = new BlockSet(walls, walls, stair, pillar, door);
    this.secondary = new BlockSet(walls2, walls2, stair2, pillar2, door);

  }
}
