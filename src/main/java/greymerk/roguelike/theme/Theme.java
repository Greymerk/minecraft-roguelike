package greymerk.roguelike.theme;

import java.util.Random;

import static java.util.Optional.ofNullable;

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

  public static ITheme create(ITheme parent, ITheme child) {
    if (parent == null && child == null) {
      return null;
    }
    if (child == null) {
      return parent;
    }
    if (parent == null) {
      return child;
    }
    return new ThemeBase(
        getPrimaryBlockSet(parent, child),
        getSecondaryBlockSet(parent, child));
  }

  private static IBlockSet getPrimaryBlockSet(ITheme parent, ITheme child) {
    return newBlockSet(
        parent.getPrimary(),
        child.getPrimary());
  }

  private static IBlockSet getSecondaryBlockSet(ITheme parent, ITheme child) {
    return newBlockSet(
        parent.getSecondary(),
        child.getSecondary());
  }

  private static BlockSet newBlockSet(IBlockSet parent, IBlockSet child) {
    return new BlockSet(
        ofNullable(child.getFloor()).orElse(parent.getFloor()),
        ofNullable(child.getWall()).orElse(parent.getWall()),
        ofNullable(child.getStair()).orElse(parent.getStair()),
        ofNullable(child.getPillar()).orElse(parent.getPillar()),
        ofNullable(child.getDoor()).orElse(parent.getDoor()),
        ofNullable(child.getLightBlock()).orElse(parent.getLightBlock()),
        ofNullable(child.getLiquid()).orElse(parent.getLiquid()));
  }

  public static Theme randomTheme() {
    return values()[random.nextInt(values().length)];
  }

}
