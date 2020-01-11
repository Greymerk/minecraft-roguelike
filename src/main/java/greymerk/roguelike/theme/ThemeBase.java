package greymerk.roguelike.theme;

public class ThemeBase implements ITheme {

  protected IBlockSet primary;
  protected IBlockSet secondary;

  public ThemeBase(IBlockSet primary, IBlockSet secondary) {
    this.primary = primary;
    this.secondary = secondary;
  }

  public ThemeBase(ThemeBase base, IBlockSet primary, IBlockSet secondary) {
    this.primary = primary == null ? base.primary : primary;
    this.secondary = secondary == null ? base.secondary : secondary;
  }

  public ThemeBase() {
  }

  @Override
  public IBlockSet getPrimary() {
    return this.primary != null ? primary : new BlockSet();
  }

  @Override
  public IBlockSet getSecondary() {
    return this.secondary != null ? secondary : primary;
  }

}
