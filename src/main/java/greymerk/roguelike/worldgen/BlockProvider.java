package greymerk.roguelike.worldgen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public enum BlockProvider {

  METABLOCK,
  WEIGHTED,
  CHECKERS,
  JUMBLE,
  STRIPES,
  LAYERS,
  COLUMNS;

  public static IBlockFactory create(JsonObject block) throws Exception {

    BlockProvider type;

    if (block.has("type")) {
      String t = block.get("type").getAsString();
      type = BlockProvider.valueOf(t);
    } else {
      type = METABLOCK;
    }

    JsonElement data;
    if (block.has("data")) {
      data = block.get("data");
    } else {
      data = block;
    }

    switch (type) {
      case METABLOCK:
        return new MetaBlock(data);
      case WEIGHTED:
        return new BlockWeightedRandom(data);
      case CHECKERS:
        return new BlockCheckers(data);
      case JUMBLE:
        return new BlockJumble(data);
      case STRIPES:
        return new BlockStripes(data);
      case LAYERS:
        return new BlockLayers(data);
      case COLUMNS:
        return new BlockColumns(data);
      default:
        return null;
    }
  }
}
