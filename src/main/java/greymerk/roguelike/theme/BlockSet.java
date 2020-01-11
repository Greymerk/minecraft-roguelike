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
      this.walls = BlockProvider.create(json.get("walls").getAsJsonObject());
    } else if (base != null) {
      this.walls = base.getWall();
    }


    if (json.has("floor")) {
      this.floor = BlockProvider.create(json.get("floor").getAsJsonObject());
    } else if (base != null) {
      this.floor = base.getFloor();
    }

    if (json.has("stair")) {
      JsonObject stair = json.get("stair").getAsJsonObject();
      this.stair = stair.has("data")
          ? new MetaStair(new MetaBlock(stair.get("data").getAsJsonObject()))
          : new MetaStair(new MetaBlock(stair));
    } else if (base != null) {
      this.stair = base.getStair();
    }

    if (json.has("pillar")) {
      this.pillar = BlockProvider.create(json.get("pillar").getAsJsonObject());
    } else if (base != null) {
      this.pillar = base.getPillar();
    }

    if (json.has("door")) {
      this.door = new Door(json.get("door"));
    } else if (base != null) {
      this.door = base.getDoor();
    }

    if (json.has("lightblock")) {
      this.lightblock = BlockProvider.create(json.get("lightblock").getAsJsonObject());
    } else if (base != null) {
      this.lightblock = base.getLightBlock();
    }

    if (json.has("liquid")) {
      this.liquid = BlockProvider.create(json.get("liquid").getAsJsonObject());
    } else if (base != null) {
      this.liquid = base.getLiquid();
    }

  }

  public BlockSet(IBlockSet toCopy) {
    this.walls = toCopy.getWall();
    this.floor = toCopy.getFloor();
    this.stair = toCopy.getStair();
    this.pillar = toCopy.getPillar();
    this.door = toCopy.getDoor();
    this.lightblock = toCopy.getLightBlock();
    this.liquid = toCopy.getLiquid();
  }

  public BlockSet(IBlockSet base, IBlockSet other) {
    this.walls = other.getWall() != null ? other.getWall() : base.getWall();
    this.floor = other.getFloor() != null ? other.getFloor() : base.getFloor();
    this.stair = other.getStair() != null ? other.getStair() : base.getStair();
    this.pillar = other.getPillar() != null ? other.getPillar() : base.getPillar();
    this.door = other.getDoor() != null ? other.getDoor() : base.getDoor();
    this.lightblock = other.getLightBlock() != null ? other.getLightBlock() : base.getLightBlock();
    this.liquid = other.getLiquid() != null ? other.getLiquid() : base.getLiquid();
  }

  @Override
  public IBlockFactory getWall() {
    return walls != null ? walls : BlockType.get(BlockType.STONE_BRICK);
  }

  @Override
  public IStair getStair() {
    return stair != null ? this.stair : new MetaStair(StairType.STONEBRICK);
  }

  @Override
  public IBlockFactory getPillar() {
    return pillar != null ? this.pillar : this.getWall();
  }

  @Override
  public IBlockFactory getFloor() {
    return this.floor != null ? this.floor : this.getWall();
  }

  @Override
  public IDoor getDoor() {
    return this.door != null ? this.door : new Door(DoorType.OAK);
  }

  @Override
  public IBlockFactory getLightBlock() {
    return this.lightblock != null ? this.lightblock : BlockType.get(BlockType.GLOWSTONE);
  }

  @Override
  public IBlockFactory getLiquid() {
    return this.liquid != null ? this.liquid : BlockType.get(BlockType.WATER_FLOWING);
  }
}
