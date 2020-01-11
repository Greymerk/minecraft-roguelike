package greymerk.roguelike.dungeon.towers;

import java.util.Random;

import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public enum Tower {

  ROGUE,
  ENIKO,
  ETHO,
  PYRAMID,
  JUNGLE,
  WITCH,
  HOUSE,
  BUNKER,
  RUIN,
  HOLE,
  TREE,
  BUMBO;

  public static ITower get(Tower type) {

    switch (type) {
      case ROGUE:
        return new RogueTower();
      case ENIKO:
        return new EniTower();
      case ETHO:
        return new EthoTower();
      case PYRAMID:
        return new PyramidTower();
      case JUNGLE:
        return new JungleTower();
      case WITCH:
        return new WitchTower();
      case HOUSE:
        return new HouseTower();
      case BUNKER:
        return new BunkerTower();
      case RUIN:
        return new RuinTower();
      case HOLE:
        return new HoleTower();
      case TREE:
        return new TreeTower();
      case BUMBO:
        return new BumboTower();
      default:
        return new RogueTower();
    }
  }

  public static Theme getDefaultTheme(Tower type) {
    switch (type) {
      case ETHO:
        return Theme.ETHOTOWER;
      case PYRAMID:
        return Theme.PYRAMID;
      case JUNGLE:
        return Theme.JUNGLE;
      case WITCH:
        return Theme.DARKHALL;
      case HOUSE:
        return Theme.HOUSE;
      case BUMBO:
        return Theme.BUMBO;
      default:
        return Theme.OAK;
    }
  }

  public static Coord getBaseCoord(IWorldEditor editor, Coord pos) {

    Coord cursor = new Coord(pos.getX(), 128, pos.getZ());

    while (cursor.getY() > 60) {
      if (editor.validGroundBlock(cursor)) {
        break;
      }
      cursor.add(Cardinal.DOWN);
    }

    cursor.add(Cardinal.UP);

    int yOffset = cursor.getY() - pos.getY();

    if (yOffset < 14) {
      yOffset = 14;
    }

    return new Coord(pos.getX(), pos.getY() + yOffset, pos.getZ());
  }

  public static Tower get(String name) throws Exception {
    if (!contains(name.toUpperCase())) {
      throw new Exception("No such tower type: " + name);
    }

    return valueOf(name.toUpperCase());
  }

  public static boolean contains(String name) {
    for (Tower value : Tower.values()) {
      if (value.toString().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public static Tower getRandom(Random rand) {
    return Tower.values()[rand.nextInt(Tower.values().length)];
  }


}
