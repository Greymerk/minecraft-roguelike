package greymerk.roguelike.util;

public enum TextFormat {

  BLACK,
  DARKBLUE,
  DARKGREEN,
  DARKAQUA,
  DARKRED,
  DARKPURPLE,
  GOLD,
  GRAY,
  DARKGRAY,
  BLUE,
  GREEN,
  AQUA,
  RED,
  LIGHTPURPLE,
  YELLOW,
  WHITE,
  OBFUSCATED,
  BOLD,
  STRIKETHROUGH,
  UNDERLINE,
  ITALIC,
  RESET;

  public static String apply(String text, TextFormat option) {
    return "\u00A7" + getCodeChar(option) + text;
  }

  public static String getCode(TextFormat option) {
    return "\u00A7" + getCodeChar(option);
  }

  public static char getCodeChar(TextFormat option) {

    switch (option) {
      case BLACK:
        return '0';
      case DARKBLUE:
        return '1';
      case DARKGREEN:
        return '2';
      case DARKAQUA:
        return '3';
      case DARKRED:
        return '4';
      case DARKPURPLE:
        return '5';
      case GOLD:
        return '6';
      case GRAY:
        return '7';
      case DARKGRAY:
        return '8';
      case BLUE:
        return '9';
      case GREEN:
        return 'a';
      case AQUA:
        return 'b';
      case RED:
        return 'c';
      case LIGHTPURPLE:
        return 'd';
      case YELLOW:
        return 'e';
      case WHITE:
        return 'f';
      case OBFUSCATED:
        return 'k';
      case BOLD:
        return 'l';
      case STRIKETHROUGH:
        return 'm';
      case UNDERLINE:
        return 'n';
      case ITALIC:
        return 'o';
      case RESET:
        return 'r';
    }
    return 'f';
  }


}


