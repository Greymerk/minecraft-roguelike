package greymerk.roguelike.dungeon.settings;

import net.minecraft.util.ResourceLocation;

public class SettingIdentifier {

  private ResourceLocation identifier;

  public SettingIdentifier(String namespace, String name) {
    identifier = new ResourceLocation(namespace, name);
  }

  public SettingIdentifier(String name) {
    String[] parts = name.split(":");
    String namespace = parts.length > 1 ? parts[0] : SettingsContainer.DEFAULT_NAMESPACE;
    String uniqueName = parts.length > 1 ? parts[1] : name;
    identifier = new ResourceLocation(namespace, uniqueName);
  }

  public String getNamespace() {
    return identifier.getResourceDomain();
  }

  public String getName() {
    return identifier.getResourcePath();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SettingIdentifier)) {
      return false;
    }
    return identifier.equals(((SettingIdentifier) o).identifier);
  }

  @Override
  public String toString() {
    return getNamespace() + ":" + getName();
  }
}
