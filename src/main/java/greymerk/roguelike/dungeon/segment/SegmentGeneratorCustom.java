package greymerk.roguelike.dungeon.segment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

public class SegmentGeneratorCustom extends SegmentGeneratorBase {

  public SegmentGeneratorCustom(JsonObject json) {

    if (json.has("segments")) {
      segments = new WeightedRandomizer<>();
      JsonArray data = json.get("segments").getAsJsonArray();
      for (JsonElement e : data) {
        JsonObject weighted = e.getAsJsonObject();
        String type = weighted.get("type").getAsString();
        int weight = weighted.get("weight").getAsInt();
        segments.add(new WeightedChoice<>(Segment.valueOf(type), weight));
      }
    }

    if (json.has("arch")) {
      String s = json.get("arch").getAsString();
      arch = Segment.valueOf(s);
    }

  }
}
