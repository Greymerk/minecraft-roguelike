package greymerk.roguelike.util;

import net.minecraft.item.EnumDyeColor;

import java.util.Random;

public enum DyeColor {

  WHITE,
  ORANGE,
  MAGENTA,
  LIGHT_BLUE,
  YELLOW,
  LIME,
  PINK,
  GRAY,
  LIGHT_GRAY,
  CYAN,
  PURPLE,
  BLUE,
  BROWN,
  GREEN,
  RED,
  BLACK;

  public static EnumDyeColor get(DyeColor color) {
    try {
      return EnumDyeColor.valueOf(color.toString());
    } catch(IllegalArgumentException illegalArgumentException) {
      return EnumDyeColor.WHITE;
    }
  }

  public static DyeColor get(Random rand) {
    return DyeColor.values()[rand.nextInt(DyeColor.values().length)];
  }

  public static int RGBToColor(int r, int g, int b) {
    return r << 16 | g << 8 | b;
  }

  public static int HSLToColor(float h, float s, float l) {
    float r, g, b;

    if (s == 0f) {
      r = g = b = l;
    } else {
      float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
      float p = 2 * l - q;
      r = hueToRgb(p, q, h + 1f / 3f);
      g = hueToRgb(p, q, h);
      b = hueToRgb(p, q, h - 1f / 3f);
    }
    return RGBToColor((int) (r * 255), (int) (g * 255), (int) (b * 255));
  }

  public static float hueToRgb(float p, float q, float t) {
    if (t < 0f) {
      t += 1f;
    }
    if (t > 1f) {
      t -= 1f;
    }
    if (t < 1f / 6f) {
      return p + (q - p) * 6f * t;
    }
    if (t < 1f / 2f) {
      return q;
    }
    if (t < 2f / 3f) {
      return p + (q - p) * (2f / 3f - t) * 6f;
    }
    return p;
  }
}
