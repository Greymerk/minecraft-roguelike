package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import java.util.Random;

public enum Theme {

  OAK,
  SPRUCE,
  CRYPT,
  MOSSY,
  MUDDY,
  NETHER,
  SANDSTONE,
  QUARTZ,
  BLING,
  CHECKER,
  RAINBOW,
  SNOW,
  JUNGLE,
  BRICK,
  DARKOAK,
  ICE,
  ENIKO,
  ENIKO2,
  ENIQUARTZ,
  ENIICE,
  TOWER,
  ETHO,
  CAVE,
  SEWER,
  ENDER,
  MINESHAFT,
  ETHOTOWER,
  PYRAMID,
  DARKHALL,
  TEMPLE,
  SANDSTONERED,
  HOUSE,
  GREY,
  PURPUR,
  HELL,
  TERRACOTTA,
  STONE,
  BUMBO;

  public static ITheme getTheme(Theme type) {

    ITheme theme;

    switch (type) {
      case OAK:
        theme = new ThemeOak();
        break;
      case SPRUCE:
        theme = new ThemeSpruce();
        break;
      case CRYPT:
        theme = new ThemeCrypt();
        break;
      case MOSSY:
        theme = new ThemeMossy();
        break;
      case MUDDY:
        theme = new ThemeMuddy();
        break;
      case NETHER:
        theme = new ThemeNether();
        break;
      case SANDSTONE:
        theme = new ThemeSandstone();
        break;
      case QUARTZ:
        theme = new ThemeQuartz();
        break;
      case BLING:
        theme = new ThemeBling();
        break;
      case CHECKER:
        theme = new ThemeChecker();
        break;
      case RAINBOW:
        theme = new ThemeRainbow();
        break;
      case SNOW:
        theme = new ThemeSnow();
        break;
      case JUNGLE:
        theme = new ThemeJungle();
        break;
      case BRICK:
        theme = new ThemeBrick();
        break;
      case DARKOAK:
        theme = new ThemeDarkOak();
        break;
      case ICE:
        theme = new ThemeIce();
        break;
      case ENIKO:
        theme = new ThemeEniko();
        break;
      case ENIKO2:
        theme = new ThemeEniko2();
        break;
      case ENIQUARTZ:
        theme = new ThemeEniQuartz();
        break;
      case ENIICE:
        theme = new ThemeEniIce();
        break;
      case TOWER:
        theme = new ThemeTower();
        break;
      case ETHO:
        theme = new ThemeEtho();
        break;
      case CAVE:
        theme = new ThemeCave();
        break;
      case SEWER:
        theme = new ThemeSewer();
        break;
      case ENDER:
        theme = new ThemeEnder();
        break;
      case MINESHAFT:
        theme = new ThemeMineShaft();
        break;
      case ETHOTOWER:
        theme = new ThemeEthoTower();
        break;
      case PYRAMID:
        theme = new ThemePyramid();
        break;
      case DARKHALL:
        theme = new ThemeDarkHall();
        break;
      case TEMPLE:
        theme = new ThemeTemple();
        break;
      case SANDSTONERED:
        theme = new ThemeSandstoneRed();
        break;
      case HOUSE:
        theme = new ThemeHouse();
        break;
      case GREY:
        theme = new ThemeGrey();
        break;
      case PURPUR:
        theme = new ThemePurpur();
        break;
      case HELL:
        theme = new ThemeHell();
        break;
      case TERRACOTTA:
        theme = new ThemeTerracotta();
        break;
      case STONE:
        theme = new ThemeStone();
        break;
      case BUMBO:
        theme = new ThemeBumbo();
        break;
      default:
        return null;
    }

    return theme;
  }

  public static ITheme create(JsonObject json) throws Exception {

    ITheme base = json.has("base")
        ? Theme.getTheme(Theme.get(json.get("base").getAsString()))
        : null;

    BlockSet primary = null;
    BlockSet secondary = null;

    // primary blocks
    if (json.has("primary")) {
      JsonObject data = json.get("primary").getAsJsonObject();
      primary = new BlockSet(data, base != null ? base.getPrimary() : null);
    }

    // secondary blocks
    if (json.has("secondary")) {
      JsonObject data = json.get("secondary").getAsJsonObject();
      secondary = new BlockSet(data, base != null ? base.getSecondary() : null);
    }

    if (base == null) {
      return new ThemeBase(primary, secondary);
    }

    return new ThemeBase((ThemeBase) base, primary, secondary);
  }

  public static ITheme create(ITheme toCopy) {
    BlockSet primary = toCopy.getPrimary() != null ? new BlockSet(toCopy.getPrimary()) : null;
    BlockSet secondary = toCopy.getSecondary() != null ? new BlockSet(toCopy.getSecondary()) : null;
    return new ThemeBase(primary, secondary);
  }

  public static ITheme create(ITheme base, ITheme other) {
    if (base == null && other == null) {
      return null;
    }

    if (other == null && base != null) {
      return create(base);
    }

    if (other != null && base == null) {
      return create(other);
    }

    BlockSet primary = other.getPrimary() != null
        ? new BlockSet(
        base.getPrimary() != null
            ? new BlockSet(base.getPrimary())
            : null,
        other.getPrimary()
    )
        : base.getPrimary() != null
            ? new BlockSet(base.getPrimary())
            : null;
    BlockSet secondary = other.getSecondary() != null
        ? new BlockSet(
        base.getPrimary() != null
            ? new BlockSet(base.getPrimary())
            : null,
        other.getSecondary()
    )
        : base.getSecondary() != null
            ? new BlockSet(base.getSecondary())
            : null;
    return new ThemeBase(primary, secondary);
  }

  public static Theme get(String name) throws Exception {
    if (!contains(name.toUpperCase())) {
      throw new Exception("No such theme: " + name);
    }

    return valueOf(name.toUpperCase());
  }

  public static boolean contains(String name) {
    for (Theme value : values()) {
      if (value.toString().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public static ITheme getRandom(Random rand) {
    return Theme.getTheme(Theme.values()[rand.nextInt(Theme.values().length)]);
  }

}
