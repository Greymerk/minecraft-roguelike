package greymerk.roguelike.dungeon.settings;

import java.util.Collection;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import static java.util.stream.Collectors.joining;

public class SettingsResolver {

  private ISettingsContainer settings;

  public SettingsResolver(ISettingsContainer settings) {
    this.settings = settings;
  }

  public static DungeonSettings processInheritance(
      DungeonSettings toProcess,
      ISettingsContainer settingsContainer
  ) throws Exception {
    DungeonSettings setting = new SettingsBlank();

    if (toProcess == null) {
      throw new Exception("Process Inheritance called with null settings object");
    }

    for (SettingIdentifier id : toProcess.getInherits()) {
      if (settingsContainer.contains(id)) {
        DungeonSettings inherited = new DungeonSettings(settingsContainer.get(id));

        if (!inherited.getInherits().isEmpty()) {
          inherited = processInheritance(inherited, settingsContainer);
        }

        setting = new DungeonSettings(setting, inherited);

      } else {
        throw new Exception("Setting not found: " + id.toString());
      }
    }

    return new DungeonSettings(setting, toProcess);
  }

  // called from Dungeon class
  public ISettings getSettings(IWorldEditor editor, Random rand, Coord pos) throws Exception {
    if (RogueConfig.getBoolean(RogueConfig.RANDOM)) {
      return new SettingsRandom(rand);
    }

    DungeonSettings builtin = getBuiltin(editor, rand, pos);
    DungeonSettings custom = getCustom(editor, rand, pos);

    // there are no valid dungeons for this location
    if (builtin == null && custom == null) {
      return null;
    }

    DungeonSettings exclusive = (custom != null) ? custom : builtin;

    return applyInclusives(exclusive, editor, pos);
  }

  public ISettings getWithName(String name, IWorldEditor editor, Random rand, Coord pos) throws Exception {
    if (name.equals("random")) {
      return new SettingsRandom(rand);
    }

    DungeonSettings byName = getByName(name);

    if (byName == null) {
      return null;
    }
    DungeonSettings withInclusives = applyInclusives(byName, editor, pos);

    return new DungeonSettings(new SettingsBlank(), withInclusives);
  }

  public ISettings getDefaultSettings() {
    return new DungeonSettings(settings.get(new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "base")));
  }

  private DungeonSettings getByName(String name) throws Exception {
    SettingIdentifier id;

    try {
      id = new SettingIdentifier(name);
    } catch (Exception e) {
      throw new Exception("Malformed Setting ID String: " + name);
    }

    if (!settings.contains(id)) {
      return null;
    }
    DungeonSettings setting = new DungeonSettings(settings.get(id));

    return processInheritance(setting, settings);
  }

  private DungeonSettings getBuiltin(IWorldEditor editor, Random rand, Coord pos) throws Exception {
    if (!RogueConfig.getBoolean(RogueConfig.SPAWNBUILTIN)) {
      return null;
    }

    WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<>();

    for (DungeonSettings setting : settings.getBuiltinSettings()) {
      if (setting.isValid(editor, pos)) {
        settingsRandomizer.add(new WeightedChoice<>(setting, setting.getCriteria().getWeight()));
      }
    }

    if (settingsRandomizer.isEmpty()) {
      return null;
    }

    DungeonSettings chosen = settingsRandomizer.get(rand);

    return processInheritance(chosen, settings);
  }

  private DungeonSettings getCustom(IWorldEditor editor, Random rand, Coord pos) throws Exception {

    WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<>();

    for (DungeonSettings setting : settings.getCustomSettings()) {
      if (setting.isValid(editor, pos) && setting.isExclusive()) {
        settingsRandomizer.add(new WeightedChoice<>(setting, setting.getCriteria().getWeight()));
      }
    }

    DungeonSettings chosen = settingsRandomizer.get(rand);

    if (chosen == null) {
      return null;
    }

    return processInheritance(chosen, settings);
  }

  private DungeonSettings applyInclusives(DungeonSettings setting, IWorldEditor editor, Coord pos) throws Exception {

    DungeonSettings toReturn = new DungeonSettings(setting);

    for (DungeonSettings s : settings.getCustomSettings()) {
      if (!s.isValid(editor, pos)) {
        continue;
      }
      if (s.isExclusive()) {
        continue;
      }
      toReturn = new DungeonSettings(toReturn, processInheritance(s, settings));
    }

    return toReturn;
  }

  public String toString(String namespace) {
    Collection<DungeonSettings> byNamespace = settings.getByNamespace(namespace);

    if (byNamespace.isEmpty()) {
      return "";
    }

    return byNamespace.stream()
        .map(setting -> setting.getId().toString() + " ")
        .collect(joining());
  }

  @Override
  public String toString() {
    return settings.toString();
  }
}
