package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.command.ICommandContext;
import greymerk.roguelike.command.MessageType;

public class CommandRouteRoguelike extends CommandRouteBase {

  public CommandRouteRoguelike() {
    super();
    this.addRoute("dungeon", new CommandRouteDungeon());
    this.addRoute("give", new CommandRouteGive());
    this.addRoute("config", new CommandRouteConfig());
    this.addRoute("settings", new CommandRouteSettings());
    this.addRoute("tower", new CommandRouteTower());
    this.addRoute("biome", new CommandRouteBiome());
    this.addRoute("citadel", new CommandRouteCitadel());
  }

  @Override
  public void execute(ICommandContext context, List<String> args) {
    if (args.size() == 0) {
      context.sendMessage("Usage: roguelike [dungeon | give | config | settings | tower]", MessageType.INFO);
    }

    super.execute(context, args);
  }
}
