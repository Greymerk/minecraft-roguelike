package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.command.ICommandContext;
import greymerk.roguelike.command.MessageType;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.util.ArgumentParser;

public class CommandRouteSettings extends CommandRouteBase {

  @Override
  public void execute(ICommandContext context, List<String> args) {
    ArgumentParser ap = new ArgumentParser(args);

    if (!ap.hasEntry(0)) {
      context.sendMessage("Usage: roguelike settings [reload | list]", MessageType.INFO);
      return;
    }
    if (ap.match(0, "reload")) {
      try {
        Dungeon.initResolver();
        context.sendMessage("Success: Settings Reloaded", MessageType.SUCCESS);
      } catch (Exception e) {
        if (e.getMessage() == null) {
          context.sendMessage("Failure: Uncaught Exception", MessageType.ERROR);
        } else {
          context.sendMessage("Failure: " + e.getMessage(), MessageType.ERROR);
        }
      }
      return;
    }
    if (ap.match(0, "list")) {
      if (ap.hasEntry(1)) {
        String namespace = ap.get(1);
        context.sendMessage(Dungeon.settingsResolver.toString(namespace), MessageType.SUCCESS);
        return;
      }
      context.sendMessage(Dungeon.settingsResolver.toString(), MessageType.SUCCESS);
      return;
    }

    return;
  }
}
