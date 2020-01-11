package greymerk.roguelike.dungeon.base;


import java.util.Random;

import greymerk.roguelike.dungeon.rooms.DungeonAshlea;
import greymerk.roguelike.dungeon.rooms.DungeonAvidya;
import greymerk.roguelike.dungeon.rooms.DungeonBTeam;
import greymerk.roguelike.dungeon.rooms.DungeonBedRoom;
import greymerk.roguelike.dungeon.rooms.DungeonBlaze;
import greymerk.roguelike.dungeon.rooms.DungeonCorner;
import greymerk.roguelike.dungeon.rooms.DungeonDarkHall;
import greymerk.roguelike.dungeon.rooms.DungeonEniko;
import greymerk.roguelike.dungeon.rooms.DungeonEtho;
import greymerk.roguelike.dungeon.rooms.DungeonFirework;
import greymerk.roguelike.dungeon.rooms.DungeonLab;
import greymerk.roguelike.dungeon.rooms.DungeonLibrary;
import greymerk.roguelike.dungeon.rooms.DungeonLinker;
import greymerk.roguelike.dungeon.rooms.DungeonLinkerTop;
import greymerk.roguelike.dungeon.rooms.DungeonMess;
import greymerk.roguelike.dungeon.rooms.DungeonObsidian;
import greymerk.roguelike.dungeon.rooms.DungeonOssuary;
import greymerk.roguelike.dungeon.rooms.DungeonPyramidCorner;
import greymerk.roguelike.dungeon.rooms.DungeonPyramidSpawner;
import greymerk.roguelike.dungeon.rooms.DungeonPyramidTomb;
import greymerk.roguelike.dungeon.rooms.DungeonReward;
import greymerk.roguelike.dungeon.rooms.DungeonStorage;
import greymerk.roguelike.dungeon.rooms.DungeonTreetho;
import greymerk.roguelike.dungeon.rooms.DungeonsBrick;
import greymerk.roguelike.dungeon.rooms.DungeonsCreeperDen;
import greymerk.roguelike.dungeon.rooms.DungeonsCrypt;
import greymerk.roguelike.dungeon.rooms.DungeonsEnchant;
import greymerk.roguelike.dungeon.rooms.DungeonsEnder;
import greymerk.roguelike.dungeon.rooms.DungeonsFire;
import greymerk.roguelike.dungeon.rooms.DungeonsMusic;
import greymerk.roguelike.dungeon.rooms.DungeonsNetherBrick;
import greymerk.roguelike.dungeon.rooms.DungeonsNetherBrickFortress;
import greymerk.roguelike.dungeon.rooms.DungeonsPit;
import greymerk.roguelike.dungeon.rooms.DungeonsPrison;
import greymerk.roguelike.dungeon.rooms.DungeonsSlime;
import greymerk.roguelike.dungeon.rooms.DungeonsSmithy;
import greymerk.roguelike.dungeon.rooms.DungeonsSpiderNest;
import greymerk.roguelike.dungeon.rooms.DungeonsWood;


public enum DungeonRoom {

  BRICK,
  CREEPER,
  CRYPT,
  ENCHANT,
  ENDER,
  FIRE,
  MUSIC,
  NETHER,
  NETHERFORT,
  PIT,
  PRISON,
  SLIME,
  SMITH,
  SPIDER,
  CAKE,
  LAB,
  CORNER,
  MESS,
  ETHO,
  ENIKO,
  BTEAM,
  OSSUARY,
  OBSIDIAN,
  AVIDYA,
  STORAGE,
  ASHLEA,
  FIREWORK,
  BEDROOM,
  REWARD,
  LIBRARY,
  PYRAMIDTOMB,
  DARKHALL,
  TREETHO,
  LINKER,
  LINKERTOP,
  PYRAMIDSPAWNER,
  PYRAMIDCORNER,
  BLAZE;

  public static DungeonRoom[] intersectionRooms = {
      BRICK, CREEPER, CRYPT, ENDER, FIRE, MUSIC, NETHER, NETHERFORT, PIT, PRISON,
      SLIME, SPIDER, CAKE, LAB, MESS, OSSUARY, OBSIDIAN, STORAGE, PYRAMIDTOMB,
      DARKHALL, PYRAMIDSPAWNER, PYRAMIDCORNER, BLAZE
  };

  public static DungeonRoom[] secrets = {
      ENCHANT, SMITH, CAKE, BEDROOM
  };

  public static IDungeonRoom getInstance(DungeonRoom choice) {
    switch (choice) {
      case BRICK:
        return new DungeonsBrick();
      case CREEPER:
        return new DungeonsCreeperDen();
      case CRYPT:
        return new DungeonsCrypt();
      case ENCHANT:
        return new DungeonsEnchant();
      case ENDER:
        return new DungeonsEnder();
      case FIRE:
        return new DungeonsFire();
      case MUSIC:
        return new DungeonsMusic();
      case NETHER:
        return new DungeonsNetherBrick();
      case NETHERFORT:
        return new DungeonsNetherBrickFortress();
      case PIT:
        return new DungeonsPit();
      case PRISON:
        return new DungeonsPrison();
      case SLIME:
        return new DungeonsSlime();
      case SMITH:
        return new DungeonsSmithy();
      case SPIDER:
        return new DungeonsSpiderNest();
      case CAKE:
        return new DungeonsWood();
      case LAB:
        return new DungeonLab();
      case CORNER:
        return new DungeonCorner();
      case MESS:
        return new DungeonMess();
      case ETHO:
        return new DungeonEtho();
      case ENIKO:
        return new DungeonEniko();
      case BTEAM:
        return new DungeonBTeam();
      case OSSUARY:
        return new DungeonOssuary();
      case OBSIDIAN:
        return new DungeonObsidian();
      case AVIDYA:
        return new DungeonAvidya();
      case STORAGE:
        return new DungeonStorage();
      case ASHLEA:
        return new DungeonAshlea();
      case FIREWORK:
        return new DungeonFirework();
      case BEDROOM:
        return new DungeonBedRoom();
      case REWARD:
        return new DungeonReward();
      case LIBRARY:
        return new DungeonLibrary();
      case PYRAMIDTOMB:
        return new DungeonPyramidTomb();
      case DARKHALL:
        return new DungeonDarkHall();
      case TREETHO:
        return new DungeonTreetho();
      case LINKER:
        return new DungeonLinker();
      case LINKERTOP:
        return new DungeonLinkerTop();
      case PYRAMIDSPAWNER:
        return new DungeonPyramidSpawner();
      case PYRAMIDCORNER:
        return new DungeonPyramidCorner();
      case BLAZE:
        return new DungeonBlaze();
      default:
        return null;
    }
  }

  public static boolean contains(String name) {
    for (DungeonRoom value : values()) {
      if (value.toString().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public static DungeonRoom getRandomRoom(Random rand) {
    return intersectionRooms[rand.nextInt(intersectionRooms.length)];
  }

  public static DungeonRoom getRandomSecret(Random rand) {
    return secrets[rand.nextInt(secrets.length)];
  }
}
