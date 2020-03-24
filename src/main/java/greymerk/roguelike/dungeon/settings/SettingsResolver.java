package greymerk.roguelike.dungeon.settings;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

public class SettingsResolver {

  private ISettingsContainer settingsContainer;

  public SettingsResolver(
      ISettingsContainer settingsContainer
  ) {
    this.settingsContainer = settingsContainer;
  }

  public DungeonSettings getAnyCustomDungeonSettings(IWorldEditor editor, Coord coord) {
    Optional<DungeonSettings> builtin = ofNullable(getBuiltin(editor, coord));
    Optional<DungeonSettings> custom = chooseRandomCustomDungeonIfPossible(editor, coord);
    if (builtin.isPresent() || custom.isPresent()) {
      return custom.orElseGet(builtin::get);
    }
    return null;
  }

  public DungeonSettings getByName(String name) {
    try {
      SettingIdentifier id = new SettingIdentifier(name);
      if (settingsContainer.contains(id)) {
        DungeonSettings setting = new DungeonSettings(settingsContainer.get(id));
        DungeonSettings byName = processInheritance(setting);
        if (byName != null) {
          return new DungeonSettings(new SettingsBlank(), byName);
        }
      }
      return null;
    } catch (Exception e) {
      throw new RuntimeException("Malformed Setting ID String: " + name);
    }
  }

  public ISettings getDefaultSettings() {
    return new DungeonSettings(settingsContainer.get(new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "base")));
  }

  public DungeonSettings processInheritance(
      DungeonSettings dungeonSettings
  ) {
    return dungeonSettings.getInherits().stream()
        .peek(this::throwIfNotFound)
        .map(settingsContainer::get)
        .map(DungeonSettings::new)
        .reduce(dungeonSettings, inherit());
  }

  private void throwIfNotFound(SettingIdentifier settingIdentifier) {
    if (!settingsContainer.contains(settingIdentifier)) {
      throw new RuntimeException("Setting not found: " + settingIdentifier.toString());
    }
  }

  private BinaryOperator<DungeonSettings> inherit() {
    return (DungeonSettings acc, DungeonSettings element) -> {
      DungeonSettings inherited = element.getInherits().isEmpty()
          ? element
          : processInheritance(element);
      return new DungeonSettings(inherited, acc);
    };
  }

  private DungeonSettings getBuiltin(IWorldEditor editor, Coord coord) {
    if (!RogueConfig.getBoolean(RogueConfig.SPAWNBUILTIN)) {
      return null;
    }
    WeightedRandomizer<DungeonSettings> settingsRandomizer = newWeightedRandomizer(settingsContainer.getBuiltinSettings(), isValid(editor, coord));
    if (settingsRandomizer.isEmpty()) {
      return null;
    }
    Random random = Dungeon.getRandom(editor, coord);
    return processInheritance(settingsRandomizer.get(random));
  }

  private Optional<DungeonSettings> chooseRandomCustomDungeonIfPossible(
      IWorldEditor editor,
      Coord coord
  ) {
    WeightedRandomizer<DungeonSettings> settingsRandomizer = newWeightedRandomizer(
        settingsContainer.getCustomSettings(),
        DungeonSettings::isExclusive,
        isValid(editor, coord)
    );
    Random random = Dungeon.getRandom(editor, coord);
    return ofNullable(settingsRandomizer.get(random))
        .map(this::processInheritance);
  }

  @SafeVarargs
  private final WeightedRandomizer<DungeonSettings> newWeightedRandomizer(
      Collection<DungeonSettings> dungeonSettingsCollection,
      Predicate<DungeonSettings>... predicates
  ) {
    WeightedRandomizer<DungeonSettings> settingsRandomizer = new WeightedRandomizer<>();
    dungeonSettingsCollection.stream()
        .filter(stream(predicates).reduce(x -> true, Predicate::and))
        .map(setting -> new WeightedChoice<>(setting, setting.getSpawnCriteria().getWeight()))
        .forEach(settingsRandomizer::add);
    return settingsRandomizer;
  }

  private Predicate<DungeonSettings> isValid(IWorldEditor editor, Coord pos) {
    return setting -> setting.isValid(editor, pos);
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
