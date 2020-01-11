package greymerk.roguelike.command.routes;

import net.minecraft.item.ItemStack;

import java.util.List;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.command.ICommandContext;
import greymerk.roguelike.command.MessageType;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.util.ArgumentParser;

public class CommandRouteGive extends CommandRouteBase {

  @Override
  public void execute(ICommandContext context, List<String> args) {
    ArgumentParser ap = new ArgumentParser(args);

    if (!ap.hasEntry(0)) {
      context.sendMessage("Usage: roguelike give novelty_name", MessageType.INFO);
      return;
    }

    ItemStack item = ItemNovelty.getItemByName(ap.get(0));
    if (item == null) {
      context.sendMessage("Failed: No such item", MessageType.ERROR);
      return;
    }

    context.give(item);
    context.sendMessage("Success: Given " + item.getDisplayName(), MessageType.SUCCESS);
  }
}
