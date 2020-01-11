package greymerk.roguelike.dungeon.base;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.util.WeightedRandomizer;

import static java.util.stream.IntStream.range;

public class DungeonFactory implements IDungeonFactory {

  private Map<DungeonRoom, Integer> singles;
  private Map<DungeonRoom, Integer> multiple;
  private DungeonRoom base;

  private Iterator<IDungeonRoom> singleRooms;


  public DungeonFactory() {
    this(DungeonRoom.CORNER);
  }

  public DungeonFactory(DungeonRoom base) {
    singles = new HashMap<>();
    multiple = new HashMap<>();
    this.base = base;
  }

  public DungeonFactory(JsonArray json) throws Exception {
    this();

    for (JsonElement e : json) {
      this.add(e.getAsJsonObject());
    }
  }

  public DungeonFactory(DungeonFactory toCopy) {
    this();
    for (DungeonRoom room : toCopy.singles.keySet()) {
      this.singles.put(room, toCopy.singles.get(room));
    }

    for (DungeonRoom room : toCopy.multiple.keySet()) {
      this.multiple.put(room, toCopy.multiple.get(room));
    }

    base = toCopy.base;
  }

  public DungeonFactory(DungeonFactory base, DungeonFactory other) {
    this();
    this.base = other.base;
    DungeonFactory dungeonFactory = other.multiple.keySet().isEmpty() ? base : other;
    dungeonFactory.singles.keySet()
        .forEach(room -> singles.put(room, dungeonFactory.singles.get(room)));
    dungeonFactory.multiple.keySet()
        .forEach(room -> multiple.put(room, dungeonFactory.multiple.get(room)));
  }

  public static DungeonFactory getRandom(Random rand, int numRooms) {
    DungeonFactory rooms = new DungeonFactory();
    rooms.base = DungeonRoom.CORNER;
    range(0, numRooms).forEach(i -> {
      if (rand.nextBoolean()) {
        rooms.addRandom(DungeonRoom.getRandomRoom(rand), 1);
      } else {
        rooms.addSingle(DungeonRoom.getRandomRoom(rand), 1);
      }
    });
    return rooms;
  }

  public void add(JsonObject entry) throws Exception {
    String mode = (entry.get("type").getAsString()).toLowerCase();
    String type = (entry.get("name").getAsString()).toUpperCase();

    int weight = entry.has("weight") ? entry.get("weight").getAsInt() : 1;

    if (!DungeonRoom.contains(type)) {
      throw new Exception("No such dungeon: " + type);
    }

    DungeonRoom toAdd = DungeonRoom.valueOf(entry.get("name").getAsString());

    if (mode.equals("single")) {
      this.addSingle(toAdd);
    }

    if (mode.equals("random")) {
      this.addRandom(toAdd, weight);
    }
  }

  public IDungeonRoom get(Random rand) {

    if (this.singleRooms == null) {
      this.singleRooms = new RoomIterator();
    }

    if (this.singleRooms.hasNext()) {
      return this.singleRooms.next();
    }

    Set<DungeonRoom> keyset = this.multiple.keySet();
    if (keyset.isEmpty()) {
      return DungeonRoom.getInstance(base);
    }

    WeightedRandomizer<DungeonRoom> randomizer = new WeightedRandomizer<>();
    for (DungeonRoom room : keyset) {
      randomizer.add(new WeightedChoice<>(room, multiple.get(room)));
    }

    DungeonRoom choice = randomizer.get(rand);
    return DungeonRoom.getInstance(choice);
  }

  public void addSingle(DungeonRoom type) {
    this.addSingle(type, 1);
  }

  public void addSingle(DungeonRoom type, int num) {
    if (!singles.containsKey(type)) {
      singles.put(type, num);
      return;
    }

    int count = singles.get(type);
    count += num;
    singles.put(type, count);
  }

  public void addRandom(DungeonRoom type, int weight) {
    multiple.put(type, weight);
  }

  @Override
  public boolean equals(Object o) {
    DungeonFactory other = (DungeonFactory) o;

    if (!this.base.equals(other.base)) {
      return false;
    }

    if (!this.singles.equals(other.singles)) {
      return false;
    }

    return this.multiple.equals(other.multiple);
  }

  private class RoomIterator implements Iterator<IDungeonRoom> {
    private PriorityQueue<IDungeonRoom> rooms;

    public RoomIterator() {
      rooms = new PriorityQueue<>();

      singles.keySet()
          .forEach(dungeonRoom ->
              range(0, singles.get(dungeonRoom))
                  .forEach(i -> rooms.add(DungeonRoom.getInstance(dungeonRoom))));
    }

    @Override
    public boolean hasNext() {
      return !rooms.isEmpty();
    }

    @Override
    public IDungeonRoom next() {
      return rooms.poll();
    }
  }
}
