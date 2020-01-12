package greymerk.roguelike.dungeon.settings;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.function.BinaryOperator;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class SettingsResolver {

  private ISettingsContainer settingsContainer;

  public SettingsResolver(
      ISettingsContainer settingsContainer
  ) {
    this.settingsContainer = settingsContainer;
  }

  public DungeonSettings processInheritance(
      DungeonSettings toProcess
  ) {
    return toProcess.getInherits().stream()
        .peek(this::throwIfNotFound)
        .map(settingsContainer::get)
        .map(DungeonSettings::new)
        .reduce(toProcess, inherit());
  }

  private BinaryOperator<DungeonSettings> inherit() {
    return (DungeonSettings acc, DungeonSettings element) -> {
      DungeonSettings inherited = element.getInherits().isEmpty()
          ? element
          : processInheritance(element);
      return new DungeonSettings(acc, inherited);
    };
  }

  private void throwIfNotFound(SettingIdentifier settingIdentifier) {
    if (!settingsContainer.contains(settingIdentifier)) {
      throw new RuntimeException("Setting not found: " + settingIdentifier.toString());
    }
  }

  // called from Dungeon class
  public ISettings getSettings(IWorldEditor editor, Random rand, Coord pos) throws Exception {
    if (RogueConfig.getBoolean(RogueConfig.RANDOM)) {
      return new SettingsRandom(rand);
    }
    Optional<DungeonSettings> builtin = ofNullable(getBuiltin(editor, rand, pos));
    Optional<DungeonSettings> custom = ofNullable(getCustom(editor, rand, pos));
    if (!builtin.isPresent() && !custom.isPresent()) {
      return null;
    }
    DungeonSettings dungeonSettings = custom.orElseGet(builtin::get);

    return applyInclusions(dungeonSettings, editor, pos);
  }

  public ISettings getWithName(String name, IWorldEditor editor, Random rand, Coord pos) {
    if (name.equals("random")) {
      return new SettingsRandom(rand);
    }
    DungeonSettings byName = getByName(name);
    if (byName == null) {
      return null;
    }
    DungeonSettings withInclusions = applyInclusions(byName, editor, pos);
    return new DungeonSettings(new SettingsBlank(), withInclusions);
  }

  public ISettings getDefaultSettings() {
    return new DungeonSettings(settingsContainer.get(new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "base")));
  }

  private DungeonSettings getByName(String name) {
    SettingIdentifier id;

    try {
      id = new SettingIdentifier(name);
    } catch (Exception e) {
      throw new RuntimeException("Malformed Setting ID String: " + name);
    }

    if (!settingsContainer.contains(id)) {
      return null;
    }
    DungeonSettings setting = new DungeonSettings(settingsContainer.get(id));

    return processInheritance(setting);
  }

  private DungeonSettings getBuiltin(IWorldEditor editor, Random rand, Coord pos) {
    if (!RogueConfig.getBoolean(RogueConfig.SPAWNBUILTIN)) {
      return null;
    }

    WeightedRandomizer<DungeonSettings> settingsRandomizer = newWeightedRandomizer(settingsContainer.getBuiltinSettings(), editor, pos);

    if (settingsRandomizer.isEmpty()) {
      return null;
    }

    return processInheritance(settingsRandomizer.get(rand));
  }

  private DungeonSettings getCustom(IWorldEditor editor, Random rand, Coord pos) {
    Collection<DungeonSettings> custom = settingsContainer.getCustomSettings();
    Collection<DungeonSettings> exclusive = filterExclusive(custom);
    WeightedRandomizer<DungeonSettings> settingsRandomizer = newWeightedRandomizer(exclusive, editor, pos);
    return ofNullable(settingsRandomizer.get(rand))
        .map(this::processInheritance)
        .orElse(null);
  }

  private WeightedRandomizer<DungeonSettings> newWeightedRandomizer(
      Collection<DungeonSettings> dungeonSettingsCollection,
      IWorldEditor editor,
      Coord pos
  ) {
    WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<>();
    dungeonSettingsCollection.stream()
        .filter(setting -> setting.isValid(editor, pos))
        .map(setting -> new WeightedChoice<>(setting, setting.getCriteria().getWeight()))
        .forEach(settingsRandomizer::add);
    return settingsRandomizer;
  }

  private Collection<DungeonSettings> filterExclusive(
      Collection<DungeonSettings> dungeonSettingsCollection
  ) {
    return dungeonSettingsCollection.stream()
        .filter(DungeonSettings::isExclusive)
        .collect(toList());
  }

  private DungeonSettings applyInclusions(DungeonSettings dungeonSettings, IWorldEditor editor, Coord pos) {
    return settingsContainer.getCustomSettings().stream()
        .filter(DungeonSettings::isInclusive)
        .filter(customDungeonSettings -> customDungeonSettings.isValid(editor, pos))
        .reduce(dungeonSettings, this::inherit);
  }

  private DungeonSettings inherit(DungeonSettings ds0, DungeonSettings ds1) {
    return new DungeonSettings(ds0, processInheritance(ds1));
  }

  public String toString(String namespace) {
    return settingsContainer.getByNamespace(namespace).stream()
        .map(DungeonSettings::getId)
        .map(SettingIdentifier::toString)
        .collect(joining(" "));
  }

  @Override
  public String toString() {
    return settingsContainer.toString();
  }
}
