package greymerk.roguelike.command.routes.exception;

public class SettingNameNotFoundException extends Exception {
  public SettingNameNotFoundException(String settingName) {
    super(settingName + " not found.");
  }
}
