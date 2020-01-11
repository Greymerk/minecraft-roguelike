package greymerk.roguelike.treasure.loot;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class BookBase implements IBook {

  private List<String> pages;
  private String author;
  private String title;

  public BookBase() {
    this.pages = new ArrayList<>();
  }

  public BookBase(String author, String title) {
    this.pages = new ArrayList<>();
    this.author = author;
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void addPage(String page) {
    this.pages.add(page);
  }

  @Override
  public ItemStack get() {
    ItemStack book = new ItemStack(Items.WRITTEN_BOOK, 1);

    NBTTagList nbtPages = new NBTTagList();

    for (String page : this.pages) {
      ITextComponent text = new TextComponentString(page);
      String json = ITextComponent.Serializer.componentToJson(text);
      NBTTagString nbtPage = new NBTTagString(json);
      nbtPages.appendTag(nbtPage);
    }

    book.setTagInfo("pages", nbtPages);
    book.setTagInfo("author", new NBTTagString(this.author == null ? "Anonymous" : this.author));
    book.setTagInfo("title", new NBTTagString(this.title == null ? "Book" : this.title));


    return book;
  }
}
