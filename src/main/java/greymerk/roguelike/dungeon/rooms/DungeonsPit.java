package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.redstone.Piston;
import greymerk.roguelike.worldgen.redstone.Torch;
import greymerk.roguelike.worldgen.shapes.RectHollow;

public class DungeonsPit extends DungeonBase {
  IWorldEditor editor;
  Random rand;
  int originX;
  int originY;
  int originZ;
  byte dungeonHeight;
  int dungeonLength;
  int dungeonWidth;
  int woolColor;
  int numChests;
  IBlockFactory blocks;
  MetaBlock air = BlockType.get(BlockType.AIR);

  public DungeonsPit() {
    super();
    dungeonHeight = 3;
    dungeonLength = 2;
    dungeonWidth = 2;
  }

  public boolean generate(IWorldEditor editor, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {

    ITheme theme = settings.getTheme();

    this.editor = editor;
    rand = inRandom;
    originX = origin.getX();
    originY = origin.getY();
    originZ = origin.getZ();

    blocks = theme.getPrimary().getWall();

    buildWalls();
    buildFloor();
    buildRoof();
    buildPit();


    for (Cardinal dir : Cardinal.directions) {
      setTrap(editor, rand, settings, dir, origin);
    }

    List<Coord> space = new ArrayList<Coord>();
    space.add(new Coord(originX - 2, originY, originZ - 2));
    space.add(new Coord(originX - 2, originY, originZ + 2));
    space.add(new Coord(originX + 2, originY, originZ - 2));
    space.add(new Coord(originX + 2, originY, originZ + 2));

    Treasure.createChests(editor, inRandom, 1, space, Dungeon.getLevel(originY));

    return true;
  }

  protected void buildWalls() {
    for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++) {
      for (int blockY = originY + dungeonHeight; blockY >= originY - 1; blockY--) {
        for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
          if (blockX == originX - dungeonLength - 1
              || blockZ == originZ - dungeonWidth - 1
              || blockX == originX + dungeonLength + 1
              || blockZ == originZ + dungeonWidth + 1) {

            if (blockY >= 0 && !editor.getBlock(new Coord(blockX, blockY - 1, blockZ)).getMaterial().isSolid()) {
              air.set(editor, new Coord(blockX, blockY, blockZ));
              continue;
            }

            if (!editor.getBlock(new Coord(blockX, blockY, blockZ)).getMaterial().isSolid()) {
              continue;
            }

            blocks.set(editor, rand, new Coord(blockX, blockY, blockZ));

          } else {
            air.set(editor, new Coord(blockX, blockY, blockZ));
          }
        }
      }
    }
  }

  protected void buildFloor() {

    for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++) {
      for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
        blocks.set(editor, rand, new Coord(blockX, originY - 1, blockZ));
      }
    }
  }

  protected void buildRoof() {
    for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++) {
      for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
        blocks.set(editor, rand, new Coord(blockX, dungeonHeight + 1, blockZ));
      }
    }
  }

  private void buildPit() {

    for (int x = originX - 2; x <= originX + 2; x++) {
      for (int z = originZ - 2; z <= originZ + 2; z++) {
        for (int y = originY - 1; y > 0; y--) {

          if (editor.isAirBlock(new Coord(x, y, z))) {
            continue;
          }

          if (y < 0 + rand.nextInt(5) && editor.getBlock(new Coord(x, y, z)).getBlock() == BlockType.get(BlockType.BEDROCK).getBlock()) {
            continue;
          }

          if (x == originX - 2
              || x == originX + 2
              || z == originZ - 2
              || z == originZ + 2) {

            blocks.set(editor, rand, new Coord(x, y, z), true, true);
            continue;

          }

          if (y < 10) {
            BlockType.get(BlockType.WATER_FLOWING).set(editor, new Coord(x, y, z));
            continue;
          }

          BlockType.get(BlockType.AIR).set(editor, new Coord(x, y, z));
        }
      }
    }
  }

  private void setTrap(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin) {
    ITheme theme = settings.getTheme();
    IBlockFactory walls = theme.getPrimary().getWall();
    MetaBlock plate = BlockType.get(BlockType.PRESSURE_PLATE_STONE);
    MetaBlock wire = BlockType.get(BlockType.REDSTONE_WIRE);
    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    start.add(dir, 3);
    start.add(Cardinal.DOWN);
    start.add(Cardinal.left(dir));
    end = new Coord(origin);
    end.add(dir, 6);
    end.add(Cardinal.UP, 3);
    end.add(Cardinal.right(dir));

    for (Coord cell : new RectHollow(start, end)) {
      if (editor.isAirBlock(cell)) {
        return;
      }
      walls.set(editor, rand, cell);
    }

    cursor = new Coord(origin);
    cursor.add(dir, 2);
    plate.set(editor, cursor);

    cursor = new Coord(origin);
    cursor.add(Cardinal.DOWN);
    cursor.add(dir, 3);
    Torch.generate(editor, Torch.REDSTONE, dir, cursor);
    cursor.add(dir);
    wire.set(editor, cursor);
    cursor.add(Cardinal.UP);
    cursor.add(dir);
    Torch.generate(editor, Torch.REDSTONE_UNLIT, Cardinal.UP, cursor);
    cursor.add(Cardinal.reverse(dir));
    cursor.add(Cardinal.UP);
    Piston.generate(editor, cursor, Cardinal.reverse(dir), true);
  }

  public int getSize() {
    return 4;
  }
}
