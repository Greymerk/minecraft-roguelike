package greymerk.roguelike.command.routes;

import java.util.List;

import greymerk.roguelike.citadel.Citadel;
import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.command.ICommandContext;
import greymerk.roguelike.worldgen.Coord;

public class CommandRouteCitadel extends CommandRouteBase {

  @Override
  public void execute(ICommandContext context, List<String> args) {
    try {
      Coord pos = CommandRouteDungeon.getLocation(context, args);
      Citadel.generate(context.createEditor(), pos.getX(), pos.getZ());
    } catch (Exception ignored) {
    }
  }
}
