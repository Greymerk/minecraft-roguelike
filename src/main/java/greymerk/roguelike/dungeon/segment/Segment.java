package greymerk.roguelike.dungeon.segment;

import java.util.Random;

import greymerk.roguelike.dungeon.segment.part.SegmentAnkh;
import greymerk.roguelike.dungeon.segment.part.SegmentArch;
import greymerk.roguelike.dungeon.segment.part.SegmentBooks;
import greymerk.roguelike.dungeon.segment.part.SegmentCave;
import greymerk.roguelike.dungeon.segment.part.SegmentChest;
import greymerk.roguelike.dungeon.segment.part.SegmentDoor;
import greymerk.roguelike.dungeon.segment.part.SegmentFireArch;
import greymerk.roguelike.dungeon.segment.part.SegmentFirePlace;
import greymerk.roguelike.dungeon.segment.part.SegmentFlowers;
import greymerk.roguelike.dungeon.segment.part.SegmentInset;
import greymerk.roguelike.dungeon.segment.part.SegmentJungle;
import greymerk.roguelike.dungeon.segment.part.SegmentLamp;
import greymerk.roguelike.dungeon.segment.part.SegmentMineShaft;
import greymerk.roguelike.dungeon.segment.part.SegmentMossyArch;
import greymerk.roguelike.dungeon.segment.part.SegmentMushrooms;
import greymerk.roguelike.dungeon.segment.part.SegmentNetherArch;
import greymerk.roguelike.dungeon.segment.part.SegmentNetherLava;
import greymerk.roguelike.dungeon.segment.part.SegmentNetherStripes;
import greymerk.roguelike.dungeon.segment.part.SegmentNetherWart;
import greymerk.roguelike.dungeon.segment.part.SegmentPlant;
import greymerk.roguelike.dungeon.segment.part.SegmentPrisonCell;
import greymerk.roguelike.dungeon.segment.part.SegmentSewer;
import greymerk.roguelike.dungeon.segment.part.SegmentSewerArch;
import greymerk.roguelike.dungeon.segment.part.SegmentSewerDoor;
import greymerk.roguelike.dungeon.segment.part.SegmentSewerDrain;
import greymerk.roguelike.dungeon.segment.part.SegmentShelf;
import greymerk.roguelike.dungeon.segment.part.SegmentSilverfish;
import greymerk.roguelike.dungeon.segment.part.SegmentSkull;
import greymerk.roguelike.dungeon.segment.part.SegmentSpawner;
import greymerk.roguelike.dungeon.segment.part.SegmentSquareArch;
import greymerk.roguelike.dungeon.segment.part.SegmentTomb;
import greymerk.roguelike.dungeon.segment.part.SegmentTrap;
import greymerk.roguelike.dungeon.segment.part.SegmentWall;
import greymerk.roguelike.dungeon.segment.part.SegmentWheat;

public enum Segment {

  ARCH,
  FIREARCH,
  FIREPLACE,
  SHELF,
  INSET,
  MOSSYARCH,
  MUSHROOM,
  NETHERARCH,
  NETHERSTRIPE,
  NETHERWART,
  NETHERLAVA,
  JUNGLE,
  BOOKS,
  SPAWNER,
  WHEAT,
  TOMB,
  CHEST,
  SILVERFISH,
  SKULL,
  FLOWERS,
  DOOR,
  ANKH,
  CAVE,
  SEWER,
  SEWERARCH,
  SEWERDOOR,
  SEWERDRAIN,
  MINESHAFT,
  LAMP,
  ARROW,
  SQUAREARCH,
  CELL,
  WALL,
  PLANT;

  public static Segment[] segments = {
      FIREPLACE, SHELF, INSET, MOSSYARCH, MUSHROOM,
      NETHERSTRIPE, NETHERWART, NETHERLAVA, JUNGLE, BOOKS, SPAWNER,
      WHEAT, TOMB, CHEST, SILVERFISH, SKULL, FLOWERS, DOOR, ANKH, CAVE,
      SEWER, SEWERARCH, SEWERDOOR, SEWERDRAIN, MINESHAFT, LAMP, ARROW, SQUAREARCH,
      CELL, WALL, PLANT
  };

  public static ISegment getSegment(Segment choice) {

    switch (choice) {
      case ARCH:
        return new SegmentArch();
      case FIREARCH:
        return new SegmentFireArch();
      case FIREPLACE:
        return new SegmentFirePlace();
      case SHELF:
        return new SegmentShelf();
      case INSET:
        return new SegmentInset();
      case MOSSYARCH:
        return new SegmentMossyArch();
      case MUSHROOM:
        return new SegmentMushrooms();
      case NETHERARCH:
        return new SegmentNetherArch();
      case NETHERSTRIPE:
        return new SegmentNetherStripes();
      case NETHERWART:
        return new SegmentNetherWart();
      case NETHERLAVA:
        return new SegmentNetherLava();
      case JUNGLE:
        return new SegmentJungle();
      case BOOKS:
        return new SegmentBooks();
      case SPAWNER:
        return new SegmentSpawner();
      case WHEAT:
        return new SegmentWheat();
      case TOMB:
        return new SegmentTomb();
      case CHEST:
        return new SegmentChest();
      case SILVERFISH:
        return new SegmentSilverfish();
      case SKULL:
        return new SegmentSkull();
      case FLOWERS:
        return new SegmentFlowers();
      case DOOR:
        return new SegmentDoor();
      case ANKH:
        return new SegmentAnkh();
      case CAVE:
        return new SegmentCave();
      case SEWER:
        return new SegmentSewer();
      case SEWERARCH:
        return new SegmentSewerArch();
      case SEWERDOOR:
        return new SegmentSewerDoor();
      case SEWERDRAIN:
        return new SegmentSewerDrain();
      case MINESHAFT:
        return new SegmentMineShaft();
      case LAMP:
        return new SegmentLamp();
      case ARROW:
        return new SegmentTrap();
      case SQUAREARCH:
        return new SegmentSquareArch();
      case CELL:
        return new SegmentPrisonCell();
      case WALL:
        return new SegmentWall();
      case PLANT:
        return new SegmentPlant();
    }

    return null;

  }

  public static Segment getRandom(Random rand) {
    return segments[rand.nextInt(segments.length)];
  }
}
