package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.door.Door;
import greymerk.roguelike.worldgen.blocks.door.DoorType;

public class ThemeCrypt extends ThemeBase {

  public ThemeCrypt() {

    BlockJumble stone = new BlockJumble();
    stone.addBlock(BlockType.get(BlockType.STONE_BRICK));
    stone.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED));
    stone.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY));

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(stone, 100);
    walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 10);
    walls.addBlock(BlockType.get(BlockType.GRAVEL), 5);

    MetaBlock andesite = BlockType.get(BlockType.ANDESITE);
    MetaBlock smoothAndesite = BlockType.get(BlockType.ANDESITE_POLISHED);
    BlockCheckers pillar = new BlockCheckers(andesite, smoothAndesite);

    MetaStair stair = new MetaStair(StairType.STONEBRICK);

    this.primary = new BlockSet(walls, walls, stair, pillar, new Door(DoorType.IRON));
    this.secondary = this.primary;

  }
}
