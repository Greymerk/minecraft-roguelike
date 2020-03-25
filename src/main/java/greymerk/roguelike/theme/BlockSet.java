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

import static java.util.Optional.ofNullable;

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
    selectWalls(json, base);
    selectFloor(json, base);
    selectStair(json, base);
    selectPillar(json, base);
    selectDoor(json, base);
    selectLightBlock(json, base);
    selectLiquid(json, base);
  }

  private void selectFloor(JsonObject json, IBlockSet base) throws Exception {
    floor = json.has("floor")
        ? BlockProvider.create(json.get("floor").getAsJsonObject())
        : ofNullable(base)
            .map(IBlockSet::getFloor)
            .orElse(null);
  }

  private void selectWalls(JsonObject json, IBlockSet base) throws Exception {
    walls = json.has("walls")
        ? BlockProvider.create(json.get("walls").getAsJsonObject())
        : ofNullable(base)
            .map(IBlockSet::getWall)
            .orElse(null);
  }

  private void selectStair(JsonObject json, IBlockSet base) throws Exception {
    stair = json.has("stair")
        ? somethingAboutStairWithData(json)
        : ofNullable(base)
            .map(IBlockSet::getStair)
            .orElse(null);
  }

  private MetaStair somethingAboutStairWithData(JsonObject json) throws Exception {
    JsonObject stairData = json.get("stair").getAsJsonObject();
    return stairData.has("data")
        ? new MetaStair(new MetaBlock(stairData.get("data").getAsJsonObject()))
        : new MetaStair(new MetaBlock(stairData));
  }

  private void selectPillar(JsonObject json, IBlockSet base) throws Exception {
    pillar = json.has("pillar")
        ? BlockProvider.create(json.get("pillar").getAsJsonObject())
        : ofNullable(base)
            .map(IBlockSet::getPillar)
            .orElse(null);
  }

  private void selectDoor(JsonObject json, IBlockSet base) throws Exception {
    door = json.has("door") ? new Door(json.get("door")) : ofNullable(base)
        .map(IBlockSet::getDoor)
        .orElse(null);
  }

  private void selectLightBlock(JsonObject json, IBlockSet base) throws Exception {
    lightblock = json.has("lightblock")
        ? BlockProvider.create(json.get("lightblock").getAsJsonObject())
        : ofNullable(base)
            .map(IBlockSet::getLightBlock)
            .orElse(null);
  }

  private void selectLiquid(JsonObject json, IBlockSet base) throws Exception {
    liquid = json.has("liquid")
        ? BlockProvider.create(json.get("liquid").getAsJsonObject())
        : ofNullable(base)
            .map(IBlockSet::getLiquid)
            .orElse(null);
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
    walls = ofNullable(child.getWall()).orElse(parent.getWall());
    floor = ofNullable(child.getFloor()).orElse(parent.getFloor());
    stair = ofNullable(child.getStair()).orElse(parent.getStair());
    pillar = ofNullable(child.getPillar()).orElse(parent.getPillar());
    door = ofNullable(child.getDoor()).orElse(parent.getDoor());
    lightblock = ofNullable(child.getLightBlock()).orElse(parent.getLightBlock());
    liquid = ofNullable(child.getLiquid()).orElse(parent.getLiquid());
  }

  @Override
  public IBlockFactory getWall() {
    return ofNullable(walls).orElse(BlockType.get(BlockType.STONE_BRICK));
  }

  @Override
  public IStair getStair() {
    return ofNullable(stair).orElse(new MetaStair(StairType.STONEBRICK));
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
    return ofNullable(door).orElse(new Door(DoorType.OAK));
  }

  @Override
  public IBlockFactory getLightBlock() {
    return ofNullable(lightblock).orElse(BlockType.get(BlockType.GLOWSTONE));
  }

  @Override
  public IBlockFactory getLiquid() {
    return ofNullable(liquid).orElse(BlockType.get(BlockType.WATER_FLOWING));
  }
}
