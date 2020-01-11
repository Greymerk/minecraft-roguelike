package greymerk.roguelike.dungeon.towers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Leaves;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.shapes.Line;
import greymerk.roguelike.worldgen.shapes.MultiShape;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import greymerk.roguelike.worldgen.shapes.Sphere;

public class TreeTower implements ITower {

  public static final Wood WOOD_TYPE = Wood.OAK;

  @Override
  public void generate(IWorldEditor editor, Random rand, ITheme theme, Coord origin) {

    Coord start;
    Coord end;
    Coord ground = Tower.getBaseCoord(editor, origin);
    Coord upstairs = new Coord(ground);
    upstairs.add(Cardinal.UP, 7);

    IBlockFactory air = BlockType.get(BlockType.AIR);
    IBlockFactory log = Log.get(WOOD_TYPE, Cardinal.UP);

    start = new Coord(ground);
    start.add(Cardinal.DOWN, 10);

    // generate the tree
    Branch tree = new Branch(rand, start);
    tree.genWood(editor, rand);
    tree.genLeaves(editor, rand);


    start = new Coord(ground);
    start.add(new Coord(-3, -3, -3));
    end = new Coord(ground);
    end.add(new Coord(3, 3, 3));
    RectSolid.fill(editor, rand, start, end, log);

    carveRoom(editor, rand, ground);
    carveRoom(editor, rand, upstairs);

    Cardinal dir = Cardinal.directions[rand.nextInt(Cardinal.directions.length)];
    start = new Coord(ground);
    end = new Coord(ground);
    end.add(Cardinal.UP);
    end.add(dir, 8);
    RectSolid.fill(editor, rand, start, end, air);

    start = new Coord(ground);
    end = new Coord(ground);
    end.add(new Coord(8, 8, 8));
    new Sphere(start, end).fill(editor, rand, log, false, true);

    start = new Coord(upstairs);
    start.add(Cardinal.DOWN);
    for (Coord p : new RectSolid(start, origin)) {
      editor.spiralStairStep(rand, p, theme.getPrimary().getStair(), theme.getPrimary().getPillar());
    }
  }

  private void carveRoom(IWorldEditor editor, Random rand, Coord origin) {
    Coord start;
    Coord end;
    IBlockFactory air = BlockType.get(BlockType.AIR);
    IBlockFactory log = Log.get(WOOD_TYPE, Cardinal.UP);
    int size = 4;

    start = new Coord(origin);
    start.add(new Coord(-(size - 1), 0, -(size - 1)));
    end = new Coord(origin);
    end.add(new Coord(size - 1, 2, size - 1));
    air.fill(editor, rand, new RectSolid(start, end));

    start = new Coord(origin);
    end = new Coord(start);
    start.add(Cardinal.UP, 2);
    end.add(new Coord(size - 1, size - 1, size - 1));
    new Sphere(start, end).fill(editor, rand, air);

    for (Cardinal dir : Cardinal.directions) {
      start = new Coord(origin);
      start.add(dir, size - 1);
      start.add(Cardinal.left(dir), size - 1);
      end = new Coord(start);
      end.add(Cardinal.UP, size + 1);
      new RectSolid(start, end).fill(editor, rand, log);
    }

    start = new Coord(origin);
    end = new Coord(origin);
    start.add(new Coord(-(size - 1), -1, -(size - 1)));
    end.add(new Coord(size - 1, -1, size - 1));
    new RectSolid(start, end).fill(editor, rand, log);
  }

  private class Branch {

    Coord start;
    Coord end;
    List<Branch> branches;
    double thickness;

    public Branch(Random rand, Coord start) {
      this.start = new Coord(start);
      this.branches = new ArrayList<Branch>();
      int counter = 7;
      double length = 12;
      this.thickness = 7;
      int mainBranches = 5;
      int density = 3;
      double noise = 0.15;
      double pitch = 0;
      double yaw = Math.PI / 2;
      this.end = getEnd(start, 4, pitch, yaw);

      for (int i = 0; i < mainBranches; ++i) {
        this.branches.add(
            new Branch(
                rand,
                new Coord(end),
                counter,
                length,
                thickness,
                4,
                noise,
                pitch + ((Math.PI * 2 / density) * i),
                yaw + (rand.nextDouble() - 0.5) * noise
            )
        );
      }
    }

    public Branch(Random rand, Coord start, int counter, double length, double thickness, int density, double noise, double pitch, double yaw) {

      this.start = new Coord(start);
      this.thickness = thickness < 1 ? 1 : thickness;
      this.branches = new ArrayList<Branch>();
      this.end = getEnd(start, length, pitch, yaw);

      if (counter <= 0) {
        return;
      }

      for (int i = 0; i < rand.nextInt(density) + 1; ++i) {
        this.branches.add(
            new Branch(
                rand,
                new Coord(end),
                counter - (rand.nextInt(2) + 1),
                length < 1 ? 1 : length * 0.88,
                thickness * 0.72,
                density,
                noise + (thickness / 5) * 0.55,
                pitch + (rand.nextDouble() - 0.5) * noise,
                yaw + (rand.nextDouble() - 0.5) * noise
            )
        );
      }
    }

    public void genWood(IWorldEditor editor, Random rand) {
      MetaBlock log = Log.get(WOOD_TYPE, start.dirTo(end));
      for (Branch b : this.branches) {
        b.genWood(editor, rand);
      }

      if (thickness == 1) {
        new Line(start, end).fill(editor, rand, log, true, false);
      } else if (thickness > 1) {
        MultiShape shape = new MultiShape();
        for (Coord pos : new Line(start, end)) {
          Coord s = new Coord(pos);
          Coord e = new Coord(s);
          e.add(new Coord((int) thickness, (int) thickness, (int) thickness));
          shape.addShape(new Sphere(s, e));
        }
        shape.fill(editor, rand, log);
      }
    }

    public void genLeaves(IWorldEditor editor, Random rand) {
      MultiShape leafShape = new MultiShape();
      getLeafShape(leafShape, rand);

      BlockWeightedRandom leaves = new BlockWeightedRandom();
      leaves.addBlock(Leaves.get(WOOD_TYPE, true), 5);
      leaves.addBlock(BlockType.get(BlockType.AIR), 1);

      leafShape.fill(editor, rand, leaves, true, false);
    }

    public void getLeafShape(MultiShape leaves, Random rand) {
      if (!this.branches.isEmpty()) {
        for (Branch b : this.branches) {
          b.getLeafShape(leaves, rand);
        }
        return;
      }

      Coord s = new Coord(end);
      Coord e = new Coord(s);
      final int size = 2;
      final int noise = 2;
      e.add(new Coord(size + rand.nextInt(noise), size + rand.nextInt(noise), size + rand.nextInt(noise)));
      leaves.addShape(new Sphere(s, e));
    }

    private Coord getEnd(Coord start, double length, double pitch, double yaw) {
      Coord end = new Coord(start);
      Coord offset = new Coord(
          (int) (Math.cos(pitch) * Math.cos(yaw) * length),
          (int) (Math.sin(yaw) * length),
          (int) (Math.sin(pitch) * Math.cos(yaw) * length)
      );

      end = new Coord(start);
      end.add(offset);
      return end;
    }
  }
}