package greymerk.roguelike.treasure.loot.books;

import greymerk.roguelike.Roguelike;
import greymerk.roguelike.treasure.loot.BookBase;

public class BookStarter extends BookBase {

  public BookStarter() {
    super("greymerk", "Roguelike Dungeons");

    this.addPage(
        "Roguelike Dungeons v" + Roguelike.version + "\n"
            + Roguelike.date + "\n\n"
            + "Credits\n\n"
            + "Author: Greymerk\n\n"
            + "Bits: Drainedsoul\n\n"
            + "Ideas: Eniko @enichan"
    );
  }
}
