package greymerk.roguelike.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

import java.util.Map;

public enum JsonNBT {

  END,
  BYTE,
  SHORT,
  INT,
  LONG,
  FLOAT,
  DOUBLE,
  BYTEARRAY,
  STRING,
  LIST,
  COMPOUND,
  INTARRAY;


  public static NBTTagCompound jsonToCompound(JsonObject data) {

    NBTTagCompound toReturn = new NBTTagCompound();

    for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
      JsonObject obj = entry.getValue().getAsJsonObject();
      JsonNBT type = getType(obj.get("type").getAsString());
      setCompoundEntry(toReturn, entry.getKey(), type, obj.get("value"));
    }

    return toReturn;
  }

  public static NBTTagList jsonToList(JsonObject data) {
    NBTTagList toReturn = new NBTTagList();
    JsonNBT type = JsonNBT.valueOf(data.get("type").getAsString());
    for (JsonElement e : data.get("value").getAsJsonArray()) {
      append(toReturn, type, e);
    }

    return toReturn;
  }

  public static JsonNBT getType(String type) {
    return JsonNBT.valueOf(type);
  }

  public static void setCompoundEntry(NBTTagCompound nbt, String name, JsonNBT type, JsonElement data) {
    switch (type) {
      case END:
        return;
      case BYTE:
        nbt.setByte(name, data.getAsByte());
        return;
      case SHORT:
        nbt.setShort(name, data.getAsShort());
        return;
      case INT:
        nbt.setInteger(name, data.getAsInt());
        return;
      case LONG:
        nbt.setLong(name, data.getAsLong());
        return;
      case FLOAT:
        nbt.setFloat(name, data.getAsFloat());
        return;
      case DOUBLE:
        nbt.setDouble(name, data.getAsDouble());
        return;
      case BYTEARRAY:
        nbt.setByteArray(name, jsonToByteArray(data.getAsJsonObject()));
        return;
      case STRING:
        nbt.setString(name, data.getAsString());
        return;
      case LIST:
        nbt.setTag(name, jsonToList(data.getAsJsonObject()));
        return;
      case COMPOUND:
        nbt.setTag(name, jsonToCompound(data.getAsJsonObject()));
        return;
      case INTARRAY:
        nbt.setTag(name, new NBTTagIntArray(jsonToIntArray(data.getAsJsonObject())));
        return;
    }
  }

  public static void append(NBTTagList nbt, JsonNBT type, JsonElement data) {
    switch (type) {
      case END:
        return;
      case BYTE:
        nbt.appendTag(new NBTTagByte(data.getAsByte()));
        return;
      case SHORT:
        nbt.appendTag(new NBTTagShort(data.getAsShort()));
        return;
      case INT:
        nbt.appendTag(new NBTTagInt(data.getAsInt()));
        return;
      case LONG:
        nbt.appendTag(new NBTTagLong(data.getAsLong()));
        return;
      case FLOAT:
        nbt.appendTag(new NBTTagFloat(data.getAsFloat()));
        return;
      case DOUBLE:
        nbt.appendTag(new NBTTagDouble(data.getAsDouble()));
        return;
      case BYTEARRAY:
        nbt.appendTag(new NBTTagByteArray(jsonToByteArray(data.getAsJsonObject())));
        return;
      case STRING:
        nbt.appendTag(new NBTTagString(data.getAsString()));
        return;
      case LIST:
        nbt.appendTag(jsonToList(data.getAsJsonObject()));
        return;
      case COMPOUND:
        nbt.appendTag(jsonToCompound(data.getAsJsonObject()));
        return;
      case INTARRAY:
        nbt.appendTag(new NBTTagIntArray(jsonToIntArray(data.getAsJsonObject())));
        return;
    }
  }

  public static byte[] jsonToByteArray(JsonObject data) {
    JsonArray arr = data.getAsJsonArray();

    byte[] bytes = new byte[arr.size()];

    int i = 0;
    for (JsonElement e : arr) {
      bytes[i] = e.getAsByte();
      i++;
    }

    return bytes;
  }

  public static int[] jsonToIntArray(JsonObject data) {
    JsonArray arr = data.getAsJsonArray();

    int[] ints = new int[arr.size()];

    int i = 0;
    for (JsonElement e : arr) {
      ints[i] = e.getAsInt();
      i++;
    }

    return ints;
  }
}
