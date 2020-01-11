package greymerk.roguelike.theme;

import com.google.gson.JsonObject;

import java.util.Random;

public enum Theme {

  OAK(new ThemeOak()),
  SPRUCE(new ThemeSpruce()),
  CRYPT(new ThemeCrypt()),
  MOSSY(new ThemeMossy()),
  MUDDY(new ThemeMuddy()),
  NETHER(new ThemeNether()),
  SANDSTONE(new ThemeSandstone()),
  QUARTZ(new ThemeQuartz()),
  BLING(new ThemeBling()),
  CHECKER(new ThemeChecker()),
  RAINBOW(new ThemeRainbow()),
  SNOW(new ThemeSnow()),
  JUNGLE(new ThemeJungle()),
  BRICK(new ThemeBrick()),
  DARKOAK(new ThemeDarkOak()),
  ICE(new ThemeIce()),
  ENIKO(new ThemeEniko()),
  ENIKO2(new ThemeEniko2()),
  ENIQUARTZ(new ThemeEniQuartz()),
  ENIICE(new ThemeEniIce()),
  TOWER(new ThemeTower()),
  ETHO(new ThemeEtho()),
  CAVE(new ThemeCave()),
  SEWER(new ThemeSewer()),
  ENDER(new ThemeEnder()),
  MINESHAFT(new ThemeMineShaft()),
  ETHOTOWER(new ThemeEthoTower()),
  PYRAMID(new ThemePyramid()),
  DARKHALL(new ThemeDarkHall()),
  TEMPLE(new ThemeTemple()),
  SANDSTONERED(new ThemeSandstoneRed()),
  HOUSE(new ThemeHouse()),
  GREY(new ThemeGrey()),
  PURPUR(new ThemePurpur()),
  HELL(new ThemeHell()),
  TERRACOTTA(new ThemeTerracotta()),
  STONE(new ThemeStone()),
  BUMBO(new ThemeBumbo());

  private static final Random random = new Random();
  private final ThemeBase themeBase;

  Theme(ThemeBase themeBase) {
    this.themeBase = themeBase;
  }

  public ThemeBase getThemeBase() {
    return themeBase;
  }

  public static ITheme create(JsonObject json) throws Exception {

    ITheme base = json.has("base")
        ? get(json.get("base").getAsString()).getThemeBase()
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

    if (other == null) {
      return create(base);
    }

    if (base == null) {
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

  public static Theme randomTheme() {
    return values()[random.nextInt(values().length)];
  }

}
