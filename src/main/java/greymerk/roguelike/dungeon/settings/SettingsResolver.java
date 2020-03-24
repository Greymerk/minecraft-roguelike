package greymerk.roguelike.dungeon.settings;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

import greymerk.roguelike.config.RogueConfig;
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

  // called from Dungeon class

  public DungeonSettings chooseDungeonSettingsToGenerate(IWorldEditor editor, Random random, Coord pos) throws Exception {
    if (RogueConfig.getBoolean(RogueConfig.RANDOM)) {
      return new SettingsRandom(random);
    }
    Optional<DungeonSettings> builtin = ofNullable(getBuiltin(editor, random, pos));
    Optional<DungeonSettings> custom = chooseRandomCustomDungeonIfPossible(editor, random, pos);
    if (!builtin.isPresent() && !custom.isPresent()) {
      return null;
    }
    DungeonSettings dungeonSettings = custom.orElseGet(builtin::get);

    return applyInclusions(dungeonSettings, editor, pos);
  }

  public DungeonSettings getWithName(String name, IWorldEditor editor, Random rand, Coord pos) {
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

  private DungeonSettings getBuiltin(IWorldEditor editor, Random rand, Coord pos) {
    if (!RogueConfig.getBoolean(RogueConfig.SPAWNBUILTIN)) {
      return null;
    }
    WeightedRandomizer<DungeonSettings> settingsRandomizer = newWeightedRandomizer(settingsContainer.getBuiltinSettings(), isValid(editor, pos));
    if (settingsRandomizer.isEmpty()) {
      return null;
    }
    return processInheritance(settingsRandomizer.get(rand));
  }

  private Optional<DungeonSettings> chooseRandomCustomDungeonIfPossible(
      IWorldEditor editor,
      Random rand,
      Coord pos
  ) {
    WeightedRandomizer<DungeonSettings> settingsRandomizer = newWeightedRandomizer(
        settingsContainer.getCustomSettings(),
        DungeonSettings::isExclusive,
        isValid(editor, pos)
    );
    return ofNullable(settingsRandomizer.get(rand))
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

  private DungeonSettings applyInclusions(DungeonSettings dungeonSettings, IWorldEditor editor, Coord pos) {
    return dungeonSettings;
//    return settingsContainer.getCustomSettings().stream()
//        .filter(DungeonSettings::isInclusive)
//        .filter(isValid(editor, pos))
//        .reduce(dungeonSettings, this::inherit);
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
