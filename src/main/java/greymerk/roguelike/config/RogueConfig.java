package greymerk.roguelike.config;


import net.minecraft.util.Tuple;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public enum RogueConfig {

  DONATURALSPAWN,
  SPAWNFREQUENCY,
  GENEROUS,
  MOBDROPS,
  DIMENSIONWL,
  DIMENSIONBL,
  PRECIOUSBLOCKS,
  LOOTING,
  UPPERLIMIT,
  LOWERLIMIT,
  ROGUESPAWNERS,
  ENCASE,
  FURNITURE,
  RANDOM,
  SPAWNBUILTIN,
  SPAWNCHANCE;

  public static final String configDirName = "config/roguelike_dungeons";
  public static final String configFileName = "roguelike.cfg";

  public static boolean testing = false;
  private static ConfigFile instance = null;

  public static String getName(RogueConfig option) {
    switch (option) {
      case DONATURALSPAWN:
        return "doNaturalSpawn";
      case SPAWNFREQUENCY:
        return "spawnFrequency";
      case SPAWNCHANCE:
        return "spawnChance";
      case GENEROUS:
        return "generous";
      case DIMENSIONWL:
        return "dimensionWL";
      case DIMENSIONBL:
        return "dimensionBL";
      case PRECIOUSBLOCKS:
        return "preciousBlocks";
      case LOOTING:
        return "looting";
      case UPPERLIMIT:
        return "upperLimit";
      case LOWERLIMIT:
        return "lowerLimit";
      case ROGUESPAWNERS:
        return "rogueSpawners";
      case ENCASE:
        return "encase";
      case FURNITURE:
        return "furniture";
      case RANDOM:
        return "random";
      case SPAWNBUILTIN:
        return "doBuiltinSpawn";
      default:
        return null;
    }
  }

  public static Tuple<String, ?> getDefault(RogueConfig option) {
    switch (option) {
      case DONATURALSPAWN:
        return new Tuple<>(getName(option), true);
      case SPAWNCHANCE:
        return new Tuple<>(getName(option), 1.0);
      case SPAWNFREQUENCY:
        return new Tuple<>(getName(option), 10);
      case GENEROUS:
        return new Tuple<>(getName(option), true);
      case DIMENSIONWL:
        List<Integer> bl = new ArrayList<>();
        bl.add(0);
        return new Tuple<>(getName(option), bl);
      case DIMENSIONBL:
        return new Tuple<String, List<Integer>>(getName(option), new ArrayList<>());
      case PRECIOUSBLOCKS:
        return new Tuple<>(getName(option), true);
      case LOOTING:
        return new Tuple<>(getName(option), 0.085D);
      case UPPERLIMIT:
        return new Tuple<>(getName(option), 100);
      case LOWERLIMIT:
        return new Tuple<>(getName(option), 60);
      case ROGUESPAWNERS:
        return new Tuple<>(getName(option), true);
      case ENCASE:
        return new Tuple<>(getName(option), false);
      case FURNITURE:
        return new Tuple<>(getName(option), true);
      case RANDOM:
        return new Tuple<>(getName(option), false);
      case SPAWNBUILTIN:
        return new Tuple<>(getName(option), true);
      default:
        return null;
    }
  }

  @SuppressWarnings("unchecked")
  private static void setDefaults() {
    if (!instance.ContainsKey(getName(DONATURALSPAWN))) {
      setBoolean(DONATURALSPAWN, (Boolean) getDefault(DONATURALSPAWN).getSecond());
    }
    if (!instance.ContainsKey(getName(SPAWNFREQUENCY))) {
      setInt(SPAWNFREQUENCY, (Integer) getDefault(SPAWNFREQUENCY).getSecond());
    }
    if (!instance.ContainsKey(getName(SPAWNCHANCE))) {
      setDouble(SPAWNCHANCE, (Double) getDefault(SPAWNCHANCE).getSecond());
    }
    if (!instance.ContainsKey(getName(GENEROUS))) {
      setBoolean(GENEROUS, (Boolean) getDefault(GENEROUS).getSecond());
    }
    if (!instance.ContainsKey(getName(DIMENSIONWL))) {
      setIntList(DIMENSIONWL, (List<Integer>) getDefault(DIMENSIONWL).getSecond());
    }
    if (!instance.ContainsKey(getName(DIMENSIONBL))) {
      setIntList(DIMENSIONBL, (List<Integer>) getDefault(DIMENSIONBL).getSecond());
    }
    if (!instance.ContainsKey(getName(PRECIOUSBLOCKS))) {
      setBoolean(PRECIOUSBLOCKS, (Boolean) getDefault(PRECIOUSBLOCKS).getSecond());
    }
    if (!instance.ContainsKey(getName(LOOTING))) {
      setDouble(LOOTING, (Double) getDefault(LOOTING).getSecond());
    }
    if (!instance.ContainsKey(getName(UPPERLIMIT))) {
      setInt(UPPERLIMIT, (Integer) getDefault(UPPERLIMIT).getSecond());
    }
    if (!instance.ContainsKey(getName(LOWERLIMIT))) {
      setInt(LOWERLIMIT, (Integer) getDefault(LOWERLIMIT).getSecond());
    }
    if (!instance.ContainsKey(getName(ROGUESPAWNERS))) {
      setBoolean(ROGUESPAWNERS, (Boolean) getDefault(ROGUESPAWNERS).getSecond());
    }
    if (!instance.ContainsKey(getName(ENCASE))) {
      setBoolean(ENCASE, (Boolean) getDefault(ENCASE).getSecond());
    }
    if (!instance.ContainsKey(getName(FURNITURE))) {
      setBoolean(FURNITURE, (Boolean) getDefault(FURNITURE).getSecond());
    }
    if (!instance.ContainsKey(getName(RANDOM))) {
      setBoolean(RANDOM, (Boolean) getDefault(RANDOM).getSecond());
    }
    if (!instance.ContainsKey(getName(SPAWNBUILTIN))) {
      setBoolean(SPAWNBUILTIN, (Boolean) getDefault(SPAWNBUILTIN).getSecond());
    }
  }

  public static double getDouble(RogueConfig option) {
    if (testing) {
      return (Double) getDefault(option).getSecond();
    }
    reload(false);
    Tuple<String, ?> def = getDefault(option);
    return instance.GetDouble(getName(option), (Double) def.getSecond());
  }

  public static void setDouble(RogueConfig option, double value) {
    reload(false);
    instance.Set(getName(option), value);
  }

  public static boolean getBoolean(RogueConfig option) {
    if (testing) {
      return (Boolean) getDefault(option).getSecond();
    }
    reload(false);
    Tuple<String, ?> def = getDefault(option);
    return instance.GetBoolean(getName(option), (Boolean) def.getSecond());
  }

  public static void setBoolean(RogueConfig option, Boolean value) {
    reload(false);
    instance.Set(getName(option), value);
  }

  public static int getInt(RogueConfig option) {
    if (testing) {
      return (Integer) getDefault(option).getSecond();
    }
    reload(false);
    Tuple<String, ?> def = getDefault(option);
    return instance.GetInteger(def.getFirst(), (Integer) def.getSecond());
  }

  public static void setInt(RogueConfig option, int value) {
    reload(false);
    Tuple<String, ?> def = getDefault(option);
    instance.Set(def.getFirst(), value);
  }

  @SuppressWarnings("unchecked")
  public static List<Integer> getIntList(RogueConfig option) {
    if (testing) {
      return (ArrayList<Integer>) getDefault(option).getSecond();
    }
    reload(false);
    Tuple<String, ?> def = getDefault(option);
    return instance.GetListInteger(def.getFirst(), (ArrayList<Integer>) def.getSecond());
  }

  public static void setIntList(RogueConfig option, List<Integer> value) {
    reload(false);
    Tuple<String, ?> def = getDefault(option);
    instance.Set(def.getFirst(), value);
  }

  private static void init() {

    if (testing) {
      return;
    }

    // make sure file exists
    File configDir = new File(configDirName);
    if (!configDir.exists()) {
      configDir.mkdir();
    }

    File cfile = new File(configDirName + "/" + configFileName);

    if (!cfile.exists()) {
      try {
        cfile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // read in configs
    try {
      instance = new ConfigFile(configDirName + "/" + configFileName, new INIParser());
    } catch (Exception e) {
      e.printStackTrace();
    }

    setDefaults();

    try {
      instance.Write();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void reload(boolean force) {
    if (instance == null || force) {
      init();
    }
  }
}
