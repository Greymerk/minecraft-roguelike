package greymerk.roguelike.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumTools {

  public static List<String> valuesToStrings(Class<? extends Enum<?>> e) {
    return Arrays.stream(e.getEnumConstants())
        .map(Enum::name)
        .collect(Collectors.toList()
        );
  }

}
