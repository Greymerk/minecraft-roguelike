package greymerk.roguelike.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockJumble extends BlockBase {

  private List<IBlockFactory> blocks;

  public BlockJumble() {
    blocks = new ArrayList<>();
  }

  public BlockJumble(JsonElement data) throws Exception {
    this();
    for (JsonElement entry : (JsonArray) data) {
      this.addBlock(BlockProvider.create(entry.getAsJsonObject()));
    }
  }

  public void addBlock(IBlockFactory toAdd) {
    blocks.add(toAdd);
  }

  @Override
  public boolean set(IWorldEditor editor, Random rand, Coord origin, boolean fillAir, boolean replaceSolid) {
    IBlockFactory block = blocks.get(rand.nextInt(blocks.size()));
    return block.set(editor, rand, origin, fillAir, replaceSolid);
  }

}
