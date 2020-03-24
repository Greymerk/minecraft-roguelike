package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import greymerk.roguelike.worldgen.BlockProvider;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.door.Door;
import greymerk.roguelike.worldgen.blocks.door.DoorType;
import greymerk.roguelike.worldgen.blocks.door.IDoor;

public class BlockSet implements IBlockSet {

  private IBlockFactory floor;
  private IBlockFactory walls;
  private IStair stair;
  private IBlockFactory pillar;
  private IDoor door;
  private IBlockFactory lightblock;
  private IBlockFactory liquid;

  public BlockSet() {

  }

  public BlockSet(IBlockFactory floor, IBlockFactory walls, IStair stair, IBlockFactory pillar,
      IDoor door, IBlockFactory lightblock, IBlockFactory liquid) {

    this.floor = floor;
    this.walls = walls;
    this.stair = stair;
    this.pillar = pillar;
    this.door = door;
    this.lightblock = lightblock;
    this.liquid = liquid;

  }

  public BlockSet(IBlockFactory floor, IBlockFactory walls, IStair stair, IBlockFactory pillar,
      IDoor door) {
    this(floor, walls, stair, pillar, door,
        new MetaBlock(BlockType.get(BlockType.GLOWSTONE)),
        new MetaBlock(BlockType.get(BlockType.WATER_FLOWING))
    );
  }

  public BlockSet(IBlockFactory floor, IBlockFactory walls, IStair stair, IBlockFactory pillar) {
    this(floor, walls, stair, pillar,
        new Door(DoorType.get(DoorType.OAK))
    );
  }


  public BlockSet(IBlockFactory walls, IStair stair, IBlockFactory pillar) {
    this(walls, walls, stair, pillar);
  }

  public BlockSet(JsonObject json, IBlockSet base) throws Exception {


    if (json.has("walls")) {
      walls = BlockProvider.create(json.get("walls").getAsJsonObject());
    } else if (base != null) {
      walls = base.getWall();
    }


    if (json.has("floor")) {
      floor = BlockProvider.create(json.get("floor").getAsJsonObject());
    } else if (base != null) {
      floor = base.getFloor();
    }

    if (json.has("stair")) {
      JsonObject stair = json.get("stair").getAsJsonObject();
      this.stair = stair.has("data")
          ? new MetaStair(new MetaBlock(stair.get("data").getAsJsonObject()))
          : new MetaStair(new MetaBlock(stair));
    } else if (base != null) {
      stair = base.getStair();
    }

    if (json.has("pillar")) {
      pillar = BlockProvider.create(json.get("pillar").getAsJsonObject());
    } else if (base != null) {
      pillar = base.getPillar();
    }

    if (json.has("door")) {
      door = new Door(json.get("door"));
    } else if (base != null) {
      door = base.getDoor();
    }

    if (json.has("lightblock")) {
      lightblock = BlockProvider.create(json.get("lightblock").getAsJsonObject());
    } else if (base != null) {
      lightblock = base.getLightBlock();
    }

    if (json.has("liquid")) {
      liquid = BlockProvider.create(json.get("liquid").getAsJsonObject());
    } else if (base != null) {
      liquid = base.getLiquid();
    }

  }

  public BlockSet(IBlockSet toCopy) {
    walls = toCopy.getWall();
    floor = toCopy.getFloor();
    stair = toCopy.getStair();
    pillar = toCopy.getPillar();
    door = toCopy.getDoor();
    lightblock = toCopy.getLightBlock();
    liquid = toCopy.getLiquid();
  }

  public BlockSet(IBlockSet parent, IBlockSet child) {
    walls = child.getWall() != null ? child.getWall() : parent.getWall();
    floor = child.getFloor() != null ? child.getFloor() : parent.getFloor();
    stair = child.getStair() != null ? child.getStair() : parent.getStair();
    pillar = child.getPillar() != null ? child.getPillar() : parent.getPillar();
    door = child.getDoor() != null ? child.getDoor() : parent.getDoor();
    lightblock = child.getLightBlock() != null ? child.getLightBlock() : parent.getLightBlock();
    liquid = child.getLiquid() != null ? child.getLiquid() : parent.getLiquid();
  }

  @Override
  public IBlockFactory getWall() {
    return walls != null ? walls : BlockType.get(BlockType.STONE_BRICK);
  }

  @Override
  public IStair getStair() {
    return stair != null ? stair : new MetaStair(StairType.STONEBRICK);
  }

  @Override
  public IBlockFactory getPillar() {
    return pillar != null ? pillar : getWall();
  }

  @Override
  public IBlockFactory getFloor() {
    return floor != null ? floor : getWall();
  }

  @Override
  public IDoor getDoor() {
    return door != null ? door : new Door(DoorType.OAK);
  }

  @Override
  public IBlockFactory getLightBlock() {
    return lightblock != null ? lightblock : BlockType.get(BlockType.GLOWSTONE);
  }

  @Override
  public IBlockFactory getLiquid() {
    return liquid != null ? liquid : BlockType.get(BlockType.WATER_FLOWING);
  }
}
