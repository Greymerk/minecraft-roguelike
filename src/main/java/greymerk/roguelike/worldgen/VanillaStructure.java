package greymerk.roguelike.worldgen;

public enum VanillaStructure {

  STRONGHOLD,
  MANSION,
  MONUMENT,
  VILLAGE,
  MINESHAFT,
  TEMPLE;

  public static String getName(VanillaStructure type) {
    switch (type) {
      case STRONGHOLD:
        return "Stronghold";
      case MANSION:
        return "Mansion";
      case MONUMENT:
        return "Monument";
      case VILLAGE:
        return "Village";
      case MINESHAFT:
        return "Mineshaft";
      case TEMPLE:
        return "Temple";
      default:
        return null;
    }
  }

  public static VanillaStructure getType(String name) {
    return VanillaStructure.valueOf(name.toUpperCase());
  }
}


