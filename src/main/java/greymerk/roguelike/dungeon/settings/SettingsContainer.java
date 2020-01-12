package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

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

import static java.util.stream.Collectors.joining;

public class SettingsContainer implements ISettingsContainer {

  public static final String DEFAULT_NAMESPACE = "default";
  public static final String BUILTIN_NAMESPACE = "builtin";

  private Map<String, Map<String, DungeonSettings>> settingsByNamespace;

  public SettingsContainer() {
    this.settingsByNamespace = new HashMap<>();

    this.put(new SettingsRooms());
    this.put(new SettingsSecrets());
    this.put(new SettingsSegments());
    this.put(new SettingsLayout());
    this.put(new SettingsTheme());
    this.put(new SettingsLootRules());
    this.put(new SettingsBase());

    this.put(new SettingsDesertTheme());
    this.put(new SettingsGrasslandTheme());
    this.put(new SettingsJungleTheme());
    this.put(new SettingsSwampTheme());
    this.put(new SettingsMountainTheme());
    this.put(new SettingsForestTheme());
    this.put(new SettingsMesaTheme());
    this.put(new SettingsIceTheme());
  }

  public void parseCustomSettings(Map<String, String> files) throws Exception {
    for (String name : files.keySet()) {
      try {
        this.put(parseFile(files.get(name)));
      } catch (Exception e) {
        throw new Exception("Error in: " + name + " : " + e.getMessage());
      }
    }
  }

  private DungeonSettings parseFile(String content) throws Exception {
    try {
      return new DungeonSettings((JsonObject) new JsonParser().parse(content));
    } catch (JsonSyntaxException e) {
      Throwable cause = e.getCause();
      throw new Exception(cause.getMessage());
    } catch (Exception e) {
      throw new Exception("An unknown error occurred while parsing json");
    }

  }

  public void put(DungeonSettings setting) {
    String namespace = setting.getNameSpace() != null ? setting.getNameSpace() : DEFAULT_NAMESPACE;
    String name = setting.getName();

    if (!settingsByNamespace.containsKey(namespace)) {
      settingsByNamespace.put(namespace, new HashMap<>());
    }

    Map<String, DungeonSettings> settings = this.settingsByNamespace.get(namespace);
    settings.put(name, setting);
  }

  public Collection<DungeonSettings> getByNamespace(String namespace) {
    if (!this.settingsByNamespace.containsKey(namespace)) {
      return new ArrayList<>();
    }
    return this.settingsByNamespace.get(namespace).values();
  }

  public Collection<DungeonSettings> getBuiltinSettings() {
    List<DungeonSettings> settings = new ArrayList<>();

    for (String namespace : settingsByNamespace.keySet()) {
      if (!namespace.equals(SettingsContainer.BUILTIN_NAMESPACE)) {
        continue;
      }
      settings.addAll(settingsByNamespace.get(namespace).values());
    }

    return settings;
  }

  public Collection<DungeonSettings> getCustomSettings() {

    List<DungeonSettings> settings = new ArrayList<>();

    for (String namespace : settingsByNamespace.keySet()) {
      if (namespace.equals(SettingsContainer.BUILTIN_NAMESPACE)) {
        continue;
      }
      settings.addAll(settingsByNamespace.get(namespace).values());
    }

    return settings;
  }

  public DungeonSettings get(SettingIdentifier id) {
    if (!contains(id)) {
      return null;
    }
    Map<String, DungeonSettings> settings = settingsByNamespace.get(id.getNamespace());
    return settings.get(id.getName());
  }

  public boolean contains(SettingIdentifier id) {

    if (!settingsByNamespace.containsKey(id.getNamespace())) {
      return false;
    }

    Map<String, DungeonSettings> settings = settingsByNamespace.get(id.getNamespace());
    return settings.containsKey(id.getName());
  }

  @Override
  public String toString() {
    return settingsByNamespace.keySet().stream()
        .flatMap(namespace -> settingsByNamespace.get(namespace).values().stream())
        .map(setting -> setting.getId().toString() + " ")
        .collect(joining());
  }
}
