package greymerk.roguelike.command;

import greymerk.roguelike.util.TextFormat;

public enum MessageType {
  INFO,
  ERROR,
  SUCCESS,
  SPECIAL,
  WARNING;

  public static TextFormat getFormat(MessageType type) {
    switch (type) {
      case INFO:
        return TextFormat.GRAY;
      case SUCCESS:
        return TextFormat.GREEN;
      case WARNING:
        return TextFormat.YELLOW;
      case ERROR:
        return TextFormat.RED;
      case SPECIAL:
        return TextFormat.GOLD;
      default:
        return TextFormat.WHITE;
    }
  }
}
