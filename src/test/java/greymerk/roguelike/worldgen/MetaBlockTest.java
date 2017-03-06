package greymerk.roguelike.worldgen;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;

public class MetaBlockTest {

	@Before
	public void setup(){
		Bootstrap.register();
	}
	
	@Test
	public void jsonArgs() {
		
		MetaBlock dirt = new MetaBlock(Blocks.DIRT);
		MetaBlock stone = new MetaBlock(Blocks.STONE);
		
		JsonParser parser = new JsonParser();
		JsonElement e = parser.parse("{\"name\": \"minecraft:dirt\"}");
		
		MetaBlock test = new MetaBlock(e);
		assert(test.equals(dirt));
		
		MetaBlock podzol = new MetaBlock(Blocks.DIRT);
		podzol.withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
		
		e = parser.parse("{\"name\": \"minecraft:dirt\", \"meta\": \"2\"}");
		test = new MetaBlock(e);
		
		assertTrue(!test.equals(stone));
		assertTrue(!test.equals(dirt));
		assertTrue(test.equals(podzol));
	}
	
	@Test
	public void equalsTest(){
		IBlockState dirtState = Blocks.DIRT.getDefaultState();
		
		MetaBlock dirt = new MetaBlock(Blocks.DIRT);
		MetaBlock stone = new MetaBlock(Blocks.STONE);		
		
		assert(dirt.equals(dirtState));
		
		assertTrue(!dirt.equals(stone));
		
		assertTrue(dirt.equals(new MetaBlock(Blocks.DIRT)));
		
		MetaBlock podzol = new MetaBlock(Blocks.DIRT);
		podzol.withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
		
		assertTrue(!dirt.equals(podzol));
	}
}
