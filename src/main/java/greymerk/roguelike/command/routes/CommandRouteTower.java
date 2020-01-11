package greymerk.roguelike.command.routes;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.command.ICommandContext;
import greymerk.roguelike.command.MessageType;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.towers.ITower;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.util.EnumTools;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class CommandRouteTower extends CommandRouteBase {

  @Override
  public void execute(ICommandContext context, List<String> args) {
    ArgumentParser ap = new ArgumentParser(args);

    if (!ap.hasEntry(0)) {
      List<String> towers = EnumTools.valuesToStrings(Tower.class)
          .stream()
          .map(String::toLowerCase)
          .collect(Collectors.toList());
      context.sendMessage("Tower types: " + StringUtils.join(towers, " "), MessageType.INFO);

      return;
    }
    String towerName = ap.get(0);
    Tower type;
    try {
      type = Tower.get(towerName.toUpperCase());
    } catch (Exception e) {
      context.sendMessage("Failure: No such tower type: " + towerName, MessageType.ERROR);
      return;
    }

    Coord here = new Coord(context.getPos().getX(), 50, context.getPos().getZ());
    ITower tower = Tower.get(type);

    IWorldEditor editor = context.createEditor();
    tower.generate(editor, Dungeon.getRandom(editor, here), Theme.getTheme(Tower.getDefaultTheme(type)), here);
    context.sendMessage("Success: " + towerName + " Tower generated at " + here.toString(), MessageType.SUCCESS);

    return;
  }

  @Override
  public List<String> getTabCompletion(List<String> args) {
    List<String> towers = EnumTools.valuesToStrings(Tower.class)
        .stream()
        .map(String::toLowerCase)
        .collect(Collectors.toList());

    if (args.size() > 0) {
      return this.getListTabOptions(args.get(0).toLowerCase(), towers);
    }

    return towers;
  }

}
