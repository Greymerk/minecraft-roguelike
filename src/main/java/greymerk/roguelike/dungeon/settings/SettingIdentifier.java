package greymerk.roguelike.dungeon.settings;

import net.minecraft.util.ResourceLocation;

public class SettingIdentifier {

  private ResourceLocation identifier;

  public SettingIdentifier(String namespace, String name) {
    this.identifier = new ResourceLocation(namespace, name);
  }

  public SettingIdentifier(String name) {
    String[] parts;
    parts = name.split(":");
    if (parts.length > 1) {
      this.identifier = new ResourceLocation(parts[0], parts[1]);
      return;
    }

    this.identifier = new ResourceLocation(SettingsContainer.DEFAULT_NAMESPACE, name);
  }

  public String getNamespace() {
    return this.identifier.getResourceDomain();
  }

  public String getName() {
    return this.identifier.getResourcePath();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SettingIdentifier)) {
      return false;
    }
    return this.identifier.equals(((SettingIdentifier) o).identifier);
  }

  @Override
  public String toString() {
    return this.getNamespace() + ":" + this.getName();
  }
}
