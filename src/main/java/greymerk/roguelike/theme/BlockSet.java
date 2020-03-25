package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.door.Door;
import greymerk.roguelike.worldgen.blocks.door.DoorType;
import greymerk.roguelike.worldgen.blocks.door.IDoor;

import static java.util.Optional.ofNullable;

public class BlockSet implements IBlockSet {

  public static final Door OAK_DOOR = new Door(DoorType.OAK);
  public static final MetaBlock GLOWSTONE_LIGHT = BlockType.get(BlockType.GLOWSTONE);
  public static final MetaBlock STONE_BRICK_BLOCK = BlockType.get(BlockType.STONE_BRICK);
  public static final MetaStair STONE_BRICK_STAIR = new MetaStair(StairType.STONEBRICK);
  public static final MetaBlock FLOWING_WATER_BLOCK = BlockType.get(BlockType.WATER_FLOWING);

  private IBlockFactory floor;
  private IBlockFactory walls = STONE_BRICK_BLOCK;
  private IStair stair = STONE_BRICK_STAIR;
  private IBlockFactory pillar;
  private IDoor door = OAK_DOOR;
  private IBlockFactory lightBlock = GLOWSTONE_LIGHT;
  private IBlockFactory liquid = FLOWING_WATER_BLOCK;

  public BlockSet() {

  }

  public BlockSet(
      IBlockFactory floor,
      IBlockFactory walls,
      IStair stair,
      IBlockFactory pillar,
      IDoor door,
      IBlockFactory lightBlock,
      IBlockFactory liquid
  ) {
    this.floor = floor;
    this.walls = walls;
    this.stair = stair;
    this.pillar = pillar;
    this.door = door;
    this.lightBlock = lightBlock;
    this.liquid = liquid;
  }

  public BlockSet(
      IBlockFactory floor,
      IBlockFactory walls,
      IStair stair,
      IBlockFactory pillar,
      IDoor door
  ) {
    this(
        floor,
        walls,
        stair,
        pillar,
        door,
        new MetaBlock(BlockType.get(BlockType.GLOWSTONE)),
        new MetaBlock(BlockType.get(BlockType.WATER_FLOWING))
    );
  }

  public BlockSet(
      IBlockFactory floor,
      IBlockFactory walls,
      IStair stair,
      IBlockFactory pillar
  ) {
    this(
        floor,
        walls,
        stair,
        pillar,
        new Door(DoorType.get(DoorType.OAK))
    );
  }

  public BlockSet(
      IBlockFactory walls,
      IStair stair,
      IBlockFactory pillar
  ) {
    this(
        walls,
        walls,
        stair,
        pillar
    );
  }

  @Override
  public IBlockFactory getWall() {
    return walls;
  }

  @Override
  public IStair getStair() {
    return stair;
  }

  @Override
  public IBlockFactory getPillar() {
    return ofNullable(pillar).orElse(getWall());
  }

  @Override
  public IBlockFactory getFloor() {
    return ofNullable(floor).orElse(getWall());
  }

  @Override
  public IDoor getDoor() {
    return door;
  }

  @Override
  public IBlockFactory getLightBlock() {
    return lightBlock;
  }

  @Override
  public IBlockFactory getLiquid() {
    return liquid;
  }
}
