package greymerk.roguelike.treasure;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

import static java.lang.Math.*;

public class TreasureChest implements ITreasureChest {

  protected Inventory inventory;
  protected Treasure type;
  protected Random rand;
  private long seed;
  private TileEntityChest chest;
  private int level;

  public TreasureChest(Treasure type) {
    this.type = type;
    this.level = 0;
  }

  public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) throws ChestPlacementException {

    this.rand = rand;
    this.level = level;

    MetaBlock chestType = new MetaBlock(trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST);

    boolean success = chestType.set(editor, pos);

    if (!success) {
      throw new ChestPlacementException("Failed to place chest in world");
    }

    this.chest = (TileEntityChest) editor.getTileEntity(pos);
    this.inventory = new Inventory(rand, chest);
    this.seed = Objects.hash(pos.hashCode(), editor.getSeed());

    editor.addChest(this);
    return this;
  }

  @Override
  public boolean setSlot(int slot, ItemStack item) {
    return this.inventory.setInventorySlot(slot, item);
  }

  @Override
  public boolean setRandomEmptySlot(ItemStack item) {
    return this.inventory.setRandomEmptySlot(item);
  }

  @Override
  public boolean isEmptySlot(int slot) {
    return this.inventory.isEmptySlot(slot);
  }

  @Override
  public Treasure getType() {
    return this.type;
  }

  @Override
  public int getSize() {
    return this.inventory.getInventorySize();
  }

  @Override
  public int getLevel() {
    return max(0, min(level, 4));
  }

  @Override
  public void setLootTable(ResourceLocation table) {
    this.chest.setLootTable(table, seed);
  }
}
