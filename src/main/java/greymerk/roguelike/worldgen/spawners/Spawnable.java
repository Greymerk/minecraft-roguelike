package greymerk.roguelike.worldgen.spawners;

import com.google.gson.JsonElement;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;

public class Spawnable {

  private Spawner type;
  private List<SpawnPotential> potentials = new ArrayList<>();

  public Spawnable(Spawner type) {
    this.type = type;
  }

  public Spawnable(JsonElement data) throws Exception {
    for (JsonElement spawnPotential : data.getAsJsonArray()) {
      this.potentials.add(new SpawnPotential(spawnPotential.getAsJsonObject()));
    }
  }

  public void generate(IWorldEditor editor, Random rand, Coord cursor, int level) {
    Coord pos = new Coord(cursor);
    editor.setBlock(pos, new MetaBlock(Blocks.MOB_SPAWNER.getDefaultState()), true, true);

    TileEntity tileentity = editor.getTileEntity(pos);
    if (!(tileentity instanceof TileEntityMobSpawner)) {
      return;
    }

    TileEntityMobSpawner spawner = (TileEntityMobSpawner) tileentity;
    MobSpawnerBaseLogic spawnerLogic = spawner.getSpawnerBaseLogic();

    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("x", pos.getX());
    nbt.setInteger("y", pos.getY());
    nbt.setInteger("z", pos.getZ());

    nbt.setTag("SpawnPotentials", getSpawnPotentials(rand, level));

    spawnerLogic.readFromNBT(nbt);
    spawnerLogic.updateSpawner();
    tileentity.markDirty();
  }

  private NBTTagList getSpawnPotentials(Random rand, int level) {

    if (this.type != null) {
      SpawnPotential potential = new SpawnPotential(Spawner.getName(type));
      return potential.get(rand, level);
    }

    NBTTagList potentials = new NBTTagList();

    for (SpawnPotential potential : this.potentials) {
      NBTTagCompound nbt = potential.get(level);
      potentials.appendTag(nbt);
    }

    return potentials;
  }
}
