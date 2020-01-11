package greymerk.roguelike.treasure.loot;

import net.minecraft.item.ItemStack;

import greymerk.roguelike.treasure.loot.books.BookStarter;

public enum Book {

  CREDITS;

  public static ItemStack get(Book type) {
    return new BookStarter().get();
  }

}
