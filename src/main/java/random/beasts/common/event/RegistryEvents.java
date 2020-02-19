package random.beasts.common.event;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import random.beasts.api.configuration.BeastsConfig;
import random.beasts.api.main.BeastsReference;
import random.beasts.api.main.BeastsRegistries;
import random.beasts.client.init.BeastsSounds;
import random.beasts.common.BeastsMod;
import random.beasts.common.init.*;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BeastsReference.ID)
public class RegistryEvents {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //resolving the class
        BeastsBlocks.COCONUT.getRegistryName();

        event.getRegistry().registerAll(BeastsRegistries.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //resolving the class
        BeastsItems.ICON.getRegistryName();

        event.getRegistry().registerAll(BeastsRegistries.ITEMS.toArray(new Item[0]));
        FurnaceRecipes.instance().addSmelting(BeastsItems.CRAB_LEG, new ItemStack(BeastsItems.COOKED_CRAB_LEG), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.EEL_CHOP, new ItemStack(BeastsItems.COOKED_EEL_CHOP), 0.50f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.SCALLOP_TONGUE, new ItemStack(BeastsItems.COOKED_SCALLOP_TONGUE), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.SHRIMP, new ItemStack(BeastsItems.COOKED_SHRIMP), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.RAW_KEBAB, new ItemStack(BeastsItems.COOKED_KEBAB), 0.35f);
        FurnaceRecipes.instance().addSmelting(BeastsItems.DAGGERFISH, new ItemStack(BeastsItems.COOKED_DAGGERFISH), 0.50f);

        BeastsEntities.registerEggs();
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        //resolving the class
        BeastsBiomes.DRIED_REEF.getRegistryName();

        event.getRegistry().registerAll(BeastsRegistries.BIOMES.toArray(new Biome[0]));
        BeastsBiomes.addTypes(BeastsBiomes.DRIED_REEF, BiomeManager.BiomeType.WARM, BeastsConfig.reefWeight, BEACH, HOT, DRY, SANDY);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(BeastsSounds.LIST.toArray(new SoundEvent[0]));
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(BeastsEntities.ENTRIES.values().stream().map(BeastsEntities.EntityType::getFinal).toArray(EntityEntry[]::new));
        BeastsEntities.SPAWNS.forEach((type, spawns) -> {
            if (type.getFinal().getEntityClass().isAssignableFrom(EntityLiving.class)) {
                Class<? extends EntityLiving> cls = (Class<? extends EntityLiving>) type.getFinal().getEntityClass();
                for (BeastsEntities.SpawnEntry spawn : spawns) {
                    Biome.SpawnListEntry entry = new Biome.SpawnListEntry(cls, spawn.weight, spawn.min, spawn.max);
                    for (Biome biome : spawn.biomes) {
                        biome.getSpawnableList(type.getClassification()).add(entry);
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().registerAll(BeastsRecipes.LIST.toArray(new IRecipe[0]));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        BeastsMod.proxy.registerEventRenders();
    }
}
