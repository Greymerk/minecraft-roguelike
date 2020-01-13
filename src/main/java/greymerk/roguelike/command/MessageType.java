package greymerk.roguelike.command;

import greymerk.roguelike.util.TextFormat;

public enum MessageType {
  INFO(TextFormat.GRAY),
  ERROR(TextFormat.RED),
  SUCCESS(TextFormat.GREEN),
  SPECIAL(TextFormat.GOLD),
  WARNING(TextFormat.YELLOW);

  private TextFormat textFormat;

  MessageType(TextFormat textFormat) {
    this.textFormat = textFormat;
  }

  public TextFormat getTextFormat() {
    return textFormat;
  }

  public String apply(String message) {
    return getTextFormat().apply(message);
  }
}
