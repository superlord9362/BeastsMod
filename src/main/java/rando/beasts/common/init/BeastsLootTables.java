package rando.beasts.common.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import rando.beasts.common.main.BeastsReference;
import rando.beasts.common.world.storage.loot.BeastsLootTable;

import java.util.HashMap;
import java.util.Map;

public class BeastsLootTables {
	public static final Map<String, BeastsLootTable> TABLES = new HashMap<>();
	private static final BeastsLootTable FISH = new BeastsLootTable("inject/fish", "gameplay/fishing", () -> getInjectEntry("fish", 100));
	
	public static void init() {
		for(BeastsLootTable table : TABLES.values()) LootTableList.register(table.id);
	}

	private static LootPool getInjectPool(String entryName) {
		return new LootPool(new LootEntry[] { getInjectEntry(entryName, 1) }, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0), "beasts_inject_pool");
	}

	private static LootEntryTable getInjectEntry(String name, int weight) {
		return new LootEntryTable(new ResourceLocation(BeastsReference.ID, "inject/" + name), weight, 0, new LootCondition[0], "beasts_inject_entry");
	}
}