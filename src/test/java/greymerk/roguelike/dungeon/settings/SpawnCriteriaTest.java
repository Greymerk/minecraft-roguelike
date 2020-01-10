package greymerk.roguelike.dungeon.settings;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.minecraft.init.Bootstrap;
import net.minecraftforge.common.BiomeDictionary;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpawnCriteriaTest {

  @BeforeClass
  public static void beforeClass() {
    Bootstrap.register();
  }

  @Test
  public void overworld() {
    boolean valid;
    int dim = 0;
    List<Integer> wl = new ArrayList<>();
    wl.add(0);
    List<Integer> bl = new ArrayList<>();

    valid = SpawnCriteria.isValidDimension(dim, wl, bl);
    assertTrue(valid);

  }

  @Test
  public void notInNether() {

    int dim = -1;
    List<Integer> wl = new ArrayList<>();
    wl.add(0);
    List<Integer> bl = new ArrayList<>();

    assertFalse(SpawnCriteria.isValidDimension(dim, wl, bl));
  }

  @Test
  public void whiteListSeveral() {
    List<Integer> wl = new ArrayList<>();
    wl.add(5);
    wl.add(8);
    wl.add(12);
    List<Integer> bl = new ArrayList<>();

    assertFalse(SpawnCriteria.isValidDimension(0, wl, bl)); // not overworld
    assertFalse(SpawnCriteria.isValidDimension(-1, wl, bl)); // not nether
    assertFalse(SpawnCriteria.isValidDimension(1, wl, bl)); // not end
    assertFalse(SpawnCriteria.isValidDimension(15, wl, bl));
    assertTrue(SpawnCriteria.isValidDimension(5, wl, bl));
    assertTrue(SpawnCriteria.isValidDimension(8, wl, bl));
    assertTrue(SpawnCriteria.isValidDimension(12, wl, bl));
  }

  @Test
  public void isValid_ReturnsTrue_WhenBothBiomeCriteriaAndBiomeTypeCriteriaArePresentAndSatisfied() {
    SpawnContext mockSpawnContext = mock(SpawnContext.class);
    when(mockSpawnContext.includesBiome(any())).thenReturn(true);
    when(mockSpawnContext.includesBiomeType(any())).thenReturn(true);

    JsonArray criteriaBiomes = new JsonArray();
    criteriaBiomes.add(new JsonPrimitive("Ice Mountains"));

    JsonArray criteriaBiomeTypes = new JsonArray();
    criteriaBiomeTypes.add(new JsonPrimitive(BiomeDictionary.Type.SNOWY.toString()));

    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomes", criteriaBiomes);
    spawnCriteriaJson.add("biomeTypes", criteriaBiomeTypes);

    SpawnCriteria spawnCriteria = new SpawnCriteria(spawnCriteriaJson);

    assertThat(spawnCriteria.isValid(mockSpawnContext)).isTrue();
  }

  @Test
  public void isValid_ReturnsTrue_WhenOnlyBiomeCriteriaIsPresentAndSatisfied() {
    SpawnContext mockSpawnContext = mock(SpawnContext.class);
    when(mockSpawnContext.includesBiome(any())).thenReturn(true);
    when(mockSpawnContext.includesBiomeType(any())).thenReturn(false);

    JsonArray criteriaBiomes = new JsonArray();
    criteriaBiomes.add(new JsonPrimitive("Ice Mountains"));

    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomes", criteriaBiomes);

    SpawnCriteria spawnCriteria = new SpawnCriteria(spawnCriteriaJson);

    assertThat(spawnCriteria.isValid(mockSpawnContext)).isTrue();
  }

  @Test
  public void isValid_ReturnsFalse_WhenOnlyBiomeCriteriaIsPresentAndIsNotSatisfied() {
    SpawnContext mockSpawnContext = mock(SpawnContext.class);
    when(mockSpawnContext.includesBiome(any())).thenReturn(false);
    when(mockSpawnContext.includesBiomeType(any())).thenReturn(false);

    JsonArray criteriaBiomes = new JsonArray();
    criteriaBiomes.add(new JsonPrimitive("Ice Mountains"));

    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomes", criteriaBiomes);

    SpawnCriteria spawnCriteria = new SpawnCriteria(spawnCriteriaJson);

    assertThat(spawnCriteria.isValid(mockSpawnContext)).isFalse();
  }

  @Test
  public void isValid_ReturnsTrue_WhenOnlyBiomeTypeCriteriaIsProvidedAndSatisfied() {
    SpawnContext mockSpawnContext = mock(SpawnContext.class);
    when(mockSpawnContext.includesBiome(any())).thenReturn(false);
    when(mockSpawnContext.includesBiomeType(any())).thenReturn(true);

    JsonArray criteriaBiomeTypes = new JsonArray();
    criteriaBiomeTypes.add(new JsonPrimitive(BiomeDictionary.Type.SNOWY.toString()));

    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomeTypes", criteriaBiomeTypes);

    SpawnCriteria spawnCriteria = new SpawnCriteria(spawnCriteriaJson);

    assertThat(spawnCriteria.isValid(mockSpawnContext)).isTrue();
  }

  @Test
  public void isValid_ReturnsFalse_WhenOnlyBiomeTypeCriteriaIsProvidedAndIsNotSatisfied() {
    SpawnContext mockSpawnContext = mock(SpawnContext.class);
    when(mockSpawnContext.includesBiome(any())).thenReturn(false);
    when(mockSpawnContext.includesBiomeType(any())).thenReturn(false);

    JsonArray criteriaBiomeTypes = new JsonArray();
    criteriaBiomeTypes.add(new JsonPrimitive(BiomeDictionary.Type.SNOWY.toString()));

    JsonObject spawnCriteriaJson = new JsonObject();
    spawnCriteriaJson.add("biomeTypes", criteriaBiomeTypes);

    SpawnCriteria spawnCriteria = new SpawnCriteria(spawnCriteriaJson);

    assertThat(spawnCriteria.isValid(mockSpawnContext)).isFalse();
  }
}
