package com.greymerk.roguelike.treasure.loot.trim;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

class TrimTest {

    @BeforeAll
    public static void init() throws Exception {

    	/*
    	Version v = ServerMetadata.Version.create();
    	
        Constructor<DetectedVersion> versionC = DetectedVersion.class.getDeclaredConstructor();
        versionC.setAccessible(true);
        DetectedVersion v = versionC.newInstance();
        
    	
        SharedConstants.setGameVersion(); // In order to Bootstrap correctly, you need to set the Game Version (MC has a built in way to do this)
		
        Bootstrap.bootStrap();
        */
    }
	
	@Test
	void testAdd() {
		//Bootstrap.initialize();
		ItemStack helmet = Items.GOLDEN_HELMET.getDefaultStack();
		TrimMaterial m = TrimMaterial.REDSTONE;
		TrimPattern p = TrimPattern.DUNE;
		Trim.add(helmet, p, m);
		NbtCompound nbt = helmet.getNbt();
		assert(nbt.contains("tag"));
		NbtCompound tag = nbt.getCompound("tag");
		assert(tag.contains("Trim"));
		NbtCompound trim = tag.getCompound("Trim");
		assert(trim.contains("material"));
		assert(trim.contains("pattern"));
		
		String mat = trim.getString("material");
		String pat = trim.getString("pattern");
		
		assert(mat.equals(TrimMaterial.getId(TrimMaterial.REDSTONE)));
		assert(pat.equals(TrimPattern.getId(TrimPattern.DUNE)));
	}

	@Test
	void testAddRandom() {
	}

}
