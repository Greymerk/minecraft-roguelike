package greymerk.roguelike.treasure.loot.books;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import greymerk.roguelike.treasure.loot.BookBase;
import greymerk.roguelike.worldgen.IWorldEditor;

import static org.apache.commons.lang3.StringUtils.abbreviate;

public class BookStatistics extends BookBase {

  public BookStatistics(IWorldEditor editor) {
    super("greymerk", "Statistics");

    for (String page : getPages(editor)) {
      this.addPage(page);
    }
  }

  private List<String> getPages(IWorldEditor editor) {
    List<String> pages = new ArrayList<>();
    Map<Block, Integer> stats = editor.getStats();

    int counter = 0;
    StringBuilder page = new StringBuilder();
    for (Block type : stats.keySet()) {
      int count = stats.get(type);
      String name = abbreviate(type.getLocalizedName(), 16);
      String line = name + " : " + count + "\n";
      page.append(line);
      ++counter;
      if (counter == 12) {
        counter = 0;
        pages.add(page.toString());
        page = new StringBuilder();
      }
    }
    pages.add(page.toString());
    return pages;
  }
}
