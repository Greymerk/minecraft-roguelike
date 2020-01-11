package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockCheckers extends BlockBase {

  private IBlockFactory fillOne;
  private IBlockFactory fillTwo;
  private Coord offset;


  public BlockCheckers(IBlockFactory fillOne, IBlockFactory fillTwo, Coord offset) {
    this.fillOne = fillOne;
    this.fillTwo = fillTwo;
    this.offset = new Coord(offset);
  }

  public BlockCheckers(IBlockFactory fillOne, IBlockFactory fillTwo) {
    this(fillOne, fillTwo, new Coord(0, 0, 0));
  }

  public BlockCheckers(JsonElement json) throws Exception {
    JsonArray arr = (JsonArray) json;
    List<IBlockFactory> blocks = new ArrayList<>();

    for (JsonElement entry : arr) {
      blocks.add(BlockProvider.create(entry.getAsJsonObject()));
    }

    this.fillOne = blocks.get(0);
    this.fillTwo = blocks.get(1);
  }

  @Override
  public boolean set(IWorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {

    int x = origin.getX() - this.offset.getX();
    int y = origin.getY() - this.offset.getY();
    int z = origin.getZ() - this.offset.getY();

    if (x % 2 == 0) {
      if (z % 2 == 0) {
        if (y % 2 == 0) {
          return fillOne.set(editor, rand, new Coord(origin), fillAir, replaceSolid);
        } else {
          return fillTwo.set(editor, rand, new Coord(origin), fillAir, replaceSolid);
        }
      } else {
        if (y % 2 == 0) {
          return fillTwo.set(editor, rand, new Coord(origin), fillAir, replaceSolid);
        } else {
          return fillOne.set(editor, rand, new Coord(origin), fillAir, replaceSolid);
        }
      }
    } else {
      if (z % 2 == 0) {
        if (y % 2 == 0) {
          return fillTwo.set(editor, rand, new Coord(origin), fillAir, replaceSolid);
        } else {
          return fillOne.set(editor, rand, new Coord(origin), fillAir, replaceSolid);
        }
      } else {
        if (y % 2 == 0) {
          return fillOne.set(editor, rand, new Coord(origin), fillAir, replaceSolid);
        } else {
          return fillTwo.set(editor, rand, new Coord(origin), fillAir, replaceSolid);
        }
      }
    }
  }
}
