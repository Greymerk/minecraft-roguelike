package greymerk.roguelike.worldgen.filter;

import java.util.Random;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.IWorldEditor;

public interface IFilter {

  void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box);

}
