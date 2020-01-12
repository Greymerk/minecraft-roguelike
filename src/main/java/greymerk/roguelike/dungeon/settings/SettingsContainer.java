package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import greymerk.roguelike.dungeon.settings.base.SettingsBase;
import greymerk.roguelike.dungeon.settings.base.SettingsLayout;
import greymerk.roguelike.dungeon.settings.base.SettingsLootRules;
import greymerk.roguelike.dungeon.settings.base.SettingsRooms;
import greymerk.roguelike.dungeon.settings.base.SettingsSecrets;
import greymerk.roguelike.dungeon.settings.base.SettingsSegments;
import greymerk.roguelike.dungeon.settings.base.SettingsTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsDesertTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsForestTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsGrasslandTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsIceTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsJungleTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsMesaTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsMountainTheme;
import greymerk.roguelike.dungeon.settings.builtin.SettingsSwampTheme;

import static com.google.common.base.Predicates.not;
import static greymerk.roguelike.dungeon.settings.DungeonSettingsParser.parseFile;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class SettingsContainer implements ISettingsContainer {

  public static final String DEFAULT_NAMESPACE = "default";
  public static final String BUILTIN_NAMESPACE = "builtin";

  private Map<String, Map<String, DungeonSettings>> settingsByNamespace = new HashMap<>();

  public SettingsContainer(DungeonSettings... dungeonSettings) {
    put(
        new SettingsRooms(),
        new SettingsSecrets(),
        new SettingsSegments(),
        new SettingsLayout(),
        new SettingsTheme(),
        new SettingsLootRules(),
        new SettingsBase(),

        new SettingsDesertTheme(),
        new SettingsGrasslandTheme(),
        new SettingsJungleTheme(),
        new SettingsSwampTheme(),
        new SettingsMountainTheme(),
        new SettingsForestTheme(),
        new SettingsMesaTheme(),
        new SettingsIceTheme()
    );

    put(dungeonSettings);
  }

  public void parseCustomSettings(Map<String, String> files) throws Exception {
    for (String name : files.keySet()) {
      try {
        DungeonSettings dungeonSettings = parseFile(files.get(name));
        put(dungeonSettings);
      } catch (Exception e) {
        throw new Exception("Error in: " + name + " : " + e.getMessage());
      }
    }
  }

  private void put(DungeonSettings dungeonSettings) {
    String namespace = dungeonSettings.getNamespace();

    if (!containsNamespace(namespace)) {
      settingsByNamespace.put(namespace, new HashMap<>());
    }

    settingsByNamespace.get(namespace).put(dungeonSettings.getName(), dungeonSettings);
  }

  public void put(DungeonSettings... dungeonSettings) {
    stream(dungeonSettings).forEach(this::put);
  }

  public void put(List<DungeonSettings> dungeonSettings) {
    dungeonSettings.forEach(this::put);
  }

  public Collection<DungeonSettings> getByNamespace(String namespace) {
    if (containsNamespace(namespace)) {
      return settingsByNamespace.get(namespace).values();
    }
    return new ArrayList<>();
  }

  public Collection<DungeonSettings> getBuiltinSettings() {
    return settingsByNamespace.entrySet().stream()
        .filter(this::isBuiltIn)
        .map(Map.Entry::getValue)
        .map(Map::values)
        .flatMap(Collection::stream)
        .collect(toList());
  }

  private boolean isBuiltIn(Map.Entry<String, Map<String, DungeonSettings>> entry) {
    return isBuiltIn(entry.getKey());
  }

  private boolean isBuiltIn(String key) {
    return key.equals(BUILTIN_NAMESPACE);
  }

  public Collection<DungeonSettings> getCustomSettings() {
    return settingsByNamespace.entrySet().stream()
        .filter(not(this::isBuiltIn))
        .map(Map.Entry::getValue)
        .map(Map::values)
        .flatMap(Collection::stream)
        .collect(toList());
  }

  public DungeonSettings get(SettingIdentifier id) {
    if (!contains(id)) {
      return null;
    }
    return getNamespace(id).get(id.getName());
  }

  public boolean contains(SettingIdentifier id) {
    return containsNamespace(id.getNamespace()) && getNamespace(id).containsKey(id.getName());
  }

  private boolean containsNamespace(String namespace) {
    return settingsByNamespace.containsKey(namespace);
  }

  private Map<String, DungeonSettings> getNamespace(SettingIdentifier id) {
    return settingsByNamespace.get(id.getNamespace());
  }

  @Override
  public String toString() {
    return settingsByNamespace.values().stream()
        .map(Map::values)
        .flatMap(Collection::stream)
        .map(DungeonSettings::getId)
        .map(SettingIdentifier::toString)
        .collect(joining(" "));
  }
}
