package greymerk.roguelike.command.routes;

import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;

import java.util.List;
import java.util.Optional;

import greymerk.roguelike.command.CommandRouteBase;
import greymerk.roguelike.command.ICommandContext;
import greymerk.roguelike.command.MessageType;
import greymerk.roguelike.command.routes.exception.NoValidLocationException;
import greymerk.roguelike.command.routes.exception.SettingNameNotFoundException;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.util.ArgumentParser;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class CommandRouteDungeon extends CommandRouteBase {

  public static Coord getLocation(ICommandContext context, List<String> args) throws NumberInvalidException {
    ArgumentParser argumentParser = new ArgumentParser(args);
    if (argumentParser.match(0, "here") || argumentParser.match(0, "nearby")) {
      Coord coord = context.getPos();
      return new Coord(coord.getX(), 0, coord.getZ());
    }
    try {
      int x = CommandBase.parseInt(argumentParser.get(0));
      int z = CommandBase.parseInt(argumentParser.get(1));
      return new Coord(x, 0, z);
    } catch (NumberInvalidException e) {
      context.sendMessage("Failure: Invalid Coords: X Z", MessageType.ERROR);
      throw (e);
    }
  }

  @Override
  public void execute(ICommandContext context, List<String> args) {
    ArgumentParser argumentParser = new ArgumentParser(args);
    if (!argumentParser.hasEntry(0)) {
      context.sendMessage("Usage: roguelike dungeon {X Z | here} [setting]", MessageType.INFO);
      return;
    }
    String settingName = getSettingName(argumentParser);

    try {
      Coord pos = getLocation(context, args);
      IWorldEditor editor = context.createEditor();
      DungeonSettings dungeonSettings = settingName == null
          ? resolveAnyDungeonSettings(pos, editor)
          : resolveNamedDungeonSettings(pos, editor, settingName);
      generateDungeon(context, pos, editor, dungeonSettings);
    } catch (Exception e) {
      context.sendMessage("Failure: " + e.getMessage(), MessageType.ERROR);
    }
  }

  private DungeonSettings resolveNamedDungeonSettings(Coord pos, IWorldEditor editor, String settingName) throws Exception {
    Dungeon.initResolver();
    DungeonSettings dungeonSettings = Dungeon.settingsResolver.getWithName(settingName, editor, Dungeon.getRandom(editor, pos), pos);
    return Optional.ofNullable(dungeonSettings)
        .orElseThrow(() -> new SettingNameNotFoundException(settingName));
  }

  private DungeonSettings resolveAnyDungeonSettings(Coord pos, IWorldEditor editor) throws Exception {
    DungeonSettings dungeonSettings = Dungeon.settingsResolver.chooseDungeonSettingsToGenerate(editor, Dungeon.getRandom(editor, pos), pos);
    return Optional.ofNullable(dungeonSettings)
        .orElseThrow(() -> new NoValidLocationException(pos));
  }

  private void generateDungeon(ICommandContext context, Coord pos, IWorldEditor editor, DungeonSettings dungeonSettings) {
    Dungeon dungeon = new Dungeon(editor);
    dungeon.generate(dungeonSettings, pos);
    context.sendMessage("Success: Dungeon generated at " + pos.toString(), MessageType.SUCCESS);
  }

  private String getSettingName(ArgumentParser argumentParser) {
    int index = argumentParser.match(0, "here")
        || argumentParser.match(0, "nearby")
        ? 1
        : 2;
    return argumentParser.get(index);
  }
}
